package com.changjinglu.domain.plugins.action.component;

import com.changjinglu.domain.plugins.entity.GeneratorFile;
import javax.swing.*;
import java.util.List;

public class SelectFiles {

    public SelectFiles(List<GeneratorFile> fileList) {
        this.fileList = fileList;
    }

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
    private JTextField excludeFields;
    private JTextField author;

    private final List<GeneratorFile> fileList;

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

    public JTextField getBasePackage() {
        return basePackage;
    }

    public JTextField getModuleName() {
        return moduleName;
    }

    public JTextField getRemovedPrefix() {
        return removedPrefix;
    }

    public JTextField getExcludeFields() {
        return excludeFields;
    }

    public JTextField getAuthor() {
        return author;
    }
}
