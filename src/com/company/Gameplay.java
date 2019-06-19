package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Gameplay extends JPanel implements KeyListener, ActionListener{

    private int[] snakeXlength = new int[750];
    private int[] snakeYlength = new int[750];

    private int lengthOfSnake = 3;

    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;

    private ImageIcon rightmouth;
    private ImageIcon upmouth;
    private ImageIcon downmouth;
    private ImageIcon leftmouth;

    private int scores = 0;
    private int moves = 0;
    private boolean gameOver = false;

    private Timer timer;
    private int delay = 100;
    private ImageIcon snakeimage;

    private int[] enemyXpos = {25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625,650,675,700,725,750,775,800,825,850};
    private int[] enemyYpos = {75, 100, 125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625};

    private ImageIcon enemyImage;
    private Random random = new Random();

    private int xPos = random.nextInt(enemyXpos.length);
    private int yPos = random.nextInt(enemyYpos.length);


    private ImageIcon titleImage;

    public Gameplay(){
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g){

        if(moves == 0){

            snakeXlength[2] = 50;
            snakeXlength[1] = 75;
            snakeXlength[0] = 100;

            snakeYlength[2] = 100;
            snakeYlength[1] = 100;
            snakeYlength[0] = 100;

        }

       // draw title image border
       g.setColor(Color.white);
       g.drawRect(24, 10, 851, 55);

       // draw the title
        titleImage = new ImageIcon(getClass().getResource("/images/snaketitle.jpg"));
        titleImage.paintIcon(this, g, 25, 11);

        // draw the border for the gameplay
        g.setColor(Color.white);
        g.drawRect(25, 74, 851, 577);

        g.setColor(Color.black);
        g.fillRect(25,75,850,575);

        // draw scores
        g.setColor(Color.white);
        g.setFont(new Font("arial", Font.PLAIN, 14));
        g.drawString("Scores: " + scores, 780, 30);

        // draw length of snake
        g.setColor(Color.white);
        g.setFont(new Font("arial", Font.PLAIN, 14));
        g.drawString("Length: " + lengthOfSnake, 780, 50);

        rightmouth = new ImageIcon(getClass().getResource("/images/rightmouth.png"));
        rightmouth.paintIcon(this, g, snakeXlength[0], snakeYlength[0]);

        for(int a = 0; a < lengthOfSnake; a++){
            if(a == 0 && right){
                rightmouth = new ImageIcon(getClass().getResource("/images/rightmouth.png"));
                rightmouth.paintIcon(this, g, snakeXlength[a], snakeYlength[a]);

            }

            if(a == 0 && left){
                leftmouth = new ImageIcon(getClass().getResource("/images/leftmouth.png"));
                leftmouth.paintIcon(this, g, snakeXlength[a], snakeYlength[a]);

            }

            if(a == 0 && up){
                upmouth = new ImageIcon(getClass().getResource("/images/upmouth.png"));
                upmouth.paintIcon(this, g, snakeXlength[a], snakeYlength[a]);

            }

            if(a == 0 && down){
                downmouth = new ImageIcon(getClass().getResource("/images/downmouth.png"));
                downmouth.paintIcon(this, g, snakeXlength[a], snakeYlength[a]);

            }

            if(a != 0){
                snakeimage = new ImageIcon(getClass().getResource("/images/snakeimage.png"));
                snakeimage.paintIcon(this, g, snakeXlength[a], snakeYlength[a]);
            }
        }

        enemyImage = new ImageIcon(getClass().getResource("/images/enemy.png"));

        // Snake eats the enemy
        if((enemyXpos[xPos] == snakeXlength[0]) && (enemyYpos[yPos] == snakeYlength[0])){
            scores++;
            lengthOfSnake++;
            xPos = random.nextInt(enemyXpos.length);
            yPos = random.nextInt(enemyYpos.length);
        }

        // Check collision
        for (int i = 1; i < lengthOfSnake; i++){
            if(snakeXlength[i] == snakeXlength[0] && snakeYlength[i] == snakeYlength[0]){
                right = true;
                left = false;
                down = false;
                up = false;
                gameOver = true;

                g.setColor(Color.white);
                g.setFont(new Font("arial", Font.BOLD, 50));
                g.drawString("Game Over", 300, 300);

                g.setFont(new Font("arial", Font.BOLD, 20));
                g.drawString("Press Space to Restart", 330, 340);
            }
        }

        enemyImage.paintIcon(this, g, enemyXpos[xPos], enemyYpos[yPos]);

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if(!gameOver){
            if(right){
                for(int i = lengthOfSnake - 1; i >= 0; i--){
                    snakeYlength[i + 1] = snakeYlength[i];
                }

                for(int i = lengthOfSnake; i >= 0; i--){
                    if(i == 0){
                        snakeXlength[i] = snakeXlength[i] + 25;
                    } else {
                        snakeXlength[i] = snakeXlength[i - 1];
                    }

                    if(snakeXlength[i] > 850) {
                        snakeXlength[i] = 25;
                    }
                }
            }

            if(left){
                for(int i = lengthOfSnake - 1; i >= 0; i--){
                    snakeYlength[i + 1] = snakeYlength[i];
                }

                for(int i = lengthOfSnake; i >= 0; i--){
                    if(i == 0){
                        snakeXlength[i] = snakeXlength[i] - 25;
                    } else {
                        snakeXlength[i] = snakeXlength[i - 1];
                    }

                    if(snakeXlength[i] < 25) {
                        snakeXlength[i] = 850;
                    }
                }
            }

            if(up){
                for(int i = lengthOfSnake - 1; i >= 0; i--){
                    snakeXlength[i + 1] = snakeXlength[i];
                }

                for(int i = lengthOfSnake; i >= 0; i--){
                    if(i == 0){
                        snakeYlength[i] = snakeYlength[i] - 25;
                    } else {
                        snakeYlength[i] = snakeYlength[i - 1];
                    }

                    if(snakeYlength[i] < 75) {
                        snakeYlength[i] = 625;
                    }
                }
            }

            if(down){
                for(int i = lengthOfSnake - 1; i >= 0; i--){
                    snakeXlength[i + 1] = snakeXlength[i];
                }

                for(int i = lengthOfSnake; i >= 0; i--){
                    if(i == 0){
                        snakeYlength[i] = snakeYlength[i] + 25;
                    } else {
                        snakeYlength[i] = snakeYlength[i - 1];
                    }

                    if(snakeYlength[i] > 625) {
                        snakeYlength[i] = 75;
                    }
                }
            }
        }

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            right = true;
            moves = 0;
            scores = 0;
            lengthOfSnake = 3;
            gameOver = false;
            repaint();
        }

        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            moves++;
            right = true;
            if(!left){
                left = false;
                right = true;
            } else {
                right = false;
                left = true;
            }

            up = false;
            down = false;
        }

        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            moves++;
            left = true;
            if(!right){
                right = false;
                left = true;
            } else {
                right = true;
                left = false;
            }

            up = false;
            down = false;
        }

        if(e.getKeyCode() == KeyEvent.VK_UP){
            moves++;
            up = true;
            if(!down) {
                up = true;
                down = false;
            } else {
                up = false;
                down = true;
            }

            left = false;
            right = false;
        }

        if(e.getKeyCode() == KeyEvent.VK_DOWN){
            moves++;
            down = true;
            if(!up) {
                down = true;
                up = false;
            } else {
                down = false;
                up = true;
            }

            left = false;
            right = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
