import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable{

    // all static final instansce varibles for the gameframe
    static final int GAME_WIDTH = 1000;
    static final int GAME_HEIGHT = (int)(GAME_WIDTH * (0.555)); //The height is multiplied by a ratio of 0.555 to the width
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    static final int BAll_DIAMETER = 20;
    static final int PADDLE_WIDTH = 25;
    static final int PADDLE_HEIGHT = 100;

    Thread gameThread;
    Image image;
    Graphics graphics;
    Random random;
    Paddle paddle1;
    Paddle paddle2;
    Ball ball;
    Score score;

    GamePanel(){

        newPaddles();
        newBall();
        score = new Score(GAME_WIDTH,GAME_HEIGHT);
        this.setFocusable(true);
        this.addKeyListener(new ActionListener());
        this.setPreferredSize(SCREEN_SIZE);

        gameThread = new Thread(this);
        gameThread.start();

    }

    public void newBall(){
        random = new Random();
        ball = new Ball((GAME_WIDTH/2)-(BAll_DIAMETER/2),random.nextInt(GAME_HEIGHT-BAll_DIAMETER),BAll_DIAMETER,BAll_DIAMETER);
    }

    public void newPaddles(){
        paddle1 = new Paddle(0,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,1);
        paddle2 = new Paddle(GAME_WIDTH-PADDLE_WIDTH,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,2);
    }

    /**
     * Paints the game
     * @param g
     */
    public void paint(Graphics g){
        image = createImage(getWidth(),getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image,0,0,this);

    }

    public void draw(Graphics g){
       paddle1.draw(g);
       paddle2.draw(g);
       ball.draw(g);
       score.drawScore(g);
    }

    public void move(){
        paddle1.movePaddle();
        paddle2.movePaddle();
        ball.move();
        

    }

    public void checkClash(){
        //stops paddles at window edges
        if(paddle1.y <= 0)
            paddle1.y=0; 
        if(paddle1.y >=(GAME_HEIGHT - PADDLE_HEIGHT))
            paddle1.y = GAME_HEIGHT - PADDLE_HEIGHT;

        if(paddle2.y <= 0)
            paddle2.y=0;
        if(paddle2.y >= (GAME_HEIGHT - PADDLE_HEIGHT))
            paddle2.y = GAME_HEIGHT - PADDLE_HEIGHT;

        //bounce the ball off the frame
        if (ball.y <= 0)
            ball.setYDirection(-ball.yVelocity);
        if ( ball.y >= GAME_HEIGHT-BAll_DIAMETER)
            ball.setYDirection(-ball.yVelocity);

        //bounces ball of the paddle
        if(ball.intersects(paddle1)){
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.xVelocity++;// increases the speed if the ball hits the padle
            if(ball.yVelocity >0)
                ball.yVelocity ++;//increases the speed if the ball hits the padle
            else
                ball.yVelocity--;
            ball.setXDirection(ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }
        if(ball.intersects(paddle2)){
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.xVelocity++;// increases the speed if the ball hits the padle
            if(ball.yVelocity >0)
                ball.yVelocity ++;//increases the speed if the ball hits the padle
            else
                ball.yVelocity--;
            ball.setXDirection(-ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }

        //gives the player 1 point and creates new paddle and ball
        if(ball.x <= 0){
            score.player2++;
            newPaddles();
            newBall();
            System.out.println("Player 2 score: " + score.player2);
        }
        if(ball.x >= GAME_WIDTH-BAll_DIAMETER){
            score.player1++;
            newPaddles();
            newBall();
            System.out.println("Player 1 score : " + score.player1);
        }
        
       
            

    }

    public void run(){
        //loop
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double nanoSeconds = 1000000000/ amountOfTicks;
        double delta = 0;

        while(true){
            long now = System.nanoTime();
            delta += (now-lastTime)/nanoSeconds;
            lastTime = now;
            if(delta >= 1){
                move();
                checkClash();
                repaint();
                delta--;
            }
        }
    }

    public class ActionListener extends KeyAdapter{

        public void keyPressed(KeyEvent e){
            paddle1.keyPressed(e);
            paddle2.keyPressed(e);
        }

        public void keyReleased(KeyEvent e){
            paddle1.keyReleased(e);
            paddle2.keyReleased(e);
        }
    }
}
