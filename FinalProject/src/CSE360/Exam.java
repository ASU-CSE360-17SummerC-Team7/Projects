/**
 *
 * @author 
 * filename: 
 * creation date: 06/30/2017
 * description: 
 * Team members:
 *  - Chen Yang (cyang112@asu.edu)
 *  - Pemma Reiter (pdreiter@asu.edu)
 *  Notes from author: astah profession created the initial barebones class
 *
 */
package CSE360;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Observable;


public class Exam extends Observable{

	
	private int[] correctAnswers;
	private ArrayList<Question> questions;
	//private ArrayList<Answer> choice = new ArrayList<Answer>();
	private ExamBrain exambrain;
	private int questionNum;
	private String string;
	
	public Exam(String fPath)
	{
		exambrain = new ExamBrain(fPath);
		questionNum = 0;
		correctAnswers = new int [questionNum];
		
		questions = new ArrayList<Question>();
		string = "";
		//populateExam(exambrain, questions, string);
		
	//}

   // public void populateExam(ExamBrain exambrain, ArrayList<Question> questions, String string)
	//{	    	
    	for(int i= 0; i<(exambrain.arr).size(); i=i+5)
			{
				//all the info is in the arr arraylist, parse the info
    			if(i%5==0&&(((exambrain.arr).size()-i)>=5))
    			{
    				questionNum++;
    				Question q = new Question();
    				q.question = exambrain.arr.get(i);
    				for(int j = 1; j<=4; j++)
    				{
    					Answer a = new Answer();
    					a.answer =j;
        				a.choice = exambrain.arr.get(i+j); 
        				q.choices[j-1] = a;
    				}
    				questions.add(q);
    			}
    			else
    			{   				
    				string = string + exambrain.arr.get(i);
    			}
			}       
    	correctAnswers=getCorrectAnswer(string);
	}
		

	public ArrayList<Question> getQuestion()
	{
		return questions;
	}

	public int[] getCorrectAnswer(String string)
	{
		String delims = "[|]";
		String[] parts =  string.split(delims);
	    int result[] = new int[questionNum];
		for(int i=0 ;i< questionNum; i++)
		{
			 result[i] = Integer.parseInt(parts[i]);
		}
		return result;
	}
	public int getNumOfQuest()
	{
		return questionNum;
	}
	

}
