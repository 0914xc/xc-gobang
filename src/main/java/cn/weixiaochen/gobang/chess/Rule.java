package cn.weixiaochen.gobang.chess;


import java.util.HashMap;
import java.util.Map;

/**
 * 1. 胜负判断：黑棋先手，白棋无禁手，先五为胜
 * 2. 禁手判断：长连、双四、双活三
 *
 * @author 魏小宸 2021/9/19
 */
public class Rule {

    public static boolean win() {
        Piece piece = Board.get().getLastPiece();
        return piece != null && (getPiecesStatusInHorizontal(piece).num() >= 5 || getPiecesStatusInVertical(piece).num() >= 5
                || getPiecesStatusInLeftSlash(piece).num() >= 5 || getPiecesStatusInRightSlash(piece).num() >= 5);
    }

    /**
     * 计算棋子在特定方向上的数量
     */
    public static PiecesStatus getPiecesStatusInDirection(Piece piece, int dx, int dy) {

        Board board = Board.get();
        PiecesStatus piecesStatus = new PiecesStatus(piece);

        /* 左方向 */

        /* 找到左边第一个不为空的点 */
        int ti = 4;
        for (int i = 4; i >= 1; i--) {
            int x = piece.getX() - dx * i;
            int y = piece.getY() - dy * i;
            if (board.isValid(x, y) || !board.isEmpty(x, y)) {
                ti = i;
                break;
            }
        }

        for (int i = 1; i <= ti; i++) {
            int x = piece.getX() - dx * i;
            int y = piece.getY() - dy * i;

            /* 左端点 */
            if (board.contain(new Piece(x, y, Piece.Color.reverse(piece.getColor())))) {
                piecesStatus.setLeftEmpty(false);
                break;
            }

            /* 左边空格数 */
            if (board.isEmpty(x, y)) {
                piecesStatus.increasingLeftEmptyNum();
                continue;
            }

            /* 棋子数量（连子、非连子） */
            if (piecesStatus.leftEmptyNum() == 0) {
                piecesStatus.increasing();
            } else {
                piecesStatus.increasingLeftNum();
            }
        }

        /* 右方向 */

        /*  找到右边第一个不为空的点 */
        ti = 4;
        for (int i = 4; i >= 1; i--) {
            int x = piece.getX() + dx * i;
            int y = piece.getY() + dy * i;
            if (board.isValid(x, y) || !board.isEmpty(x, y)) {
                ti = i;
                break;
            }
        }
        for (int i = 1; i <= ti; i++) {
            int x = piece.getX() + dx * i;
            int y = piece.getY() + dy * i;

            /* 右端点 */
            if (board.contain(new Piece(x, y, Piece.Color.reverse(piece.getColor())))) {
                piecesStatus.setRightEmpty(false);
                break;
            }

            /* 右边空格数 */
            if (board.isEmpty(x, y)) {
                piecesStatus.increasingRightEmptyNum();
                continue;
            }

            /* 棋子数量（连子、非连子） */
            if (piecesStatus.rightEmptyNum() == 0) {
                piecesStatus.increasing();
            } else {
                piecesStatus.increasingRightNum();
            }
        }

        return piecesStatus;
    }

    /**
     * 横
     */
    public static PiecesStatus getPiecesStatusInHorizontal(Piece piece) {
        return getPiecesStatusInDirection(piece, 1, 0);
    }

    /**
     * 竖
     */
    public static PiecesStatus getPiecesStatusInVertical(Piece piece) {
        return getPiecesStatusInDirection(piece, 0, 1);
    }

    /**
     * 左斜
     */
    public static PiecesStatus getPiecesStatusInLeftSlash(Piece piece) {
        return getPiecesStatusInDirection(piece, 1, 1);
    }

    /**
     * 右斜
     */
    public static PiecesStatus getPiecesStatusInRightSlash(Piece piece) {
        return getPiecesStatusInDirection(piece, 1, -1);
    }

