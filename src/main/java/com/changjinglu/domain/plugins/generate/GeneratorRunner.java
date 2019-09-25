package com.changjinglu.domain.plugins.generate;

import com.changjinglu.domain.plugins.Holder;
import com.changjinglu.domain.plugins.LocaleContextHolder;
import com.changjinglu.domain.plugins.entity.*;
import com.google.common.collect.Lists;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import java.io.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import static com.changjinglu.domain.plugins.util.KeyUtil.createKey;

/**
 * <p>GeneratorRunner</p>
 * <p>代码生成执行线程</p>
 * @author fengxiao
 * @since 2019/9/24 22:55
 */
public class GeneratorRunner implements Runnable {
    private List<Table> tableList;
    private List<GeneratorFile> fileList;
    private GeneratorConfig config;
    private Logger log = Logger.getInstance(GeneratorRunner.class);

    private static final String JAVA_PATH = "/src/main/java/";
    private static final SimpleDateFormat DOC_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public GeneratorRunner(List<Table> tableList, List<GeneratorFile> fileList) {
        this.tableList = tableList;
        this.fileList = fileList;
        config = new GeneratorConfig();
        PropertiesComponent applicationProperties = Holder.getApplicationProperties();
        config.setAuthor(applicationProperties.getValue(createKey("author"), ""));
        config.setBasePackage(applicationProperties.getValue(createKey("basePackage"), ""));
        config.setEntityPrefixToBeRemoved(applicationProperties.getValue(createKey("removedEntityPrefix"), ""));
        config.setModuleName(applicationProperties.getValue(createKey("moduleName"), ""));
        config.setDateStr(DOC_DATE_FORMAT.format(new Date()));
        String excludeFields = applicationProperties.getValue(createKey("excludeFields"), "");
        if(StringUtils.isNotBlank(excludeFields)) {
            config.setExcludeFields(Lists.newArrayList(excludeFields.split(",")));
        }else {
            config.setExcludeFields(Lists.newArrayList());
        }
        config.setFileList(fileList);
    }

    @Override
    public void run() {
        fileList.forEach(file -> {
            if(file.isSelected()) {
                tableList.forEach(table -> {
                    if (table.isSelected()) {
                        initColumns(table);
                        fileGenerate(file, table);
                    }
                });
            }
        });
        ApplicationManager.getApplication()
                .invokeLater(() -> Notifications.Bus.notify(
                        new Notification("GeneratorRunner",
                                LocaleContextHolder.format("prompt"),
                                LocaleContextHolder.format("generate_success"),
                                NotificationType.INFORMATION)));
    }

