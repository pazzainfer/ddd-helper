package com.changjinglu.domain.plugins.entity;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * <p> TableSchema </p>
 * <p> 数据库Table表信息 </p>
 *
 * @author fengxiao
 * @since 2019/9/23 5:09 下午
 */
@Data
@javax.persistence.Table(name = "information_schema.TABLES")
public class TableSchema implements Serializable {

    private static final long serialVersionUID = 1853575310189734827L;
    @Id
    @javax.persistence.Column(name = "TABLE_NAME")
    private String tableName;

    @javax.persistence.Column(name = "TABLE_COMMENT")
    private String tableComment;

    @javax.persistence.Column(name = "TABLE_SCHEMA")
    private String tableSchema;
}
