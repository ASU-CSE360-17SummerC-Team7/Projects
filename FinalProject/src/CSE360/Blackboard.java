/**
 *
 * @author pdreiter
 * filename: 
 * creation date: 06/30/2017
 * description: 
 * Team members:
 *  - Chen Yang (cyang112@asu.edu)
 *  - Pemma Reiter (pdreiter@asu.edu)
 *  Notes from author: astah profession created the initial barebones class
 *  @modified pdreiter - modified with set/get functions and renamed based on diagram
 *
 */

package CSE360;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;

public class Blackboard extends Observable implements Observer {

	private Time[] answerTime;

	private boolean[] correctlyAnsweredQuestion;

	private int qNum; // this is the current Question Number

	private int totalNumQuestions;

	private int questionsLeft;

	private Time startTime;

	private boolean submitEvent;

	private boolean saveEvent;

	private boolean chooseEvent;

	private String nameOfTestee;

	private Question currentQuestion;

	private ExamPanel examPanel;

	public Blackboard() { 
		SetStartTime();
		ClearSubmitEvent();
		ClearSaveEvent();
		ClearChooseEvent();
		answerTime = null;
		correctlyAnsweredQuestion=null;
	}
	

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public void initializeArrays(int totalNumQuestions) { 
		answerTime = new Time[totalNumQuestions];
		correctlyAnsweredQuestion = new boolean[totalNumQuestions];
	}
	public Time getAnswerTime(int answerNum) { return answerTime[answerNum];}
	public void setAnswerTime(int answerNum, Time t) { answerTime[answerNum]=t;}

	public boolean getCorrectlyAnsweredQuestion(int questionNum) { return correctlyAnsweredQuestion[questionNum];}
	public void setCorrectlyAnsweredQuestion(int questionNum, boolean correctOrIncorrect) { correctlyAnsweredQuestion[questionNum]=correctOrIncorrect;}
	
	public int GetQuestionNumber() { return qNum;}
	public void SetQuestionNumber(int i) { qNum=i;}
	
	public int GetTotalNumQuestions() { return totalNumQuestions;}
	public void SetTotalNumQuestions(int i) { totalNumQuestions=i;}
	
	public int GetQuestionsLeft() { return questionsLeft;}
	public void SetQuestionsLeft(int i) { questionsLeft=i;}

	public Time GetStartTime() { return startTime;}
	private void SetStartTime() { startTime = Time.valueOf(LocalDateTime.now().toLocalTime());}

	public void SetSubmitEvent() { submitEvent=true;}
	public void ClearSubmitEvent() { submitEvent=false;}
	public boolean GetSubmitEvent() { return submitEvent;}
	
	public void SetSaveEvent() { saveEvent=true;}
	public void ClearSaveEvent() { saveEvent=false;}
	public boolean GetSaveEvent() { return saveEvent;}

	public void SetChooseEvent() { chooseEvent=true;}
	public void ClearChooseEvent() { chooseEvent=false;}
	public boolean GetChooseEvent() { return chooseEvent;}

	String GetNameOfTestee() { return nameOfTestee;}
	public void setNameOfTestee(String n) { nameOfTestee=n;}

	Question GetCurrentQuestion() { return currentQuestion;}
	void SetCurrentQuestion(Question q) { currentQuestion=q;}

}
