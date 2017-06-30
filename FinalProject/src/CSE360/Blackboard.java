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
public class Blackboard implements Observable, Observer {

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

}
