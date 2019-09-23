package com.changjinglu.domain.plugins.adapter;

import com.changjinglu.domain.plugins.entity.Column;
import com.changjinglu.domain.plugins.entity.ColumnSchema;
import com.changjinglu.domain.plugins.entity.TableSchema;

import java.sql.SQLException;
import java.util.List;

/**
 * <p> DriverAdapter </p>
 * <p> 驱动适配器 </p>
 *
 * @author fengxiao
 * @since 2019/9/23 4:55 下午
 */
public interface DriverAdapter {

    /**
     * 查询指定数据库的schema
     *
     * @param database database name
     */
    List<TableSchema> findDatabaseSchemas(String database) throws SQLException;

    /**
     * 查询指定数据库指定表格的schema
     *
     * @param database database name
     * @param table    table name
     */
    List<ColumnSchema> findTableSchemas(String database, String table) throws SQLException;

    /**
     * 解析拼装数据为{@link Column}
     *
     * @param columnSchema
     * @param removeFieldPrefix
     * @param useWrapper
     * @param useJava8DataType
     * @return
     */
    Column parseToColumn(ColumnSchema columnSchema, String removeFieldPrefix, boolean useWrapper,
                         boolean useJava8DataType);

    /**
     * 转换为 connection url
     *
     * @param oldConnectionUrl 原始的 connection url
     * @param host             host
     * @param port             port
     * @param username         username
     * @param database         database
     */
    String toConnectionUrl(String oldConnectionUrl, String host, String port, String username, String database);
}
