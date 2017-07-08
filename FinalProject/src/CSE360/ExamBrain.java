package CSE360;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

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


public class ExamBrain {
	ArrayList<String> arr;
	String string;
	String fPath;
	public ExamBrain(String p)
	{
		fPath=p;
	System.out.print("ExamBrain");
	arr = new ArrayList<String>();
	readFile(arr);
	string = "hello";
    System.out.print(string);
	}
	
   public void readFile(ArrayList<String> arr) 
	{	
    	try
    	{
    		Scanner scanner = new Scanner(new File(fPath+"/"+"questions.txt"));         
            while (scanner.hasNextLine()) 
            {
                arr.add(scanner.nextLine());
            }          
            scanner.close();
    	} catch(FileNotFoundException ex)
    	{
            System.out.println("File was not found.");
        }
        
	}
}
    


