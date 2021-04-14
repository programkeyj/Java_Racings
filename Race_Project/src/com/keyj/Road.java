package com.keyj;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Road extends JPanel implements ActionListener, Runnable {
    Timer mainTimer = new Timer(20, this);
    public double nightInt;
    public boolean night = false;
    static int EnemyCar = 0;
    Image Road = new ImageIcon("rec/Road.png").getImage();
    Image nightRoad = new ImageIcon("rec/NightRoad.jpg").getImage();
    Image img;
    Image speed = new ImageIcon("rec/speedometr.png").getImage();
    Player p = new Player();
    Thread enemiseFactory = new Thread(this);
    List<Enemy> enemies = new ArrayList();

    public Road() {
        this.mainTimer.start();
        this.enemiseFactory.start();
        this.addKeyListener(new Road.myKeyAdapter());
        this.setFocusable(true);
    }

    public void paint(Graphics g) {
        g = (Graphics2D)g;
        if (this.nightInt >= 20.0*2) {
            this.night = false;
            this.nightInt = 0.0;
        }

        if (this.nightInt >= 20.0) {
            this.night = true;
        }

        this.nightInt += 0.02;
        if (night) {
            img = Road;
        } else {
            img = nightRoad;
        }

        g.drawImage(img, p.layer1, 0, (ImageObserver)null);
        g.drawImage(img, this.p.layer2, 0, (ImageObserver)null);
        g.drawImage(p.img, p.x, p.y, (ImageObserver)null);
        g.drawImage(this.speed, 80, 284, (ImageObserver)null);
        double V = (double)(300 / Player.MAX_V * this.p.v);
        g.setColor(Color.GREEN);
        Font font = new Font("Arial", 1, 35);
        g.setFont(font);
        g.drawString("скорость:  " + V + "км/ч", 100, 332);
        if (p.Light == true){
            Font fnitro = new Font("Arial", 2, 20);
            g.setFont(fnitro);
            g.setColor(Color.orange);
            g.drawString("LIGHT ON", 300, 355);
        }
        if (Player.nitro) {
            Font fnitro = new Font("Arial", 2, 20);
            g.setFont(fnitro);
            g.setColor(Color.CYAN);
            int procent = 1;
            if (this.p.Knitro == 5) {
                procent = 100;
            } else if (this.p.Knitro == 4) {
                procent = 80;
            } else if (this.p.Knitro == 3) {
                procent = 60;
            } else if (this.p.Knitro == 2) {
                procent = 30;
            } else if (this.p.Knitro == 1) {
                procent = 10;
            }

            g.drawString("NITRO TRUE " + procent + "%", 120, 355);
        }

        Iterator i = this.enemies.iterator();

        while(true) {
            while(i.hasNext()) {
                Enemy e = (Enemy)i.next();
                if (e.x < 2500 && e.x > -2250) {
                    e.move();
                    g.drawImage(e.img, e.x, e.y, (ImageObserver)null);
                } else {
                    i.remove();
                }
            }

            return;
        }
    }

    public void actionPerformed(ActionEvent e) {
        this.p.move();
        this.repaint();
        this.testColisionWithEnemy();
    }

    public void testColisionWithEnemy() {
        Iterator i = this.enemies.iterator();

        while(i.hasNext()) {
            Enemy e = (Enemy)i.next();
            if (this.p.getRect().intersects(e.getRect())) {
                e.img = (new ImageIcon("rec/Boom.png")).getImage();
                StartSound gaz = new StartSound();
                gaz.file = new File("rec/boom.wav");
                gaz.start();
            }
        }

    }

    public void run() {
        while(true) {
            Random rand = new Random();

            try {
                Thread.sleep((long)rand.nextInt(2000));
                int y = 340 + rand.nextInt(121);
                EnemyCar++;
                if (EnemyCar == 4) {
                    EnemyCar = 0;
                }

                this.enemies.add(new Enemy(1300, y, rand.nextInt(41), EnemyCar, this));
            } catch (InterruptedException var3) {
                var3.printStackTrace();
            }
        }
    }

    private class myKeyAdapter extends KeyAdapter {
        private myKeyAdapter() {
        }

        public void keyPressed(KeyEvent e) {
            Road.this.p.keyPressed(e);
        }

        public void keyReleased(KeyEvent e) {
            Road.this.p.keyReleased(e);
        }
    }
}