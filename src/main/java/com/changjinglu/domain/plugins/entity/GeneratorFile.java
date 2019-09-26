package com.changjinglu.domain.plugins.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p> Table </p>
 * <p> 配置的表模型 </p>
 *
 * @author fengxiao
 * @since 2019/9/23 5:09 下午
 */
@AllArgsConstructor
@Data
public class GeneratorFile {

    /**
     * 是否被选择，选择后才可生成类
     */
    private boolean selected;

    /**
     * 文件类型
     */
    private FileType type;

    /**
     * 通过枚举对象，构建构建集合
     * @return
     */
    public static List<GeneratorFile> buildByEnum(){
        List<GeneratorFile> list = new ArrayList<>();
        Arrays.stream(FileType.values()).forEach(t -> list.add(new GeneratorFile(true, t)));
        return list;
    }
}
