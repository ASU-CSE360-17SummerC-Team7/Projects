import javax.swing.JApplet;
public class test extends JApplet
{
	public void init()
	{
		ExamPanel p1 = new ExamPanel();
		getContentPane().add(p1);
		setSize(250,450);
	}
}
/*public class test{
public void main(String[] args)
{
	ExamBrain eb = new ExamBrain();
}
}*/