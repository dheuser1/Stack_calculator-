import myUtil.Calculator;
import myUtil.LStack;
import java.io.BufferedReader;
import java.io.FileReader;

public class Asg5
{
	public static void main(String[] args)
	{
		String file_line;
		Calculator myCalc;
		try 
		{
			if (args.length == 0) throw new Exception("Please provide a file name.");
			BufferedReader  br = new BufferedReader(new FileReader(args[0]));
			while((file_line=br.readLine()) != null)
			{
				Calculator myClac = new Calculator(file_line);
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		
	}
}

