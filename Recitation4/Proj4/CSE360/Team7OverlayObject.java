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
public class Team7OverlayObject extends JLabel{
    private static final String iconPath="CSE360\\imagesTeam7\\gear.png";
    private static final int xscale=30;
    private static final int yscale=30;
    public Team7OverlayObject(int xbound, int ybound) {
 //       JFrame upperFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        System.out.println("Getting image from "+iconPath);
        this.setIcon(new ImageIcon((new ImageIcon(iconPath).getImage().getScaledInstance(xscale, yscale,
                java.awt.Image.SCALE_SMOOTH)),"GearIcon"));
        this.setBounds(xbound-xscale-10,ybound-yscale-10,xscale,yscale); //upperFrame.getSize().width/2,upperFrame.getSize().height,512,512);

        setVisible(true);
        setOpaque(false);
    }
        
}
