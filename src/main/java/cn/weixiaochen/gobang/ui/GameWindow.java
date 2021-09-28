package cn.weixiaochen.gobang.ui;

import cn.weixiaochen.gobang.listener.BoardListener;
import cn.weixiaochen.gobang.listener.ButtonListener;

import javax.swing.*;
import java.awt.*;

/**
 * 游戏窗口
 * @author 魏小宸 2021/9/19
 */
public class GameWindow extends JFrame {

    /* 棋盘界面 */
    private final ChessBoard chessBoard = new ChessBoard();

    /* 开始按钮 */
    private final JButton startBtn = new JButton("开始");

    /* 悔棋按钮 */
    private final JButton cancelBtn = new JButton("悔棋");

    /* 游戏进行中标志 */
    private boolean isRunning;

    public GameWindow() throws HeadlessException {
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
        add(chessBoard, BorderLayout.CENTER);

        /* 添加开始、悔棋按钮 */
        JPanel south = new JPanel();
        south.add(startBtn);
        south.add(cancelBtn);
        add(south, BorderLayout.SOUTH);

        /* 初始设置悔棋按钮不可用 */
        this.cancelBtn.setEnabled(false);
    }

    /**
     * 设置游戏窗口大小
     */
    protected void setWindowSize() {
        // 根据棋盘大小计算出游戏窗口大小
        int width = ChessBoard.BOARD_SIZE + (ChessBoard.MARGIN * 2) + (ChessBoard.PADDING * 2);
        // "55" 是留给按钮的高度
        int height = width + 55;
        setSize(width, height);
    }

    /**
     * 设置监听事件
     */
    protected void addListeners() {
        this.chessBoard.addMouseListener(new BoardListener(this));
        this.startBtn.addActionListener(new ButtonListener(this));
        this.cancelBtn.addActionListener(new ButtonListener(this));
    }

    public ChessBoard getBoardPanel() {
        return chessBoard;
    }

    public JButton getStartBtn() {
        return this.startBtn;
    }

    public JButton getCancelBtn() {
        return this.cancelBtn;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

}
