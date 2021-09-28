package cn.weixiaochen.gobang.player;

import cn.weixiaochen.gobang.chess.Board;
import cn.weixiaochen.gobang.chess.Piece;
import cn.weixiaochen.gobang.ui.ChessBoard;

import java.util.List;

/**
 * @author 魏小宸 2021/9/19
 */
public class Human {

    private static Human instance;

    private Piece.Color color;

    private List<Piece> pieces;

    public static Human get() {
        if (instance == null) {
            instance = new Human();
        }
        return instance;
    }

    private Human() { }

    /**
     * @param x 鼠标点击的x坐标
     * @param y 鼠标点击的y坐标
     */
    public boolean play(int x, int y) {
        x = (int) Math.round((x - ChessBoard.MARGIN - ChessBoard.PADDING) * 1.0 / ChessBoard.GRID_SIZE);
        y = (int) Math.round((y - ChessBoard.MARGIN - ChessBoard.PADDING) * 1.0 / ChessBoard.GRID_SIZE);
        Piece piece = new Piece(x, y, color);
        return Board.get().add(piece);
    }

    public List<Piece> getHumanPieces() {
        if (pieces == null) {
            if (getColor() == Piece.Color.BLACK) {
                pieces = Board.get().getBlackPieces();
            } else {
                pieces = Board.get().getWhitePieces();
            }
        }
        return pieces;
    }

    public Piece.Color getColor() {
        return color;
    }

    public void setColor(Piece.Color color) {
        this.color = color;
    }
}
