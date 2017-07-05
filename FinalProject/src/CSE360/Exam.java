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
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Observable;

public class Exam extends Observable{

	
	private int[] correctAnswers;
	private int[] studentAnswers;
	private ArrayList<Question> questions;
	//private ArrayList<Answer> choice = new ArrayList<Answer>();
	private ExamBrain exambrain;
	private int questionNum = 0;
	private String string;
	public Exam()
	{
		exambrain = new ExamBrain();
		correctAnswers = new int[(exambrain.arr).size()];
		studentAnswers = new int[(exambrain.arr).size()];
		questions = new ArrayList<Question>();
		questionNum = (exambrain.arr).size();
		string = null;
	}
	
    public void populateExam() throws FileNotFoundException
	{	
    	for(int i= 0; i<(exambrain.arr).size(); i=i+5)
			{
				//all the info is in the arr arraylist, parse the info
    			if(i%5==0)
    			{
    				Question q = new Question();
    				q.question = exambrain.arr.get(i);
    				q.choices[0].answer =1;
    				q.choices[0].choice = exambrain.arr.get(i+1); 
    				q.choices[1].answer =2;
    				q.choices[1].choice = exambrain.arr.get(i+2); 
    				q.choices[2].answer =3;
    				q.choices[2].choice = exambrain.arr.get(i+3); 
    				q.choices[3].answer =4;
    				q.choices[3].choice = exambrain.arr.get(i+4); 
    				questions.add(q);
    			}
    			else
    			{   				
    				string = string + exambrain.arr.get(i);
    			}
			}       
	}
		

	public ArrayList<Question> getQuestion()
	{
		return questions;
	}
	public int[] getStudentAnswers()
	{
		return studentAnswers;
	}
	public void setStudentAnswers(int[] i)
	{
		studentAnswers = i;
	}
	public int[] getCorrectAnswer()
	{
		String[] parts = string.split("|");
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
