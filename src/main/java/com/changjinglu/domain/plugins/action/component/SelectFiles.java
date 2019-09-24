package com.changjinglu.domain.plugins.action.component;

import java.awt.*;
import java.util.*;
import javax.swing.border.*;
import com.changjinglu.domain.plugins.entity.GeneratorFile;
import javax.swing.*;
import java.util.List;
import com.intellij.uiDesigner.core.*;
import com.jgoodies.forms.layout.*;
import lombok.Data;

@Data
public class SelectFiles {
  private final List<GeneratorFile> fileList;

  public SelectFiles(List<GeneratorFile> fileList) {
    initComponents();
    this.fileList = fileList;
  }

  public JTable getTblTableSchema() {
    return tblTableSchema;
  }

  public JPanel getRootComponent() {
    return rootComponent;
  }

  public JButton getBtnSelectOther() {
    return btnSelectOther;
  }

  public JButton getBtnSelectAll() {
    return btnSelectAll;
  }

  public JButton getBtnSelectNone() {
    return btnSelectNone;
  }

  public JButton getBtnCancel() {
    return btnCancel;
  }

  public JButton getBtnGenerate() {
    return btnGenerate;
  }

  public List<GeneratorFile> getFileList() {
    return fileList;
  }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.ui_i18n");
        rootComponent = new JPanel();
        JPanel panel1 = new JPanel();
        JLabel label1 = new JLabel();
        basePackage = new JTextField();
        JLabel label2 = new JLabel();
        moduleName = new JTextField();
        JLabel label3 = new JLabel();
        removedPrefix = new JTextField();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JPanel panel4 = new JPanel();
        btnSelectAll = new JButton();
        btnSelectOther = new JButton();
        btnSelectNone = new JButton();
        JPanel panel5 = new JPanel();
        btnCancel = new JButton();
        btnGenerate = new JButton();
        JScrollPane scrollPane1 = new JScrollPane();
        tblTableSchema = new JTable();
        CellConstraints cc = new CellConstraints();

        //======== rootComponent ========
        {

            // JFormDesigner evaluation mark
            rootComponent.setBorder(new javax.swing.border.CompoundBorder(
                new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                    "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
                    javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                    java.awt.Color.red), rootComponent.getBorder())); rootComponent.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

            rootComponent.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));

            //======== panel1 ========
            {
                panel1.setBorder(new TitledBorder(bundle.getString("file_code_config")));
                panel1.setLayout(new FormLayout(
                    "default, left:4dlu, default:grow",
                    "default:grow, top:3dlu, max(default;4px), top:3dlu, max(default;4px)"));

                //---- label1 ----
                label1.setText(bundle.getString("file_set_base_package"));
                panel1.add(label1, cc.xy(1, 1));
                panel1.add(basePackage, cc.xy(3, 1, CellConstraints.FILL, CellConstraints.DEFAULT));

                //---- label2 ----
                label2.setText(bundle.getString("file_set_module_name"));
                panel1.add(label2, cc.xy(1, 3));
                panel1.add(moduleName, cc.xy(3, 3, CellConstraints.FILL, CellConstraints.DEFAULT));

                //---- label3 ----
                label3.setText(bundle.getString("file_set_remove_prefix"));
                panel1.add(label3, cc.xy(1, 5));
                panel1.add(removedPrefix, cc.xy(3, 5, CellConstraints.FILL, CellConstraints.DEFAULT));
            }
            rootComponent.add(panel1, new GridConstraints(0, 0, 1, 1,
                GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                null, null, null));

            //======== panel2 ========
            {
                panel2.setBorder(new TitledBorder(bundle.getString("select_file")));
                panel2.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));

                //======== panel3 ========
                {
                    panel3.setLayout(new BorderLayout());

                    //======== panel4 ========
                    {
                        panel4.setLayout(new FlowLayout());

                        //---- btnSelectAll ----
                        btnSelectAll.setText(bundle.getString("button_select_all"));
                        panel4.add(btnSelectAll);

                        //---- btnSelectOther ----
                        btnSelectOther.setText(bundle.getString("button_inverse_selection"));
                        panel4.add(btnSelectOther);

                        //---- btnSelectNone ----
                        btnSelectNone.setText(bundle.getString("button_select_none"));
                        panel4.add(btnSelectNone);
                    }
                    panel3.add(panel4, BorderLayout.WEST);

                    //======== panel5 ========
                    {
                        panel5.setLayout(new FlowLayout());

                        //---- btnCancel ----
                        btnCancel.setText(bundle.getString("button_cancel"));
                        panel5.add(btnCancel);

                        //---- btnGenerate ----
                        btnGenerate.setText(bundle.getString("button_start_generate"));
                        panel5.add(btnGenerate);
                    }
                    panel3.add(panel5, BorderLayout.EAST);
                }
                panel2.add(panel3, new GridConstraints(0, 0, 1, 1,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    null, null, null));

                //======== scrollPane1 ========
                {
                    scrollPane1.setViewportView(tblTableSchema);
                }
                panel2.add(scrollPane1, new GridConstraints(1, 0, 1, 1,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
                    null, null, null));
            }
            rootComponent.add(panel2, new GridConstraints(1, 0, 1, 1,
                GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                null, null, null));
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JPanel rootComponent;
    private JTextField basePackage;
    private JTextField moduleName;
    private JTextField removedPrefix;
    private JButton btnSelectAll;
    private JButton btnSelectOther;
    private JButton btnSelectNone;
    private JButton btnCancel;
    private JButton btnGenerate;
    private JTable tblTableSchema;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
