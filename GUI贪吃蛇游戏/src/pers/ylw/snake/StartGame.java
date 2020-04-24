package pers.ylw.snake;

import javax.swing.*;

//游戏的主启动类
public class StartGame {
    public static void main(String[] args) {
        JFrame jFrame = new JFrame();

        jFrame.setBounds(10,10,900,720); //设置窗口诞生位置和窗口大小
        jFrame.setResizable(false); //设置窗口大小不可变
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置点击关闭窗口

        //游戏的界面都在面板上
        jFrame.add(new GamePanel());

        jFrame.setVisible(true); //设置窗口可见
    }
}
