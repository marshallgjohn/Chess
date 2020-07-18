package org.johngmarshall;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JPanel {
    public MainMenu (JFrame frame) {

        setLayout(new BorderLayout());
        JButton singleButton = new JButton("SINGLEPLAYER");
        JButton multiButton = new JButton("MULTIPLAYER");
        JLabel text = new JLabel("Play chess!");
        add(text,BorderLayout.NORTH);
        add(singleButton,BorderLayout.CENTER);
        add(multiButton,BorderLayout.SOUTH);

        singleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.removeAll();
                frame.setContentPane(new Board());
                repaint();
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {

    }
}
