package cn.weixiaochen.gobang.Rule;

import cn.weixiaochen.gobang.chess.CheckerBoard;
import cn.weixiaochen.gobang.chess.ChessMan;

import java.util.List;

/**
 * @author 魏小宸 2021/9/19
 */
public class Rule {

    // 定义黑棋先手
    public static final int FIRST = ChessMan.BLACK;

    public static boolean checkWin(CheckerBoard checkerBoard) {
        // 棋盘上棋子总数大于6颗，才有机会判输赢
        if (checkerBoard.getChessManNum() < 6) {
            return false;
        }
        ChessMan lastChessMan = checkerBoard.getLastChessMan();
        List<ChessMan> allChessMan;
        if (lastChessMan.getColor() == ChessMan.WHITE) {
            allChessMan = checkerBoard.getWhiteChess();
        } else {
            allChessMan = checkerBoard.getBlackChess();
        }

        // 横
        if (doCheckWinner(allChessMan, lastChessMan, 1, 0)) {
            return true;
        }

        // 竖
        if (doCheckWinner(allChessMan, lastChessMan, 0, 1)) {
            return true;
        }

        // 左斜
        if (doCheckWinner(allChessMan, lastChessMan, 1, 1)) {
            return true;
        }

        // 右斜
        if (doCheckWinner(allChessMan, lastChessMan, 1, -1)) {
            return true;
        }

        return false;
    }

    public static boolean doCheckWinner(List<ChessMan> allChessMan, ChessMan lastChessMan, int dx, int dy) {
        int count = 1;

        for (int i = 1; i <= 4; i++) {
            int x = lastChessMan.getX() - dx * i;
            int y = lastChessMan.getY() - dy * i;
            int color = lastChessMan.getColor();
            ChessMan chessMan = new ChessMan(x, y, color);
            if (!allChessMan.contains(chessMan)) {
                break;
            }
            count++;
        }

        for (int i = 1; i <= 4; i++) {
            int x = lastChessMan.getX() + dx * i;
            int y = lastChessMan.getY() + dy * i;
            int color = lastChessMan.getColor();
            ChessMan chessMan = new ChessMan(x, y, color);
            if (!allChessMan.contains(chessMan)) {
                break;
            }
            count++;
        }

        return count >= 5;
    }


}
