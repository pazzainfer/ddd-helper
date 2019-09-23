package com.changjinglu.domain.plugins.entity;

import lombok.Data;

import java.util.List;

/**
 * <p> Table </p>
 * <p> 数据库表 </p>
 *
 * @author fengxiao
 * @since 2019/9/23 5:09 下午
 */
@Data
public class Table {

    /**
     * 是否被选择，选择后才可生成类
     */
    private boolean selected;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 表注释
     */
    private String tableComment;

    /**
     * 数据库名称
     */
    private String tableSchema;

    /**
     * 实体名称
     */
    private String entityName;

    /**
     * 包名称
     */
    private String packageName;

    /**
     * 主键类型
     */
    private Class<?> primaryKeyClassType;

    private List<Column> columns;

    public static Table from(TableSchema tableSchema, String entityName, boolean selected) {
        Table table = new Table();
        table.setTableName(tableSchema.getTableName());
        table.setTableComment(tableSchema.getTableComment());
        table.setTableSchema(tableSchema.getTableSchema());
        table.setEntityName(entityName);
        table.setSelected(selected);
        return table;
    }
}