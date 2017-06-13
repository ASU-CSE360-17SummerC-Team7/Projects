package CSE360;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Team7 extends JPanel
{
    JLayeredPane layer;
    Proj1Panel p1;
    Proj2Panel p2;
    
    public Team7()
    {
        this.setPreferredSize(new Dimension(300, 100));
        
        layer = new JLayeredPane();
        layer.setPreferredSize(new Dimension(300, 200));
        
        p1 = new Proj1Panel();
        p1.setSize(new Dimension(300, 200));
        
        p2 = new Proj2Panel();
        p2.setSize(new Dimension(300, 200));
        
        JButton toggle = new JButton("toggle");
        toggle.setBounds(200, 50, 50, 50);
        
        toggleListener t = new toggleListener();
        toggle.addActionListener(t);
        
        layer.add(p1, new Integer(1));
        layer.add(toggle, new Integer(2));
        
        add(layer);
    }
    private class toggleListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            layer.remove(p1);
            layer.add(p2, new Integer(1));
            layer.revalidate();
            layer.repaint();
        }
    }
    
}
