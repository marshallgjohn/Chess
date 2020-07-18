package org.johngmarshall;

import javax.swing.*;

public class Chess extends JFrame{
    public static void main(String[] args) {
        JFrame frame = new JFrame("Chess");

        frame.setSize(450,550);
        frame.setContentPane(new Board());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
