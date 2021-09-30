package cn.weixiaochen.gobang.chess;


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
     * 计算棋子在特定方向上的得分
     */
    public static PiecesStatus getPiecesStatusInDirection(Piece piece, int dx, int dy) {

        Board board = Board.get();
        PiecesStatus piecesStatus = new PiecesStatus(piece);

        // 正方向
        for (int i = 1; i <= 4; i++) {
            int x = piece.getX() - dx * i;
            int y = piece.getY() - dy * i;
            if (!board.contain(new Piece(x, y, piece.getColor()))) {
                piecesStatus.setLeftEmpty(board.isEmpty(x, y));
                break;
            }
            piecesStatus.increasing();
        }

        // 反方向
        for (int i = 1; i <= 4; i++) {
            int x = piece.getX() + dx * i;
            int y = piece.getY() + dy * i;
            if (!board.contain(new Piece(x, y, piece.getColor()))) {
                piecesStatus.setRightEmpty(board.isEmpty(x, y));
                break;
            }
            piecesStatus.increasing();
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
    public static int getScore(Piece piece) {
        return calculateScoreOfPieces(getPiecesStatusInHorizontal(piece))
                + calculateScoreOfPieces(getPiecesStatusInVertical(piece))
                + calculateScoreOfPieces(getPiecesStatusInLeftSlash(piece))
                + calculateScoreOfPieces(getPiecesStatusInRightSlash(piece));
    }

    public static int calculateScoreOfPieces(PiecesStatus piecesStatus) {
        int score = 0;
        switch (piecesStatus.num()) {
            case 1:
                score = Score.LIVE_ONE.getScore();
            case 2:
                if (piecesStatus.live()) {
                    score = Score.LIVE_TWO.getScore();
                } else if (piecesStatus.death()) {
                    score = Score.DEATH_TWO.getScore();
                }
                break;
            case 3:
                if (piecesStatus.live()) {
                    score = Score.LIVE_THREE.getScore();
                } else if (piecesStatus.death()) {
                    score = Score.DEATH_THREE.getScore();
                }
                break;
            case 4:
                if (piecesStatus.live()) {
                    score = Score.LIVE_FOUR.getScore();
                } else if (piecesStatus.death()) {
                    score = Score.DEATH_FOUR.getScore();
                }
                break;
            case 5:
                score = Score.LIVE_FIVE.getScore();
                break;
        }
        return score;
    }

    /**
     * 记录棋子所在位置某一个方向连子状态
     */
    private static class PiecesStatus {

        /* 当前棋子 */
        private Piece piece;

        /* 连子数量, 默认为1(自己) */
        private int num = 1;

        /* 左端点是否为空 */
        private boolean isLeftEmpty = false;

        /* 右端点是否为空 */
        private boolean isRightEmpty = false;

        public PiecesStatus(Piece piece) {
            this.piece = piece;
        }

        public int num() {
            return num;
        }

        public void increasing() {
            num += 1;
        }

        public void setLeftEmpty(boolean leftEmpty) {
            isLeftEmpty = leftEmpty;
        }

        public void setRightEmpty(boolean rightEmpty) {
            isRightEmpty = rightEmpty;
        }

        public boolean live() {
            return isLeftEmpty && isRightEmpty;
        }

        public boolean death() {
            return isLeftEmpty || isRightEmpty;
        }
    }


    private enum Score {

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

        /* 死二 */
        DEATH_TWO(10),

        /* 死三 */
        DEATH_THREE(100),

        /* 死四 */
        DEATH_FOUR(1000);

        private final int score;

        Score(int score) {
            this.score = score;
        }

        public int getScore() {
            return score;
        }
    }
}
