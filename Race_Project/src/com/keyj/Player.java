package com.keyj;

import sun.awt.SunHints;

import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Player {
    public static boolean nitro = false;
    public int Knitro = 5;
    public static  int MAX_V = 50;
    public static final int MAX_TOP = 340;
    public static final int MAX_BOTTOM = 461;
    public boolean Light = false;
    public int LightI = 0;

    Image img_c = new ImageIcon("rec/player.png").getImage();
    Image img_l = new ImageIcon("rec/player_left.png").getImage();
    Image img_r = new ImageIcon("rec/player_right.png").getImage();
    Image img_c_l = new ImageIcon("rec/playerLight.png").getImage();
    Image img_l_l = new ImageIcon("rec/player_leftLight.png").getImage();
    Image img_r_l = new ImageIcon("rec/player_rightLight.png").getImage();
    Image img = img_c;

    public Rectangle getRect(){
        return new Rectangle (x, y, 220, 85);
    }

    int v = 0, dv = 0, s = 0;
    int layer1 = 0, layer2 = 1365;
    int x = 100, y = 510;
    int dy = 0;
    public void move(){
        s += v;
        v += dv;
        if (v <= 0)v =0;
        if (v >= MAX_V) v = MAX_V;

        y -= dy;
        if (y <= MAX_TOP) y = MAX_TOP;
        if (y >= MAX_BOTTOM) y = MAX_BOTTOM;
        if (layer2 - v <= 0){
            layer1 = 0;
            layer2 = 1365;
        }
        else {
            layer1 -= v;
            layer2 -= v;
        }
    }

    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_RIGHT){
               dv = 1;
              StartSound gaz1 = new StartSound();
              gaz1.file = new File("rec/gaz.wav");
              gaz1.start();
        }
        if (key == KeyEvent.VK_LEFT){
            dv = -1;
        }
        if (key == KeyEvent.VK_UP){
            dy = 8;
            if (Light){
                img = img_l_l;
            }
            else {
                img = img_l;
            }
        }
        if (key == KeyEvent.VK_DOWN){
            dy = -8;
            if (Light == true){
                img = img_r_l;
            }
            else {
                img = img_r;
            }
        }
        if (key == KeyEvent.VK_SHIFT){
            nitro = true;
            Knitro--;

            StartSound gaz = new StartSound();
            gaz.file = new File("rec/start.wav");
            gaz.start();
            if (Light == true){
                img = img_l_l;
            }
            else {
                img = img_l;
            }
            dv = 3;
            MAX_V = 100;
            dv = 1;
            if (Knitro <= 0){
                nitro = false;
                dv = 0;
            }
        }
        if (key == KeyEvent.VK_SPACE){
            StartSound gaz1 = new StartSound();
            gaz1.file = new File("rec/shift.wav");
            gaz1.start();
            if (Light){
                img = img_l_l;
            }
            else {
                img = img_l;
            }
            dv = -1;
        }
        if (key == KeyEvent.VK_L){
            LightI++;
            System.out.println(LightI);
            if (LightI == 1) {
                Light = true;
                img = img_c_l;
            }
            if (LightI >= 2){
                LightI = 0;
                Light = false;
                img = img_c;
            }
        }

    }
    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_LEFT){
            dv = 0;
            StartSound edem = new StartSound();
            edem.file = new File("rec/start.wav");
            edem.start();


        }
        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN){
            dy = 0;
            if (Light) {
                img = img_c_l;
            }
            else{
                img = img_c;
            }
        }
        if (key == KeyEvent.VK_SHIFT){
            nitro = false;
            if (Knitro <= 0){
                Knitro = 6;
            }
            dv = 0;
            if (Light) {
                img = img_c_l;
            }
            else{
                img = img_c;
            }
        }
        if (key == KeyEvent.VK_SPACE){
            if (Light){
                img = img_c_l;
            }
            else {
                img = img_c;
            }
            dv++;
        }
        if (key == KeyEvent.VK_L){}
    }

}
