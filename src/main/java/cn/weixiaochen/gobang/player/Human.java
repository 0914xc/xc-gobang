package cn.weixiaochen.gobang.player;

import cn.weixiaochen.gobang.chess.Board;
import cn.weixiaochen.gobang.chess.Piece;
import cn.weixiaochen.gobang.ui.ChessBoard;
import org.omg.CORBA.PUBLIC_MEMBER;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @author 魏小宸 2021/9/19
 */
public class Human {

    private static Human instance;

    private Piece.Color color;

    private List<Piece> pieces;

    private boolean isThinking = false;

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
        isThinking = true;
        x = (int) Math.round((x - ChessBoard.MARGIN - ChessBoard.PADDING) * 1.0 / ChessBoard.GRID_SIZE);
        y = (int) Math.round((y - ChessBoard.MARGIN - ChessBoard.PADDING) * 1.0 / ChessBoard.GRID_SIZE);
        Piece piece = new Piece(x, y, color);
        return Board.get().add(piece);
    }

    /** 通知AI下棋 */
    public void noticeRobot() {
        setThinking(false);
        Robot.get().setThinking(true);

    }

    public List<Piece> getPieces() {
        if (pieces == null) {
            if (getColor() == Piece.Color.BLACK) {
                pieces = Board.get().getBlackPieces();
            } else {
                pieces = Board.get().getWhitePieces();
            }
        }
        return pieces;
    }

    /** 宣布玩家获得胜利 */
    public void win(Component component) {
        String message = "游戏结束，玩家执" + Piece.Color.getName(getColor())+"获胜！";
        JOptionPane.showMessageDialog(component, message, "提示", JOptionPane.PLAIN_MESSAGE);
    }

    public Piece.Color getColor() {
        return color;
    }

    public void setColor(Piece.Color color) {
        this.color = color;
    }

    public boolean isThinking() {
        return isThinking;
    }

    public void setThinking(boolean isThinking) {
        this.isThinking = isThinking;
    }
}
