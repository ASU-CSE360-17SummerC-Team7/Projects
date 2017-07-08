/**
 *
 * @author pdreiter
 * filename: CompanionBrain.java
 * creation date: 06/30/2017
 * description: 
 * Team members:
 *  - Chen Yang (cyang112@asu.edu)
 *  - Pemma Reiter (pdreiter@asu.edu)
 *  Notes from author: astah profession created the initial barebones class
 *
 */

package CSE360;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.*;




public class CompanionBrain extends Observable implements Observer {

	private boolean  needsHint;
	private String   currentMsg;
	private List<String> currentImg;
	private CompanionMood currentMood;
	private CompanionState currentState;
	private Time     currentTime;
	// removing to keep everything in the update function
	private Blackboard blackboard;
	// these class variables have been added since inception of class diagram

	private int idleCounter;

	private boolean isMoving;	
	private HashMap<CompanionMood,List<String>> moodMessages;
	private HashMap<CompanionMood,List<String>> moodImages;
	private HashMap<CompanionState,List<String>> stateMessages;	

	private double latitude;
	private double longitude;
	private String cityName;
	private String wsummary;
	private String wtemp;
	private String studentName;
	
	private double finalScore;

	private static final int M_IDLE_COUNT =10;

	private static final int N_IDLE_COUNT = 50;

	private static final int L_TIMES_Q_ANSWERED = 2;

	
    //-------------------------------------------------------------------------------------------------------	
	// The following CITY lookup was reused from Professor Javier Gonzalez-Sanchez's ControlCenter.java class
	private Hashtable<String, String> cityData = new Hashtable<String, String>();
	// The following CITY lookup was reused from Professor Javier Gonzalez-Sanchez's ControlCenter.java class	   
	public final static String[] CITIES = { "Tempe", "NY", "Bangalore",
	  "Venice", "Dublin", "SFO", "Berlin", "London",
	  "Mexico", "Delhi" };
	// The following CITY lookup was reused from Professor Javier Gonzalez-Sanchez's ControlCenter.java class	   
	private void initializeCityData() {
	  cityData.put(CITIES[0], "33.424564,-111.928001");    
	  cityData.put(CITIES[1], "40.730610,-73.935242");
	  cityData.put(CITIES[2], "12.972442,77.580643");
	  cityData.put(CITIES[3], "45.444958,12.328463");
	  cityData.put(CITIES[4], "53.350140,-6.266155" );
	  cityData.put(CITIES[5], "37.733795,-122.446747");
	  cityData.put(CITIES[6], "52.518623,13.376198" );
	  cityData.put(CITIES[7], "51.501476,-0.140634");
	  cityData.put(CITIES[8], "19.451054,-99.125519"); 
	  cityData.put(CITIES[9], "28.644800,77.216721");
	}
	// end of reuse
    //-------------------------------------------------------------------------------------------------------	
	
	// method: getRandomCity()
	// description: picks a random city from the list of CITIES
    private final String getRandomCity() {  
     		Random r = new Random();
     		// generate a random number between 0 and the # of moodMessages for that particular state
     		int rand = r.nextInt(CITIES.length); 
     		return CITIES[rand];
    }
    
	public CompanionBrain(String fPath, String sName) {
		moodMessages=new HashMap<CompanionMood,List<String>>();
		moodImages=new HashMap<CompanionMood,List<String>>();
		stateMessages=new HashMap<CompanionState,List<String>>();
		studentName=sName;
		initializeCityData();
		initializePhrases(fPath);
		initializeImages(fPath);
    	isMoving=false;idleCounter=0;
    	currentMood=CompanionMood.INDIFFERENT;
    	currentImg=moodImages.get(currentMood);
    	currentState=CompanionState.WELCOME;
    	currentMsg=getRandomStateResponse(currentState);
		cityName=getRandomCity();
		String[] geoLoc = cityData.get(cityName).split(",");
		latitude= Double.parseDouble(geoLoc[0]);
		longitude= Double.parseDouble(geoLoc[1]);
		Team7WeatherInfo weather= new Team7WeatherInfo(latitude,longitude);
		wsummary=weather.getWeatherFieldString("currently", "summary");
		wtemp=weather.getWeatherFieldString("currently", "temperature");
		currentTime = Time.valueOf(LocalDateTime.now().toLocalTime());
		blackboard = Blackboard.getInstance();

    	if(Project7Global.DEBUG&&Project7Global.DEBUG_LEVEL==0) {
			printAllMessages();
			printAllImages();
		}
		this.setChanged();
	}
	public CompanionMood getMood() {return currentMood;}
	public boolean getIsMoving() {return isMoving;}
	public  void updateIdleCounter() { idleCounter++; this.handleIdleEvent(idleCounter);}
	private void resetIdleCounter() { idleCounter=0; }
	public int getIdleCounter() { return idleCounter; }
	
