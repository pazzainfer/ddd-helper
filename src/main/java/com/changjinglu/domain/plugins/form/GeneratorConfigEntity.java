package com.changjinglu.domain.plugins.form;

/**
 * <p>GeneratorConfigEntity</p>
 * <p>代码生成配置实体</p>
 *
 * @author fengxioa
 * @since 2019/9/1 0:21
 */
public class GeneratorConfigEntity {
    public GeneratorConfigEntity() {
    }

    ;

    public GeneratorConfigEntity(String dbType, String driver, String url, String username, String password, String[] tableNames) {
        this.dbType = dbType;
        this.driver = driver;
        this.url = url;
        this.username = username;
        this.password = password;
        this.tableNames = tableNames;
    }

    /**
     * 数据库类型
     */
    private String dbType = "MySQL";
    /**
     * 驱动地址
     */
    private String driver;
    /**
     * 数据库地址
     */
    private String url;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 表名数组
     */
    private String[] tableNames;

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String[] getTableNames() {
        return tableNames;
    }

    public void setTableNames(String[] tableNames) {
        this.tableNames = tableNames;
    }
}
