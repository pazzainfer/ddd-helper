package com.changjinglu.domain.plugins.action.component;

import com.alibaba.fastjson.JSON;
import com.changjinglu.domain.plugins.Holder;
import com.changjinglu.domain.plugins.LocaleContextHolder;
import com.changjinglu.domain.plugins.entity.Table;
import com.changjinglu.domain.plugins.util.WindowUtil;
import com.intellij.openapi.ui.Messages;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.List;
/**
 * <p> SelectTablesFrame </p>
 * <p> 数据库表 </p>
 * @since 2019/9/24 2:55 下午
 * @author fengxiao
 */
public class SelectTablesFrame extends JFrame {

    public SelectTablesFrame(List<Table> tableList) {
        super(LocaleContextHolder.format("select_database_tables"));
        this.setMinimumSize(new Dimension(600, 400));

        int rowCount = tableList.size();
        SelectTables selectTablesHolder = new SelectTables(tableList);
        JTable table = selectTablesHolder.getTblTableSchema();
        table.setModel(new AbstractTableModel() {
            private static final long serialVersionUID = 8974669315458199207L;
            String[] columns = {
                    LocaleContextHolder.format("table_selected"),
                    LocaleContextHolder.format("table_sequence"),
                    LocaleContextHolder.format("table_table_name"),
                    LocaleContextHolder.format("table_class_name"),
                    LocaleContextHolder.format("table_class_comment")
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
                return columnIndex == 0 || columnIndex == 3 || columnIndex == 4;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 0:
                        return Boolean.class;
                    case 1:
                        return Integer.class;
                    default:
                        return String.class;
                }
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                switch (columnIndex) {
                    case 0:
                        return tableList.get(rowIndex).isSelected();
                    case 1:
                        return rowIndex + 1;
                    case 2:
                        return tableList.get(rowIndex).getTableName();
                    case 3:
                        return tableList.get(rowIndex).getEntityName();
                    case 4:
                        return tableList.get(rowIndex).getTableComment();
                    default:
                        throw new IllegalStateException("无法识别的列索引:" + columnIndex);
                }
            }

            @Override
            public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
                switch (columnIndex) {
                    case 0:
                        tableList.get(rowIndex).setSelected((Boolean) aValue);
                        break;
                    case 3:
                        tableList.get(rowIndex).setEntityName(aValue.toString());
                        break;
                    case 4:
                        tableList.get(rowIndex).setTableComment(aValue.toString());
                        break;
                    default:
                        break;
                }
            }
        });
        table.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new JCheckBox()));
        table.getColumnModel().getColumn(0).setMaxWidth(60);
        table.getColumnModel().getColumn(1).setMaxWidth(40);
        this.setContentPane(selectTablesHolder.getRootComponent());
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(WindowUtil.getParentWindow(Holder.getEvent().getProject()));
        this.pack();
        this.setVisible(true);

        selectTablesHolder.getBtnCancel().addActionListener(event -> this.dispose());
        // 选中所有行
        selectTablesHolder.getBtnSelectAll().addActionListener(event -> {
            for (Table t : tableList) {
                t.setSelected(true);
            }
            table.updateUI();
        });
        // 全不选
        selectTablesHolder.getBtnSelectNone().addActionListener(event -> {
            for (Table t : tableList) {
                t.setSelected(false);
            }
            table.updateUI();
        });
        // 反选
        selectTablesHolder.getBtnSelectOther().addActionListener(event -> {
            for (Table t : tableList) {
                t.setSelected(!t.isSelected());
            }
            table.updateUI();
        });
        // 下一页
        selectTablesHolder.getBtnNext().addActionListener(event -> {
            if (tableList.stream().noneMatch(Table::isSelected)) {
                Messages.showWarningDialog(Holder.getEvent().getProject(),
                        LocaleContextHolder.format("at_least_select_one_table"),
                        LocaleContextHolder.format("prompt"));
                return;
            }
            // 释放窗口
            SelectTablesFrame.this.dispose();
            // 开始生成
//            ApplicationManager.getApplication().executeOnPooledThread(new GeneratorRunner(tableList, config));
        });
    }
}
