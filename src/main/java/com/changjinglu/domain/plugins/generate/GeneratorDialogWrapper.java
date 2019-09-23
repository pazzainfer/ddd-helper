package com.changjinglu.domain.plugins.generate;

import com.alibaba.fastjson.JSON;
import com.changjinglu.domain.plugins.form.GeneratorConfigEntity;
import com.changjinglu.domain.plugins.form.GeneratorConfigForm;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

/**
 * <p>SampleDialogWrapper</p>
 * <p>生成器向导窗口</p>
 *
 * @author fengxioa
 * @since 2019/8/31 13:17
 */
public class GeneratorDialogWrapper extends DialogWrapper {

    private GeneratorConfigForm configForm;
    private GeneratorConfigEntity configEntity;
    private Project project;

    public GeneratorDialogWrapper(Project project) {
        super(true);
        this.project = project;
        configEntity = new GeneratorConfigEntity();
        init();
        setTitle("生成器向导窗口");
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JPanel dialogPanel = new JPanel(new BorderLayout());

        configForm = new GeneratorConfigForm();
        dialogPanel.add(configForm.getPanel(), BorderLayout.CENTER);

        configForm.reset(configEntity);
        return dialogPanel;
    }

    @Override
    protected void doOKAction() {
        configForm.apply(configEntity);
        Messages.showMessageDialog(project,
                JSON.toJSONString(configEntity),
                "配置内容",
                Messages.getInformationIcon());
        super.doOKAction();
    }
}