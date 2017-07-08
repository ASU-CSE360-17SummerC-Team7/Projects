package CSE360;
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




public class Question{

	public String question;

	public String icon;

	public Answer choices[] =  new Answer[4];
	
	public Question()
	{	for(int i =0 ; i<4; i++)
			{
				choices[i] = new Answer();
				//choices[i].answer = 0;
				//choices[i].choice = "";
			}
	}

}
