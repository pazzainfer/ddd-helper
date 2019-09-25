package com.changjinglu.domain.plugins.action;

import com.changjinglu.domain.plugins.Holder;
import com.changjinglu.domain.plugins.LocaleContextHolder;
import com.changjinglu.domain.plugins.action.component.DatabaseSettingFrame;
import com.changjinglu.domain.plugins.adapter.DatabaseDrivers;
import com.changjinglu.domain.plugins.util.Constants;
import com.changjinglu.domain.plugins.util.PropertiesConstants;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.IconLoader;

/**
 * <p>GeneratorAction</p>
 * <p>代码生成器Action</p>
 * @author fengxiao
 * @since 2019/8/31 11:51
 */
public class GeneratorAction extends AnAction {

    public GeneratorAction() {
        super(LocaleContextHolder.format(PropertiesConstants.PROP_GENERATE_ACTION_TITLE),
                LocaleContextHolder.format(PropertiesConstants.PROP_GENERATE_ACTION_TITLE),
                IconLoader.getIcon(Constants.ICON_GENERATE));
    }

    @Override
    public void actionPerformed(AnActionEvent event) {
        if (event.getProject() == null) {
            Messages.showWarningDialog("Project not activated!", "DDD Helper");
            return;
        }
        // 注册事件
        Holder.registerEvent(event);
        Holder.registerApplicationProperties(PropertiesComponent.getInstance());
        Holder.registerProjectProperties(PropertiesComponent.getInstance(event.getProject()));
        Holder.registerDatabaseDrivers(DatabaseDrivers.MYSQL);

        new DatabaseSettingFrame();
    }
}