    /**
     * 计算棋子得分
     */
    public static int calculateScoreOfPieces(Piece piece) {
        Map<PiecesType, Integer> types = new HashMap<>();

        PiecesType horizontalType = getPiecesType(getPiecesStatusInHorizontal(piece));
        types.put(horizontalType, types.getOrDefault(horizontalType, 1));

        PiecesType verticalType = getPiecesType(getPiecesStatusInVertical(piece));
        types.put(verticalType, types.getOrDefault(verticalType, 1));
        PiecesType leftSlashType = getPiecesType(getPiecesStatusInLeftSlash(piece));
        types.put(leftSlashType, types.getOrDefault(leftSlashType, 1));
        PiecesType rightSlashType = getPiecesType(getPiecesStatusInRightSlash(piece));
        types.put(rightSlashType, types.getOrDefault(rightSlashType, 1));

        int score = horizontalType.getScore() + verticalType.getScore() + leftSlashType.getScore() + rightSlashType.getScore();

        /* 双二 */
        if (types.containsKey(PiecesType.LIVE_TWO) && types.get(PiecesType.LIVE_TWO) >= 2) {
            score += PiecesType.LIVE_THREE.getScore();
        }

        /* 双三 */
        if (types.containsKey(PiecesType.LIVE_THREE) && types.get(PiecesType.LIVE_THREE) >= 2) {
            score += PiecesType.LIVE_FOUR.getScore();
        }

        /* 双四 */
        if (types.containsKey(PiecesType.LIVE_FOUR) && types.get(PiecesType.LIVE_FOUR) >= 2) {
            score += PiecesType.LIVE_FIVE.getScore();
        }

        return score;
    }

    /**
     * 获取棋子连子类型
     */
    public static PiecesType getPiecesType(PiecesStatus piecesStatus) {
        PiecesType type = PiecesType.DEATH_ZERO;
        int num = piecesStatus.num() + Math.max(piecesStatus.leftNum(), piecesStatus.rightNum());
        switch (num) {
            case 1:
                type = onePieces(piecesStatus);
                break;
            case 2:
                type = twoPieces(piecesStatus);
                break;
            case 3:
                type = threePieces(piecesStatus);
                break;
            case 4:
                type = fourPieces(piecesStatus);
                break;
            case 5:
                type = fivePieces(piecesStatus);
                break;
        }
        return type;
    }

    /**
     * 活一、眠一、死一
     */
    public static PiecesType onePieces(PiecesStatus piecesStatus) {
        /* 死一 */
        if (!piecesStatus.leftEmpty() && !piecesStatus.rightEmpty()
                && piecesStatus.leftEmptyNum() == 0 && piecesStatus.rightEmptyNum() == 0) {
            return PiecesType.DEATH_ZERO;
        }

        /* 眠一 */
        if (!piecesStatus.leftEmpty() || !piecesStatus.rightEmpty()) {
            return PiecesType.SLEEP_ONE;
        }

        return PiecesType.LIVE_ONE;
    }

    /**
     * 活二、眠二、死二
     */
    public static PiecesType twoPieces(PiecesStatus piecesStatus) {
        /* 死二 */
        if (!piecesStatus.leftEmpty() && !piecesStatus.rightEmpty()
                && piecesStatus.leftEmptyNum() == 0 && piecesStatus.rightEmptyNum() == 0) {
            return PiecesType.DEATH_ZERO;
        }

        /* 眠二 */
        if (!piecesStatus.leftEmpty() && piecesStatus.leftEmptyNum() == 0) {
            return PiecesType.SLEEP_TWO;
        }

        if (!piecesStatus.rightEmpty() && piecesStatus.rightEmptyNum() == 0) {
            return PiecesType.SLEEP_TWO;
        }

        if (piecesStatus.leftEmptyNum() == 2 || piecesStatus.rightEmptyNum() == 2) {
            return PiecesType.SLEEP_TWO;
        }

        return PiecesType.LIVE_TWO;
    }

