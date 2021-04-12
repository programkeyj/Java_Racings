package com.keyj;

import java.awt.*;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.ImageIcon;


import static com.keyj.Player.MAX_BOTTOM;
import static com.keyj.Player.MAX_TOP;

public class Road extends JPanel implements ActionListener, Runnable {

    Timer mainTimer = new Timer(20, this);

    public double nightInt;
    public boolean night = false;
    static int EnemyCar = 0;
    Image nightRoad = new ImageIcon("rec/NightRoad").getImage();
    Image img;// = new ImageIcon("rec/NightRoad.png").getImage();
    Image speed = new ImageIcon("rec/speedometr.png").getImage();
    Player p = new Player();
    Thread enemiseFactory = new Thread(this);
    List<Enemy> enemies = new ArrayList<Enemy>();


    public Road(){
        mainTimer.start();
        enemiseFactory.start();
        addKeyListener(new myKeyAdapter());
        setFocusable(true);
    }
    private class myKeyAdapter extends KeyAdapter{
        public void keyPressed(KeyEvent e){
              p.keyPressed(e);
        }
        public void keyReleased(KeyEvent e){
              p.keyReleased(e);
        }
    }
    public void paint(Graphics g){

        g = (Graphics2D) g;
        if (nightInt >= 20.0 * 2){
            night = false;
            nightInt = 0.0;
        }
        if (nightInt >= 20.0) {
            night = true;
        }
        nightInt += 0.02;

        if (night == true) {
            img = new ImageIcon("rec/NightRoad.png").getImage();
        }
        else {
            img = new ImageIcon("rec/Road.png").getImage();
        }
        g.drawImage(img, p.layer1, 0, null);
        g.drawImage(img, p.layer2, 0, null);
        g.drawImage(p.img, p.x, p.y, null);
        g.drawImage(speed, 80, 284, null);
        double V = (300 / Player.MAX_V) * p.v;
        g.setColor(Color.GREEN);
        Font font = new Font("Arial", Font.BOLD, 35);
        g.setFont(font);
        g.drawString("скорость:  "+ V +"км/ч", 100, 332);
        if (Player.nitro == true){
            Font fnitro = new Font("Arial", Font.ITALIC, 20);
            g.setFont(fnitro);
            g.setColor(Color.CYAN);
            int procent = 1;
            if (p.Knitro == 5){
                procent = 100;
            }
            else if (p.Knitro == 4){
                procent = 80;
            }
            else if (p.Knitro == 3){
                procent = 60;
            }
            else if (p.Knitro == 2){
                procent = 30;
            }
            else if (p.Knitro == 1){
                procent = 10;
            }
            g.drawString("NITRO TRUE "+procent+"%", 180, 355);
        }
        Iterator<Enemy> i = enemies.iterator();
        while(i.hasNext()){
              Enemy e = i.next();
              if (e.x >= 2400 || e.x <= -2400){
                  i.remove();
              }else {
                  e.move();
                  g.drawImage(e.img, e.x, e.y, null);
              }
        }
    }
    public void actionPerformed(ActionEvent e){
         p.move();

         repaint();
         testColisionWithEnemy();



    }
    public void testColisionWithEnemy(){
        Iterator<Enemy> i = enemies.iterator();
        while (i.hasNext()){
            Enemy e = i.next();
            if (p.getRect().intersects(e.getRect())){
                e.img = new ImageIcon("rec/Boom.png").getImage();
                StartSound gaz = new StartSound();
                gaz.file = new File("rec/boom.wav");
                gaz.start();

            }
        }
    }
    @Override
    public void run(){
        while(true){
            Random rand = new Random();
            try {
                Thread.sleep(rand.nextInt(2000));
                int y = MAX_TOP + rand.nextInt(MAX_BOTTOM - MAX_TOP);
                EnemyCar++;
                if (EnemyCar == 4){EnemyCar = 0;}
                enemies.add(new Enemy(1300,
                                      y,
                                      rand.nextInt(41),
                                      EnemyCar,
                                      this));
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

}
