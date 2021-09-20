package cn.weixiaochen.gobang.listener;

import cn.weixiaochen.gobang.Rule.Rule;
import cn.weixiaochen.gobang.chess.ChessBoard;
import cn.weixiaochen.gobang.chess.ChessMan;
import cn.weixiaochen.gobang.player.HumanPlayer;
import cn.weixiaochen.gobang.player.RobotPlayer;
import cn.weixiaochen.gobang.ui.GameWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 开始按钮监听事件
 *
 * @author 魏小宸 2021/9/12
 */
public class StartActionListener implements ActionListener {

    private final GameWindow gameWindow;

    public StartActionListener(GameWindow gamePanel) {
        this.gameWindow = gamePanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (chooseChessManColor() != -1) {
            init();
            // 如果玩家选的不是黑色，则让AI先走
            if (HumanPlayer.get().getChessManColor() != Rule.FIRST) {
                RobotPlayer.get().play();
            }
        }
    }

    protected void init() {
        ChessBoard.get().clearChessBoard();
        this.gameWindow.getStartBtn().setText("重开");
        this.gameWindow.getCheckerBoardPanel().repaint();
        this.gameWindow.setRunning(true);
    }

    protected int chooseChessManColor() {
        String message = "请选择你要执的棋子，默认黑子先手。";
        String[] choices = {"黑棋(先)", "白棋"};
        int response = JOptionPane.showOptionDialog(this.gameWindow, message, "",
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, choices, null);
        /*
         * response == -1，即取消开始游戏;
         * response == 0, 即选择黑棋;
         * response == 1, 即选择白棋;
         */
        if (response == 0) {
            HumanPlayer.get().setChessManColor(ChessMan.BLACK);
            RobotPlayer.get().setChessManColor(ChessMan.WHITE);
        } else if (response == 1) {
            HumanPlayer.get().setChessManColor(ChessMan.WHITE);
            RobotPlayer.get().setChessManColor(ChessMan.BLACK);
        }
        return response;
    }

}
