package cn.weixiaochen.gobang.listener;

import cn.weixiaochen.gobang.ui.GameWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 开始按钮监听事件
 *
 * @author 魏小宸 2021/9/12
 */
public class StartActionListener implements ActionListener {

    private final GameWindow gamePanel;

    public StartActionListener(GameWindow gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("start");
    }
}
