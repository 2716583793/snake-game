package com.wang.snakegame;

import com.wang.snakedialog.LoginDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @author Busy_luo
 * @Date: 2021/2/28
 * 程序启动
 */
public class StartGame {

    public static void main(String[] args) {
        //绘制静态窗口
        JFrame frame = new JFrame("贪吃蛇小游戏");
        //设置界面的宽高
        frame.setSize(965, 655);
        //屏幕中央弹出
        frame.setLocationRelativeTo(null);
        //窗口大小固定
        frame.setResizable(false);
        //关闭窗口时退出程序
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //添加窗口图标
        frame.setIconImage(Data.Timage);

        //添加游戏面板
        frame.add(new GamePanel());
        //设置窗体可见
        frame.setVisible(true);

        new LoginDialog(); //游戏初始进入界面

        //如果平台支持系统托盘则设置系统托盘
        if (SystemTray.isSupported()) {
            //新建弹出菜单
            PopupMenu popupMenu = new PopupMenu();
            //新建菜单选项
            MenuItem openItem = new MenuItem("open");
            MenuItem hideItem = new MenuItem("hide");
            MenuItem exitItem = new MenuItem("exit");

            //设置open菜单监听
            openItem.addActionListener(e -> {
                if (!frame.isShowing()) {
                    frame.setExtendedState(JFrame.NORMAL);
                    frame.setVisible(true);
                }
            });

            //设置hide菜单监听
            hideItem.addActionListener(actionEvent -> frame.setExtendedState(JFrame.ICONIFIED));

            //设置exit菜单监听
            exitItem.addActionListener(e -> System.exit(0));

            //添加菜单项
            popupMenu.add(openItem);
            popupMenu.add(hideItem);
            popupMenu.add(exitItem);

            //调用构造器//图片/信息/菜单
            TrayIcon trayIcon = new TrayIcon(Data.image, "贪吃蛇小游戏", popupMenu);
            //获得系统托盘
            SystemTray systemTray = SystemTray.getSystemTray();
            //自适应图片大小
            trayIcon.setImageAutoSize(true);
            //添加到系统托盘
            try {
                systemTray.add(trayIcon);
            } catch (AWTException e) {
                e.printStackTrace();
            }

            //窗口最小化事件监听
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowIconified(WindowEvent e) {
                    frame.dispose();
                }
            });

            //鼠标点击托盘事件监听
            trayIcon.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1 && !frame.isShowing()) {
                        frame.setExtendedState(JFrame.NORMAL);
                        frame.setVisible(true);
                    }
                }
            });
        }
    }
}
