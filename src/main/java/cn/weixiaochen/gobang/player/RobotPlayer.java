package cn.weixiaochen.gobang.player;

import cn.weixiaochen.gobang.chess.ChessBoard;
import cn.weixiaochen.gobang.chess.ChessMan;

/**
 * @author 魏小宸 2021/9/19
 */
public class RobotPlayer  {

    private static RobotPlayer instance;

    private int chessManColor;

    public static RobotPlayer get() {
        if (instance == null) {
            instance = new RobotPlayer();
        }
        return instance;
    }

    private RobotPlayer() {

    }

    public boolean play() {
        // 计算得出x, y
        int x = 0;
        int y = 0;
        ChessMan chessMan = new ChessMan(x, y, getChessManColor());
        return ChessBoard.get().addNextChessMan(chessMan);
    }

    public int getChessManColor() {
        return chessManColor;
    }

    public void setChessManColor(int chessManColor) {
        this.chessManColor = chessManColor;
    }
}
