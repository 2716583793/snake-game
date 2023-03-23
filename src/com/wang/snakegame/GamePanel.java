package com.wang.snakegame;

import com.wang.snakedialog.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

/**
 * 游戏面板
 */
public class GamePanel extends JPanel implements KeyListener, ActionListener {

    //一、基本属性

    //定义蛇
    private static int length; //蛇的长度
    //蛇的坐标
    private static final int[] snakeX = new int[800];
    private static final int[] snakeY = new int[800];
    private static String order; //方向，"R"(右),"L"(左),"U"(上),"D"(下);

    private static boolean isStart; //开始判断(默认暂停)
    private static boolean isFail; //死亡判断(默认存活)
    //细分失败种类判定
    private static boolean wallFail;
    private static boolean ownFail;
    private static boolean poisonFail;
    //添加是否取反判定
    private static boolean isRevere;
    //添加弹窗所需判定
    private static boolean isFirst;
    private static boolean isAgain;

    //初始化定时器
    Timer timer = new Timer(100, this);

    //定义食物
    //定义随机数，用于概率生成不同食物
    private static int randomInt1;
    private static int randomInt2;
    //食物的坐标
    private static int foodX;
    private static int foodY;
    //实例化Random对象
    Random random = new Random();

    //设置积分系统
    private static int score; //积分
    private static final int f = 50; //蓝豆50分
    private static final int Df = 100; //红豆100分
    private static final int Rf = 200; //紫豆200分

    //特殊食物的坐标
    private static int specialX;
    private static int specialY;

    //get()/set()方法

    public static boolean isIsFail() {
        return isFail;
    }

    public static boolean isOwnFail() {
        return ownFail;
    }

    public static boolean isWallFail() {
        return wallFail;
    }

    public static boolean isPoisonFail() {
        return poisonFail;
    }

    public static int getScore() {
        return score;
    }

    public static void setIsAgain(boolean isAgain) {
        GamePanel.isAgain = isAgain;
    }

    public static void setIsStart(boolean isStart) {
        GamePanel.isStart = isStart;
    }

    //二、初始化操作

    //定义构造器
    public GamePanel() {
        init(); //调用初始化方法
        isFirst = true; //初始启动
        //获取键盘监听事件
        this.setFocusable(true); //对窗口设置键盘焦点
        this.addKeyListener(this); //添加监听器
        //启动定时器
        timer.start();
    }

    //生成食物坐标的方法
    public void foodUpdate() {

        //生成随机数(0~100)//用于概率生成不同食物
        //(0~20)(大积分)，(20~100)(小积分)
        randomInt1 = (int) (Math.random() * 100);
        randomInt2 = (int) (Math.random() * 100);

        //随机生成食物/毒食物位置
        int cnt;
        do {
            //生成食物坐标
            foodX = 25 + 25 * random.nextInt(36);
            foodY = 50 + 25 * random.nextInt(22);
            specialX = 100 + 25 * random.nextInt(30);
            specialY = 100 + 25 * random.nextInt(16);
            //循环判定是否有两者坐标重合
            cnt = 0;
            for (int i = 0; i <= length; i++) {
                if (!((snakeX[i] == foodX && snakeY[i] == foodY) ||
                        (snakeX[i] == specialX && snakeY[i] != specialY) ||
                        (foodX == specialX && foodY == specialY))) { //只要有两者位置重合则不执行自加操作
                    cnt++;
                }
            }
        } while (cnt <= length); //cnt小于等于length证明有重合现象，回到循环
    }

