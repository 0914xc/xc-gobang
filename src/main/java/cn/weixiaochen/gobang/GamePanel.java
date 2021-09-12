package cn.weixiaochen.gobang;

import cn.weixiaochen.gobang.chess.CheckerBoard;
import cn.weixiaochen.gobang.chess.ChessMan;
import cn.weixiaochen.gobang.listener.StartActionListener;
import cn.weixiaochen.gobang.listener.WithdrawActionListener;

import javax.swing.*;
import java.awt.*;

/**
 * 游戏窗口
 * @author 魏小宸 2021/9/12
 */
public class GamePanel extends JFrame {

    /* 记录棋子的数据结构 */
    private ChessMan chessMan;

    /* 棋盘 */
    CheckerBoard checkerBoard;

    /* 开始 */
    JButton startBtn;

    /* 悔棋 */
    JButton withdrawBtn;

    public GamePanel() throws HeadlessException {
        init();
        setResizable(false); // 游戏窗口大小不可编辑
        setLocationRelativeTo(null); // 设置游戏窗口居中显示
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置游戏关闭按钮
        setVisible(true);
    }

    /**
     * 初始化游戏窗口基本属性
     */
    protected void init() {
        setTitle("五子棋");
        /* 根据棋盘大小计算出游戏窗口大小 */
        int width = (CheckerBoard.STANDARD_SIZE + 1) * CheckerBoard.GRID_SIZE;
        int height = width + 50; // "50"是预留给下方操作按钮的高度
        setSize(width, height);

        setLayout(new BorderLayout());

        /* 添加棋盘 */
        chessMan = new ChessMan();
        checkerBoard = new CheckerBoard(chessMan);
        add(checkerBoard, BorderLayout.CENTER);

        /* 添加开始、悔棋按钮 */
        JPanel south = new JPanel();

        startBtn = new JButton("开始");
        south.add(startBtn);

        withdrawBtn = new JButton("悔棋");
        south.add(withdrawBtn);

        add(south, BorderLayout.SOUTH);

        /* 给按钮设置监听事件 */
        startBtn.addActionListener(new StartActionListener(this));
        withdrawBtn.addActionListener(new WithdrawActionListener(this));
    }

    public JButton getStartBtn() {
        return startBtn;
    }

    public JButton getWithdrawBtn() {
        return withdrawBtn;
    }
}
