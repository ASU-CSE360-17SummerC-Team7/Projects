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

	private static final int MAX_SEL_QUESTION = 2;
	private Time[] answerTime;
	private boolean[] correctlyAnsweredQuestion;
	private int qNum; // this is the current Question Number
	private int totalNumQuestions;
	private int questionsLeft;
	private Time startTime;
	private boolean submitEvent;
	private boolean saveEvent;
	private boolean chooseEvent;
	private boolean needsHint;
	private String nameOfTestee;
	private Question currentQuestion;
	private ExamPanel examPanel;
	private Time currentTime;
	private int[] selectionCounter;
	private int totalQuestions;
	private int numAnsweredQuestions;
	private static Blackboard _instance;
	public static Blackboard getInstance() { 
		if(_instance == null) {
			_instance = new Blackboard();
			Project7Global.DEBUG_MSG(6, "Blackboard::getInstance()");
		}
		return _instance;
	}
	
	protected Blackboard() {
		Project7Global.DEBUG_MSG(9, "Blackboard::Blackboard()");
		SetStartTime();
		ClearSubmitEvent();
		ClearSaveEvent();
		ClearChooseEvent();
		answerTime = null;
		correctlyAnsweredQuestion = null;
		totalQuestions = 10;
		selectionCounter = new int[totalQuestions];
		initializeArrays(totalQuestions);
		for (int i = 0; i < totalQuestions; i++) {
			selectionCounter[i] = 0;
		}
		numAnsweredQuestions = 0;
	}
    
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		

	}

	public void setExamPanel(ExamPanel e) {
		examPanel = e;
	}

	public void update(String event_type, boolean correct, int questionAnswered) {
		Project7Global.DEBUG_MSG(5, "Blackboard::update("+event_type+","+Boolean.toString(correct)+","+Integer.toString(questionAnswered)+");");
		this.SetCurrentQuestionNumber(questionAnswered);
		this.updateSelectionCounter();
		if (event_type == "save") {
			this.SetSaveEvent();
			this.setCorrectlyAnsweredQuestion(questionAnswered, correct);
		}
		if(event_type == "submit") {
			this.SetSubmitEvent();
			this.setCorrectlyAnsweredQuestion(questionAnswered, correct);
		}
		if(this.getSelectionCounter(questionAnswered)>MAX_SEL_QUESTION) { 
		   this.needsHint=true;
		}
		this.SetChooseEvent();
		this.notifyObservers();
	}

	public double calculateScore() {
		return (100 * (double) getNumberOfCorrectAnswers()) / (double) (totalQuestions);
	}

	public int getNumberOfCorrectAnswers() {
		int total = 0;
		for (int i = 0; i < totalQuestions; i++) {
			if (getCorrectlyAnsweredQuestion(i)) {
				total += 1;
			}
		}
		return total;
	}

	private void updateSelectionCounter() {
		selectionCounter[GetCurrentQuestionNumber()]++;
	}

	public int getSelectionCounter(int q) {
		return selectionCounter[q];
	}

	private void recalculateNumberOfAnsweredQuestions() {
		numAnsweredQuestions = 0;
		for (int i = 0; i < totalQuestions; i++) {
			if (selectionCounter[i] > 0) {
				numAnsweredQuestions++;
			}
		}
	}

	public int getNumAnsweredQuestions() {
		return numAnsweredQuestions;
	}

	public void initializeArrays(int totalNumQuestions) {
		answerTime = new Time[totalNumQuestions];
		correctlyAnsweredQuestion = new boolean[totalNumQuestions];
	}

	public Time getAnswerTime(int answerNum) {
		return answerTime[answerNum];
	}

	public void setAnswerTime(int answerNum, Time t) {
		answerTime[answerNum] = t;
	}

	public boolean getCorrectlyAnsweredQuestion(int questionNum) {
		return correctlyAnsweredQuestion[questionNum];
	}

	public void setCorrectlyAnsweredQuestion(int questionNum, boolean correctOrIncorrect) {
		correctlyAnsweredQuestion[questionNum] = correctOrIncorrect;
	}

	public int GetCurrentQuestionNumber() {
		return qNum;
	}

	private void SetCurrentQuestionNumber(int i) {
		qNum = i;
	}

	public int GetTotalNumQuestions() {
		return totalNumQuestions;
	}

	public void SetTotalNumQuestions(int i) {
		totalNumQuestions = i;
	}

	public int GetQuestionsLeft() {
		return questionsLeft;
	}

	public void SetQuestionsLeft(int i) {
		questionsLeft = i;
	}

	public Time GetStartTime() {
		return startTime;
	}

	private void SetStartTime() {
		startTime = Time.valueOf(LocalDateTime.now().toLocalTime());
	}

	public void SetSubmitEvent() {
		submitEvent = true;
	}

	public void ClearSubmitEvent() {
		submitEvent = false;
	}

	public boolean GetSubmitEvent() {
		return submitEvent;
	}

	public void SetSaveEvent() {
		saveEvent = true;
	}

	public void ClearSaveEvent() {
		saveEvent = false;
	}

	public boolean GetSaveEvent() {
		return saveEvent;
	}

	public void SetChooseEvent() {
		chooseEvent = true;
	}

	public void ClearChooseEvent() {
		chooseEvent = false;
	}

	public boolean GetChooseEvent() {
		return chooseEvent;
	}

	String GetNameOfTestee() {
		return nameOfTestee;
	}

	public void setNameOfTestee(String n) {
		nameOfTestee = n;
	}

	Question GetCurrentQuestion() {
		return currentQuestion;
	}

	void SetCurrentQuestion(Question q) {
		currentQuestion = q;
	}

}
