package CSE360;



import org.json.JSONException;
import org.json.JSONObject;
import org.json.*;

import java.io.FileReader;
import java.io.Reader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.net.URL;

import java.lang.Object;
import java.util.Date;
import java.sql.Timestamp;
import java.util.Date;


import java.awt.*;
import java.text.SimpleDateFormat;
import javax.swing.*;
import java.util.Date;

import java.awt.GridLayout;
import java.io.IOException;
import javax.swing.JFrame;
import org.json.JSONException;

import java.awt.Font;
import java.awt.font.*;

public class Team7WeatherPanel extends JPanel
{
    private WeatherInfo geoLocation;
    private ImageIcon weatherIcon;
    private JLabel weatherLabel;
    // Variables declaration - do not modify                     
    private javax.swing.JPanel IconImage;
    private javax.swing.JTextArea TextSummary;
    private javax.swing.JTextArea TextCloudCover;
    private javax.swing.JTextArea TextPrecipitationProbability;
    private javax.swing.JTextArea TextTemperature;
    private javax.swing.JTextArea TextHumidity;
    private static final String iconPath = "CSE360\\imagesTeam7\\";
    // End of variables declaration 
    /**
     * Creates new form WeatherPanel
     */
    public Team7WeatherPanel(double latitude, double longitude)  {
        geoLocation = new WeatherInfo(latitude,longitude);
        initComponents();
        setOpaque(false);
    }

    @SuppressWarnings("unchecked")
    private void initComponents()  {
        setBackground(Color.white);
        Font uniformFont = new Font("Courier",Font.TRUETYPE_FONT,8);
        TextSummary = new javax.swing.JTextArea(2,4);
        TextSummary.setFont(uniformFont);
        TextHumidity = new javax.swing.JTextArea(2,10);
        TextHumidity.setFont(uniformFont);
        TextTemperature = new javax.swing.JTextArea(2,10);
        TextTemperature.setFont(uniformFont);
        TextPrecipitationProbability = new javax.swing.JTextArea(2,10);
        TextPrecipitationProbability.setFont(uniformFont);
        TextCloudCover = new javax.swing.JTextArea(2,10);
        TextCloudCover.setFont(uniformFont);
        weatherIcon = new ImageIcon(iconPath+geoLocation.getWeatherFieldString("currently","icon")+".png");
        
        weatherLabel = new JLabel("", weatherIcon, JLabel.CENTER);
        weatherLabel.setIcon(weatherIcon);
        
        setLayout(new java.awt.GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx=0;c.gridy=0;
        c.gridheight=1;
        c.gridwidth=1;
        c.weighty = 0.333;
        c.weightx = 1;
        weatherLabel.setOpaque(true);
        weatherLabel.setBackground(Color.white);
        add(weatherLabel,c);
        c.gridx=0;c.gridy=1;
        TextSummary.setText(geoLocation.getWeatherFieldString("currently", "summary"));
        
        add(TextSummary,c);
        TextHumidity.setText(geoLocation.getWeatherFieldString("currently", "humidity")+"[Humidity]");
        c.gridx=0;c.gridy=2;
        add(TextHumidity,c);

        TextTemperature.setText(geoLocation.getWeatherFieldString("currently", "temperature")+"\u00b0"+" F[Temp]");
        c.gridx=0;c.gridy=3;
        add(TextTemperature,c);

        TextPrecipitationProbability.setText(geoLocation.getWeatherFieldString("currently", "precipProbability")+"[PrecipProb]");
        c.gridx=0;c.gridy=4;
        add(TextPrecipitationProbability,c);

        TextCloudCover.setText(geoLocation.getWeatherFieldString("currently", "cloudCover")+"[CloudCover]");
        c.gridx=0;c.gridy=5;
        add(TextCloudCover,c);
    }

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }
    public void updateWeatherPanel(double latitude,double longitude) { 
        geoLocation.UpdateGeoLocation(latitude, longitude);
        weatherLabel.setIcon(new ImageIcon(iconPath+geoLocation.getWeatherFieldString("currently","icon")+".png"));
        TextSummary.setText(geoLocation.getWeatherFieldString("currently", "summary"));
        TextHumidity.setText(geoLocation.getWeatherFieldString("currently", "humidity")+"\n[Humidity]");
        TextTemperature.setText(geoLocation.getWeatherFieldString("currently", "temperature")+"\u00b0"+" F[Temp]");
        TextPrecipitationProbability.setText(geoLocation.getWeatherFieldString("currently", "precipProbability")+"[PrecipProb]");
        TextCloudCover.setText(geoLocation.getWeatherFieldString("currently", "cloudCover")+"[CloudCover]");
    }
}

class WeatherInfo {
    private Timestamp curTime;
    private Date curDate;
    private JSONObject darksky;
    private double latitude;
    private double longitude;
    
