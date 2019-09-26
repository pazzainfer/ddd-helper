package com.changjinglu.domain.plugins.util;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.openapi.wm.ex.WindowManagerEx;
import com.intellij.openapi.wm.impl.IdeFrameImpl;

import java.awt.*;

/**
 * <p> WindowUtil </p>
 * <p> 窗口工具 </p>
 * @since 2019/9/24 10:51 上午
 * @author fengxiao
 */
public class WindowUtil {
    /**
     * 获取父窗口
     * @param project
     * @return
     */
    public static Window getParentWindow(Project project) {
        WindowManagerEx windowManager = (WindowManagerEx) WindowManager.getInstance();
        Window window = windowManager.suggestParentWindow(project);
        if (window == null) {
            Window focusedWindow = windowManager.getMostRecentFocusedWindow();
            if (focusedWindow instanceof IdeFrameImpl) {
                window = focusedWindow;
            }
        }
        return window;
    }
}
