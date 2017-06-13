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
public class OverlayObject extends JLabel{
    private static final String iconPath="CSE360\\imagesTeam7\\gear.png";
    public OverlayObject() {
 //       JFrame upperFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        System.out.println("Getting image from "+iconPath);
        this.setBounds(150,100,200,200); //upperFrame.getSize().width/2,upperFrame.getSize().height,512,512);
        this.setIcon(new ImageIcon(iconPath,"Gear Icon"));
        setVisible(true);
        setOpaque(false);
    }
        
}
