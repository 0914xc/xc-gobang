package cn.weixiaochen.gobang.chess;

/**
 * 棋子
 * @author 魏小宸 2021/9/12
 */
public class ChessMan {

    /* 棋子颜色：黑子 */
    public static final int BLACK = 1;

    /* 棋子颜色：白子 */
    public static final int WHITE = 2;

    /* 棋子x坐标 */
    private final int x;

    /* 棋子y坐标 */
    private final int y;

    private final int color;

    public ChessMan(int x, int y, int color) {
        this.x = x;
        this.y = y;
        this.color = color;
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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ChessMan) {
            ChessMan another = (ChessMan) obj;
            return this.x == another.getX() && this.y == another.getY() && this.color == another.getColor();
        }
        return false;
    }
}
