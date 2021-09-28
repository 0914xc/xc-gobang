package cn.weixiaochen.gobang.listener;

import cn.weixiaochen.gobang.chess.Rule;
import cn.weixiaochen.gobang.chess.Board;
import cn.weixiaochen.gobang.chess.Piece;
import cn.weixiaochen.gobang.player.Human;
import cn.weixiaochen.gobang.player.Robot;
import cn.weixiaochen.gobang.ui.GameWindow;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author 魏小宸 2021/9/19
 */
public class BoardListener implements MouseListener {

    private final GameWindow window;

    public BoardListener(GameWindow window) {
        this.window = window;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (window.isRunning() && Human.get().play(e.getX(), e.getY())) {
            window.getBoardPanel().repaint();
            if (Rule.win()) {
                announceTheWinner();
                window.setRunning(false);
            } else {
                new AiThread().start();
            }
            // 更新悔棋按钮状态
            window.getCancelBtn().setEnabled(Human.get().getHumanPieces().size() > 0);
        }
    }

    protected void announceTheWinner() {
        Piece.Color winnerColor = Board.get().getColorOfLastPiece();
        String color = winnerColor == Piece.Color.WHITE ? "白" : "黑";

        Piece.Color humanColor = Human.get().getColor();

        String message;
        if (winnerColor == humanColor) {
            message = "游戏结束，玩家执" + color + "获胜！";
        } else {
            message = "游戏结束，AI执" + color + "获胜！";
        }
        JOptionPane.showMessageDialog(window, message, "", JOptionPane.PLAIN_MESSAGE);
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

    private class AiThread extends Thread {

        @Override
        public void run() {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (window.isRunning() && Robot.get().play()) {
                window.getBoardPanel().repaint();
                if (Rule.win()) {
                    announceTheWinner();
                    window.setRunning(false);
                }
            }
        }
    }
}
