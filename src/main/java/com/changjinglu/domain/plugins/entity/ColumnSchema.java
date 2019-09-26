package com.changjinglu.domain.plugins.entity;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * <p> ColumnSchema </p>
 * <p> 数据库字段表信息 </p>
 *
 * @author fengxiao
 * @since 2019/9/23 5:08 下午
 */
@Data
@javax.persistence.Table(name = "information_schema.COLUMNS")
public class ColumnSchema implements Serializable {

    private static final long serialVersionUID = -7523969607822355567L;
    @Id
    @javax.persistence.Column(name = "COLUMN_NAME")
    private String columnName;

    @javax.persistence.Column(name = "TABLE_SCHEMA")
    private String tableSchema;

    @javax.persistence.Column(name = "TABLE_NAME")
    private String tableName;

    @javax.persistence.Column(name = "ORDINAL_POSITION")
    private int ordinalPosition;

    @javax.persistence.Column(name = "DATA_TYPE")
    private String dataType;

    @javax.persistence.Column(name = "COLUMN_TYPE")
    private String columnType;

    @javax.persistence.Column(name = "EXTRA")
    private String extra;

    @javax.persistence.Column(name = "COLUMN_COMMENT")
    private String columnComment;

    @javax.persistence.Column(name = "IS_NULLABLE")
    private String isNullable;

    @javax.persistence.Column(name = "COLUMN_DEFAULT")
    private String columnDefault;

    @javax.persistence.Column(name = "COLUMN_KEY")
    private String columnKey;

}
