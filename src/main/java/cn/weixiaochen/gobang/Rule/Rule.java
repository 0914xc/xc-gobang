package cn.weixiaochen.gobang.Rule;

import cn.weixiaochen.gobang.chess.ChessBoard;
import cn.weixiaochen.gobang.chess.ChessMan;
import cn.weixiaochen.gobang.player.HumanPlayer;
import cn.weixiaochen.gobang.ui.GameWindow;

import javax.swing.*;
import java.util.List;

/**
 * 1. 胜负判断：黑棋先手，白棋无禁手，先五为胜
 * 2. 禁手判断：长连、双四、双活三
 *
 * @author 魏小宸 2021/9/19
 */
public class Rule {

    // 定义黑棋先手
    public static final int FIRST = ChessMan.BLACK;

    public static boolean checkWin() {
        // 棋盘上棋子总数大于6颗，才有机会判输赢
        if (ChessBoard.get().getChessManNum() < 6) {
            return false;
        }
        ChessMan lastChessMan = ChessBoard.get().getLastChessMan();
        List<ChessMan> allChessMan;
        if (lastChessMan.getColor() == ChessMan.WHITE) {
            allChessMan = ChessBoard.get().getWhiteChess();
        } else {
            allChessMan = ChessBoard.get().getBlackChess();
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
