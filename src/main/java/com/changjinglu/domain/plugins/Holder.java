package com.changjinglu.domain.plugins;

import com.changjinglu.domain.plugins.adapter.DatabaseDrivers;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.AnActionEvent;
import fastjdbc.FastJdbc;

import java.io.IOException;
import java.util.Objects;

/**
 * <p> Holder </p>
 * <p> 对象暂存器 </p>
 *
 * @author fengxiao
 * @since 2019/9/23 5:17 下午
 */
public class Holder {

    private static volatile AnActionEvent eventHolder;
    private static volatile PropertiesComponent applicationProperties;
    private static volatile PropertiesComponent projectProperties;
    private static volatile DatabaseDrivers databaseDrivers;
    private static volatile FastJdbc fastJdbc;

    public static synchronized void registerEvent(AnActionEvent event) {
        eventHolder = event;
    }

    public static synchronized AnActionEvent getEvent() {
        Objects.requireNonNull(eventHolder);
        return eventHolder;
    }

    public static synchronized void registerApplicationProperties(PropertiesComponent propertiesComponent) {
        applicationProperties = propertiesComponent;
    }

    public static synchronized PropertiesComponent getApplicationProperties() {
        return applicationProperties;
    }

    public static synchronized void registerProjectProperties(PropertiesComponent propertiesComponent) {
        projectProperties = propertiesComponent;
    }

    public static synchronized PropertiesComponent getProjectProperties() {
        return projectProperties;
    }

    public static synchronized void registerDatabaseDrivers(DatabaseDrivers databaseDrivers) {
        Holder.databaseDrivers = databaseDrivers;
    }

    public static DatabaseDrivers getDatabaseDrivers() {
        return databaseDrivers;
    }

    public static synchronized void registerFastJdbc(FastJdbc fastJdbc) {
        if (Holder.fastJdbc != null) {
            try {
                Holder.fastJdbc.close();
            } catch (IOException e) {
                // ignore
            }
        }
        Holder.fastJdbc = fastJdbc;
    }

    public static synchronized FastJdbc getFastJdbc() {
        return fastJdbc;
    }
}
