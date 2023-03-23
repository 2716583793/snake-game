package com.wang.snakegame;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * 图片资源
 */
public class Data {

    //导入图片素材

    //窗口图标
    public static Image Timage = new ImageIcon(Objects.requireNonNull(Data.class.getResource("/com/wang/resources/TheSnake.jpg"))).getImage();

    //托盘图标
    public static Image image = Toolkit.getDefaultToolkit().getImage(Data.class.getResource("/com/wang/resources/TheSnake.jpg"));

    //蛇头图片
    public static ImageIcon up = new ImageIcon(Objects.requireNonNull(Data.class.getResource("/com/wang/resources/up.jpg")));
    public static ImageIcon left = new ImageIcon(Objects.requireNonNull(Data.class.getResource("/com/wang/resources/left.jpg")));
    public static ImageIcon right = new ImageIcon(Objects.requireNonNull(Data.class.getResource("/com/wang/resources/right.jpg")));
    public static ImageIcon down = new ImageIcon(Objects.requireNonNull(Data.class.getResource("/com/wang/resources/down.jpg")));

    //蛇身图片
    public static ImageIcon body = new ImageIcon(Objects.requireNonNull(Data.class.getResource("/com/wang/resources/body.jpg")));

    //食物图片
    public static ImageIcon food = new ImageIcon(Objects.requireNonNull(Data.class.getResource("/com/wang/resources/food.jpg")));
    public static ImageIcon Dfood = new ImageIcon(Objects.requireNonNull(Data.class.getResource("/com/wang/resources/Dfood.jpg")));
    public static ImageIcon poison = new ImageIcon(Objects.requireNonNull(Data.class.getResource("/com/wang/resources/poison.jpg")));
    public static ImageIcon reverse = new ImageIcon(Objects.requireNonNull(Data.class.getResource("/com/wang/resources/reverse.jpg")));
}
