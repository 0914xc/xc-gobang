package cn.weixiaochen.gobang.listener;

import cn.weixiaochen.gobang.GamePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 悔棋按钮监听事件
 *
 * @author 魏小宸 2021/9/12
 */
public class WithdrawActionListener implements ActionListener {

    private final GamePanel gamePanel;

    public WithdrawActionListener(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("withdraw");
    }
}
