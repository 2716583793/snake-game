package com.wang.snakedialog;

import com.wang.snakegame.GamePanel;

import javax.swing.*;
import java.awt.*;

/**
 * 失败弹窗
 */
public class FailDialog extends JDialog {

    public FailDialog() {

        setTitle("游戏结束"); //标题
        setSize(650, 450); //宽高
        setLocationRelativeTo(null); //屏幕中央弹出
        setResizable(false); //不可改变窗口大小

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JButton jb1 = new JButton("重新开始");
        JButton jb2 = new JButton("退出游戏");
        jb1.setBounds(150, 250, 130, 70);
        jb2.setBounds(370, 250, 130, 70);
        jb1.setFont(new Font("方正舒体", Font.BOLD, 22));
        jb2.setFont(new Font("方正舒体", Font.BOLD, 22));

        if(GamePanel.isIsFail()) {
            JLabel label1 = new JLabel();
            if (GamePanel.isWallFail()) {
                label1.setText("撞到墙壁");
            } else if(GamePanel.isOwnFail()) {
                label1.setText("撞到自己");
            } else if(GamePanel.isPoisonFail()){
                label1.setText("食物中毒");
            }
            label1.setFont(new Font("方正舒体", Font.BOLD, 70));
            label1.setForeground(Color.red);
            label1.setBounds(120, 40, 400, 80);
            label1.setHorizontalAlignment(SwingConstants.CENTER);
            panel.add(label1);
        }

        JLabel label2 = new JLabel();
        label2.setText("最终得分："+GamePanel.getScore());
        label2.setFont(new Font("方正舒体", Font.BOLD, 50));
        label2.setBounds(120, 120, 400, 80);
        label2.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(jb1);
        panel.add(jb2);
        panel.add(label2);
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
    }
}
