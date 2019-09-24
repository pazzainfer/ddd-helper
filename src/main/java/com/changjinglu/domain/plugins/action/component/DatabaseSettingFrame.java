package com.changjinglu.domain.plugins.action.component;

import com.changjinglu.domain.plugins.Holder;
import com.changjinglu.domain.plugins.LocaleContextHolder;
import com.changjinglu.domain.plugins.LocaleItem;
import com.changjinglu.domain.plugins.adapter.DatabaseDrivers;
import com.changjinglu.domain.plugins.adapter.DriverDelegate;
import com.changjinglu.domain.plugins.entity.Table;
import com.changjinglu.domain.plugins.entity.TableSchema;
import com.changjinglu.domain.plugins.util.StringHelper;
import com.changjinglu.domain.plugins.util.WindowUtil;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.ui.Messages;
import com.intellij.util.lang.UrlClassLoader;
import fastjdbc.FastJdbc;
import fastjdbc.NoPoolDataSource;
import fastjdbc.SimpleFastJdbc;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static com.changjinglu.domain.plugins.util.KeyUtil.createKey;
import static org.apache.commons.lang3.StringUtils.trim;

/**
 * <p> DatabaseSettingFrame </p>
 * <p> 数据库设置窗口 </p>
 * @author fengxiao
 * @since 2019/9/24 10:20 上午
 */
public class DatabaseSettingFrame extends JFrame {

    private Logger log = Logger.getInstance(DatabaseSettingFrame.class);
    /** 原子的类加载器 */
    public static AtomicReference<ClassLoader> classLoaderRef = new AtomicReference<>(DatabaseSettingFrame.class.getClassLoader());
    /** 数据库配置form */
    private DatabaseSettings databaseSettings;

