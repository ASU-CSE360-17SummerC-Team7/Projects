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

import javax.swing.*;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import org.json.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ExamPanel extends JPanel implements Observer {

	private Exam exam;
	private ArrayList<Question> copyQuestion;
	private Question eachQuestion;
	private Answer oneProblemChoices;
	//private JPanel oneProPanel;
	private JPanel[] p;
	private JLabel title;
	private JScrollPane spane;
	private int[] stuA;
	private int eachPchoice;
	
	//variables for one problem panel
	private Question q; 
	//private JTextField questiont, ans1t, ans2t, ans3t, ans4t;
	private JLabel questionl;
	private JRadioButton ans1,ans2,ans3,ans4;
	private JButton save, submit;
	
	//constructor
	public ExamPanel()
	{
		exam =  new Exam();
		copyQuestion = exam.getQuestion();
		this.setLayout(new GridLayout(2,1));
		stuA = new int[exam.getNumOfQuest()];

		
		for(int i =0; i<exam.getNumOfQuest(); i++)
		{
			eachQuestion =  copyQuestion.remove(0);
			p[i] = new oneProPanel(eachQuestion);			
		}
		
		JPanel totPro = new JPanel();
		totPro.setLayout(new GridLayout(exam.getNumOfQuest(),1));
		for(int i =0; i<exam.getNumOfQuest(); i++)
		{
			totPro.add(p[i]);
		}
		
		//finally...
		title = new JLabel("SUMMER CSE360 online exam project----in process");		
		spane= new JScrollPane(totPro);
		//setLayout(new GridLayout((exam.getNumOfQuest()+1),1));
		this.add(title);
		this.add(spane);
	}
	
	public JPanel oneProPanel(Question q)
	{
		//create question label
		
		this.q = q;
		questionl = new JLabel();
		questionl.add(new JTextField(q.question));
		questionl.add(new JLabel(new ImageIcon((new ImageIcon(q.icon)).getImage().getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH))));
		
		//create choice panel	
		ans1 = new JRadioButton(q.choices[0].choice);
		ans2 = new JRadioButton(q.choices[1].choice);
		ans3 = new JRadioButton(q.choices[2].choice);
		ans4 = new JRadioButton(q.choices[3].choice);
		ButtonGroup group = new ButtonGroup();
			group.add(ans1);
			group.add(ans2);
			group.add(ans3);
			group.add(ans4);
			
		JPanel radioPanel = new JPanel();
		radioPanel.setLayout(new GridLayout(4, 1));
		radioPanel.add(ans1); 
		radioPanel.add(ans2); 
		radioPanel.add(ans3); 
		radioPanel.add(ans4); 
		ChoiceListener list = new ChoiceListener();
		ans1.addActionListener(list);
		ans2.addActionListener(list);
		ans3.addActionListener(list);
		ans4.addActionListener(list);
		//one problem may occur: ans is unmatched stored 
		
		//create save and submit panel
		save = new JButton("save");
		submit = new JButton("submit");
		ButtonListener sslistener = new ButtonListener();
		save.addActionListener(sslistener);
		submit.addActionListener(sslistener);
		JPanel ssPanel = new JPanel();
		ssPanel.setLayout(new BorderLayout());
		ssPanel.add(save, BorderLayout.CENTER);
		ssPanel.add(submit,BorderLayout.SOUTH);

		//main panel
		JPanel main = new JPanel();
		main.setLayout(new GridLayout(3,1));
		main.add(questionl);
		main.add(radioPanel);
		main.add(ssPanel);
		
		return main;
	}
	
	

private class ChoiceListener implements ActionListener
{
	public void actionPerformed(ActionEvent event) {
			Answer answer = new Answer();
			Object source = event.getSource();
			if(source == ans1)
			{
				answer.answer = 1;
				ans1.setForeground(Color.RED);
			}
			else if(source == ans2)
			{
				answer.answer = 2;
				ans2.setForeground(Color.RED);
			}
			else if(source == ans3)
			{
				answer.answer = 3;
				ans3.setForeground(Color.RED);
			}
			else
			{
				answer.answer = 4;
				ans4.setForeground(Color.RED);
			}	
			
	}
	
}
private class ButtonListener implements ActionListener
{
	public void actionPerformed(ActionEvent event) {
		Object source = new Object();
		if(source == save)
		{
			save.setEnabled(false);
		}
		if(source == submit)
		{
			//main.removeAll();
			
		}
		
	}
	
}

	public void drawExam() 
	{
		
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}



}
