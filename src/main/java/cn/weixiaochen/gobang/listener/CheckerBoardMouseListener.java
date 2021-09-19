package cn.weixiaochen.gobang.listener;

import cn.weixiaochen.gobang.Rule.Rule;
import cn.weixiaochen.gobang.chess.CheckerBoard;
import cn.weixiaochen.gobang.chess.ChessMan;
import cn.weixiaochen.gobang.ui.GameWindow;
import cn.weixiaochen.gobang.ui.component.CheckerBoardPanel;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author 魏小宸 2021/9/19
 */
public class CheckerBoardMouseListener implements MouseListener {

    private final GameWindow gameWindow;

    public CheckerBoardMouseListener(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = (int) Math.round((e.getX() - CheckerBoardPanel.MARGIN - CheckerBoardPanel.PADDING) * 1.0
                / CheckerBoardPanel.GRID_SIZE);
        int y = (int) Math.round((e.getY() - CheckerBoardPanel.MARGIN - CheckerBoardPanel.PADDING) * 1.0
                / CheckerBoardPanel.GRID_SIZE);
        if (addNextChessMan(x, y)) {
            checkWin();
        }
    }

    protected boolean addNextChessMan(int x, int y) {
        int color = Rule.FIRST;
        CheckerBoard checkerBoard = this.gameWindow.getCheckerBoard();
        if (!checkerBoard.isEmpty() && checkerBoard.getColorOfLastChessMan() == ChessMan.BLACK) {
            color = ChessMan.WHITE;
        }
        ChessMan chessMan = new ChessMan(x, y, color);
        return checkerBoard.addNextChessMan(chessMan);
    }

    protected void checkWin() {
        this.gameWindow.getCheckerBoardPanel().repaint();
        if (Rule.checkWin(this.gameWindow.getCheckerBoard())) {
            String result;
            if (this.gameWindow.getCheckerBoard().getColorOfLastChessMan() == ChessMan.BLACK) {
                result = "黑棋胜利！";
            } else {
                result = "白棋胜利！";
            }
            JOptionPane.showMessageDialog(this.gameWindow, result, "游戏结束", JOptionPane.PLAIN_MESSAGE);
        }
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
