package cn.weixiaochen.gobang.listener;

import cn.weixiaochen.gobang.chess.Rule;
import cn.weixiaochen.gobang.chess.Board;
import cn.weixiaochen.gobang.chess.Piece;
import cn.weixiaochen.gobang.player.Human;
import cn.weixiaochen.gobang.player.Robot;
import cn.weixiaochen.gobang.ui.GameWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 开始、悔棋按钮监听事件
 *
 * @author 魏小宸 2021/9/12
 */
public class ButtonListener implements ActionListener {

    private final GameWindow window;

    public ButtonListener(GameWindow window) {
        this.window = window;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "开始":
                start();
                break;
            case "重开":
                restart();
                break;
            case "悔棋":
                cancel();
                break;
            default:
                break;
        }

    }

    protected void start() {
        if (chooseChessManColor() != -1) {
            /* 清空棋盘 */
            Board.get().clear();
            window.getBoardPanel().repaint();

            /* 修改开始按钮为重开按钮 */
            window.getStartBtn().setText("重开");

            /* 如果玩家选的不是黑色，则让AI先走 */
            if (Human.get().getColor() != Rule.FIRST) {
                /* 先通知AI下棋 */
                Human.get().noticeRobot();
                /* AI下棋 */
                Robot.get().play();
            }

            /* 通知玩家开始下棋 */
            Robot.get().noticeHuman();
        }
    }

    protected void restart() {
        start();
    }

    protected void cancel() {

    }


    /** 开始按钮弹出框 */
    protected int chooseChessManColor() {
        String message = "请选择你要执的棋子，默认黑子先手。";
        String[] choices = {"黑棋(先)", "白棋"};
        int response = JOptionPane.showOptionDialog(this.window, message, "提示",
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, choices, null);
        /*
         * response == -1，即取消开始游戏;
         * response == 0, 即选择黑棋;
         * response == 1, 即选择白棋;
         */
        if (response == 0) {
            Human.get().setColor(Piece.Color.BLACK);
            Robot.get().setColor(Piece.Color.WHITE);
        } else if (response == 1) {
            Human.get().setColor(Piece.Color.WHITE);
            Robot.get().setColor(Piece.Color.BLACK);
        }
        return response;
    }

}
