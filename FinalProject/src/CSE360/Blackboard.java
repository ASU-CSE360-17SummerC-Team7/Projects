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

import java.sql.Time;
import java.util.Observable;
import java.util.Observer;

public class Blackboard extends Observable implements Observer {

	private Time[] answerTime;

	private boolean[] correctTesterAnswers;

	private int currentQuestion;

	private int totalNumQuestions;

	private int questionsLeft;

	private Time startTime;

	private boolean submitEvent;

	private boolean saveEvent;

	private boolean chooseEvent;

	private String nameOfTestee;

	private Question question;

	private ExamPanel examPanel;

	public void update() {

	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

}
