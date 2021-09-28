package cn.weixiaochen.gobang.chess;

/**
 * 棋子
 * @author 魏小宸 2021/9/12
 */
public class Piece {

    /* 棋子x坐标 */
    private final int x;

    /* 棋子y坐标 */
    private final int y;

    /* 棋子颜色 */
    private final Color color;

    public Piece(int x, int y, Color color) {
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

    public Color getColor() {
        return color;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Piece) {
            Piece another = (Piece) obj;
            return x == another.getX() && y == another.getY() && color == another.getColor();
        }
        return false;
    }

    public enum Color {
        /* 棋子颜色：黑子 */
        BLACK,

        /* 棋子颜色：白子 */
        WHITE;

        public static Color reverse(Color color) {
            return color.equals(WHITE) ? BLACK : WHITE;
        }

    }
}
