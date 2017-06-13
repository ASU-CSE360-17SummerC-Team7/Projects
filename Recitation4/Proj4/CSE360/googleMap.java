package CSE360;

import java.awt.*;
import javax.swing.*;

import java.io.FileReader;
import java.lang.String;
import java.io.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

class googleMap extends JPanel{
    double latitude,longitude;
    //constructor
    public googleMap(double latitude, double longitude)     {
    	this.latitude = latitude;
    	this.longitude = longitude;
    	
   
    	int zoom = 11;
    	
        try {
            String imageUrl = "https://maps.googleapis.com/maps/api/staticmap?" + "center=" + latitude + "," + longitude + 
            		"&zoom=" + zoom + "&size=640x640&maptype=road";
            String outputImageFile = "mycity.jpg";
            URL url = new URL(imageUrl);
            InputStream is = url.openStream();
            OutputStream os = new FileOutputStream(outputImageFile);

            byte[] b = new byte[2048];
            int length;

            while ((length = is.read(b)) != -1) {
                os.write(b, 0, length);
            }

            is.close();
            os.close();
        } catch (IOException e) {
            System.exit(1);
        }
        //add(new JLabel(new ImageIcon((new ImageIcon("mycity.jpg")).getImage().getScaledInstance(250, 250,
                //java.awt.Image.SCALE_SMOOTH))));
        //setVisible(true);
        add(new JLabel(new ImageIcon((new ImageIcon("mycity.jpg")).getImage().getScaledInstance(200, 200,
                java.awt.Image.SCALE_SMOOTH))));
        setVisible(true);
}
}