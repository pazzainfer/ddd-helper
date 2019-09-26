package com.changjinglu.domain.plugins.action.component;

import com.changjinglu.domain.plugins.Holder;
import com.changjinglu.domain.plugins.LocaleContextHolder;
import com.changjinglu.domain.plugins.entity.GeneratorFile;
import com.changjinglu.domain.plugins.entity.Table;
import com.changjinglu.domain.plugins.generate.GeneratorRunner;
import com.changjinglu.domain.plugins.util.PropertiesConstants;
import com.changjinglu.domain.plugins.util.WindowUtil;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.ui.Messages;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import static com.changjinglu.domain.plugins.util.KeyUtil.createKey;

/**
 * <p> SelectFilesFrame </p>
 * <p> 选择代码文件并生成界面 </p>
 * @since 2019/9/24 7:06 下午
 * @author fengxiao
 */
public class SelectFilesFrame extends JFrame {

    private List<Table> tableList;
    private SelectFiles selectTablesHolder;

    public SelectFilesFrame(List<Table> tableList, List<GeneratorFile> fileList) {
        super(LocaleContextHolder.format(PropertiesConstants.PROP_SELECT_FILES));
        this.tableList = tableList;
        this.setMinimumSize(new Dimension(600, 400));

        int rowCount = fileList.size();
        selectTablesHolder = new SelectFiles(fileList);
        initFormField();


        JTable table = selectTablesHolder.getTblTableSchema();
        table.setModel(new AbstractTableModel() {
            private static final long serialVersionUID = 8101289068997810922L;
            String[] columns = {
                    LocaleContextHolder.format(PropertiesConstants.PROP_FILE_TABLE_SELECTED),
                    LocaleContextHolder.format(PropertiesConstants.PROP_FILE_TABLE_TYPE)
            };

            @Override
            public int getRowCount() {
                return rowCount;
            }

            @Override
            public int getColumnCount() {
                return columns.length;
            }

            @Override
            public String getColumnName(int column) {
                return columns[column];
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return columnIndex == 0;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 0:
                        return Boolean.class;
                    default:
                        return String.class;
                }
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                switch (columnIndex) {
                    case 0:
                        return fileList.get(rowIndex).isSelected();
                    case 1:
                        return fileList.get(rowIndex).getType().getDesc();
                    default:
                        throw new IllegalStateException("无法识别的列索引:" + columnIndex);
                }
            }

            @Override
            public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
                switch (columnIndex) {
                    case 0:
                        fileList.get(rowIndex).setSelected((Boolean) aValue);
                        break;
                    default:
                        break;
                }
            }
        });
        table.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new JCheckBox()));
        table.getColumnModel().getColumn(0).setMaxWidth(60);
        this.setContentPane(selectTablesHolder.getRootComponent());
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(WindowUtil.getParentWindow(Holder.getEvent().getProject()));
        this.pack();
        this.setVisible(true);

        selectTablesHolder.getBtnCancel().addActionListener(event -> this.dispose());
        // 选中所有行
        selectTablesHolder.getBtnSelectAll().addActionListener(event -> {
            for (GeneratorFile t : fileList) {
                t.setSelected(true);
            }
            table.updateUI();
        });
        // 全不选
        selectTablesHolder.getBtnSelectNone().addActionListener(event -> {
            for (GeneratorFile t : fileList) {
                t.setSelected(false);
            }
            table.updateUI();
        });
        // 反选
        selectTablesHolder.getBtnSelectOther().addActionListener(event -> {
            for (GeneratorFile t : fileList) {
                t.setSelected(!t.isSelected());
            }
            table.updateUI();
        });
        // 下一页
        selectTablesHolder.getBtnGenerate().addActionListener(event -> {
            String author = selectTablesHolder.getAuthor().getText().trim();
            String basePackage = selectTablesHolder.getBasePackage().getText().trim();
            String module = selectTablesHolder.getModuleName().getText().trim();
            String excludeFields = selectTablesHolder.getExcludeFields().getText().trim();
            if (author.isEmpty()) {
                selectTablesHolder.getAuthor().requestFocus();
                return;
            }
            if (basePackage.isEmpty()) {
                selectTablesHolder.getBasePackage().requestFocus();
                return;
            }
            if (module.isEmpty()) {
                selectTablesHolder.getModuleName().requestFocus();
                return;
            }
            saveFormField(author, basePackage, module, excludeFields);
            if (fileList.stream().noneMatch(GeneratorFile::isSelected)) {
                Messages.showWarningDialog(Holder.getEvent().getProject(),
                        LocaleContextHolder.format(PropertiesConstants.PROP_AT_LEASE_SELECT_ONE_FILE),
                        LocaleContextHolder.format(PropertiesConstants.PROP_PROMPT));
                return;
            }
            // 释放窗口
            SelectFilesFrame.this.dispose();
            // 开始生成
            ApplicationManager.getApplication().executeOnPooledThread(new GeneratorRunner(tableList, fileList));
        });

        this.addKeyListener(new KeyListener());
        selectTablesHolder.getAuthor().addKeyListener(new KeyListener());
        selectTablesHolder.getExcludeFields().addKeyListener(new KeyListener());
        selectTablesHolder.getBasePackage().addKeyListener(new KeyListener());
        selectTablesHolder.getTblTableSchema().addKeyListener(new KeyListener());
    }


    /**
     * 保存表单数据
     * @param author
     * @param basePackage
     * @param moduleName
     * @param excludeFields
     */
    private void saveFormField(String author, String basePackage, String moduleName, String excludeFields) {
        PropertiesComponent applicationProperties = Holder.getApplicationProperties();
        applicationProperties.setValue(createKey("basePackage"), basePackage);
        applicationProperties.setValue(createKey("author"), author);
        applicationProperties.setValue(createKey("moduleName"), moduleName);
        applicationProperties.setValue(createKey("excludeFields"), excludeFields);
    }

    /**
     * 初始化表单数据展示
     */
    private void initFormField() {
        PropertiesComponent applicationProperties = Holder.getApplicationProperties();
        selectTablesHolder.getAuthor().setText(applicationProperties.getValue(createKey("author"), ""));
        selectTablesHolder.getBasePackage().setText(applicationProperties.getValue(createKey("basePackage"), ""));
        selectTablesHolder.getModuleName().setText(applicationProperties.getValue(createKey("moduleName"), ""));
        selectTablesHolder.getExcludeFields().setText(applicationProperties.getValue(createKey("excludeFields"), "id,createdAt,updatedAt"));
    }

    /**
     * 快捷键监听器
     */
    private class KeyListener extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                SelectFilesFrame.this.setVisible(false);
                SelectFilesFrame.this.dispose();
            }
        }
    }
}
