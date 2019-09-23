package com.changjinglu.domain.plugins.form;

import com.google.common.base.Joiner;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * <p>GeneratorConfigForm</p>
 * <p>代码生成配置表单控件</p>
 *
 * @author fengxioa
 * @since 2019/9/1 13:13
 */
public class GeneratorConfigForm {

    public JPanel getPanel() {
        return topPanel;
    }

    public GeneratorConfigForm() {
        initComponents();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    /**
     * 获取表单值
     *
     * @param configEntity
     */
    public void apply(GeneratorConfigEntity configEntity) {
        configEntity.setDbType((String) dbType.getSelectedItem());
        configEntity.setDriver(dbDriver.getText());
        configEntity.setUrl(dbUrl.getText());
        configEntity.setUsername(dbUser.getText());
        configEntity.setPassword(new String(dbPassword.getPassword()));
        configEntity.setTableNames(dbTables.getText().split(","));
    }

    /**
     * 重置表单值到控件
     *
     * @param configEntity
     */
    public void reset(GeneratorConfigEntity configEntity) {
        dbType.setSelectedItem(configEntity.getDbType());
        dbDriver.setText(configEntity.getDriver());
        dbUrl.setText(configEntity.getUrl());
        dbUser.setText(configEntity.getUsername());
        dbPassword.setText(configEntity.getPassword());
        if (configEntity.getTableNames() != null) {
            dbTables.setText(Joiner.on(",").join(configEntity.getTableNames()));
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        topPanel = new JPanel();
        JPanel panel1 = new JPanel();
        JLabel label1 = new JLabel();
        dbUrl = new JTextField();
        JLabel label2 = new JLabel();
        dbUser = new JTextField();
        JLabel label3 = new JLabel();
        JLabel label4 = new JLabel();
        dbPassword = new JPasswordField();
        JLabel label5 = new JLabel();
        dbType = new ComboBox();
        JLabel label6 = new JLabel();
        dbDriver = new TextFieldWithBrowseButton();
        dbTables = new TextFieldWithBrowseButton();
        dbTest = new JButton();

        //======== topPanel ========
        {

            // JFormDesigner evaluation mark
            topPanel.setBorder(new javax.swing.border.CompoundBorder(
                    new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                            "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
                            javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                            java.awt.Color.red), topPanel.getBorder()));
            topPanel.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
                public void propertyChange(java.beans.PropertyChangeEvent e) {
                    if ("border".equals(e.getPropertyName())) throw new RuntimeException();
                }
            });

            topPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));

            //======== panel1 ========
            {
                panel1.setBorder(new TitledBorder(new EtchedBorder(), "\u6570\u636e\u5e93\u914d\u7f6e", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
                        UIManager.getFont("TitledBorder.font")));
                panel1.setLayout(new GridLayoutManager(6, 4, new Insets(10, 5, 0, 5), -1, -1));

                //---- label1 ----
                label1.setLabelFor(dbUrl);
                label1.setText("URL\uff1a");
                panel1.add(label1, new GridConstraints(2, 0, 1, 1,
                        GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null, null, null, 1));
                panel1.add(dbUrl, new GridConstraints(2, 1, 1, 3,
                        GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null, null, null));

                //---- label2 ----
                label2.setLabelFor(dbUser);
                label2.setText("\u7528\u6237\u540d\uff1a");
                panel1.add(label2, new GridConstraints(3, 0, 1, 1,
                        GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null, null, null, 1));
                panel1.add(dbUser, new GridConstraints(3, 1, 1, 1,
                        GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null, null, null));

                //---- label3 ----
                label3.setText("\u9a71\u52a8Jar\uff1a");
                panel1.add(label3, new GridConstraints(1, 0, 1, 1,
                        GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null, null, null, 1));

                //---- label4 ----
                label4.setLabelFor(dbPassword);
                label4.setText("\u5bc6\u7801\uff1a");
                panel1.add(label4, new GridConstraints(3, 2, 1, 1,
                        GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null, null, null, 1));
                panel1.add(dbPassword, new GridConstraints(3, 3, 1, 1,
                        GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null, null, null));

                //---- label5 ----
                label5.setText("\u7c7b\u578b\uff1a");
                panel1.add(label5, new GridConstraints(0, 0, 1, 1,
                        GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null, null, null, 1));

                //---- dbType ----
                dbType.setEditable(true);
                dbType.setModel(new DefaultComboBoxModel<>(new String[]{
                        "MySQL"
                }));
                dbType.setSwingPopup(true);
                panel1.add(dbType, new GridConstraints(0, 1, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null, null, null));

                //---- label6 ----
                label6.setText("\u8868\u914d\u7f6e\uff1a");
                panel1.add(label6, new GridConstraints(5, 0, 1, 1,
                        GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null, null, null, 1));
                panel1.add(dbDriver, new GridConstraints(1, 1, 1, 3,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null, null, null));
                panel1.add(dbTables, new GridConstraints(5, 1, 1, 3,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null, null, null));

                //---- dbTest ----
                dbTest.setText("\u6d4b\u8bd5");
                panel1.add(dbTest, new GridConstraints(4, 0, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null, null, null, 1));
            }
            topPanel.add(panel1, new GridConstraints(0, 0, 1, 1,
                    GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    GridConstraints.SIZEPOLICY_FIXED,
                    null, null, null));
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JPanel topPanel;
    private JTextField dbUrl;
    private JTextField dbUser;
    private JPasswordField dbPassword;
    private ComboBox dbType;
    private TextFieldWithBrowseButton dbDriver;
    private TextFieldWithBrowseButton dbTables;
    private JButton dbTest;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
