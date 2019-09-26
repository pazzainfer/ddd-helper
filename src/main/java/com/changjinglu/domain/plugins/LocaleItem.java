package com.changjinglu.domain.plugins;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Locale;

/**
 * <p> LocaleItem </p>
 * <p> 本地存储数据项 </p>
 *
 * @author fengxiao
 * @since 2019/9/21 5:44 下午
 */
@Data
public class LocaleItem implements Serializable, Comparable<LocaleItem> {

    private static final long serialVersionUID = 4358892090079199203L;
    private final Locale locale;
    private final String value;

    public String getLanguageTag() {
        return locale.toLanguageTag();
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public int compareTo(@NotNull LocaleItem o) {
        return locale.toLanguageTag().compareTo(o.getLocale().toLanguageTag());
    }
}
