package com.wang.snakedialog;

import com.wang.snakegame.GamePanel;

import javax.swing.*;
import java.awt.*;

/**
 * 暂停弹窗
 */
public class PauseDialog extends JDialog {

    public PauseDialog() {

        setTitle("游戏暂停"); //标题
        setSize(650, 450); //宽高
        setLocationRelativeTo(null); //屏幕中央弹出
        setResizable(false); //不可改变窗口大小

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JButton jb1 = new JButton("重新开始");
        JButton jb2 = new JButton("退出游戏");
        JButton jb3 = new JButton("继续游戏");
        jb1.setBounds(260, 250, 130, 70);
        jb2.setBounds(420, 250, 130, 70);
        jb3.setBounds(100, 250, 130, 70);
        jb1.setFont(new Font("方正舒体", Font.BOLD, 22));
        jb2.setFont(new Font("方正舒体", Font.BOLD, 22));
        jb3.setFont(new Font("方正舒体", Font.BOLD, 22));

        JLabel label = new JLabel("游戏暂停");
        label.setFont(new Font("方正舒体", Font.BOLD, 70));
        label.setBounds(120, 80, 400, 80);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(jb1);
        panel.add(jb2);
        panel.add(jb3);
        panel.add(label);
        add(panel);

        setVisible(true); //展现窗口
        setAlwaysOnTop(true); //窗口置顶

        //按钮监听
        jb1.addActionListener(actionEvent -> {
            //重新开始按钮
            dispose();
            GamePanel.setIsAgain(true); //重新开始游戏
        });

        jb2.addActionListener(actionEvent -> {
            //退出按钮
            System.exit(0); //退出游戏
        });

        jb3.addActionListener(actionEvent -> {
            //继续按钮
            setVisible(false);
            GamePanel.setIsStart(true); //继续游戏
        });
    }
}
