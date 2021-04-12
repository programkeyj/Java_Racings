package com.keyj;

import java.awt.Rectangle;
import java.awt.Image;
import java.util.Random;
import javax.swing.ImageIcon;

public class Enemy {
    int x, y;
    int v;
    Image img_0 = new ImageIcon("rec/car1.png").getImage();
    Image img_1 = new ImageIcon("rec/car2.png").getImage();
    Image img_2 = new ImageIcon("rec/car3.png").getImage();
    Image img_3 = new ImageIcon("rec/car4.png").getImage();
    Image img;

    Road road;
    public Rectangle getRect(){
         return new Rectangle (x, y, 242, 95);
    }

    public Enemy(int x, int y, int v, int car, Road road){
         if (car == 0){
             img = img_0;
             this.y = 355;
         }
         else if (car == 1){
             img = img_1;
             this.y = 510;
         }
         else if (car == 2){
             img = img_2;
             this.y = 340;
         }
         else if (car == 3){
             img = img_3;
             this.y = 450;
         }
         this.x = x;

         this.v = v;
         this.road = road;
    }
    public void move(){
        x = x - road.p.v + v;

    }
}
