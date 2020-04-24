package pers.ylw.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

//游戏的面板
public class GamePanel extends JPanel implements KeyListener, ActionListener {

    //定义蛇的数据结构
    int length; //蛇的长度
    int[] snakeX = new int[600]; //蛇的x坐标,蛇的一截尺寸是25*25
    int[] snakeY = new int[500]; //蛇的x坐标
    String direction; //蛇头方向

    //事物坐标
    int foodX;
    int foodY;
    Random random = new Random(); //随机数

    int score; //游戏分数

    //游戏当前的状态：开始、停止、失败
    boolean isStart = false; //默认没有开始
    boolean isFail = false; //默认没有失败

    //定时器 1000ms = 1s,控制蛇的移动速度
    Timer timer = new Timer(100,this); //0.1秒执行（刷新）一次


    //构造方法
    public GamePanel(){
        init();
        //获得焦点和键盘事件
        this.setFocusable(true); //获得焦点事件
        this.addKeyListener(this); //获得键盘监听器
        timer.start(); //游戏一开始，定时器就启动
    }

    //初始化一些数据
    public void init(){
        //蛇
        length = 3; //蛇有一个头，两节身体
        snakeX[0] = 100; snakeY[0] = 100; //蛇头的坐标
        snakeX[1] = 75; snakeY[1] = 100; //第一截身体的坐标
        snakeX[2] = 50; snakeY[2] = 100; //第二截身体的坐标
        direction = "R"; //蛇头方向向右

        //使用随机数生成食物坐标，食物图片的尺寸是25*25
        foodX = 25 + 25*random.nextInt(34);
        foodY = 75 + 25*random.nextInt(24);

        //分游戏数
        score = 0;
    }

    //绘制面板（画组件），游戏中所有的组件都是使用这个画笔来画的
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); //父类有用，可以清屏，不要删掉

        // 绘制静态面板
        this.setBackground(Color.WHITE); //设置背景颜色为黑色
        //参数解释(画在哪个面板上，用哪个画笔画，x坐标，y坐标)
        Data.header.paintIcon(this,g,25,11); //头部标题栏
        g.fillRect(25,75,850,600); //蛇运动的界面

        //绘制游戏分数
        g.setColor(Color.WHITE);
        g.setFont(new Font("微软雅黑",Font.BOLD,18));
        g.drawString("长度" + length,750,30);
        g.drawString("分数" + score,750,50);

        //绘制食物
        Data.food.paintIcon(this,g,foodX,foodY);

        //绘制小蛇
        //根据方向绘制，选择相应的蛇头进行绘制
        if (direction.equals("R")){
            Data.right.paintIcon(this,g,snakeX[0],snakeY[0]);
        }else if(direction.equals("L")){
            Data.left.paintIcon(this,g,snakeX[0],snakeY[0]);
        }else if(direction.equals("U")){
            Data.up.paintIcon(this,g,snakeX[0],snakeY[0]);
        }else if(direction.equals("D")){
            Data.down.paintIcon(this,g,snakeX[0],snakeY[0]);
        }
        //蛇的长度每变化一次，就重新绘制一次身体
        for (int i = 1; i < length; i++) {
            Data.body.paintIcon(this,g,snakeX[i],snakeY[i]); //蛇身体
        }

        //游戏停止状态,绘制提醒文字
        if (isStart == false){
            g.setColor(Color.white); //设置文字颜色
            //设置文字样式
            g.setFont(new Font("微软雅黑",Font.BOLD,40));
            g.drawString("按下空格开始游戏",300,300);
        }

        //游戏失败状态，绘制文字
        if (isFail){
            g.setColor(Color.red); //设置文字颜色
            //设置文字样式
            g.setFont(new Font("微软雅黑",Font.BOLD,40));
            g.drawString("游戏失败! 按下空格重新开始",300,300);
        }

    }

    //timer游戏相关事件监听，需要通过固定的时间来刷新，比如，1秒10次
    @Override
    public void actionPerformed(ActionEvent e) {
        if (isStart && isFail==false){ //如果游戏是开始状态且没有失败

            //吃食物, 蛇头和食物坐标重合
            if (snakeX[0] == foodX && snakeY[0] == foodY){
                length++; //蛇长度+1
                score = score +10; //分数+10
                //生成下一个食物，食物图片的尺寸是25*25
                foodX = 25 + 25*random.nextInt(34);
                foodY = 75 + 25*random.nextInt(24);
            }

            //蛇移动
            //蛇身移动
            for (int i = length-1; i > 0; i--) {
                snakeX[i] = snakeX[i-1];
                snakeY[i] = snakeY[i-1];
            }
            //根据方向对蛇头进行控制
            if (direction.equals("R")){
                snakeX[0] = snakeX[0]+25; //蛇头
                //边界判断
                if (snakeX[0]>850){
                    snakeX[0] = 25; //越界回图，撞墙不死
                }
            }else if (direction.equals("L")){
                snakeX[0] = snakeX[0]-25; //蛇头
                //边界判断
                if (snakeX[0]<25){
                    snakeX[0] = 850; //越界回图，撞墙不死
                }
            }else if (direction.equals("U")){
                snakeY[0] = snakeY[0]-25; //蛇头
                //边界判断
                if (snakeY[0]<75){
                    snakeY[0] = 650; //越界回图，撞墙不死
                }
            }else if (direction.equals("D")){
                snakeY[0] = snakeY[0]+25; //蛇头
                //边界判断
                if (snakeY[0]>650){
                    snakeY[0] = 75; //越界回图，撞墙不死
                }
            }

            //失败判定，撞到自己
            for (int i = 1; i < length; i++) { //每截身体和头进行判断
                if (snakeX[0]==snakeX[i] && snakeY[0]==snakeY[i]){
                    isFail = true;
                }
            }

            //重画
            repaint();
        }
        //timer.start(); //定时器开启
    }

    //键盘监听器,接口方法重写
    @Override
    public void keyPressed(KeyEvent e) {
        int KeyCode = e.getKeyCode(); //获得键盘按键是哪一个
        //控制游戏开始、停止、重来
        if (KeyCode == KeyEvent.VK_SPACE){ //如果按下的是空格
            if (isFail){ //如果是游戏失败
                //重新开始
                isFail = false;
                init(); //初始化数据
            } else{
                isStart = !isStart; //取反
                repaint(); //游戏状态变化，重画界面
            }

        }
        //控制小蛇移动方向,上下左右方向键
        if (KeyCode == KeyEvent.VK_UP){
            direction = "U";
        }else if (KeyCode == KeyEvent.VK_DOWN){
            direction = "D";
        }else if (KeyCode == KeyEvent.VK_LEFT){
            direction = "L";
        }else if (KeyCode == KeyEvent.VK_RIGHT){
            direction = "R";
        }

    }

    //这下面两个用不到
    @Override
    public void keyReleased(KeyEvent e) {

    }
    @Override
    public void keyTyped(KeyEvent e) {

    }
}
