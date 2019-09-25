package com.changjinglu.domain.plugins.entity;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * <p> GeneratorConfig </p>
 * <p> 代码生成配置 </p>
 *
 * @author fengxiao
 * @since 2019/9/24 2:26 下午
 */
@Data
public class GeneratorConfig implements Serializable {
    private static final long serialVersionUID = -5510352536334672472L;
    /**
     * 作者名
     */
    private String author;
    /**
     * 日期字符串
     */
    private String dateStr;
    /**
     * 基础包名
     */
    private String basePackage;
    /**
     * 模块名称
     */
    private String moduleName;
    /**
     * 需要截断的实体名前缀(null代表不截断)
     */
    private String entityPrefixToBeRemoved;
    /**
     * 文件集合
     */
    private List<GeneratorFile> fileList;
    /**
     * 排除的字段集合
     */
    private List<String> excludeFields;
}
