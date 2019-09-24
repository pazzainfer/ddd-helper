package com.changjinglu.domain.plugins.action.component;

import com.changjinglu.domain.plugins.entity.GeneratorFile;
import javax.swing.*;
import java.util.List;

public class SelectFiles {
  private JTable tblTableSchema;
  private JPanel rootComponent;
  private JButton btnSelectOther;
  private JButton btnSelectAll;
  private JButton btnSelectNone;
  private JButton btnCancel;
  private JButton btnGenerate;
  private JTextField basePackage;
  private JTextField moduleName;
  private JTextField removedPrefix;
  private final List<GeneratorFile> fileList;

  public SelectFiles(List<GeneratorFile> fileList) {
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
}