 	private String getRandomStateResponse(CompanionState x) {
 		Project7Global.DEBUG_MSG(0, "CompanionBrain::getRandomState("+CompanionState.CompanionStateToString(x)+")");
 		Random r = new Random();
 		// generate a random number between 0 and the # of stateMessages for that particular state
 		int rand = r.nextInt(stateMessages.get(x).size()); 
 		return parseMessage(stateMessages.get(x).get(rand));
	}
 	
 	private String getRandomMoodResponse(CompanionMood x) {
 		Random r = new Random();
 		// generate a random number between 0 and the # of moodMessages for that particular state
 		int rand = r.nextInt(moodMessages.get(x).size()); 
 		return parseMessage(moodMessages.get(x).get(rand));
	}
 	
	public void updateForMood() {
	    currentMsg=getRandomMoodResponse(currentMood);
	    currentImg=moodImages.get(currentMood);
	}
    
	public void initializePhrases(String fPath) {
		// read in line
		String readState = "DEFAULT";
		String readMessage = "DEFAULT";
		// foreach line from File fPATH
        String ifile = fPath+"/companionMessages.txt";
        String l = "";
        String delim = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(ifile))) {
            while ((l = br.readLine()) != null) {
                String[] parsed = l.split(delim);
                readState=parsed[0];
                readMessage=parsed[1];
                Project7Global.DEBUG_MSG(0,"parsed line: ["+parsed[0]+"] ["+parsed[1]+"]");
                addPhraseEntry(readState,readMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Project7Global.DEBUG_MSG(0,"Error during handling of "+ifile);
        }

    }
	public void initializeImages(String fPath) {
		// read in line
		String readState = "DEFAULT";
		String readMessage = "DEFAULT";
		// foreach line from File fPATH
        String ifile = fPath+"/companionImages.txt";
        String l = "";
        String delim = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(ifile))) {
            while ((l = br.readLine()) != null) {
            	if(l.isEmpty()) { continue; }
                String[] parsed = l.split(delim);
                readState=parsed[0];
                List<String> imageList = new ArrayList<String>();
    			if(Project7Global.DEBUG) { 
    				System.out.println("parsed line: ["+parsed[0]+"] ");
    			}
                for (int i=1;i<parsed.length;i++) { 
                	imageList.add(i-1,parsed[i]);
                	Project7Global.DEBUG_MSG(0,Integer.toString(i)+" : ["+parsed[i]+"] ");
                }
                addImageEntry(readState,imageList);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Project7Global.DEBUG_MSG(0,"Error during handling of "+ifile);
        }

    }
	
	private void addImageEntry(String readState, List<String> readMessage) { 
		if(readState.matches("(WELCOME|BORED|SLEEPING|WORKING|DONE)")) {
			Project7Global.DEBUG_MSG(0,"Currently no support of state images");
		}		
		else if (readState.matches("(ECSTATIC|HAPPY|OKAY|INDIFFERENT|UNHAPPY|GRUMPY)")) {
			Project7Global.DEBUG_MSG(0,"Processing Line as CompanionMood: "+readState);
			List<String> x=moodImages.get(CompanionMood.StringToCompanionMood(readState));;
			if(x==null) { // if the node doesn't exist
				Project7Global.DEBUG_MSG(0,"Creating new List for Mood: "+readState);
				x = readMessage;
				moodImages.put(CompanionMood.StringToCompanionMood(readState), x);
			} 
			else { System.err.println("Duplicate entry for Mood Image : "+readState); }
		}		
		else {
			Project7Global.DEBUG_MSG(0,"Found invalid line: "+readState+" : "+readMessage);
		}
	}

	private void addPhraseEntry(String readState, String readMessage) { 
		if(readState.matches("(WELCOME|BORED|SLEEPING|WORKING|DONE)")) {
			Project7Global.DEBUG_MSG(0,"Processing Line as CompanionState: "+readState+" : "+readMessage);
			List<String> y = stateMessages.get(CompanionState.StringToCompanionState(readState));
			if(y == null) {
				Project7Global.DEBUG_MSG(1,"Creating new List for State: "+readState);
				y = new ArrayList<String>();
				stateMessages.put(CompanionState.StringToCompanionState(readState), y);
			} 

			y.add(readMessage);
			Project7Global.DEBUG_MSG(1,"CompanionState: "+readState+" : "+readMessage+"["+
			                            Integer.toString(stateMessages.size())+"]["+Integer.toString(y.size())+"]");
		}		
		else if (readState.matches("(ECSTATIC|HAPPY|OKAY|INDIFFERENT|UNHAPPY|GRUMPY)")) {
			Project7Global.DEBUG_MSG(0,"Processing Line as CompanionMood: "+readState+" : "+readMessage);
			List<String> x=moodMessages.get(CompanionMood.StringToCompanionMood(readState));;
			if(x==null) { // if the node doesn't exist
				Project7Global.DEBUG_MSG(0,"Creating new List for Mood: "+readState);
				x = new ArrayList<String>();
				moodMessages.put(CompanionMood.StringToCompanionMood(readState), x);
			} 
			x.add(readMessage);
		}		
		else {
			Project7Global.DEBUG_MSG(0,"Found invalid line: "+readState+" : "+readMessage);
		}
	}
	
	// method: parseMessage
	// if message contains:
	// <#QUESTIONS>
	// <#WLOC>
	// <#WTEMP>
	// <#WSUMMARY>
	// <#CURRENTTIME>
	// <#Q_ELAPSEDTIME>
	// <#AVERAGE_ELAPSEDTIME>
	// <#TOTAL_ELAPSEDTIME>
	// convert that substring to actual calculated value	
	private String parseMessage(String x) {
		
		// Variables are delimited with < and > and start with # 
		String startVar = "<";
		String endVar=">";
		String var="#";
		String delim="("+startVar+"|"+endVar+")";
		String[] parsed = x.split(delim);
		String processed = "";
		int i=0,fields=parsed.length;
	    while(i<fields) {
	    	if(parsed[i].startsWith(var)) { 
		    	switch (parsed[i]) {
		    	case "#QUESTIONS" :
		    		processed += " "+Integer.toString(getNumQuestions())+" ";
		    		break; 
		    	case "#WLOC":
		    		processed += " "+getWeatherLocation()+" ";
		    		break; 
		    	case "#WTEMP":
		    		processed += " "+getWeatherTemperature()+" ";
		    		break; 
		    	case "#WSUMMARY":
		    		processed += " "+getWeatherSummary()+" ";
		    		break; 
		    	case "#CURRENTTIME":
		    		processed += " "+getCurrentTime()+" ";
		    		break; 
		    	case "#Q_ELAPSEDTIME":
		    		processed += " "+getElapsedTimeForQuestion()+" ";
		    		break;
		    	case "#AVERAGE_ELAPSEDTIME": 
		    		processed += " "+getAverageElapsedTimeForQuestion()+" ";
		    		break;
		    	case "#TOTAL_ELAPSEDTIME": 
		    		processed += " "+getTotalElapsedTime()+" ";
		    		break;
		    	case "#STUDENT": 
		    		processed += " "+studentName+" ";
		    		break;

		    	}
	    	}
	    	else {
	    		processed += parsed[i];
	    	}
	    	i++; // increment while counter
	    }
	    return processed;
	}
	
	private int getNumQuestions() {
		//if ()
		return 10;
	}

	private String getWeatherLocation() {
	    return cityName;
	}
	private String getWeatherTemperature() {
	    return wtemp;
	}
	private String getWeatherSummary() {
	    return wsummary;	
	}

	private String getCurrentTime() {
		String time="";
		if(currentTime != null) {
		 String am_pm=(currentTime.getHours()>12)?"pm":"am";
		 time = Integer.toString((int)(currentTime.getHours()%12))+":"+Integer.toString(currentTime.getMinutes())+am_pm;
		}
		return time;
	}

	private String getElapsedTimeForQuestion() {
		return "10 s";
	}
	private String getAverageElapsedTimeForQuestion() {
		return "0.5 s";
	}
	private String getTotalElapsedTime() {
		return "240 s";
	}


	
	// these are original functions
	
	public void updateMood() {
		currentMood = CompanionMood.intToCompanionMood(CompanionMood.ToInt(currentMood)+1);
	}

	public void showFace(boolean madORhappy) {
			
	}

	public String[] getImage() {
		String[] img = new String[currentImg.size()];
		for(int i=0;i<currentImg.size();i++) { img[i]=currentImg.get(i);}
		return img;
	}

	public String getMessage() {
		return currentMsg;
	}

	private void handleIdleEvent(int idleCnt) {
		Project7Global.DEBUG_MSG(1, "CompanionBrain::handleIdleEvent("+Integer.toString(idleCnt)+")");
		if(idleCnt<M_IDLE_COUNT) { }
		else if((idleCnt>=M_IDLE_COUNT) && (idleCnt<N_IDLE_COUNT)) {
			Project7Global.DEBUG_MSG(1, "CompanionBrain::handleIdleEvent("+Integer.toString(idleCnt)+") => IdleEntry, IsMoving==false");
			updateAfterIdleEntry();
			isMoving=false;
		}
		else if(idleCnt>=N_IDLE_COUNT) {
			Project7Global.DEBUG_MSG(1, "CompanionBrain::handleIdleEvent("+Integer.toString(idleCnt)+") => IsMoving==true");
			isMoving=true;
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
        Blackboard blackboard=(Blackboard)arg0;
        resetIdleCounter();
		int numCorrect=blackboard.getNumberOfCorrectAnswers();
		int numAnswered=blackboard.getNumAnsweredQuestions();
		int totalNum=blackboard.GetTotalNumQuestions();
        if(blackboard.GetSubmitEvent()) {
		// TODO Auto-generated method stub
    		currentState=CompanionState.DONE;
    		currentMsg=getRandomStateResponse(currentState);
    		finalScore = blackboard.calculateScore();
        }
		if(blackboard.GetChooseEvent()) { // Answer event
			if(blackboard.getSelectionCounter(blackboard.GetCurrentQuestionNumber()) >= L_TIMES_Q_ANSWERED) { needsHint=true;}
			if(numCorrect==numAnswered) {
				currentMood=CompanionMood.ECSTATIC;
			}
			else if( (numAnswered-numCorrect) < ((1*totalNum)/5) ) {
				currentMood=CompanionMood.HAPPY;
			}
			else if( (numAnswered-numCorrect) < ((2*totalNum)/5) ) {
				currentMood=CompanionMood.OKAY;
			}
			else if( (numAnswered-numCorrect) < ((3*totalNum)/5) ) {
				currentMood=CompanionMood.INDIFFERENT;
			}
			else if( (numAnswered-numCorrect) < ((4*totalNum)/5) ) {
				currentMood=CompanionMood.UNHAPPY;
			}
			else {
				currentMood=CompanionMood.GRUMPY;
			}
			currentImg=moodImages.get(currentMood);
			currentState=CompanionState.WORKING;
			currentMsg=getRandomStateResponse(currentState);
		}
		this.setChanged();
	}
	
	private void printAllMessages() {
		System.out.println("-----------START OF MOOD MESSAGES--------------");
		for (CompanionMood c: CompanionMood.values()) {
			int iter=0;
			System.out.println("==");
			System.out.println(CompanionMood.CompanionMoodToString(c)+"=>");
			List<String> x = moodMessages.get(c);
			if(x==null) { 
				Project7Global.ERROR_MSG("Expected to see entry for mood : "+CompanionMood.CompanionMoodToString(c));
				continue;
			}
			for(iter=0;iter<x.size();iter++) {
				System.out.println(Integer.toString(iter)+":"+parseMessage(x.get(iter)));
			}
		}
		System.out.println("-----------END OF MOOD MESSAGES--------------");
		System.out.println("-----------START OF STATE MESSAGES--------------");
		for (CompanionState c: CompanionState.values()) {
			int iter=0;
			System.out.println("==");
			System.out.println(CompanionState.CompanionStateToString(c)+"=>");
			List<String> x = stateMessages.get(c);
			if(x==null) { 
				Project7Global.ERROR_MSG("Expected to see entry for state : "+CompanionState.CompanionStateToString(c));
				continue;
			}
			for(iter=0;iter<x.size();iter++) {
				System.out.println(Integer.toString(iter)+":"+parseMessage(x.get(iter)));
			}
		}
		System.out.println("-----------END OF STATE MESSAGES--------------");		
	}
	private void printAllImages() {
		System.out.println("------------START OF IMAGES----------------");
		for (CompanionMood c: CompanionMood.values()) {
			List<String> x = moodImages.get(c);
			int iter=0;
			System.out.println("==");
			System.out.println(CompanionMood.CompanionMoodToString(c)+"=>");
			for(iter=0;iter<x.size();iter++) {
				System.out.println(Integer.toString(iter)+":"+x.get(iter));
			}
		}
		System.out.println("---------------END OF IMAGES-------------");
	}
	
	public void updateAfterIdleEntry() {
		currentState=CompanionState.BORED;
		currentMsg=getRandomStateResponse(currentState);
		// need to update variables and information for use in the idle state
		cityName=getRandomCity();
		String[] geoLoc = cityData.get(cityName).split(",");
		latitude= Double.parseDouble(geoLoc[0]);
		longitude= Double.parseDouble(geoLoc[1]);
		if(idleCounter%100==0) { 
			Team7WeatherInfo weather= new Team7WeatherInfo(latitude,longitude);
			wsummary=weather.getWeatherFieldString("currently", "summary");
			wtemp=weather.getWeatherFieldString("currently", "temperature");
		}
		currentTime = Time.valueOf(LocalDateTime.now().toLocalTime());
	}
	

}
