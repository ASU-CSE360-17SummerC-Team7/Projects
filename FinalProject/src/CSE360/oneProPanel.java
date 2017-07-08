package CSE360;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

//import ExamPanel.ButtonListener;
//import ExamPanel.ChoiceListener;

public class oneProPanel extends JPanel{
	private Exam exam;
	private JLabel questionl;
	private JRadioButton ans1,ans2,ans3,ans4;
	private JButton save, submit;
	private int stuAns;
	public boolean sut;
	
	public oneProPanel(Question q, String f)
	{
		//create question label
		exam = Exam.getInstance(f);
		stuAns = 0; 
		sut = false;
		questionl = new JLabel(q.question);
		//questionl.add(new JLabel(new ImageIcon((new ImageIcon(q.icon)).getImage().getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH))));
		
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
		ssPanel.add(submit,BorderLayout.EAST);

		//main panel
		//JPanel main = new JPanel();
		setLayout(new GridLayout(3,1));
		add(questionl);
		add(radioPanel);
		add(ssPanel);
	}
	
	

private class ChoiceListener implements ActionListener
{
	public void actionPerformed(ActionEvent event) {
			//Answer answer = new Answer();
			Object source = event.getSource();
			if(source == ans1)
			{
				stuAns = 1;
				//answer.answer = 1;
				ans1.setForeground(Color.RED);
			}
			else if(source == ans2)
			{
				stuAns = 2;
				//answer.answer = 2;
				ans2.setForeground(Color.RED);
			}
			else if(source == ans3)
			{
				stuAns = 3;
				//answer.answer = 3;
				ans3.setForeground(Color.RED);
			}
			else
			{
				stuAns = 4;
				//answer.answer = 4;
				ans4.setForeground(Color.RED);
			}	
			exam.setStuAns(stuAns);
	}
	
}
private class ButtonListener implements ActionListener
{
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if(source == save)
		{
			//save.setEnabled(false);
			save.setForeground(Color.PINK);
		}
		if(source == submit)
		{
			sut = true;//main.removeAll();
			exam.setSubmit(sut);
			System.out.println("submit button is pushed");
			System.out.println(exam.getsubmit());
		}		
	}

}}
