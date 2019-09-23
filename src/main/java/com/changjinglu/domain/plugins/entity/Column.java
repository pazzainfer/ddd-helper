package com.changjinglu.domain.plugins.entity;

import lombok.Data;

/**
 * <p> Column </p>
 * <p> 数据库字段 </p>
 *
 * @author fengxiao
 * @since 2019/9/23 5:08 下午
 */
@Data
public class Column {

    /**
     * 数据库字段名
     */
    private String columnName;
    /**
     * 实体字段名
     */
    private String fieldName;
    /**
     * 字段顺序
     */
    private int sort;
    /**
     * 数据库数据类型
     */
    private String dbDataType;
    /**
     * Java数据类型
     */
    private Class<?> javaDataType;
    /**
     * 是否是主键
     */
    private boolean primary;
    /**
     * 是否允许为null
     */
    private boolean nullable;
    /**
     * 是否是自增字段
     */
    private boolean autoIncrement;
    /**
     * 是否有默认值
     */
    private boolean hasDefaultValue;
    /**
     * 默认值，如果是字符串则默认值是"默认值"
     */
    private String defaultValue;
    /**
     * 字段注释
     */
    private String columnComment;

}
