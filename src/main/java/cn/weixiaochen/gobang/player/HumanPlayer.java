package cn.weixiaochen.gobang.player;

import cn.weixiaochen.gobang.chess.ChessBoard;
import cn.weixiaochen.gobang.chess.ChessMan;
import cn.weixiaochen.gobang.ui.component.CheckerBoardPanel;

import java.util.List;

/**
 * @author 魏小宸 2021/9/19
 */
public class HumanPlayer {

    private static HumanPlayer instance;

    private int chessManColor;

    private List<ChessMan> chessManList;

    public static HumanPlayer get() {
        if (instance == null) {
            instance = new HumanPlayer();
        }
        return instance;
    }

    private HumanPlayer() {

    }

    /**
     *
     * @param x 鼠标点击的x坐标
     * @param y 鼠标点击的y坐标
     * @return
     */
    public boolean play(int x, int y) {
        x = (int) Math.round((x - CheckerBoardPanel.MARGIN - CheckerBoardPanel.PADDING) * 1.0
                / CheckerBoardPanel.GRID_SIZE);
        y = (int) Math.round((y - CheckerBoardPanel.MARGIN - CheckerBoardPanel.PADDING) * 1.0
                / CheckerBoardPanel.GRID_SIZE);
        ChessMan chessMan = new ChessMan(x, y, getChessManColor());
        return ChessBoard.get().addNextChessMan(chessMan);
    }

    public int getChessManColor() {
        return chessManColor;
    }

    public void setChessManColor(int chessManColor) {
        this.chessManColor = chessManColor;
    }

    public List<ChessMan> getHumanChessManList() {
        if (chessManList == null) {
            if (getChessManColor() == ChessMan.BLACK) {
                chessManList = ChessBoard.get().getBlackChess();
            } else {
                chessManList = ChessBoard.get().getWhiteChess();
            }
        }
        return chessManList;
    }
}
