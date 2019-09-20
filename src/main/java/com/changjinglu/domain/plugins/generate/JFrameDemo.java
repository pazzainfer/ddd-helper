package com.changjinglu.domain.plugins.generate;

import com.changjinglu.domain.plugins.form.GeneratorConfigForm;
import com.intellij.openapi.ui.ComboBox;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class JFrameDemo extends JFrame
{
    public JFrameDemo()
    {
        setTitle("Java 第一个 GUI 程序");    //设置显示窗口标题
        setSize(400,200);    //设置窗口显示尺寸
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    //置窗口是否可以关闭
        Container c=getContentPane();    //获取当前窗口的内容窗格
        c.add(createCenterPanel());    //将标签组件添加到内容窗格上
        setVisible(true);    //设置窗口是否可见
    }

    private JComponent createCenterPanel() {
        JPanel dialogPanel = new JPanel(new BorderLayout());


        dialogPanel.add(new GeneratorConfigForm().getPanel(), BorderLayout.CENTER);

        return dialogPanel;
    }

    public static void main(String[] agrs)
    {

        new JFrameDemo();    //创建一个实例化对象
    }
}