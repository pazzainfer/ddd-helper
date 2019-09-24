package com.changjinglu.domain.plugins.generate;

import com.changjinglu.domain.plugins.entity.GeneratorFile;
import com.changjinglu.domain.plugins.entity.Table;
import java.util.List;
/**
 * <p>GeneratorRunner</p>
 * <p>代码生成执行线程</p>
 * @author fengxiao
 * @since 2019/9/24 22:55
 */
public class GeneratorRunner implements Runnable {
    private List<Table> tableList;
    private List<GeneratorFile> fileList;

    public GeneratorRunner(List<Table> tableList, List<GeneratorFile> fileList) {
        this.tableList = tableList;
        this.fileList = fileList;
    }

    @Override
    public void run() {
        fileList.forEach(file -> {

        });
    }
}
