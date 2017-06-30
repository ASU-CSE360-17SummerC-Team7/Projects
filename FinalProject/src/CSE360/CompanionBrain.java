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
public class CompanionBrain implements Observer, Observer {

	private boolean needsHint;

	private int[] selectionCounter;

	private String currentMsg;

	private String[] currentImg;

	private Blackboard blackboard;

	public void update() {

	}

	public void initializePhrases() {

	}

	public void updateMood() {

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

	private String getRandomIdleResponse() {
		return null;
	}

}
