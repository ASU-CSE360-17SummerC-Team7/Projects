package CSE360;

import java.awt.*;
import javax.swing.*;

public class CSE360 {

    public static void main(String[] args) {
        JFrame u = new JFrame();
        u.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Team7 p = new Team7();
        u.add(p);
        
        u.pack();
        u.setVisible(true);
    }
}