    public WeatherInfo(double latitude, double longitude){
        this.latitude = latitude; this.longitude = longitude;
        curDate = new Date();
        curTime = new Timestamp(curDate.getTime());
        updateDarkSKYJSONObject();
    }
    public void UpdateGeoLocation(double latitude, double longitude){
        this.latitude = latitude; this.longitude = longitude;
        curDate = new Date();
        curTime = new Timestamp(curDate.getTime());
        updateDarkSKYJSONObject();
    }
    public String getLongitude() { 
        return darksky.getString("longitude");
    }
    public String getLatitude() { 
        return darksky.getString("latitude");
    }
    public String getTimezone() { 
        return darksky.getString("timezone");
    }
    private boolean isValid_TimeType(String timeType){ 
        if (    timeType.matches("currently")||
                timeType.matches("minutely")||
                timeType.matches("hourly")||
                timeType.matches("daily")
            ) { return true; }
        else { return false; }
    }
    private boolean isValid_WeatherInfoKey(String wkey){ 
        if(     wkey.matches("time")|| 
                wkey.matches("summary")|| 
                wkey.matches("icon")|| 
                wkey.matches("nearestStormDistance")|| 
                wkey.matches("precipIntensity")|| 
                wkey.matches("precipIntensityError")|| 
                wkey.matches("precipProbability")||
                wkey.matches("precipType")||
                wkey.matches("temperature")||
                wkey.matches("apparentTemperature")||
                wkey.matches("dewPoint")||
                wkey.matches("humidity")||
                wkey.matches("windSpeed")||
                wkey.matches("windBearing")||
                wkey.matches("visibility")||
                wkey.matches("cloudCover")||
                wkey.matches("pressure")||
                wkey.matches("ozone")
            ) { return true; }
        else { return false; }
    }
    private boolean isWeatherInfoKey_String(String wkey){ 
        if(
                wkey.matches("summary")|| 
                wkey.matches("icon")
            ) { return true; }
        else { return false; }
    }
    public String getWeatherFieldString(String timeType, String wkey){
        if ( !isValid_TimeType(timeType) ) { return "ERROR: Invalid timeType (valid: currently, minutely, hourly, daily)"; }
        else if ( !isValid_WeatherInfoKey(wkey)) {  return "ERROR: Invalid weather key";  }
        else { 
            if(isWeatherInfoKey_String(wkey)) { 
              return darksky.getJSONObject(timeType).getString(wkey); 
            }
            else { 
                return String.valueOf(darksky.getJSONObject(timeType).getDouble(wkey));
            }
        }
    }
    
    private JSONObject getLatestWeatherJSON() {
        curDate = new Date();
        curTime = new Timestamp(curDate.getTime());
        updateDarkSKYJSONObject();
        return darksky;
    }
    private void updateDarkSKYJSONObject() { 
        String ourAPIKey = "fc32de3a545df155ae6e26a367e4259f";
        String darkSKYURL_ForecastRequest = "https://api.darksky.net/forecast/"+ourAPIKey+"/"+String.valueOf(this.latitude)+","+String.valueOf(this.longitude);
        //System.out.print(darkSKYURL_ForecastRequest);
        try { darksky = readJSONFromURL(darkSKYURL_ForecastRequest); }
        catch(IOException e) {} // ignoring exceptions for now
    }
    // reusing the provided StringBuilder method provided by Rao 
    //[originally in CSE360 L06 - recitation 02.pdf, Main.java example]
    private static String readAll(Reader rd) throws IOException{
        StringBuilder sb = new StringBuilder();
        int cp; 
        while((cp=rd.read())!=-1) { // while reader is not at end of buffer
            sb.append((char)cp); // append the current character to stringbuilder
           // System.out.print((char)cp);
        }
        return sb.toString(); //need actual string within buffer, returning that
    }
    //based on the readJsonFromURL method provided by Rao
    //[originally in CSE360 L06 - recitation 02.pdf, Main.java example]
    private static JSONObject readJSONFromURL(String url) throws IOException, JSONException { 
        try (InputStream is = new URL(url).openStream() // opens URL stream
        ) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is,Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            is.close();
            return json;
        } 
    } 


    private JSONObject getCurrently() {
        return darksky.getJSONObject("currently");
    }
    private JSONObject getMinutely() {
        return darksky.getJSONObject("minutely");
    }
    private JSONObject getHourly() {
        return darksky.getJSONObject("hourly");
    }
    private JSONObject getDaily() {
        return darksky.getJSONObject("daily");
    }
    private JSONObject getAlerts() {
        return darksky.getJSONObject("alerts");
    }
    private JSONObject getFlags() {
        return darksky.getJSONObject("flags");
    }

}
