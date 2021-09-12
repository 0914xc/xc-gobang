package cn.weixiaochen.gobang.chess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * 棋盘
 *
 * @author 魏小宸 2021/9/12
 */
public class CheckerBoard extends JPanel implements MouseListener {

    /* 国际通用五子棋棋盘规格 */
    public static final int STANDARD_SIZE = 15;

    /* 棋盘格子大小 */
    public static final int GRID_SIZE = 35;

    /* 天元和四星的大小 */
    public static final int START_SIZE = 6;

    /* 棋子大小 */
    public static final int CHESSMAN_SIZE = 20;

    private ChessMan chessMan;

    public CheckerBoard() {
    }

    public CheckerBoard(ChessMan chessMan) {
        this.chessMan = chessMan;
        addMouseListener(this);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D pen = (Graphics2D) g; // 得到一支笔

        /* 画棋盘 */
        pen.setColor(Color.getHSBColor(29, 32, 81));
        pen.fill3DRect(GRID_SIZE / 2, GRID_SIZE / 2, GRID_SIZE * STANDARD_SIZE, GRID_SIZE * STANDARD_SIZE, true);

        pen.setColor(Color.GRAY);
        for (int i = 1; i <= STANDARD_SIZE; i++) {
            pen.drawLine(GRID_SIZE, GRID_SIZE * i, GRID_SIZE * STANDARD_SIZE, GRID_SIZE * i);
        }
        for (int i = 1; i <= STANDARD_SIZE; i++) {
            pen.drawLine(GRID_SIZE * i, GRID_SIZE, GRID_SIZE * i, GRID_SIZE * STANDARD_SIZE);
        }

        /* 画天元、四星 */
        pen.setColor(Color.BLACK);
        pen.fill3DRect(GRID_SIZE * 8 - START_SIZE / 2, GRID_SIZE * 8 - START_SIZE / 2, START_SIZE, START_SIZE, true);
        pen.fill3DRect(GRID_SIZE * 4 - START_SIZE / 2, GRID_SIZE * 4 - START_SIZE / 2, START_SIZE, START_SIZE, true);
        pen.fill3DRect(GRID_SIZE * 12 - START_SIZE / 2, GRID_SIZE * 4 - START_SIZE / 2, START_SIZE, START_SIZE, true);
        pen.fill3DRect(GRID_SIZE * 4 - START_SIZE / 2, GRID_SIZE * 12 - START_SIZE / 2, START_SIZE, START_SIZE, true);
        pen.fill3DRect(GRID_SIZE * 12 - START_SIZE / 2, GRID_SIZE * 12 - START_SIZE / 2, START_SIZE, START_SIZE, true);

        /* 画棋子 */
        int[][] chessMap = chessMan.getChessMap();
        for (int i = 0; i < chessMap.length; i++) {
            for (int j = 0; j < chessMap[i].length; j++) {
                if (chessMap[i][j] == ChessMan.BLANK) {
                    continue;
                }
                if (chessMap[i][j] == ChessMan.BLACK) {
                    pen.setColor(Color.BLACK);
                }
                if (chessMap[i][j] == ChessMan.WHITE) {
                    pen.setColor(Color.WHITE);
                }
                pen.fillOval(GRID_SIZE * i - CHESSMAN_SIZE / 2, GRID_SIZE * j - CHESSMAN_SIZE / 2, CHESSMAN_SIZE, CHESSMAN_SIZE);
            }
        }

        /* 上一手棋子特殊画 */
        if (chessMan.getColor() == ChessMan.BLACK) {
            pen.setColor(Color.WHITE);
        } else if (chessMan.getColor() == ChessMan.WHITE) {
            pen.setColor(Color.BLACK);
        }
        pen.drawLine(GRID_SIZE * chessMan.getX(), GRID_SIZE * chessMan.getY() - CHESSMAN_SIZE / 4,
                GRID_SIZE * chessMan.getX(), GRID_SIZE * chessMan.getY() + CHESSMAN_SIZE / 4);
        pen.drawLine(GRID_SIZE * chessMan.getX() - CHESSMAN_SIZE / 4, GRID_SIZE * chessMan.getY(),
                GRID_SIZE * chessMan.getX() + CHESSMAN_SIZE / 4, GRID_SIZE * chessMan.getY());

        if (chessMan.getColor() == ChessMan.BLACK) {
            pen.setColor(Color.BLACK);
        } else if (chessMan.getColor() == ChessMan.WHITE) {
            pen.setColor(Color.WHITE);
        }
        pen.fillOval(GRID_SIZE * chessMan.getX() - 2, GRID_SIZE * chessMan.getY() - 2, 4, 4);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = (int) Math.round(e.getX() * 1.0 / GRID_SIZE);
        int y = (int) Math.round(e.getY() * 1.0 / GRID_SIZE);
        chessMan.set(x, y);
        repaint();
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
