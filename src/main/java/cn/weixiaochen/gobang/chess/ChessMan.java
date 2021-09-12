package cn.weixiaochen.gobang.chess;

import java.util.Arrays;

/**
 * 棋子数据结构：记录棋子的位置
 * @author 魏小宸 2021/9/12
 */
public class ChessMan {

    /* 无子 */
    public static final int BLANK = 0;

    /* 黑子 */
    public static final int BLACK = 1;

    /* 白子 */
    public static final int WHITE = 2;

    /* 记录棋子位置：0为空、1为黑子、2为白子 */
    int[][] chessMap = new int[CheckerBoard.STANDARD_SIZE+1][CheckerBoard.STANDARD_SIZE+1];

    /* 上一手棋子x坐标 */
    private int x;

    /* 上一手棋子y坐标 */
    private int y;

    /* 上一手棋子颜色*/
    private int color;

    public ChessMan() {
        clear();
        this.x = -1;
        this.y = -1;
        this.color = BLACK; // 默认白棋先手
    }

    /** 落子 */
    public void set(int x, int y) {
        /* 为空时方可落子 */
        if (chessMap[x][y] == BLANK) {
            this.x = x;
            this.y = y;
            if (color == BLACK) {
                color = WHITE;
            } else if (color == WHITE) {
                color = BLACK;
            }
            chessMap[x][y] = color;
        }
    }

    /** 清空棋盘 */
    public void clear() {
        for (int[] ints : chessMap) {
            Arrays.fill(ints, 0);
        }
        this.color = BLACK;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getColor() {
        return color;
    }

    public int[][] getChessMap() {
        return chessMap;
    }
}
