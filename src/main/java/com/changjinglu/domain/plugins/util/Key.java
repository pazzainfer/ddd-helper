package com.changjinglu.domain.plugins.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

import static java.util.stream.Collectors.joining;

/**
 * <p> Key </p>
 * <p> 插件国际化资源Key工具 </p>
 *
 * @author fengxiao
 * @since 2019/9/23 10:07 上午
 */
public enum Key {
    ;

    /**
     * 创建统一前缀的资源Key
     *
     * @param name
     * @return
     */
    public static String createKey(String name) {
        return "com.changjinglu.domain.plugins." + name;
    }

    public static String createKey(String name, Object... args) {
        String prefix = createKey(name);
        String suffix = Arrays.stream(args)
                .map(Object::toString)
                .collect(joining("_"));
        if (StringUtils.isEmpty(suffix)) {
            return prefix;
        } else {
            return prefix + "_" + suffix;
        }
    }
}
