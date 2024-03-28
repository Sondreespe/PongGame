import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Paddle extends Rectangle{

    int id;
    int yVelocity;
    int speed = 10;

    Paddle(int x, int y, int PADDLE_WIDTH, int PADDLE_HEIGHT, int id ){
        super(x,y,PADDLE_WIDTH,PADDLE_HEIGHT);
        this.id = id;  
        
    }

    public void keyPressed(KeyEvent e){
        switch(id) {
        case 1:
            if(e.getKeyCode()==KeyEvent.VK_W){
                setYDirectionPaddle(-speed);
                movePaddle();
            }
            if(e.getKeyCode()==KeyEvent.VK_S){
                setYDirectionPaddle(speed);
                movePaddle();
            }
            break;
        case 2:
            if(e.getKeyCode()==KeyEvent.VK_UP){
                setYDirectionPaddle(-speed);
                movePaddle();
            }
        if(e.getKeyCode()==KeyEvent.VK_DOWN){
                setYDirectionPaddle(speed);
                movePaddle();
            }
            break;
        }
    }

    public void keyReleased(KeyEvent e){
        switch(id) {
        case 1:
            if(e.getKeyCode()==KeyEvent.VK_W){
                setYDirectionPaddle(0);
                movePaddle();
            }
            if(e.getKeyCode()==KeyEvent.VK_S){
                setYDirectionPaddle(0);
                movePaddle();
            }
            break;
        case 2:
            if(e.getKeyCode()==KeyEvent.VK_UP){
                setYDirectionPaddle(0);
                movePaddle();
            }
            if(e.getKeyCode()==KeyEvent.VK_DOWN){
                setYDirectionPaddle(0);
                movePaddle();
            }
            break;
        }
    }
    

    public void setYDirectionPaddle(int yDirection){
        yVelocity = yDirection;
    }
    
    public void movePaddle(){
        y = y + yVelocity;
    }

    public void draw(Graphics g){
        if (id == 1)
            g.setColor(Color.BLUE);
    
        else
            g.setColor(Color.RED);

        g.fillRect(x, y, width, height);
    }
    
}
