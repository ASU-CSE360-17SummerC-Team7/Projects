package CSE360;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Team7 extends JPanel
{
    JLayeredPane layer;
    Proj1Panel p1;
    Proj2Panel p2;
    Team7Cover gear;
    Team7Ghost ghost;
    boolean initialState;

    public Team7()
    {
        initialState=true;
        this.setPreferredSize(new Dimension(300, 300));
        
        layer = new JLayeredPane();
        layer.setPreferredSize(new Dimension(300, 300));
        
        p1 = new Proj1Panel();
        p1.setSize(new Dimension(300, 300));
        
        p2 = new Proj2Panel();
        p2.setSize(new Dimension(300, 300));
        
        gear = new Team7Cover();
        ghost = new Team7Ghost();
        JButton toggle = new JButton("toggle");
        toggle.setBounds(200, 50, 50, 50);
        
        toggleListener t = new toggleListener();
        toggle.addActionListener(t);
        layer.add(toggle, new Integer(4));
        layer.add(gear, new Integer(3));
        layer.add(ghost, new Integer(2));
        layer.add(p1, new Integer(1));
        
        add(layer);

    }
    
    private class toggleListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if(initialState==true){ 
                layer.remove(p1);
                gear.setVisible(true);
                ghost.setVisible(true);
                startGhostMovement();
                layer.add(p2, new Integer(1));
                layer.revalidate();
                layer.repaint();
                initialState=false;
            }
            else {
                p2.DisplayGeoMenu();
            }
            
        }
    }
    
    private void startGhostMovement() {
        ghost.setVisible(true);
        Thread ta = new Thread(new ghostAnimationLoop(ghost));
        ta.start();
    }

}