    public DatabaseSettingFrame() throws HeadlessException {
        initI18n();
        this.setMinimumSize(new Dimension(500, 400));
        databaseSettings = new DatabaseSettings();
        this.setContentPane(databaseSettings.getRootComponent());
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(WindowUtil.getParentWindow(Holder.getEvent().getProject()));
        this.pack();
        initFormField(databaseSettings);
        databaseSettings.getTextHost().getDocument().addDocumentListener(new ConnectionUrlUpdateListener());
        databaseSettings.getTextPort().getDocument().addDocumentListener(new ConnectionUrlUpdateListener());
        databaseSettings.getTextUsername().getDocument().addDocumentListener(new ConnectionUrlUpdateListener());
        databaseSettings.getTextDatabase().getDocument().addDocumentListener(new ConnectionUrlUpdateListener());
        databaseSettings.getUseSSL().addChangeListener(e -> DatabaseSettingFrame.this.updateConnectionUrl(false));
        this.setVisible(true);

        // 注册取消事件
        databaseSettings.getBtnCancel().addActionListener(event -> this.dispose());
        // 注册语言切换事件
        databaseSettings.getCbxSelectLanguage().addItemListener(itemEvent -> {
            if (itemEvent.getStateChange() != ItemEvent.SELECTED) {
                return;
            }
            LocaleContextHolder.setCurrentLocale(((LocaleItem) itemEvent.getItem()).getLocale());
            WriteCommandAction.runWriteCommandAction(Holder.getEvent().getProject(), () -> {
                PropertiesComponent applicationProperties = Holder.getApplicationProperties();
                applicationProperties
                        .setValue(createKey("locale"),
                                ((LocaleItem) databaseSettings.getCbxSelectLanguage().getSelectedItem()).getLanguageTag());
            });
        });
        // 注册数据库类型切换事件
        databaseSettings.getCbxSelectDatabase().addItemListener(itemEvent -> {
            if (itemEvent.getStateChange() != ItemEvent.SELECTED) {
                return;
            }
            new DownloadDriverRunnable(DatabaseSettingFrame.this,
                    Holder.getEvent().getProject(),
                    null,
                    (DatabaseDrivers) itemEvent.getItem())
                    .run();
        });
        // 注册下一步事件
        databaseSettings.getBtnNext().addActionListener(event -> {
            String host = databaseSettings.getTextHost().getText().trim();
            if (host.isEmpty()) {
                databaseSettings.getTextHost().requestFocus();
                return;
            }
            String port = databaseSettings.getTextPort().getText().trim();
            if (port.isEmpty()) {
                databaseSettings.getTextPort().requestFocus();
                return;
            }
            String username = databaseSettings.getTextUsername().getText().trim();
            if (username.isEmpty()) {
                databaseSettings.getTextUsername().requestFocus();
                return;
            }
            databaseSettings.getTextPassword().getPassword();
            String password = new String(databaseSettings.getTextPassword().getPassword()).trim();
            if (password.isEmpty()) {
                databaseSettings.getTextPassword().requestFocus();
                return;
            }
            String database = databaseSettings.getTextDatabase().getText().trim();
            if (database.isEmpty()) {
                databaseSettings.getTextDatabase().requestFocus();
                return;
            }

            String connectionUrl = databaseSettings.getTextConnectionUrl().getText().trim();
            if (connectionUrl.isEmpty()) {
                databaseSettings.getTextConnectionUrl().requestFocus();
                return;
            }
            saveFormField(host, port, username, password, database, connectionUrl);
            new Thread(() -> {
                try {
                    if (!driverHasBeenLoaded(Holder.getDatabaseDrivers())) {
                        try {
                            Class.forName(Holder.getDatabaseDrivers().getDriverClass(), true, classLoaderRef.get());
                        } catch (ClassNotFoundException ex) {
                            // driver not loaded
                            ApplicationManager.getApplication().invokeAndWait(() -> {
                                int selectButton = Messages.showOkCancelDialog(Holder.getEvent().getProject(),
                                        LocaleContextHolder.format("driver_not_found", Holder.getDatabaseDrivers().getDriverClass()),
                                        Holder.getApplicationProperties().getValue("button_ok", "Ok"),
                                        Holder.getApplicationProperties().getValue("button_cancel", "Cancel"),
                                        LocaleContextHolder.format("prompt"),
                                        Messages.getQuestionIcon());
                                if (selectButton == Messages.OK) {
                                    new DownloadDriverRunnable(DatabaseSettingFrame.this,
                                            Holder.getEvent().getProject(),
                                            null,
                                            Holder.getDatabaseDrivers()).run();
                                }
                            });
                        }
                        Driver driver = (Driver) Class
                                .forName(Holder.getDatabaseDrivers().getDriverClass(), true, classLoaderRef.get())
                                .newInstance();
                        DriverManager.registerDriver(new DriverDelegate(driver));
                    }
                } catch (ReflectiveOperationException | SQLException e1) {
                    ApplicationManager.getApplication().invokeLater(() -> Notifications.Bus
                            .notify(new Notification("DatabaseSettingFrame", "Error",
                                    LocaleContextHolder.format("database_not_exists", Holder.getDatabaseDrivers().getDriverClass()),
                                    NotificationType.ERROR)));
                    return;
                }
                // 尝试获取连接
                try (Connection connection = DriverManager.getConnection(connectionUrl, username, password)) {
                    FastJdbc fastJdbc = new SimpleFastJdbc(new NoPoolDataSource(connectionUrl, username, password));
                    Holder.registerFastJdbc(fastJdbc);
                } catch (SQLException se) {
                    ApplicationManager.getApplication().invokeLater(() -> Notifications.Bus
                            .notify(new Notification("DatabaseSettingFrame", "Error",
                                    LocaleContextHolder.format("connect_to_database_failed",
                                            se.getErrorCode(), se.getSQLState(), se.getLocalizedMessage()), NotificationType.ERROR)));
                    log.error("connect to database failed", se);
                    return;
                }

                List<TableSchema> tableSchemaList = null;
                try {
                    tableSchemaList = Holder.getDatabaseDrivers().getDriverAdapter().findDatabaseSchemas(database);
                } catch (SQLException se) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("SQL error code: ").append(se.getErrorCode())
                            .append("\nSQL error state: ").append(se.getSQLState()).append("\n");
                    sb.append("Error message: ").append(se.getLocalizedMessage());
                    ApplicationManager.getApplication()
                            .invokeLater(() -> Notifications.Bus.notify(new Notification("DatabaseSettingFrame", "Error",
                                    sb.toString(), NotificationType.ERROR)));
                }

                // 显示选择数据库表窗口
                showSelectTablesFrame(tableSchemaList);
                // 释放数据库设置窗口
                this.dispose();
            }).start();
        });
    }

    /**
     * 打开选择数据库表窗口
     * @param tableSchemaList
     */
    private void showSelectTablesFrame(List<TableSchema> tableSchemaList){
        List<Table> tableList = new ArrayList<>(tableSchemaList.size());
        for (TableSchema tableSchema : tableSchemaList) {
            String tableName = tableSchema.getTableName();
            /*if (!config.getRemoveTablePrefix().isEmpty() && tableName.startsWith(config.getRemoveTablePrefix())) {
                tableName = tableName.substring(config.getRemoveTablePrefix().length());
            }*/
            String entityName = StringHelper.parseEntityName(tableName);
            tableList.add(Table.from(tableSchema, entityName, true));
        }
        new SelectTablesFrame(tableList);
    }
    /**
     * 更新设置驱动URL
     * @param switchDatabaseType
     */
    public void updateConnectionUrl(boolean switchDatabaseType) {
        String oldConnectionUrl = trim(databaseSettings.getTextConnectionUrl().getText());
        if (switchDatabaseType) {
            oldConnectionUrl = "";
        }
        String newConnectionUrl = Holder.getDatabaseDrivers().getDriverAdapter().toConnectionUrl(
                oldConnectionUrl,
                trim(databaseSettings.getTextHost().getText()),
                trim(databaseSettings.getTextPort().getText()),
                trim(databaseSettings.getTextUsername().getText()),
                trim(databaseSettings.getTextDatabase().getText()),
                databaseSettings.getUseSSL().isSelected());
        databaseSettings.getTextConnectionUrl().setText(newConnectionUrl);
    }

    /**
     * 保存表单数据
     * @param host
     * @param port
     * @param username
     * @param password
     * @param database
     * @param connectionUrl
     */
    private void saveFormField(String host, String port, String username, String password, String database,
                               String connectionUrl) {
        PropertiesComponent applicationProperties = Holder.getApplicationProperties();
        applicationProperties.setValue(createKey("host"), host);
        applicationProperties.setValue(createKey("port"), port);
        applicationProperties.setValue(createKey("username"), username);
        applicationProperties
                .setValue(createKey("password"), Base64.getEncoder().encodeToString(password.getBytes(StandardCharsets.UTF_8)));
        applicationProperties.setValue(createKey("database"), database);
        applicationProperties.setValue(createKey("url"), connectionUrl);
        applicationProperties
                .setValue(createKey("locale"),
                        ((LocaleItem) databaseSettings.getCbxSelectLanguage().getSelectedItem()).getLanguageTag());
        applicationProperties.setValue(createKey("database_type"),
                ((DatabaseDrivers) databaseSettings.getCbxSelectDatabase().getSelectedItem()).toString());
    }

    /**
     * 初始化表单数据展示
     * @param databaseSettings
     */
    private void initFormField(DatabaseSettings databaseSettings) {
        PropertiesComponent applicationProperties = Holder.getApplicationProperties();
        databaseSettings.getTextHost().setText(applicationProperties.getValue(createKey("host"), "localhost"));
        databaseSettings.getTextPort().setText(applicationProperties.getValue(createKey("port"), "3306"));
        databaseSettings.getTextUsername().setText(applicationProperties.getValue(createKey("username"), "root"));
        databaseSettings.getTextPassword()
                .setText(new String(Base64.getDecoder().decode(applicationProperties.getValue(createKey("password"), "")),
                        StandardCharsets.UTF_8));
        databaseSettings.getTextDatabase().setText(applicationProperties.getValue(createKey("database"), ""));
        databaseSettings.getUseSSL().setSelected(applicationProperties.getBoolean(createKey("useSSL"), false));
        databaseSettings.getTextConnectionUrl().setText(applicationProperties.getValue(createKey("url"), ""));

        // select language
        Locale locale = LocaleContextHolder.getCurrentLocale();
        databaseSettings.getCbxSelectLanguage().removeAllItems();
        for (LocaleItem localeItem : LocaleContextHolder.LOCALE_ITEMS) {
            databaseSettings.getCbxSelectLanguage().addItem(localeItem);
            if (locale.equals(localeItem.getLocale())) {
                databaseSettings.getCbxSelectLanguage().setSelectedItem(localeItem);
            }
        }

        // select database
        databaseSettings.getCbxSelectDatabase().removeAllItems();
        String databaseType = applicationProperties
                .getValue(createKey("database_type"), DatabaseDrivers.MYSQL.toString());
        for (DatabaseDrivers databaseDrivers : DatabaseDrivers.values()) {
            databaseSettings.getCbxSelectDatabase().addItem(databaseDrivers);
            if (databaseDrivers.toString().equalsIgnoreCase(databaseType)) {
                databaseSettings.getCbxSelectDatabase().setSelectedItem(databaseDrivers);
                Holder.registerDatabaseDrivers(databaseDrivers);
            }
        }

        // update connection url
        updateConnectionUrl(false);

        DatabaseDrivers databaseDrivers = Holder.getDatabaseDrivers();
        // load driver path
        String databaseDriverPath = applicationProperties
                .getValue(createKey("database_driver_path", databaseDrivers.getType(), databaseDrivers.getVersion()));
        if (StringUtils.isNotBlank(databaseDriverPath)) {
            try {
                classLoaderRef.set(UrlClassLoader.build()
                        .urls(new File(databaseDriverPath).toURI().toURL())
                        .get());
            } catch (MalformedURLException e) {
                log.error("url not valid " + databaseDrivers.getDriverClass(), e);
                ApplicationManager.getApplication().invokeLater(() -> Notifications.Bus.notify(
                        new Notification("DatabaseSettingFrame", "Error",
                                "url not valid " + databaseDrivers.getDriverClass(),
                                NotificationType.ERROR)));
            }
        }
    }


    /**
     * 驱动类是否已装载
     * @param databaseDrivers
     * @return
     */
    public boolean driverHasBeenLoaded(DatabaseDrivers databaseDrivers) {
        Enumeration<Driver> driverEnumeration = DriverManager.getDrivers();
        while (driverEnumeration.hasMoreElements()) {
            Driver driver = driverEnumeration.nextElement();
            String driverName = driver.getClass().getName();
            if (driver instanceof DriverDelegate) {
                driverName = ((DriverDelegate) driver).getDriver().getClass().getName();
            }
            if (driverName.equals(databaseDrivers.getDriverClass())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 初始化i18n国际化配置
     */
    private void initI18n() {
        PropertiesComponent applicationProperties = Holder.getApplicationProperties();
        // select language
        Locale locale = Locale.forLanguageTag(applicationProperties
                .getValue(createKey("locale"), LocaleContextHolder.getCurrentLocale().toLanguageTag()));
        int localeSelectIndex = -1;
        for (int i = 0; i < LocaleContextHolder.LOCALE_ITEMS.length; i++) {
            LocaleItem localeItem = LocaleContextHolder.LOCALE_ITEMS[i];
            if (localeItem.getLocale().equals(locale)) {
                localeSelectIndex = i;
                break;
            }
        }
        // only compare by language
        if (localeSelectIndex == -1) {
            for (int i = 0; i < LocaleContextHolder.LOCALE_ITEMS.length; i++) {
                LocaleItem localeItem = LocaleContextHolder.LOCALE_ITEMS[i];
                if (localeItem.getLocale().getLanguage().equalsIgnoreCase(locale.getLanguage())) {
                    localeSelectIndex = i;
                    break;
                }
            }
        }
        // not best match language for this locale, reset locale to english
        if (localeSelectIndex == -1) {
            localeSelectIndex = 0;
        }
        LocaleContextHolder.setCurrentLocale(LocaleContextHolder.LOCALE_ITEMS[localeSelectIndex].getLocale());
    }

    /**
     * 连接URL变更处理监听器
     */
    private class ConnectionUrlUpdateListener implements DocumentListener {
        private final boolean switchDatabaseType;

        public ConnectionUrlUpdateListener() {
            this(false);
        }

        public ConnectionUrlUpdateListener(boolean switchDatabaseType) {
            this.switchDatabaseType = switchDatabaseType;
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            DatabaseSettingFrame.this.updateConnectionUrl(switchDatabaseType);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            DatabaseSettingFrame.this.updateConnectionUrl(switchDatabaseType);
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            DatabaseSettingFrame.this.updateConnectionUrl(switchDatabaseType);
        }
    }


}