    //定义初始化方法
    public void init() {

        length = 3; //初始化蛇长(3节)
        score = 0; //积分清零

        //判定初始化
        isFail = false;
        poisonFail = false;
        wallFail = false;
        ownFail = false;
        isRevere = false;

        //随机生成蛇头位置//25为最小单位
        snakeX[0] = 100 + 25 * random.nextInt(30);
        snakeY[0] = 100 + 25 * random.nextInt(16);

        //等概率生成蛇头方向
        //根据蛇头方向生成初始蛇身
        int r = (int) (Math.random() * 4);
        int a = snakeX[0];
        int b = snakeY[0];
        switch (r) {
            case 0: {
                order = "R";
                snakeX[1] = a - 25;
                snakeY[1] = b;
                snakeX[2] = a - 50;
                snakeY[2] = b;
                snakeX[3] = a - 75;
                snakeY[3] = b;
                break;
            }
            case 1: {
                order = "L";
                snakeX[1] = a + 25;
                snakeY[1] = b;
                snakeX[2] = a + 50;
                snakeY[2] = b;
                snakeX[3] = a + 75;
                snakeY[3] = b;
                break;
            }
            case 2: {
                order = "U";
                snakeX[1] = a;
                snakeY[1] = b + 25;
                snakeX[2] = a;
                snakeY[2] = b + 50;
                snakeX[3] = a;
                snakeY[3] = b + 75;
                break;
            }
            case 3: {
                order = "D";
                snakeX[1] = a;
                snakeY[1] = b - 25;
                snakeX[2] = a;
                snakeY[2] = b - 50;
                snakeX[3] = a;
                snakeY[3] = b - 75;
                break;
            }
        }
        foodUpdate(); //调用食物位置初始化方法
    }

    //三、画板：绘制操作

    @Override
    protected void paintComponent(Graphics g) {

        //画界面
        super.paintComponent(g); //清屏操作
        this.setBackground(Color.white); //设置背景颜色

        //绘制游戏区域
        g.setColor(Color.black);
        g.fillRect(25, 50, 900, 550);

        //绘制帮助提示
        g.setColor(Color.black);
        g.setFont(new Font("方正舒体", Font.BOLD, 25));
        //食物列表
        g.drawString("食物列表：", 30, 45);
        Data.food.paintIcon(this, g, 150, 24);
        Data.Dfood.paintIcon(this, g, 240, 24);
        Data.poison.paintIcon(this, g, 340, 24);
        Data.reverse.paintIcon(this, g, 545, 24);
        g.drawString("50分", 180, 45);
        g.drawString("100分", 270, 45);
        g.drawString("有毒食物(死亡)", 370, 45);
        g.drawString("迷幻食物(200分)", 575, 45);
        //按键控制
        g.drawString("按键控制：", 30, 20);
        g.drawString("(W/A/S/D)(UP/LEFT/DOWN/RIGHT)(上/左/下/右)", 150, 20);
        //画框
        g.drawRect(25, 0, 900, 50);
        g.drawLine(760, 0, 760, 50);
        g.drawLine(25, 23, 925, 23);

        //画积分
        g.setColor(Color.black); //设置字体颜色
        g.setFont(new Font("方正舒体", Font.BOLD, 25));
        g.drawString("分数：" + (score) + "分", 760, 45);
        g.drawString("长度：" + (length) + "节", 760, 20);

        //根据当前的方向(order)画蛇头
        switch (order) {
            case "R" :
                Data.right.paintIcon(this, g, snakeX[0], snakeY[0]);
                break;
            case "L" :
                Data.left.paintIcon(this, g, snakeX[0], snakeY[0]);
                break;
            case "U" :
                Data.up.paintIcon(this, g, snakeX[0], snakeY[0]);
                break;
            case "D" :
                Data.down.paintIcon(this, g, snakeX[0], snakeY[0]);
                break;
        }

        //画蛇身
        for (int i = 1; i < length; i++) {
            Data.body.paintIcon(this, g, snakeX[i], snakeY[i]);
        }

        //画食物
        if (randomInt1 < 20) { //蓝豆(1/5)
            Data.Dfood.paintIcon(this, g, foodX, foodY);
        } else { //红豆(4/5)
            Data.food.paintIcon(this, g, foodX, foodY);
        }

        if (randomInt2 < 20) { //有毒食物
            if (!poisonFail) //非中毒死亡时显示有毒食物
                Data.poison.paintIcon(this, g, specialX, specialY);
        } else { //致幻食物
            Data.reverse.paintIcon(this, g, specialX, specialY);
        }

        //对话框交互事件

        //游戏开始提示语句(游戏暂停时且游戏未失败)(非首次暂停)
        if (!isStart && !isFail && !isFirst) {
            new PauseDialog(); // 游戏停止对话框
        }

        //游戏结束提示语句(游戏失败时)
        if (isFail) {
            isStart = false; //停止游戏
            new FailDialog(); //游戏失败对话框
        }
    }

