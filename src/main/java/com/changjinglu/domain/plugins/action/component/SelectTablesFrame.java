package com.changjinglu.domain.plugins.action.component;

import com.changjinglu.domain.plugins.Holder;
import com.changjinglu.domain.plugins.LocaleContextHolder;
import com.changjinglu.domain.plugins.entity.GeneratorFile;
import com.changjinglu.domain.plugins.entity.Table;
import com.changjinglu.domain.plugins.util.PropertiesConstants;
import com.changjinglu.domain.plugins.util.StringHelper;
import com.changjinglu.domain.plugins.util.WindowUtil;
import com.intellij.openapi.ui.Messages;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
/**
 * <p> SelectTablesFrame </p>
 * <p> 数据库表界面 </p>
 * @since 2019/9/24 2:55 下午
 * @author fengxiao
 */
public class SelectTablesFrame extends JFrame {

    private SelectTables selectTablesHolder;
    private List<Table> tableList;

    public SelectTablesFrame(List<Table> tableList) {
        super(LocaleContextHolder.format(PropertiesConstants.PROP_SELECT_TABLES));
        this.tableList = tableList;
        this.setMinimumSize(new Dimension(600, 400));

        int rowCount = tableList.size();
        selectTablesHolder = new SelectTables(tableList);

        selectTablesHolder.getRemovedPrefix().getDocument().addDocumentListener(new ConfigUpdateListener());

        JTable table = selectTablesHolder.getTblTableSchema();
        table.setModel(new AbstractTableModel() {
            private static final long serialVersionUID = 8974669315458199207L;
            String[] columns = {
                    LocaleContextHolder.format(PropertiesConstants.PROP_TABLE_SELECTED),
                    LocaleContextHolder.format(PropertiesConstants.PROP_TABLE_SEQUENCE),
                    LocaleContextHolder.format(PropertiesConstants.PROP_TABLE_NAME),
                    LocaleContextHolder.format(PropertiesConstants.PROP_TABLE_CLASS_NAME),
                    LocaleContextHolder.format(PropertiesConstants.PROP_TABLE_CLASS_COMMENT)
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
                        return tableList.get(rowIndex).getEntityClass();
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
                        tableList.get(rowIndex).setEntityClass(aValue.toString());
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
                        LocaleContextHolder.format(PropertiesConstants.PROP_AT_LEASE_SELECT_ONE_TABLE),
                        PropertiesConstants.PROP_PROMPT);
                return;
            }
            // 释放窗口
            SelectTablesFrame.this.dispose();

            // 显示选择文件窗口
            new SelectFilesFrame(tableList, GeneratorFile.buildByEnum());
            // 释放数据库设置窗口
            this.dispose();
        });

        this.addKeyListener(new KeyListener());
        selectTablesHolder.getRemovedPrefix().addKeyListener(new KeyListener());
        selectTablesHolder.getTblTableSchema().addKeyListener(new KeyListener());
    }

    /**
     * 连接URL变更处理监听器
     */
    private class ConfigUpdateListener implements DocumentListener {
        @Override
        public void insertUpdate(DocumentEvent e) {
            SelectTablesFrame.this.updateTable();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            SelectTablesFrame.this.updateTable();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            SelectTablesFrame.this.updateTable();
        }
    }

    /**
     * 根据输入配置动态更新表格数据
     */
    private void updateTable(){
        String prefix = selectTablesHolder.getRemovedPrefix().getText();
        for (Table t : tableList) {
            t.setEntityClass(StringHelper.parseTableName(t.getTableName(), prefix));
        }
        selectTablesHolder.getTblTableSchema().updateUI();
    }

    /**
     * 快捷键监听器
     */
    private class KeyListener extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                SelectTablesFrame.this.setVisible(false);
                SelectTablesFrame.this.dispose();
            }
        }
    }
}
