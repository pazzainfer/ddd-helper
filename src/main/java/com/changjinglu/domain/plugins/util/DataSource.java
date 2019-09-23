package com.changjinglu.domain.plugins.util;

/**
 * <p> DataSource </p>
 * <p> 数据源枚举 </p>
 *
 * @author fengxiao
 * @since 2019/9/23 4:23 下午
 */
public enum DataSource {
    /**
     * MySQL
     */
    MYSQL("mysql"),
    ORACLE("oracle"),
    SQL_SERVER("sqlserver"),
    POSTGRE_SQL("postgre sql");
    private final String alias;

    DataSource(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }
}