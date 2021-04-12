package com.keyj;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        JFrame race = new JFrame("Racings");
        race.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        race.setSize(1200, 640);

        race.add(new Road());

        race.setResizable(false);

        race.setVisible(true);
    }
}