    //四、监听器：执行键盘操作

    @Override
    //键盘按下(未释放)
    public void keyPressed(KeyEvent e) {
        //接收键盘的输入
        int keyCode = e.getKeyCode();

        //若按下了空格键
        if (keyCode == KeyEvent.VK_SPACE) {
            //按下空格键暂停游戏
            isFirst = false;
            //停止后不再是进入游戏的第一次
            isStart = false;
            //刷新(重画)界面
            repaint();
        }

        //('W'/'S'/'A'/'D'键或'up'/'down'/'left'/'right'键)按键控制蛇头转向//防止蛇头反向转动
        if (isStart) { //防止游戏暂停时方向按键被有效监听//增加取反判定
            if (!order.equals("R") && !order.equals("L")) {
                if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
                    if (isRevere) {
                        order = "R";
                    } else {
                        order = "L";
                    }
                }
                if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
                    if (isRevere) {
                        order = "L";
                    } else {
                        order = "R";
                    }
                }
            } else { //(!order.equals("W") && !order.equals("D"))
                if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
                    if (isRevere) {
                        order = "D";
                    } else {
                        order = "U";
                    }
                }
                if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
                    if (isRevere) {
                        order = "U";
                    } else {
                        order = "D";
                    }
                }
            }
        }
    }

    //五、定时器：执行定时操作

    @Override
    public void actionPerformed(ActionEvent e) {

        //如果选择重新开始则重新初始化程序
        if (isAgain) {
            init(); //重新做初始化操作
            isAgain = false; //重置判定
            isStart = true; //开始游戏
        }

        //若游戏处于开始状态(蛇发生移动)
        if (isStart) {

            //蛇身移动//保留第length个蛇身的坐标
            for (int i = length; i > 0; i--) {
                snakeX[i] = snakeX[i - 1];
                snakeY[i] = snakeY[i - 1];
            }

            //蛇头移动
            switch (order) {
                case "R" :
                    snakeX[0] += 25;
                    break;
                case "L" :
                    snakeX[0] -= 25;
                    break;
                case "U" :
                    snakeY[0] -= 25;
                    break;
                case "D" :
                    snakeY[0] += 25;
                    break;
            }

            //游戏失败判定

            //蛇头碰到墙后失败(判定蛇头坐标是否在墙外)
            if (snakeX[0] > 900 || snakeX[0] < 25 || snakeY[0] < 50 || snakeY[0] > 575) {
                //判定为失败
                isFail = true;
                wallFail = true;
            }

            //蛇头碰到蛇身后失败(循环判定蛇头坐标是否与任一节蛇身重合)
            for (int i = 1; i < length; i++) {
                if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {
                    //判定为失败
                    isFail = true;
                    ownFail = true;
                    break;
                }
            }

            //游戏失败后对蛇体展示优化
            if (isFail) {
                snakeX[0] = snakeX[1];
                snakeY[0] = snakeY[1];
                snakeX[1] = snakeX[length];
                snakeY[1] = snakeY[length];
            }

            //蛇头与特殊食物重合时
            if (snakeX[0] == specialX && snakeY[0] == specialY) {
                if (randomInt2 < 20) { //蛇吃到毒食物(坐标重合)后游戏失败
                    isFail = true;
                    poisonFail = true;
                } else { //蛇吃到致幻食物(坐标重合)后方向控制取反
                    isRevere = true;
                    length++;
                    score += Rf;
                    foodUpdate(); //调用食物位置初始化方法
                }
            }

            //蛇吃到食物(坐标重合)后增长/积分
            if (snakeX[0] == foodX && snakeY[0] == foodY) {
                length++; //蛇身长度加一
                //根据随机数判断生成的食物
                if (randomInt1 < 20) {
                    score += Df;
                } else {
                    score += f;
                }

                isRevere = false; //吃到普通食物后恢复

                foodUpdate(); //调用食物位置初始化方法
            }
            //刷新(重画)界面
            repaint();
        }
        //启动定时器
        timer.start();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
    }
}
