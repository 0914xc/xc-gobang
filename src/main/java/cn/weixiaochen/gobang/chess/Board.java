package cn.weixiaochen.gobang.chess;

import java.util.LinkedList;
import java.util.List;

/**
 * 棋盘
 *
 * @author 魏小宸 2021/9/12
 */
public class Board {

    /* 国际通用五子棋棋盘规格 */
    public static final int SIZE = 15;

    /* 单例模式 */
    private static Board board;

    /* 白棋 */
    private final List<Piece> whitePieces = new LinkedList<Piece>();

    /* 黑棋 */
    private final List<Piece> blackPieces = new LinkedList<Piece>();

    /* 记录下过的棋的顺序 */
    private final List<Piece> donePieces = new LinkedList<Piece>();

    public static Board get() {
        if (board == null) {
            board = new Board();
        }
        return board;
    }

    private Board() {
    }

    public boolean add(Piece piece) {
        int x = piece.getX();
        int y = piece.getY();

        if (x >= 0 && y >= 0 && x < SIZE && y < SIZE && isEmpty(x, y)) {
            if (piece.getColor() == Piece.Color.WHITE) {
                whitePieces.add(piece);
            } else {
                blackPieces.add(piece);
            }
            donePieces.add(piece);
            return true;
        }
        return false;
    }

    public void remove(Piece piece) {
        donePieces.remove(piece);
        whitePieces.remove(piece);
        blackPieces.remove(piece);
    }

    public void clear() {
        whitePieces.clear();
        blackPieces.clear();
        donePieces.clear();
    }

    /**
     * 获取最后一手棋子
     */
    public Piece getLastPiece() {
        return donePieces.size() == 0 ? null : donePieces.get(donePieces.size() - 1);
    }

    public Piece.Color getColorOfLastPiece() {
        return getLastPiece().getColor();
    }

    /**
     * 判断整个棋盘是否为空
     */
    public boolean isEmpty() {
        return whitePieces.size() == 0 && blackPieces.size() == 0;
    }

    /**
     * 判断棋盘某个位置是否为空
     */
    public boolean isEmpty(int x, int y) {
        for (Piece chess : whitePieces) {
            if (x == chess.getX() && y == chess.getY()) {
                return false;
            }
        }
        for (Piece chess : blackPieces) {
            if (x == chess.getX() && y == chess.getY()) {
                return false;
            }
        }
        return true;
    }

    /* 判断棋盘上是否有某个棋子 */
    public boolean contain(Piece piece) {
        return whitePieces.contains(piece) || blackPieces.contains(piece);
    }

    public List<Piece> getWhitePieces() {
        return whitePieces;
    }

    public List<Piece> getBlackPieces() {
        return blackPieces;
    }

}