    /**
     * 文件生成
     * @param generatorFile
     * @param table
     */
    private void fileGenerate(GeneratorFile generatorFile, Table table){
        try {
            //模板上下文
            VelocityContext context = new VelocityContext();
            //上下文中设置替换内容
            context.put("fileType", generatorFile.getType().getDesc());
            context.put("table", table);
            context.put("config", config);
            context.put("serialVersionUID", new Random().nextLong());
            //初始化参数
            Properties properties = new Properties();
            //设置velocity资源加载方式为class
            properties.setProperty("resource.loader", "class");
            //设置velocity资源加载方式为file时的处理类
            properties.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
            //实例化一个VelocityEngine对象
            VelocityEngine velocityEngine = new VelocityEngine(properties);

            String templateName = generatorFile.getType().getTemplateFile();
            //生成代码
            //生成的文件名
            int sepIndex = templateName.lastIndexOf("/");
            String fileName = templateName.substring(sepIndex < 0?0:(sepIndex+1), templateName.lastIndexOf(".vm"));
            String filePath = getPackagePath(generatorFile, Holder.getEvent().getProject()) + File.separator + fileName;
            filePath = filePath.replaceAll("Domain", table.getEntityClass());
            System.out.println("filePath=" + filePath);
            File file = new File(filePath);

            //判断是否覆盖存在的文件
            if (file.exists()) {
                return;
            }

            //获取父目录
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            Writer writer = new FileWriter(file);

            try (InputStream input = getClass().getClassLoader().getResourceAsStream("template" + File.separator + templateName)) {
                byte[] buffer = new byte[input.available()];
                input.read(buffer);
                velocityEngine.evaluate(context, writer, "repository", new String(buffer, "utf-8"));
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            writer.flush();
            writer.close();
        } catch (IOException e){
            ApplicationManager.getApplication()
                    .invokeLater(() -> Notifications.Bus.notify(new Notification("GeneratorRunner", "Error",
                            e.getMessage(),
                            NotificationType.ERROR)));
        }
    }

    /**
     * 获取代码所在包路径
     * @param generatorFile
     * @param project
     * @return
     */
    private String getPackagePath(GeneratorFile generatorFile, Project project){
        Module module = null;
        String moduleName = null;
        String basePath = null;
        String moduleType = null;
        switch (generatorFile.getType()){
            case CONTROLLER:
                moduleType = "application";
                break;
            case REPO_DO:
            case REPO_MANAGER:
            case REPO_MANAGER_IMPL:
            case REPO_MAPPER:
            case REPO_QUERY:
            case REPOSITORY_IMPL:
                moduleType = "repository";
                break;
            case DOMAIN_BUILD_VO:
            case DOMAIN_ENTITY:
            case DOMAIN_FACTORY:
            case DOMAIN_REPO_QUERY:
            case DOMAIN_REPOSITORY:
            case DOMAIN_VO:
                moduleType = "domain";
            case API_CREATE_PARAM:
            case API_DTO:
            case API_ID_PARAM:
            case API_INTERFACE:
            case API_PAGE_QUERY_PARAM:
            case API_QUERY_PARAM:
            case API_UPDATE_PARAM:
                moduleType = "api";
        }
        basePath = project.getBasePath();
        if(StringUtils.isNotBlank(config.getModuleName())) {
            moduleName = project.getName() + "-" + moduleType + "-" + config.getModuleName();
            module = ModuleManager.getInstance(project).findModuleByName(moduleName);
            if (module != null) {
                basePath += moduleName;
            }
        }
        if(module == null){
            moduleName = project.getName() + "-" + moduleType;
            module = ModuleManager.getInstance(project).findModuleByName(moduleName);
            if (module != null) {
                basePath += moduleName;
            }
        }
        return basePath + JAVA_PATH + config.getBasePackage().replaceAll("\\.", File.separator)
                + (StringUtils.isNotBlank(config.getModuleName()) ? (File.separator + config.getModuleName()) : "")
                + File.separator + generatorFile.getType().getPkg().replaceAll("\\.", File.separator);
    }

    /**
     * 查询数据库，初始化字段集合
     * @param table
     */
    private void initColumns(Table table){
        List<ColumnSchema> columnSchemaList;
        try {
            columnSchemaList = Holder.getDatabaseDrivers().getDriverAdapter()
                    .findTableSchemas(table.getTableSchema(), table.getTableName());
        } catch (SQLException se) {
            log.error("read table " + table.getTableName() + " schema failed", se);
            ApplicationManager.getApplication()
                    .invokeLater(() -> Notifications.Bus.notify(new Notification("GeneratorRunner", "Error",
                            se.getErrorCode() + "," + se.getSQLState() + "," + se.getLocalizedMessage(),
                            NotificationType.ERROR)));
            return;
        }
        // 解析字段列表
        List<Column> columnList = new ArrayList<>(columnSchemaList.size());
        for (ColumnSchema columnSchema : columnSchemaList) {
            Column column = Holder.getDatabaseDrivers().getDriverAdapter()
                    .parseToColumn(columnSchema, config.getEntityPrefixToBeRemoved(), true, true);
            if (column.isPrimary()) {
                table.setPrimaryKeyClassType(column.getJavaDataType());
            }
            if (!config.getExcludeFields().contains(column.getFieldName())) {
                columnList.add(column);
            }
        }
        table.setColumns(columnList);
    }
}
