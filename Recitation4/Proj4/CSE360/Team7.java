package CSE360;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Team7 extends JPanel
{
    JLayeredPane layer;
    Team7Cover p1;
    Team7Proj2Panel p2;
    Team7OverlayObject gear;
    Team7Ghost ghost;
    boolean initialState;
    private static int xbound=300;
    private static int ybound=200;

    public Team7()
    {
        initialState=true;
        this.setPreferredSize(new Dimension(xbound, ybound));
        setOpaque(false);
        layer = new JLayeredPane();
        layer.setPreferredSize(new Dimension(xbound, ybound));
        
        p1 = new Team7Cover();
        p1.setSize(new Dimension(xbound, ybound));
        
        p2 = new Team7Proj2Panel();
        p2.setSize(new Dimension(xbound, ybound));
        
        gear = new Team7OverlayObject(xbound, ybound);
        ghost = new Team7Ghost(xbound, ybound);
        gear.addMouseListener(new MouseAdapter(){
          @Override
          public void mouseClicked(MouseEvent e) { 
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
        });
        JButton toggle = new JButton("toggle");
        toggle.setBounds(200, 50, 50, 50);
        
        toggleListener t = new toggleListener();
        toggle.addActionListener(t);
//        layer.add(toggle, new Integer(4));
        layer.add(gear, new Integer(4));
        layer.add(p1, new Integer(3));
        layer.add(ghost, new Integer(2));
        
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
