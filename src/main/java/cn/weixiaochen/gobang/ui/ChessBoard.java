package cn.weixiaochen.gobang.ui;

import cn.weixiaochen.gobang.chess.Board;
import cn.weixiaochen.gobang.chess.Piece;

import javax.swing.*;
import java.awt.*;

/**
 * 棋盘界面
 *
 * @author 魏小宸 2021/9/19
 */
public class ChessBoard extends JPanel {

    /* 格子尺寸 */
    public static final int GRID_SIZE = 35;

    /* 棋盘外边距 */
    public static final int MARGIN = 15;

    /* 棋盘内边距 */
    public static final int PADDING = 20;

    /* 棋盘大小 */
    public static final int BOARD_SIZE = (Board.SIZE - 1) * GRID_SIZE;

    /* 天元和四星的大小 */
    public static final int START_SIZE = 6;

    /* 棋子大小 */
    public static final int PIECE_SIZE = 20;

    /* 网格线左上角顶点x坐标 */
    int x1 = MARGIN + PADDING;

    /* 网格线左上角顶点y坐标 */
    int y1 = MARGIN + PADDING;

    /* 网格线左上角顶点x坐标 */
    int x2 = x1 + BOARD_SIZE;

    /* 网格线左上角顶点y坐标 */
    int y2 = y1 + BOARD_SIZE;

    public ChessBoard() {
        int size = BOARD_SIZE + (MARGIN * 2) + (PADDING * 2);
        setPreferredSize(new Dimension(size, size));
    }

    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);
        // 得到一支画笔
        Graphics2D pen = (Graphics2D) g;

        fillChessBoardBackground(pen);
        paintGridLine(pen);
        paintStars(pen);
        paintPieces(pen);
        specialMarkLastPiece(pen);
    }

    /**
     * 填充棋盘背景
     */
    protected void fillChessBoardBackground(Graphics2D pen) {
        pen.setColor(new Color(255, 207, 152));
        pen.fill3DRect(MARGIN, MARGIN, BOARD_SIZE + (PADDING * 2), BOARD_SIZE + (PADDING * 2), true);
    }

    /**
     * 画网格线
     */
    protected void paintGridLine(Graphics2D pen) {
        pen.setColor(Color.GRAY);
        // 画横线：x坐标不变
        for (int i = 1; i <= Board.SIZE; i++) {
            pen.drawLine(this.x1, GRID_SIZE * i, this.x2, GRID_SIZE * i);
        }
        // 画竖线：y坐标不变
        for (int i = 1; i <= Board.SIZE; i++) {
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
    synchronized protected void paintPieces(Graphics2D pen) {
        pen.setColor(Color.WHITE);
        for (Piece piece : Board.get().getWhitePieces()) {
            pen.fillOval(x1 + (piece.getX() * GRID_SIZE) - PIECE_SIZE / 2,
                    y1 + (piece.getY() * GRID_SIZE) - PIECE_SIZE / 2,
                    PIECE_SIZE, PIECE_SIZE);
        }
        pen.setColor(Color.BLACK);
        for (Piece piece : Board.get().getBlackPieces()) {
            pen.fillOval(x1 + (piece.getX() * GRID_SIZE) - PIECE_SIZE / 2,
                    y1 + (piece.getY() * GRID_SIZE) - PIECE_SIZE / 2,
                    PIECE_SIZE, PIECE_SIZE);
        }
    }

    /**
     * 特殊标记最后一手棋子(空心十字)
     */
    protected void specialMarkLastPiece(Graphics2D pen) {
        // 没有棋子
        if (Board.get().isEmpty()) {
            return;
        }

        Piece piece = Board.get().getLastPiece();

        if (Piece.Color.BLACK == piece.getColor()) {
            pen.setColor(Color.WHITE);
        } else {
            pen.setColor(Color.BLACK);
        }

        pen.drawLine(x1 + GRID_SIZE * piece.getX() - PIECE_SIZE / 4, y1 + GRID_SIZE * piece.getY(),
                x1 + GRID_SIZE * piece.getX() + PIECE_SIZE / 4, y1 + GRID_SIZE * piece.getY());
        pen.drawLine(x1 + (GRID_SIZE * piece.getX()), y1 + GRID_SIZE * piece.getY() - PIECE_SIZE / 4,
                x1 + GRID_SIZE * piece.getX(), y1 + GRID_SIZE * piece.getY() + PIECE_SIZE / 4);

        if (piece.getColor() == Piece.Color.BLACK) {
            pen.setColor(Color.BLACK);
        } else {
            pen.setColor(Color.WHITE);
        }
        pen.fillOval(x1 + GRID_SIZE * piece.getX() - 2, y1 + GRID_SIZE * piece.getY() - 2, 4, 4);
    }
}
