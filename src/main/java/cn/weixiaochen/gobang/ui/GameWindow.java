package cn.weixiaochen.gobang.ui;

import cn.weixiaochen.gobang.chess.CheckerBoard;
import cn.weixiaochen.gobang.listener.CheckerBoardMouseListener;
import cn.weixiaochen.gobang.listener.StartActionListener;
import cn.weixiaochen.gobang.listener.WithdrawActionListener;
import cn.weixiaochen.gobang.ui.component.CheckerBoardPanel;

import javax.swing.*;
import java.awt.*;

/**
 * 游戏窗口
 * @author 魏小宸 2021/9/19
 */
public class GameWindow extends JFrame {

    /* 棋盘数据 */
    CheckerBoard checkerBoard;

    /* 棋盘界面 */
    CheckerBoardPanel checkerBoardPanel;

    /* 开始按钮 */
    JButton startBtn;

    /* 悔棋按钮 */
    JButton withdrawBtn;

    public GameWindow() throws HeadlessException {
        init();
    }

    /**
     * 初始化游戏窗口基本属性
     */
    protected void init() {
        setTitle("五子棋");
        setLayout(new BorderLayout());
        // 添加组件
        addComponents();
        // 设置游戏窗口大小
        setWindowSize();
        // 游戏窗口大小不可编辑
        setResizable(false);
        // 设置游戏窗口居中显示
        setLocationRelativeTo(null);
        // 设置游戏关闭按钮
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 添加监听事件
        addListeners();
        // 设置窗口可见
        setVisible(true);
    }

    protected void addComponents() {
        /* 添加棋盘 */
        checkerBoard = new CheckerBoard();
        checkerBoardPanel = new CheckerBoardPanel(checkerBoard);
        add(checkerBoardPanel, BorderLayout.CENTER);

        /* 添加开始、悔棋按钮 */
        JPanel south = new JPanel();

        startBtn = new JButton("开始");
        south.add(startBtn);

        withdrawBtn = new JButton("悔棋");
        south.add(withdrawBtn);

        add(south, BorderLayout.SOUTH);
    }

    /**
     * 设置游戏窗口大小
     */
    protected void setWindowSize() {
        // 根据棋盘大小计算出游戏窗口大小
        int width = CheckerBoardPanel.CHECKER_BOARD_SIZE
                + (CheckerBoardPanel.MARGIN * 2)
                + (CheckerBoardPanel.PADDING * 2);
        // "55" 是留给按钮的高度
        int height = width + 55;
        setSize(width, height);
    }

    /**
     * 设置监听事件
     */
    protected void addListeners() {
        this.checkerBoardPanel.addMouseListener(new CheckerBoardMouseListener(this));
        this.startBtn.addActionListener(new StartActionListener(this));
        this.withdrawBtn.addActionListener(new WithdrawActionListener(this));
    }

    public CheckerBoard getCheckerBoard() {
        return this.checkerBoard;
    }

    public CheckerBoardPanel getCheckerBoardPanel() {
        return checkerBoardPanel;
    }

    public JButton getStartBtn() {
        return this.startBtn;
    }

    public JButton getWithdrawBtn() {
        return this.withdrawBtn;
    }
}