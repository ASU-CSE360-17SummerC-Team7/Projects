package CSE360;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Proj2Panel extends JPanel{
    String result;
    Object[] cityList = {"Tempe", "New York", "LA", "Chicago", "Houston", "Dallas", "Austin", "Detroit", "Boston", "Seattle"};
    double latitude = 33.4255, longitude = -111.9400;
    WeatherPanel weather;
    googleMap map;
    
    Proj2Panel()
    {
        GridLayout grid = new GridLayout(1, 2);
        setLayout(grid);
        setVisible(true);
        
//        JButton choosingCity = new JButton("Choose City");
//        choosingCity.addActionListener(new cityChoosingListener());
//        add(choosingCity);
        
        
        map = new googleMap(latitude, longitude);
        add(map);
        
        weather = new WeatherPanel(latitude, longitude);
        add(weather);       
    }
    private class cityChoosingListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            result = (String)JOptionPane.showInputDialog(
                    null,
                    "Select a city:",
                    "",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    cityList,
                    "Tempe");
            
            if(result.equals("Tempe"))
            {
                latitude = 33.4255;
                longitude = -111.9400;
            }
            else if(result.equals("New York"))
            {
                latitude = 40.7128;
                longitude = -74.0059;
            }
            else if(result.equals("LA"))
            {
                latitude = 34.0522;
                longitude = -118.2437;
            }
            else if(result.equals("Chicago"))
            {
                latitude = 41.8781;
                longitude = -87.6298;
            }
            else if(result.equals("Houston"))
            {
                latitude = 29.7604;
                longitude = -95.3698;
            }
            else if(result.equals("Dallas"))
            {
                latitude = 32.7767;
                longitude = -96.7970;
            }
            else if(result.equals("Austin"))
            {
                latitude = 30.2672;
                longitude = -97.7431;
            }
            else if(result.equals("Detroit"))
            {
                latitude = 42.3314;
                longitude = -83.0458;
            }
            else if(result.equals("Boston"))
            {
                latitude = 42.3601;
                longitude = -71.0589;
            }
            else
            {
                latitude = 47.6062;
                longitude = -122.3321;
            }
            
            remove(map);
            map = new googleMap(latitude, longitude);
            add(map);
            
            //remove(weather);
            //weather = new WeatherPanel(latitude, longitude);
            //add(weather);
            
            revalidate();
            repaint();
            startGhostMovement();
        }

        private void startGhostMovement() {
            Team7Ghost u = new Team7Ghost();
            Thread ta = new Thread(new ghostAnimationLoop(u.getGhostAnimationLabel()));
//        tl.start();
        ta.start();
        }
    }
}
