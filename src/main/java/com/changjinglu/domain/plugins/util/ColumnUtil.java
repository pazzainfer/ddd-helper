package com.changjinglu.domain.plugins.util;

import com.changjinglu.domain.plugins.adapter.DriverAdapter;
import com.changjinglu.domain.plugins.entity.Column;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * <p> ColumnUtil </p>
 * <p> 数据库字段工具 </p>
 *
 * @author fengxiao
 * @since 2019/9/23 5:33 下午
 */
public class ColumnUtil {

    /**
     * 转化数据设置到字段对象中
     *
     * @param driverAdapter
     * @param column
     * @param removePrefix
     * @param useWrapper
     * @param useJava8DataType
     */
    public static void parseColumn(DriverAdapter driverAdapter, Column column, String removePrefix, boolean useWrapper,
                                   boolean useJava8DataType) {
        column.setFieldName(StringHelper.parseFieldName(column.getColumnName(), removePrefix));
        Class<?> javaDataType = StringHelper
                .parseJavaDataType(driverAdapter, column.getDbDataType(), column.getColumnName(), useWrapper, useJava8DataType);
        if ((javaDataType == Integer.class || javaDataType == int.class)
                && (column.getColumnComment().contains("true") || column.getColumnComment().contains("false"))) {
            if (useWrapper) {
                javaDataType = Boolean.class;
            } else {
                javaDataType = boolean.class;
            }
        }
        column.setJavaDataType(javaDataType);
        if (column.getDefaultValue() != null) {
            if (javaDataType == String.class) {
                column.setDefaultValue("\"" + column.getDefaultValue() + "\"");
            }
            Class<?> primitiveClass = StringHelper.getPrimitiveClass(javaDataType);
            if (primitiveClass == long.class) {
                column.setDefaultValue(column.getDefaultValue() + "L");
            }
            if (primitiveClass == float.class) {
                column.setDefaultValue(column.getDefaultValue() + "F");
            }
            if (primitiveClass == double.class) {
                column.setDefaultValue(column.getDefaultValue() + "D");
            }
            if (primitiveClass == boolean.class) {
                if (column.getDefaultValue().equals("1")) {
                    if (useWrapper) {
                        column.setDefaultValue("Boolean.TRUE");
                    } else {
                        column.setDefaultValue("true");
                    }
                } else {
                    if (useWrapper) {
                        column.setDefaultValue("Boolean.FALSE");
                    } else {
                        column.setDefaultValue("false");
                    }
                }
            }
            if (javaDataType == BigDecimal.class) {
                BigDecimal amount = new BigDecimal(column.getDefaultValue());
                if (amount.compareTo(BigDecimal.ZERO) == 0) {
                    column.setDefaultValue("BigDecimal.ZERO");
                } else if (amount.compareTo(BigDecimal.ONE) == 0) {
                    column.setDefaultValue("BigDecimal.ONE");
                } else if (amount.compareTo(BigDecimal.TEN) == 0) {
                    column.setDefaultValue("BigDecimal.TEN");
                } else {
                    column.setDefaultValue("new BigDecimal(\"" + column.getDefaultValue() + "\")");
                }
            }
            column.setHasDefaultValue(true);

            // 跳过设置 Date/Timestamp/LocalDate/LocalTime/LocalDateTime 的默认值
            if (javaDataType == java.sql.Date.class || javaDataType == Timestamp.class
                    || javaDataType == LocalDate.class || javaDataType == LocalTime.class
                    || javaDataType == LocalDateTime.class) {
                column.setDefaultValue(null);
                column.setHasDefaultValue(false);
            }
        }
    }
}
