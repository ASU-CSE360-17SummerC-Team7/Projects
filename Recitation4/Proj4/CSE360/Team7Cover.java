package CSE360;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pdreiter
 */
public class Team7Cover extends JPanel{
    private JLabel settings;
    private static final String iconPath="CSE360\\imagesTeam7\\gear.png";
    public Team7Cover() {
 //       JFrame upperFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        settings = new JLabel ("",new ImageIcon(iconPath,"Gear Icon"),JLabel.CENTER);
        this.add(settings);
        this.setBounds(300,200,200,200); //upperFrame.getSize().width/2,upperFrame.getSize().height,512,512);
        setVisible(false);
        setOpaque(false);
        settings.addMouseListener(new MouseAdapter(){
          @Override
          public void mouseClicked(MouseEvent e) { 
              System.out.println("I Clicked in the Team7Cover JLABEL");
          }
        });
    }
    
    
}
