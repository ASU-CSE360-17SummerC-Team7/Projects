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

import javax.swing.*;

import java.util.ArrayList;
//import java.util.Observable;
//import java.util.Observer;
//import org.json.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ExamPanel extends JPanel {

	private Exam exam;
	private ArrayList<Question> copyQuestion;
	private Question eachQuestion;
	private Answer oneProblemChoices;
	//private JPanel oneProPanel;
	private oneProPanel[] p;
	private JLabel title;
	private JScrollPane spane;
	private int[] stuA;
	private int eachPchoice;
	//private int[] studentAnswers;
	
	//constructor
	public ExamPanel()
	{
		setSize(500,500);
		JFrame main = new JFrame();
		main.setLayout(new BorderLayout());
		//main.setSize(500, 500);
		exam =  new Exam();
		p = new oneProPanel[exam.getNumOfQuest()];
		copyQuestion = exam.getQuestion();
		setLayout(new GridLayout(2,1));
		stuA = new int[exam.getNumOfQuest()];
		//studentAnswers = new int [exam.getNumOfQuest()];

		
		for(int i =0; i<exam.getNumOfQuest(); i++)
		{
			eachQuestion =  copyQuestion.remove(0);
			p[i]= new oneProPanel(eachQuestion);
			//stuA[i] = (p[i]).
		}
		
		JPanel totPro = new JPanel();
		totPro.setLayout(new GridLayout(exam.getNumOfQuest(),1));
		for(int i =0; i<exam.getNumOfQuest(); i++)
		{
			totPro.add(p[i]);
		}
		
		//finally...
		title = new JLabel("SUMMER CSE360 online exam project----in process");
		//title.setSize(500,100);
		spane= new JScrollPane(totPro);
		//spane.setSize(500,400);
		setLayout(new GridLayout((exam.getNumOfQuest()+1),1));
		add(title, BorderLayout.NORTH);
		add(spane, BorderLayout.CENTER);
	}
	public int[] getStudentAnswers()
	{
		return stuA;
	}
}