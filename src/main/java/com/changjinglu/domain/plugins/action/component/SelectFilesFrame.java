package com.changjinglu.domain.plugins.action.component;

import com.changjinglu.domain.plugins.Holder;
import com.changjinglu.domain.plugins.LocaleContextHolder;
import com.changjinglu.domain.plugins.entity.GeneratorFile;
import com.changjinglu.domain.plugins.entity.Table;
import com.changjinglu.domain.plugins.util.WindowUtil;
import com.intellij.openapi.ui.Messages;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.List;

/**
 * <p> SelectFilesFrame </p>
 * <p> 选择代码文件并生成界面 </p>
 * @since 2019/9/24 7:06 下午
 * @author fengxiao
 */
public class SelectFilesFrame extends JFrame {

    private List<Table> tableList;

    public SelectFilesFrame(List<Table> tableList, List<GeneratorFile> fileList) {
        super(LocaleContextHolder.format("select_files"));
        this.tableList = tableList;
        this.setMinimumSize(new Dimension(600, 400));

        int rowCount = fileList.size();
        SelectFiles selectTablesHolder = new SelectFiles(fileList);
        JTable table = selectTablesHolder.getTblTableSchema();
        table.setModel(new AbstractTableModel() {
            private static final long serialVersionUID = 8101289068997810922L;
            String[] columns = {
                    LocaleContextHolder.format("file_table_selected"),
                    LocaleContextHolder.format("file_table_type")
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
            if (fileList.stream().noneMatch(GeneratorFile::isSelected)) {
                Messages.showWarningDialog(Holder.getEvent().getProject(),
                        LocaleContextHolder.format("at_least_select_one_file"),
                        LocaleContextHolder.format("prompt"));
                return;
            }
            // 释放窗口
            SelectFilesFrame.this.dispose();
            // 开始生成
//            ApplicationManager.getApplication().executeOnPooledThread(new GeneratorRunner(fileList, config));
        });
    }
}
