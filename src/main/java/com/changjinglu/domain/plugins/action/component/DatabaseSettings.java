package com.changjinglu.domain.plugins.action.component;

import com.changjinglu.domain.plugins.LocaleItem;
import com.changjinglu.domain.plugins.adapter.DatabaseDrivers;

import javax.swing.*;

/**
 * <p> DatabaseSettings </p>
 * <p> 数据源配置 </p>
 * @author fengxiao
 * @since 2019/9/23 7:06 下午
 */
public class DatabaseSettings {

    private JPanel rootComponent;
    private JTextField textHost;
    private JTextField textUsername;
    private JTextField textDatabase;
    private JButton btnCancel;
    private JButton btnNext;
    private JPasswordField textPassword;
    private JTextField textPort;
    private JLabel lblSelectLanguage;
    private JComboBox<LocaleItem> cbxSelectLanguage;
    private JTextField textConnectionUrl;
    private JComboBox<DatabaseDrivers> cbxSelectDatabase;
    private JCheckBox useSSL;

    public JPanel getRootComponent() {
        return rootComponent;
    }

    public void setRootComponent(JPanel rootComponent) {
        this.rootComponent = rootComponent;
    }

    public JTextField getTextHost() {
        return textHost;
    }

    public void setTextHost(JTextField textHost) {
        this.textHost = textHost;
    }

    public JTextField getTextUsername() {
        return textUsername;
    }

    public void setTextUsername(JTextField textUsername) {
        this.textUsername = textUsername;
    }

    public JTextField getTextDatabase() {
        return textDatabase;
    }

    public void setTextDatabase(JTextField textDatabase) {
        this.textDatabase = textDatabase;
    }

    public JButton getBtnCancel() {
        return btnCancel;
    }

    public void setBtnCancel(JButton btnCancel) {
        this.btnCancel = btnCancel;
    }

    public JButton getBtnNext() {
        return btnNext;
    }

    public void setBtnNext(JButton btnNext) {
        this.btnNext = btnNext;
    }

    public JPasswordField getTextPassword() {
        return textPassword;
    }

    public void setTextPassword(JPasswordField textPassword) {
        this.textPassword = textPassword;
    }

    public JTextField getTextPort() {
        return textPort;
    }

    public void setTextPort(JTextField textPort) {
        this.textPort = textPort;
    }

    public JLabel getLblSelectLanguage() {
        return lblSelectLanguage;
    }

    public void setLblSelectLanguage(JLabel lblSelectLanguage) {
        this.lblSelectLanguage = lblSelectLanguage;
    }

    public JComboBox<LocaleItem> getCbxSelectLanguage() {
        return cbxSelectLanguage;
    }

    public void setCbxSelectLanguage(JComboBox<LocaleItem> cbxSelectLanguage) {
        this.cbxSelectLanguage = cbxSelectLanguage;
    }

    public JTextField getTextConnectionUrl() {
        return textConnectionUrl;
    }

    public void setTextConnectionUrl(JTextField textConnectionUrl) {
        this.textConnectionUrl = textConnectionUrl;
    }

    public JComboBox<DatabaseDrivers> getCbxSelectDatabase() {
        return cbxSelectDatabase;
    }

    public void setCbxSelectDatabase(JComboBox<DatabaseDrivers> cbxSelectDatabase) {
        this.cbxSelectDatabase = cbxSelectDatabase;
    }

    public JCheckBox getUseSSL() {
        return useSSL;
    }

    public void setUseSSL(JCheckBox useSSL) {
        this.useSSL = useSSL;
    }
}
