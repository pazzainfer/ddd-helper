package com.changjinglu.domain.plugins.action;

import com.changjinglu.domain.plugins.LocaleContextHolder;
import com.changjinglu.domain.plugins.generate.CodeGenerator;
import com.changjinglu.domain.plugins.util.Constants;
import com.changjinglu.domain.plugins.util.PropertiesConstants;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.IconLoader;

/**
 * <p>GeneratorAction</p>
 * <p>代码生成器Action</p>
 *
 * @author fengxioa
 * @since 2019/8/31 11:51
 */
public class GeneratorAction extends AnAction {
    // If you register the action from Java code, this constructor is used to set the menu item name
    // (optionally, you can specify the menu description and an icon to display next to the menu item).
    // You can omit this constructor when registering the action in the plugin.xml file.
    public GeneratorAction() {
        // Set the menu item name.
        //super("Text _Boxes");
        // Set the menu item name, description and icon.
        super(LocaleContextHolder.format(PropertiesConstants.PROP_GENERATE_ACTION_TITLE),
                LocaleContextHolder.format(PropertiesConstants.PROP_GENERATE_ACTION_TITLE),
                IconLoader.getIcon(Constants.ICON_GENERATE));
    }

    @Override
    public void actionPerformed(AnActionEvent event) {
        Project project = event.getData(PlatformDataKeys.PROJECT);
        try {
            new CodeGenerator().test(project.getBasePath().toString());
//        String txt= Messages.showInputDialog(project, "What is your name?", "Input your name", Messages.getQuestionIcon());
//        Messages.showMessageDialog(project, "Hello, " + txt + "!\n I am glad to see you.", "Information", Messages.getInformationIcon());

            //if(new GeneratorDialogWrapper(project).showAndGet()) {
            // user pressed ok
            //}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}