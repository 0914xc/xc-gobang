package cn.weixiaochen.gobang.listener;

import cn.weixiaochen.gobang.Rule.Rule;
import cn.weixiaochen.gobang.chess.ChessBoard;
import cn.weixiaochen.gobang.chess.ChessMan;
import cn.weixiaochen.gobang.player.HumanPlayer;
import cn.weixiaochen.gobang.player.RobotPlayer;
import cn.weixiaochen.gobang.ui.GameWindow;
import cn.weixiaochen.gobang.ui.component.CheckerBoardPanel;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author 魏小宸 2021/9/19
 */
public class ChessBoardMouseListener implements MouseListener {

    private final GameWindow gameWindow;

    public ChessBoardMouseListener(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (this.gameWindow.isRunning() && HumanPlayer.get().play(e.getX(), e.getY())) {
            this.gameWindow.getCheckerBoardPanel().repaint();
            if (Rule.checkWin()) {
                announceTheWinner();
                this.gameWindow.setRunning(false);
            } else {
                if (this.gameWindow.isRunning() && RobotPlayer.get().play()) {
                    this.gameWindow.getCheckerBoardPanel().repaint();
                    if (Rule.checkWin()) {
                        announceTheWinner();
                        this.gameWindow.setRunning(false);
                    }
                }
            }
            // 更新悔棋按钮状态
            this.gameWindow.getWithdrawBtn().setEnabled(HumanPlayer.get().getHumanChessManList().size() > 0);
        }
    }

    protected void announceTheWinner() {
        int winnerColor = ChessBoard.get().getColorOfLastChessMan();
        String color = winnerColor == ChessMan.WHITE ? "白" : "黑";

        int humanColor = HumanPlayer.get().getChessManColor();

        String message;
        if (winnerColor == humanColor) {
            message = "游戏结束，玩家执" + color + "获胜！";
        } else {
            message = "游戏结束，AI执" + color + "获胜！";
        }
        JOptionPane.showMessageDialog(gameWindow, message, "", JOptionPane.PLAIN_MESSAGE);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
