package cn.weixiaochen.gobang.player;

import cn.weixiaochen.gobang.chess.Rule;
import cn.weixiaochen.gobang.chess.Board;
import cn.weixiaochen.gobang.chess.Piece;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
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
        isThinking = true;
        /* 搜索AI可以落子的点 */
        maxmin(DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
        /* AI没有找到可以落子的点，则直接返回落子失败 */
        if (nextPiece == null) {
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
     * 极大极小极搜索算法
     * Alpha-Beta剪枝算法（alpha下界，beta上界）
     */
    protected int maxmin(int depth, int alpha, int beta, boolean isRobot) {
        // 走到叶子节点，或者游戏结束，直接返回当前局面得分(站在AI角度的一个值)
        if (depth == 0 || Rule.win()) {
            return evaluate();
        }

        if (isRobot) {
            for (Piece candidate : getCandidates()) {
                move(candidate);
                int value = maxmin(depth - 1, alpha, beta, false);
                unMove(candidate);
                if (value > alpha) {
                    /* 更新下界 */
                    alpha = value;
                    if (depth == DEPTH) {
                        this.nextPiece = candidate;
                    }
                    /* beta剪枝 */
                    if (alpha >= beta) {
                        return beta;
                    }
                }
            }
            return alpha;
        } else {
            for (Piece candidate : getCandidates()) {
                move(candidate);
                int value = maxmin(depth - 1, alpha, beta, true);
                unMove(candidate);
                if (value < beta) {
                    /* 更新上界 */
                    beta = value;
                    /* alpha剪枝 */
                    if (alpha >= beta) {
                        return alpha;
                    }
                }
            }
            return beta;
        }
    }

    /**
     * 评估函数(AI得分 - 玩家得分)
     */
    public int evaluate() {
        int score = 0;

        // 算AI的得分
        for (Piece piece : getPieces()) {
            score += Rule.calculateScoreOfPieces(piece);
        }

        // 算玩家的得分
        for (Piece piece : Human.get().getPieces()) {
            score -= Rule.calculateScoreOfPieces(piece);
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
        /* 生成候选落子的点, 若棋盘为空(说明电脑执黑先行), 默认下天元 */
        if (board.isEmpty()) {
            candidates.add(new Piece(7, 7, color));
        } else {
            /* 取最后一手棋子的相反颜色 */
            Piece.Color color = Piece.Color.reverse(board.getColorOfLastPiece());
            for (int i = 0; i < Board.SIZE; i++) {
                for (int j = 0; j < Board.SIZE; j++) {
                    /* 考虑一格远以内的棋 */
                    if (board.isEmpty(i, j) && hasNeighbors(i, j, 1)) {
                        candidates.add(new Piece(i, j, color));
                    }
                }
            }
        }

        /* 启发式搜索：根据每个候选棋子的得分，对候选棋子进行排序 */
        sortPieces(candidates);

        return candidates;
    }

    protected boolean hasNeighbors(int x, int y, int distance) {
        for (int i = x - distance; i <= x + distance; i++) {
            for (int j = y - distance; j <= y + distance; j++) {
                if (!board.isEmpty(i, j)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected void sortPieces(List<Piece> pieces) {
        /* 计算每个棋子得分 */
        HashMap<Piece, Integer> score = new HashMap<>();
        for (Piece piece : pieces) {
            score.put(piece, Rule.calculateScoreOfPieces(piece));
        }

        /* 对棋子进行排序 */
        pieces.sort(Comparator.comparingInt(score::get));
    }

    protected void move(Piece piece) {
        board.add(piece);
    }

    protected void unMove(Piece piece) {
        board.remove(piece);
    }

    public List<Piece> getPieces() {
        if (getColor() == Piece.Color.BLACK) {
            return board.getBlackPieces();
        } else {
            return board.getWhitePieces();
        }
    }

    /**
     * 通知AI获得胜利
     */
    public void win(Component component) {
        setThinking(false);
        String message = "游戏结束，AI执" + Piece.Color.getName(getColor()) + "获胜！";
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
