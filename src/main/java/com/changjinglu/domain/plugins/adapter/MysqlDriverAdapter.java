package com.changjinglu.domain.plugins.adapter;

import com.changjinglu.domain.plugins.Holder;
import com.changjinglu.domain.plugins.entity.Column;
import com.changjinglu.domain.plugins.entity.ColumnSchema;
import com.changjinglu.domain.plugins.entity.TableSchema;
import com.changjinglu.domain.plugins.util.ColumnUtil;
import fastjdbc.Sql;
import fastjdbc.SqlBuilder;
import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;
import java.util.List;

/**
 * <p> MysqlDriverAdapter </p>
 * <p> MySQL驱动适配器实现 </p>
 *
 * @author fengxiao
 * @since 2019/9/23 5:07 下午
 */
public class MysqlDriverAdapter extends AbstractDriverAdapter {

    @Override
    protected String toConnectionUrl(String oldConnectionUrl, String host, String port, String username, String database,
                                     String params) {
        String connectionUrl = "jdbc:mysql://" + host + ":" + port + "/" + database;
        if (StringUtils.isNotBlank(params)) {
            connectionUrl += "?" + params;
        }
        return connectionUrl;
    }

    @Override
    public List<TableSchema> findDatabaseSchemas(String database) throws SQLException {
        Sql sql = SqlBuilder.newSelectBuilder(TableSchema.class)
                .select()
                .from()
                .where()
                .equal("tableSchema", database)
                .build();
        return Holder.getFastJdbc().find(sql.getSql(), TableSchema.class, sql.getArgs().toArray());
    }

    @Override
    public List<ColumnSchema> findTableSchemas(String database, String table) throws SQLException {
        Sql sql = SqlBuilder.newSelectBuilder(ColumnSchema.class)
                .select()
                .from()
                .where()
                .equal("tableSchema", database)
                .and().equal("tableName", table)
                .build();
        return Holder.getFastJdbc().find(sql.getSql(), ColumnSchema.class, sql.getArgs().toArray());
    }

    @Override
    public Column parseToColumn(ColumnSchema columnSchema, String removeFieldPrefix, boolean useWrapper,
                                boolean useJava8DataType) {
        Column column = new Column();
        column.setColumnName(columnSchema.getColumnName());
        column.setSort(columnSchema.getOrdinalPosition());
        column.setDbDataType(columnSchema.getDataType());
        column.setPrimary("PRI".equalsIgnoreCase(columnSchema.getColumnKey()));
        column.setNullable(!"NO".equalsIgnoreCase(columnSchema.getIsNullable()));
        column.setAutoIncrement(columnSchema.getExtra().contains("auto_increment"));
        column.setColumnComment(columnSchema.getColumnComment());
        column.setDefaultValue(columnSchema.getColumnDefault());
        ColumnUtil.parseColumn(this, column, removeFieldPrefix, useWrapper, useJava8DataType);
        return column;
    }
}
