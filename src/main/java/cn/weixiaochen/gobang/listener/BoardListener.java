package cn.weixiaochen.gobang.listener;

import cn.weixiaochen.gobang.chess.Rule;
import cn.weixiaochen.gobang.player.Human;
import cn.weixiaochen.gobang.player.Robot;
import cn.weixiaochen.gobang.ui.GameWindow;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author 魏小宸 2021/9/19
 */
public class BoardListener implements MouseListener {

    private final GameWindow window;

    public BoardListener(GameWindow window) {
        this.window = window;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        /* Human不在在思考状态，不响应点击时间 */
        if (!Human.get().isThinking()) {
            return;
        }

        /* 玩家落子 */
        boolean ret = Human.get().play(e.getX(), e.getY());

        if (ret) {
            /* 落子成功，更新游戏界面 */
            window.getBoardPanel().repaint();
        } else {
            /* 落子失败，中止响应事件 */
            return;
        }

        /* 判断游戏是否结束 */
        if (!Rule.win()) {
            /* 没有结束，则通知AI开始下棋 */
            Human.get().noticeRobot();
            /* 开辟线程，AI下棋 */
            new AiThread().start();
        } else {
            /* 游戏结束，宣布玩家获得胜利 */
            Human.get().win(window);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private class AiThread extends Thread {

        @Override
        public void run() {
            /* 暂停100ms,防止和swing线程冲突（保证swing组件的UI线程先结束） */
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (!Robot.get().isThinking()) {
                return;
            }

            /* AI下棋 */
            boolean ret = Robot.get().play();

            /* 落子失败，宣布玩家获得胜利 */
            if (!ret) {
                Human.get().win(window);
            }

            /* 落子成功，更新游戏界面 */
            window.getBoardPanel().repaint();

            /* 判断AI是否获得胜利，没有结束则通知玩家开始下棋 */
            if (Rule.win()) {
                Robot.get().win(window);
            } else {
                Robot.get().noticeHuman();
            }
        }
    }
}