    /**
     * 活三、眠三、死三
     */
    public static PiecesType threePieces(PiecesStatus piecesStatus) {
        /* 死三 */
        if (!piecesStatus.leftEmpty() && !piecesStatus.rightEmpty()
                && piecesStatus.leftEmptyNum() == 0 && piecesStatus.rightEmptyNum() == 0) {
            return PiecesType.DEATH_ZERO;
        }

        /* 眠三 */
        if (!piecesStatus.leftEmpty() && piecesStatus.leftEmptyNum() == 0) {
            return PiecesType.SLEEP_THREE;
        }

        if (!piecesStatus.rightEmpty() && piecesStatus.rightEmptyNum() == 0) {
            return PiecesType.SLEEP_THREE;
        }

        if (piecesStatus.leftEmptyNum() == 2 || piecesStatus.rightEmptyNum() == 2) {
            return PiecesType.SLEEP_THREE;
        }

        return PiecesType.LIVE_THREE;
    }

    /**
     * 活四、冲四
     */
    public static PiecesType fourPieces(PiecesStatus piecesStatus) {
        /* 死四 */
        if (!piecesStatus.leftEmpty() && !piecesStatus.rightEmpty()
                && piecesStatus.leftEmptyNum() == 0 && piecesStatus.rightEmptyNum() == 0) {
            return PiecesType.DEATH_ZERO;
        }

        /* 眠四 */
        if (!piecesStatus.leftEmpty() && piecesStatus.leftEmptyNum() == 0) {
            return PiecesType.SLEEP_FOUR;
        }

        if (!piecesStatus.rightEmpty() && piecesStatus.rightEmptyNum() == 0) {
            return PiecesType.SLEEP_FOUR;
        }

        return PiecesType.LIVE_FOUR;
    }

    /**
     * 连五
     */
    public static PiecesType fivePieces(PiecesStatus piecesStatus) {
        return PiecesType.LIVE_FIVE;
    }

    /**
     * 记录棋子所在位置某一个方向连子状态
     */
    public static class PiecesStatus {

        /* 当前棋子 */
        private Piece piece;

        /* 连子数量, 默认为1(自己) */
        private int num = 1;

        /* 左端点是否为空 */
        private boolean isLeftEmpty = true;

        /* 左边非连子数 */
        private int leftNum = 0;

        /* 左边空格数量 */
        private int leftEmptyNum = 0;

        /* 右端点是否为空 */
        private boolean isRightEmpty = true;

        /* 右边非连子数 */
        private int rightNum = 0;

        /* 右边空格数量 */
        private int rightEmptyNum = 0;

        public PiecesStatus(Piece piece) {
            this.piece = piece;
        }

        public int num() {
            return num;
        }

        public void increasing() {
            num += 1;
        }

        public int leftEmptyNum() {
            return leftEmptyNum;
        }

        public void increasingLeftEmptyNum() {
            leftEmptyNum++;
        }

        public int rightEmptyNum() {
            return rightEmptyNum;
        }

        public void increasingRightEmptyNum() {
            rightEmptyNum++;
        }

        public int leftNum() {
            return leftNum;
        }

        public void increasingLeftNum() {
            leftNum++;
        }

        public int rightNum() {
            return rightNum;
        }

        public void increasingRightNum() {
            rightNum++;
        }

        public void setLeftEmpty(boolean leftEmpty) {
            isLeftEmpty = leftEmpty;
        }

        public boolean leftEmpty() {
            return isLeftEmpty;
        }

        public void setRightEmpty(boolean rightEmpty) {
            isRightEmpty = rightEmpty;
        }

        public boolean rightEmpty() {
            return isRightEmpty;
        }
    }


    private enum PiecesType {

        /* 活一 */
        LIVE_ONE(10),

        /* 活二 */
        LIVE_TWO(100),

        /* 活三 */
        LIVE_THREE(1000),

        /* 活四 */
        LIVE_FOUR(10000),

        /* 连五 */
        LIVE_FIVE(100000),

        /* 眠一 */
        SLEEP_ONE(1),

        /* 眠二 */
        SLEEP_TWO(10),

        /* 眠三 */
        SLEEP_THREE(100),

        /* 冲四 */
        SLEEP_FOUR(1000),

        /* 死棋 */
        DEATH_ZERO(0);

        private final int score;

        PiecesType(int score) {
            this.score = score;
        }

        public int getScore() {
            return score;
        }
    }
}
