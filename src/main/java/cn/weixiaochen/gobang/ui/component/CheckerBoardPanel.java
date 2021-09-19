package cn.weixiaochen.gobang.ui.component;

import cn.weixiaochen.gobang.chess.CheckerBoard;
import cn.weixiaochen.gobang.chess.ChessMan;

import javax.swing.*;
import java.awt.*;

/**
 * 棋盘界面
 *
 * @author 魏小宸 2021/9/19
 */
public class CheckerBoardPanel extends JPanel {

    /* 格子尺寸 */
    public static final int GRID_SIZE = 35;

    /* 棋盘外边距 */
    public static final int MARGIN = 15;

    /* 棋盘内边距 */
    public static final int PADDING = 20;

    /* 棋盘大小 */
    public static final int CHECKER_BOARD_SIZE = (CheckerBoard.SIZE - 1) * GRID_SIZE;

    /* 天元和四星的大小 */
    public static final int START_SIZE = 6;

    /* 棋子大小 */
    public static final int CHESSMAN_SIZE = 20;

    /* 网格线左上角顶点x坐标 */
    int x1;

    /* 网格线左上角顶点y坐标 */
    int y1;

    /* 网格线左上角顶点x坐标 */
    int x2;

    /* 网格线左上角顶点y坐标 */
    int y2;

    private CheckerBoard checkerBoard;

    public CheckerBoardPanel() {
        this.x1 = MARGIN + PADDING;
        this.y1 = this.x1;
        this.x2 = this.y1 + CHECKER_BOARD_SIZE;
        this.y2 = this.x2;
    }

    public CheckerBoardPanel(CheckerBoard checkerBoard) {
        this();
        this.checkerBoard = checkerBoard;
    }

    @Override
    public void paint(Graphics g) {
        // 得到一只画笔
        Graphics2D pen = (Graphics2D) g;

        fillChessBoardBackground(pen);
        paintGridLine(pen);
        paintStars(pen);
        paintChessMan(pen);
        specialMarkLastChessMan(pen);
    }

    /**
     * 填充棋盘背景
     */
    protected void fillChessBoardBackground(Graphics2D pen) {
        pen.setColor(Color.getHSBColor(29, 32, 81));
        pen.fill3DRect(MARGIN, MARGIN,
                CHECKER_BOARD_SIZE + (PADDING * 2),
                CHECKER_BOARD_SIZE + (PADDING * 2),
                true);
    }

    /**
     * 画网格线
     */
    protected void paintGridLine(Graphics2D pen) {
        pen.setColor(Color.GRAY);
        // 画横线：x坐标不变
        for (int i = 1; i <= CheckerBoard.SIZE; i++) {
            pen.drawLine(this.x1, GRID_SIZE * i, this.x2, GRID_SIZE * i);
        }
        // 画竖线：y坐标不变
        for (int i = 1; i <= CheckerBoard.SIZE; i++) {
            pen.drawLine(GRID_SIZE * i, this.y1, GRID_SIZE * i, this.y2);
        }
    }

    /**
     * 画天元和四星
     */
    protected void paintStars(Graphics2D pen) {
        pen.setColor(Color.BLACK);
        pen.fill3DRect(x1 + (GRID_SIZE * 7) - START_SIZE / 2, (y1 + GRID_SIZE * 7) - START_SIZE / 2,
                START_SIZE, START_SIZE, true);
        pen.fill3DRect(x1 + (GRID_SIZE * 3) - START_SIZE / 2, y1 + (GRID_SIZE * 3) - START_SIZE / 2,
                START_SIZE, START_SIZE, true);
        pen.fill3DRect(x1 + (GRID_SIZE * 11) - START_SIZE / 2, y1 + (GRID_SIZE * 3) - START_SIZE / 2,
                START_SIZE, START_SIZE, true);
        pen.fill3DRect(x1 + (GRID_SIZE * 3) - START_SIZE / 2, y1 + (GRID_SIZE * 11) - START_SIZE / 2,
                START_SIZE, START_SIZE, true);
        pen.fill3DRect(x1 + (GRID_SIZE * 11) - START_SIZE / 2, y1 + (GRID_SIZE * 11) - START_SIZE / 2,
                START_SIZE, START_SIZE, true);
    }

    /**
     * 画棋子
     */
    protected void paintChessMan(Graphics2D pen) {
        pen.setColor(Color.WHITE);
        for (ChessMan chessMan : this.checkerBoard.getWhiteChess()) {
            pen.fillOval(x1 + (chessMan.getX() * GRID_SIZE) - CHESSMAN_SIZE / 2,
                    y1 + (chessMan.getY() * GRID_SIZE) - CHESSMAN_SIZE / 2,
                    CHESSMAN_SIZE, CHESSMAN_SIZE);
        }
        pen.setColor(Color.BLACK);
        for (ChessMan chessMan : this.checkerBoard.getBlackChess()) {
            pen.fillOval(x1 + (chessMan.getX() * GRID_SIZE) - CHESSMAN_SIZE / 2,
                    y1 + (chessMan.getY() * GRID_SIZE) - CHESSMAN_SIZE / 2,
                    CHESSMAN_SIZE, CHESSMAN_SIZE);
        }
    }

    /**
     * 特殊标记最后一手棋子(空心十字)
     */
    protected void specialMarkLastChessMan(Graphics2D pen) {

        // 没有棋子
        if (this.checkerBoard.isEmpty()) {
            return;
        }

        ChessMan chessMan = this.checkerBoard.getLastChessMan();

        if (chessMan.getColor() == ChessMan.BLACK) {
            pen.setColor(Color.WHITE);
        } else if (chessMan.getColor() == ChessMan.WHITE) {
            pen.setColor(Color.BLACK);
        }

        pen.drawLine(x1 + GRID_SIZE * chessMan.getX() - CHESSMAN_SIZE / 4, y1 + GRID_SIZE * chessMan.getY(),
                x1 + GRID_SIZE * chessMan.getX() + CHESSMAN_SIZE / 4, y1 + GRID_SIZE * chessMan.getY());
        pen.drawLine(x1 + (GRID_SIZE * chessMan.getX()), y1 + GRID_SIZE * chessMan.getY() - CHESSMAN_SIZE / 4,
                x1 + GRID_SIZE * chessMan.getX(), y1 + GRID_SIZE * chessMan.getY() + CHESSMAN_SIZE / 4);

        if (chessMan.getColor() == ChessMan.BLACK) {
            pen.setColor(Color.BLACK);
        } else if (chessMan.getColor() == ChessMan.WHITE) {
            pen.setColor(Color.WHITE);
        }
        pen.fillOval(x1 + GRID_SIZE * chessMan.getX() - 2, y1 + GRID_SIZE * chessMan.getY() - 2, 4, 4);
    }
}
