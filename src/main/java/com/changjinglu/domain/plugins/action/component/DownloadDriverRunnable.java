package com.changjinglu.domain.plugins.action.component;

import com.changjinglu.domain.plugins.Holder;
import com.changjinglu.domain.plugins.adapter.DatabaseDrivers;
import com.changjinglu.domain.plugins.adapter.DriverDelegate;
import com.intellij.ide.util.DirectoryUtil;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.util.download.DownloadableFileDescription;
import com.intellij.util.download.DownloadableFileService;
import com.intellij.util.download.FileDownloader;
import com.intellij.util.lang.UrlClassLoader;

import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static com.changjinglu.domain.plugins.util.KeyUtil.createKey;
/**
 * <p> DownloadDriverRunnable </p>
 * <p> 驱动包下载线程 </p>
 * @since 2019/9/25 9:13 上午
 * @author fengxiao
 */
public class DownloadDriverRunnable implements Runnable {
    /**
     * 驱动所在位置
     */
    private static final String DRIVER_PATH = "ddd_driver";
    private final Project project;
    private final JComponent parentComponent;
    private final DatabaseDrivers databaseDrivers;
    private final DatabaseSettingFrame frame;

    private Logger log = Logger.getInstance(DownloadDriverRunnable.class);
    /** 原子的类加载器 */
    public static AtomicReference<ClassLoader> classLoaderRef = new AtomicReference<>(DatabaseSettingFrame.class.getClassLoader());

    public DownloadDriverRunnable(DatabaseSettingFrame frame,
                                   Project project,
                                   JComponent parentComponent,
                                   DatabaseDrivers databaseDrivers) {
        this.frame = frame;
        this.project = project;
        this.parentComponent = parentComponent;
        this.databaseDrivers = databaseDrivers;
    }

    @Override
    public void run() {
        WriteCommandAction.runWriteCommandAction(project, () -> {
            String dirPath = System.getProperty("user.dir") + File.separator + DRIVER_PATH;
            PsiDirectory driverTypePath = DirectoryUtil.mkdirs(PsiManager.getInstance(project), dirPath);
            PsiFile jarFile = driverTypePath.findFile(databaseDrivers.getJarFilename());
            // driver 不存在，需要下载
            if (jarFile == null) {
                if (databaseDrivers.getUrl().startsWith(DatabaseDrivers.CLASSPATH_PREFIX)) {
                    String filePath = databaseDrivers.getUrl().substring(DatabaseDrivers.CLASSPATH_PREFIX.length());
                    try (BufferedInputStream bis = new BufferedInputStream(
                            Optional.ofNullable(getClass().getClassLoader().getResourceAsStream(filePath))
                                    .orElseThrow(NoSuchElementException::new))) {
                        int available = bis.available();
                        byte[] bytes = new byte[available];
                        bis.read(bytes);
                        PsiFile file = driverTypePath.createFile(databaseDrivers.getJarFilename());
                        VirtualFile virtualFile = file.getVirtualFile();
                        virtualFile.setWritable(true);
                        virtualFile.setCharset(StandardCharsets.UTF_8);
                        virtualFile.setBinaryContent(bytes);
                        loadDriverClass(virtualFile, databaseDrivers);
                        return;
                    } catch (IOException e) {
                        log.error("copy file error, file path " + databaseDrivers.getUrl(), e);
                        ApplicationManager.getApplication().invokeLater(() -> Notifications.Bus.notify(
                                new Notification("DatabaseSettingFrame", "Error",
                                        "copy file error, file path " + databaseDrivers.getJarFilename(),
                                        NotificationType.ERROR)));
                    }
                }
                DownloadableFileService downloadableFileService = DownloadableFileService.getInstance();
                DownloadableFileDescription downloadableFileDescription = downloadableFileService
                        .createFileDescription(databaseDrivers.getUrl(), databaseDrivers.getJarFilename() + ".tmp");
                FileDownloader fileDownloader = downloadableFileService
                        .createDownloader(Collections.singletonList(downloadableFileDescription),
                                databaseDrivers.getJarFilename());
                List<VirtualFile> virtualFiles = fileDownloader
                        .downloadFilesWithProgress(driverTypePath.getVirtualFile().getPath(), project, parentComponent);
                if (virtualFiles == null) {
                    return;
                }
                try {
                    virtualFiles.get(0).rename(this, databaseDrivers.getJarFilename());
                } catch (IOException e1) {
                    log.error("rename driver error", e1);
                    ApplicationManager.getApplication().invokeLater(() -> Notifications.Bus.notify(
                            new Notification("DatabaseSettingFrame", "Error", "rename driver error " + databaseDrivers.getJarFilename(),
                                    NotificationType.ERROR))
                    );
                    return;
                }
                loadDriverClass(virtualFiles.get(0), databaseDrivers);
            } else {
                loadDriverClass(jarFile.getVirtualFile(), databaseDrivers);
            }
        });
    }

    /**
     * 装载jar包驱动类
     * @param virtualFile
     * @param databaseDrivers
     */
    private void loadDriverClass(VirtualFile virtualFile, DatabaseDrivers databaseDrivers) {
        if (frame.driverHasBeenLoaded(databaseDrivers)) {
            Holder.registerDatabaseDrivers(databaseDrivers);
            ApplicationManager.getApplication().invokeLater(() -> frame.updateConnectionUrl(true));
            return;
        }
        log.info("driver path" + virtualFile.getPath());
        try {
            UrlClassLoader urlClassLoader = UrlClassLoader.build()
                    .urls(new File(virtualFile.getPath()).toURI().toURL())
                    .get();
            Driver driver = (Driver) urlClassLoader.loadClass(databaseDrivers.getDriverClass()).newInstance();
            DriverManager.registerDriver(new DriverDelegate(driver));
            log.info("driver " + databaseDrivers.getDriverClass() + " has been loaded");
            classLoaderRef.set(urlClassLoader);
            Holder.registerDatabaseDrivers(databaseDrivers);
            ApplicationManager.getApplication().invokeLater(() -> frame.updateConnectionUrl(true));

            // save database driver path
            PropertiesComponent applicationProperties = Holder.getApplicationProperties();
            applicationProperties.setValue(
                    createKey("database_driver_path", databaseDrivers.getType(), databaseDrivers.getVersion()),
                    virtualFile.getPath());
        } catch (MalformedURLException ex) {
            log.error("url not valid " + databaseDrivers.getDriverClass(), ex);
            ApplicationManager.getApplication().invokeLater(() -> Notifications.Bus.notify(
                    new Notification("DatabaseSettingFrame", "Error",
                            "url not valid " + databaseDrivers.getDriverClass(),
                            NotificationType.ERROR)
            ));
        } catch (ReflectiveOperationException | SQLException ex) {
            log.error("driver class not found", ex);
            ApplicationManager.getApplication().invokeLater(() -> Notifications.Bus.notify(
                    new Notification("DatabaseSettingFrame", "Error",
                            "driver class not found: " + databaseDrivers.getDriverClass(), NotificationType.ERROR)
            ));
        }
    }

}