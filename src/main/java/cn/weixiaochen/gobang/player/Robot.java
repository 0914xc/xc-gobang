package cn.weixiaochen.gobang.player;

import cn.weixiaochen.gobang.chess.Rule;
import cn.weixiaochen.gobang.chess.Board;
import cn.weixiaochen.gobang.chess.Piece;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 魏小宸 2021/9/19
 */
public class Robot {

    /* 思考层数:必须为偶数才有意义（偶数代表AI思考了玩家的防守） */
    public static final int DEPTH = 4;

    private static Robot instance;

    /* AI所执颜色 */
    private Piece.Color color;

    private final Board board;

    private List<Piece> pieces;

    /* 临时存储AI下一步要走的棋 */
    private Piece nextPiece;

    private boolean isThinking = false;

    public static Robot get() {
        if (instance == null) {
            instance = new Robot();
        }
        return instance;
    }

    private Robot() {
        this.board = Board.get();
    }

    public boolean play() {
        isThinking = false;
        /* 搜索AI可以落子的点 */
        negamax(DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE);

        /* AI没有找到可以落子的点，则直接返回落子失败 */
        if(nextPiece == null) {
            return false;
        }

        return Board.get().add(nextPiece);
    }

    /* 通知Human下棋 */
    public void noticeHuman() {
        setThinking(false);
        Human.get().setThinking(true);
    }

    /**
     * 极大极小极搜索算法(负极大值搜索算法)
     * Alpha-Beta剪枝算法（alpha下界，beta上界）
     */
    protected int negamax(int depth, int alpha, int beta) {
        // 走到叶子节点，或者游戏结束，直接返回当前局面得分(站在AI角度的一个值)
        if (depth == 0 || Rule.win()) {
            return evaluate();
        }

        // 我在这一层中得到的最优的分数
        int best = Integer.MIN_VALUE;

        // 不是叶子节点，进行递归遍历搜索
        for (Piece candidate : getCandidates()) {
            move(candidate);
            // 站在对手的角度，对AI走完这一步以后，局面的一个评分
            // alpha和beta的一个转换，自己的下界alpha会变成对手的上界beta
            int value = -negamax(depth-1, -beta, -alpha);
            unMove(candidate);
            if (value > best) {
                best = value;
                if (depth == DEPTH && color.equals(candidate.getColor())) {
                    this.nextPiece = candidate;
                }
            }
            // 站在我的角度，取value中最大的一个值，使得我的利益最大化（提高我的下限）
            alpha = Math.max(alpha, value);
            if (value >= beta) {
                break;
            }
        }
        return best;
    }

    /**
     * 评估函数(AI得分 - 玩家得分)
     */
    protected int evaluate() {
        int score = 0;

        // 算AI的得分
        for (Piece piece : getPieces()) {
            score += Rule.getScore(piece);
        }

        // 算玩家的得分
        for (Piece piece : Human.get().getHumanPieces()) {
            score -= Rule.getScore(piece);
        }

        return score;
    }

    /**
     * 生成可以落子的点:
     * 1. 棋盘为空，默认下天元
     * 2. 棋盘不为空，寻找棋盘上已经存在的棋子，选择其两步以内的空位
     */
    protected List<Piece> getCandidates() {
        List<Piece> candidates = new ArrayList<>();
        // 棋盘为空(说明电脑执黑先行), 默认下天元
        if (board.isEmpty()) {
            candidates.add(new Piece(7, 7, color));
        }
        // 棋盘不为空
        else {
            // 取最后一手棋子的相反颜色
            Piece.Color color = Piece.Color.reverse(board.getColorOfLastPiece());
            for (int i = 0; i < Board.SIZE; i++) {
                for (int j = 0; j < Board.SIZE; j++) {
                    // 考虑两格远以内的棋
                    if (board.isEmpty(i, j) && hasNeighbors(i, j, 2)) {
                        candidates.add(new Piece(i, j, color));
                    }
                }
            }
        }
        return candidates;
    }

    protected boolean hasNeighbors(int x, int y, int distance) {
        for (int i = x; i < x + distance; i++) {
            for (int j = y; j < y + distance; j++) {
                if (!board.isEmpty(i, j)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected void move(Piece piece) {
        board.add(piece);
    }

    protected void unMove(Piece piece) {
        board.remove(piece);
    }

    public List<Piece> getPieces() {
        if (pieces == null) {
            if (getColor() == Piece.Color.BLACK) {
                pieces = board.getBlackPieces();
            } else {
                pieces = board.getWhitePieces();
            }
        }
        return pieces;
    }

    /** 通知AI获得胜利 */
    public void win(Component component) {
        String message = "游戏结束，AI执" + Piece.Color.getName(getColor())+"获胜！";
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
