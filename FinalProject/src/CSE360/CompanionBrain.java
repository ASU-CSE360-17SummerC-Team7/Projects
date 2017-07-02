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
import java.util.*;
import java.util.Observable;
import java.util.Observer;




public class CompanionBrain implements Observer {

	private boolean needsHint;

	private int[] selectionCounter;

	private String currentMsg;

	private String[] currentImg;

	private CompanionMood currentMood;
	
	private CompanionState currentState;

	private Blackboard blackboard;
	// these class variables have been added since inception of class diagram
	
	private HashMap<CompanionMood,List<String>> moodMessages;
	private HashMap<CompanionState,List<String>> stateMessages;
	private int totalQuestions;
	private int answeredQuestions;
	private double latitude;
	private double longitude;
	private String wsummary;
	private String wtemp;
	private Time currentTime;
	private Time startTime;
	
	public CompanionBrain(String fPath) {
		initializePhrases(fPath);
	}

 	private String getRandomStateResponse(CompanionState x) {
 		Random r = new Random();
 		// generate a random number between 0 and the # of moodMessages for that particular state
 		int rand = r.nextInt(stateMessages.get(x).size()); 
 		return parseMessage(stateMessages.get(x).get(rand));
	}
 	
 	private String getRandomMoodResponse(CompanionMood x) {
 		Random r = new Random();
 		// generate a random number between 0 and the # of moodMessages for that particular state
 		int rand = r.nextInt(moodMessages.get(x).size()); 
 		return parseMessage(moodMessages.get(x).get(rand));
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
                addEntry(readState,readMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

	void addEntry(String readState, String readMessage) { 
		if(readState.matches("(BORED|SLEEPING|WORKING|DONE)")) {
			List<String> x;
			if(!stateMessages.containsKey(CompanionState.StringToCompanionState(readState))) {
				x = new ArrayList<String>();
				stateMessages.put(CompanionState.StringToCompanionState(readState), x);
			} else {
				x=stateMessages.get(CompanionState.StringToCompanionState(readState));
			}
			x.add(readMessage);
		}		
		else if (readState.matches("(ECSTATIC|HAPPY|OKAY|INDIFFERENT|UNHAPPY|GRUMPY)")) {
			List<String> x;
			if(!moodMessages.containsKey(CompanionMood.StringToCompanionMood(readState))) {
				x = new ArrayList<String>();
				moodMessages.put(CompanionMood.StringToCompanionMood(readState), x);
			} else {
				x=moodMessages.get(CompanionMood.StringToCompanionMood(readState));
			}
			x.add(readMessage);
		}		
		else {
			if(Project7Global.DEBUG) { 
				System.out.println("Found invalid line: "+readState+" : "+readMessage);
			}
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
	    return "Tempe, AZ";
	}
	private String getWeatherTemperature() {
	    return "100 degrees";
	}
	private String getWeatherSummary() {
	    return "Sunny";	
	}

	private String getCurrentTime() {
		return "5:00pm";
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
		return null;
	}

	public String getMessage() {
		return null;
	}

	public void handleSubmissionEvent() {

	}

	public void handleAnswerEvent() {

	}

	public void handleIdleEvent(int idleCnt) {

	}

	private void calculateScore() {

	}
// TO DO: Remove this from Class Diagram
// 	private String getRandomIdleResponse() {
//		return null;
//	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

}
