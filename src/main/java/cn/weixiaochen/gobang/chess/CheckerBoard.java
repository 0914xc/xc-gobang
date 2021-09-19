package cn.weixiaochen.gobang.chess;

import java.util.LinkedList;
import java.util.List;

/**
 * 棋盘
 *
 * @author 魏小宸 2021/9/12
 */
public class CheckerBoard {

    /* 国际通用五子棋棋盘规格 */
    public static final int SIZE = 15;

    /* 白棋 */
    private final List<ChessMan> whiteChess;

    /* 黑棋 */
    private final List<ChessMan> blackChess;

    private ChessMan lastChessMan;

    public CheckerBoard() {
        whiteChess = new LinkedList<>();
        blackChess = new LinkedList<>();
    }

    public boolean addNextChessMan(ChessMan chessMan) {
        int x = chessMan.getX();
        int y = chessMan.getY();

        if (x >= 0 && y >= 0 && x < SIZE && y < SIZE && isEmpty(x, y)) {
            if (chessMan.getColor() == ChessMan.WHITE) {
                this.whiteChess.add(chessMan);
            } else {
                this.blackChess.add(chessMan);
            }
            this.lastChessMan = chessMan;
            return true;
        }
        return false;
    }

    public ChessMan getLastChessMan() {
        return this.lastChessMan;
    }

    public int getColorOfLastChessMan() {
        return getLastChessMan().getColor();
    }

    public boolean isEmpty() {
        return this.whiteChess.size() == 0 && this.blackChess.size() == 0;
    }

    public boolean isEmpty(int x, int y) {
        for (ChessMan chess : this.whiteChess) {
            if (x == chess.getX() && y == chess.getY()) {
                return false;
            }
        }

        for (ChessMan chess : this.blackChess) {
           if (x == chess.getX() && y == chess.getY()) {
               return false;
           }
        }
        return true;
    }

    public List<ChessMan> getWhiteChess() {
        return this.whiteChess;
    }

    public List<ChessMan> getBlackChess() {
        return this.blackChess;
    }

    public int getChessManNum() {
        return this.whiteChess.size() + this.blackChess.size();
    }
}
