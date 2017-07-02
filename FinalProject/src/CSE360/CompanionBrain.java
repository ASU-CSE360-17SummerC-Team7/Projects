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
	private HashMap<CompanionMood,List<String>> moodImages;
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
		moodMessages=new HashMap<CompanionMood,List<String>>();
		moodImages=new HashMap<CompanionMood,List<String>>();
		stateMessages=new HashMap<CompanionState,List<String>>();	
		initializePhrases(fPath);
		initializeImages(fPath);
		if(Project7Global.DEBUG&&Project7Global.DEBUG_LEVEL==0) {
			printAllMessages();
			printAllImages();
		}
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
                Project7Global.DEBUG_MSG(0,"parsed line: ["+parsed[0]+"] ["+parsed[1]+"]");
                addEntry(readState,readMessage);
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
	void addImageEntry(String readState, List<String> readMessage) { 
		if(readState.matches("(BORED|SLEEPING|WORKING|DONE)")) {
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

	void addEntry(String readState, String readMessage) { 
		if(readState.matches("(BORED|SLEEPING|WORKING|DONE)")) {
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
	

}
