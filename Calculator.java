package myUtil;
import java.util.*;
import java.lang.Math.*;

public class Calculator
{
	private token[] myTokens;
	private static LStack<token> op = new LStack<token>();
	private static LStack<token> val = new LStack<token>();
	// 0 if no error is found 1 if error is found
	private static int error=0;
	private static String string_error;
	
	public Calculator(String enter)
	{
		error=0;
		string_error="";
		double answer=0;
		findNumOfTokens(enter);
		answer=evaluate(enter);	
		if(error==0)
		{
			System.out.println(enter + " = " + answer);
		}
		else
		{
			System.out.println(enter + " = " + string_error);
		}			
	}
	
	public static double doTheMath(char the_char, double arg_2, double arg_1)
	{
		if(the_char=='+')
		{
			return arg_1+arg_2;
		}
		else if(the_char=='-')
		{
			return arg_1-arg_2;	
		}
		else if(the_char=='*')
		{
			return arg_1*arg_2;	
		}
		else if(the_char=='/')
		{
			if(arg_2==0)
			{
				error=1;
				string_error="Error : Divided by 0";
				return -1;
			}
			else
			{
				return arg_1/arg_2;
			}		
		}
		else if(the_char=='^')
		{
			return Math.pow(arg_1, arg_2);	
		}
		else 
		{
			error=1;
			string_error="Error : Not a valid expression!";
			return -1;
		}
	}
	
	public static int findNumOfTokens(String input)
	{
		int num_of_tokens=0;
		int got_num=0;
		int got_dot=0;
		for(int i=0; i<input.length(); i++)
		{
			got_num=0;
			got_dot=0;
			if(i<input.length())
			{
				while(i<input.length() &&
				     (Character.isDigit(input.charAt(i)) || input.charAt(i)=='.')) 
				{
					// make sure each double only has one .
					if(got_dot==0 && input.charAt(i)=='.')
					{
						got_dot=1;
					}
					else if(input.charAt(i)=='.')
					{
						System.out.println("error more than one .");
					}
					// only want one token per number
					if(got_num==0 && Character.isDigit(input.charAt(i)))
					{
						num_of_tokens++;
						got_num=1;
					}
					if(i<input.length())
					{
						i++;
					}
				}
			}
			if(i<input.length())
			{
				if(input.charAt(i)=='+' || input.charAt(i)=='-' || input.charAt(i)=='*'  
			   	   || input.charAt(i)=='/' || input.charAt(i)=='^' 
			   	   || input.charAt(i)=='(' || input.charAt(i)==')')
				{
					num_of_tokens++;
				}
				//something is in the string that should not be
				else 
				{
					System.out.println("error invalid char in sring");
				}
			}	
		}
		return num_of_tokens;
	}
	
	public static double evaluate(String infix)
	{
		double answer=0;
		token[] myTokens = new token[findNumOfTokens(infix)];
		//for printing
		//System.out.println(infix);
		//end printing
		int i=0;
		for(int k=0; k<myTokens.length; k++)
		{
			myTokens[k] = new token();
		}
		// set values for the tokens
		
			for(int j=0; j<infix.length(); j++)
			{	
				if(j<infix.length() && 
				  (Character.isDigit(infix.charAt(j)) || infix.charAt(j)=='.'))
				{
					String temp="";
					while(j<infix.length() &&
				     	     (Character.isDigit(infix.charAt(j)) || infix.charAt(j)=='.'))
					{
						temp+=Character.toString(infix.charAt(j));
						if(j<infix.length())
						{
							j++;	
						}
					}
					myTokens[i].op_or_val=2;
					myTokens[i].the_val=Double.parseDouble(temp);
					//for printing ********
					//System.out.print("token "+i+" ");
					//System.out.print(myTokens[i].the_val);
					//System.out.println("");
					//End printing ********
					i++;
					j--;		
				}
				else
				{
					myTokens[i].op_or_val=1;
					if(j==0)
					{
						myTokens[i].the_op=infix.charAt(j);
					}
					else
					{
						myTokens[i].the_op=infix.charAt(j);
					}	
					if(myTokens[i].the_op=='(')
					{
						myTokens[i].precedence=0;
					}
					else if(infix.charAt(j)=='+' || infix.charAt(j)=='-')
					{
						myTokens[i].precedence=1;
					}
					else if(infix.charAt(j)=='*' || infix.charAt(j)=='/')
					{
						myTokens[i].precedence=2;
					}	
					else if(infix.charAt(j)=='^')
					{
						myTokens[i].precedence=3;
					}
					i++;
					//for printing *******
					//System.out.print("token "+i+" ");
					//System.out.print(myTokens[i].the_op+" with precidince "+
					//myTokens[i].precedence);
					//System.out.println("");
					//end printing ********
				}		
		}
		//end set values for the tokens
		// start calculating answer
		for(int j=0; j<myTokens.length; j++)
		{
			if(error==1)
			{
				//flush stacks at end 
			}
			else
			{	
				//for printing
				/*
				System.out.print("working on token " + j);
				if(myTokens[j].op_or_val==1)
				{
					System.out.print(" op is " + myTokens[j].the_op);
					System.out.print(" precedence is " + myTokens[j].precedence);
					System.out.println("");
				}
				else
				{
					System.out.print(" val is " + myTokens[j].the_val);
					System.out.println("");
				}
				*/
				//end for printing
				//1 find a val
				if(myTokens[j].op_or_val==2)
				{
					val.push(myTokens[j]);
					//System.out.println("if-1 pushing val "  + val.peek().the_val);
					continue;
				}
				//2 find op and op stack is empty
				if(myTokens[j].op_or_val==1 && op.empty())
				{
					op.push(myTokens[j]);
					//System.out.println("if-2 pushing op " + op.peek().the_op);
					continue;
				}
				//3 find op and it is ( so push it in
				if(myTokens[j].op_or_val==1 && myTokens[j].the_op=='(' )
				{
					op.push(myTokens[j]);
					//System.out.println("if-3 pushing op " + op.peek().the_op);
					continue;
				}
				//4 find op and it is ) so pop from op until ( is found
				if(myTokens[j].op_or_val==1 && myTokens[j].the_op==')')
				{
					int done=0;
					while(op.peek().the_op!='(' && done==0 && error==0)
					{
						// did not find ( in opStack
						if(op.empty())
						{
							error=1;
							string_error="Error : Not a valid expression!";
						}
						else
						{
						token temp = new token();
						temp.op_or_val=2;
						//System.out.println("if-4-B doing op " + 
								     //op.peek().the_op);
						temp.the_val=doTheMath(op.pop().the_op, 
							     val.pop().the_val, val.pop().the_val);
						val.push(temp);
						//System.out.println("if-4-B pushing val " + 
						//	           val.peek().the_val);
						}
					}
					//System.out.println("if-4-A poping op " + op.peek().the_op);
					if(error==0)
					{
						op.pop();
						continue;
					}	
				}
				//5 found op with higher precedence than top of op stack
				if(myTokens[j].op_or_val==1 
			   	   && myTokens[j].precedence > op.peek().precedence)
				{
					op.push(myTokens[j]);
					//System.out.println("if-5 pushing op " + op.peek().the_op);
					continue;
				}
				//6 found op with lower or same precedence than top of op stack
				// and is not ^
				if(myTokens[j].op_or_val==1 
				   && myTokens[j].precedence <= op.peek().precedence 
				   && myTokens[j].the_op!='^')
				{
					while(!(op.empty()) 
						&& myTokens[j].precedence <= op.peek().precedence 
						&& error==0)
					{
						token temp = new token();
						temp.op_or_val=2;
						double temp_1=0;
						double temp_2=0;
						char temp_char='0';
						if(!(val.empty()))
						{
							temp_1=val.pop().the_val;
						}
						else
						{
							error=1;
							string_error="Error : Not a valid expression!";	
						}
						if(!(val.empty()))
						{
							temp_2=val.pop().the_val;
						}
						else
						{
							error=1;
							string_error="Error : Not a valid expression!";	
						}
						temp_char=op.pop().the_op;
						if(temp_char=='(')
						{
							error=1;
							string_error="Error : Not a valid expression!";	
						}
						//System.out.println("if-6-A doing op " + temp_char);
						if(error==0)
						{
							temp.the_val=doTheMath(temp_char, temp_1, temp_2);
				 			val.push(temp);
				 			//System.out.println("if-6-A pushing val " +
				 			//             	    val.peek().the_val);
						}	
					}
					if(error==0)
					{
						op.push(myTokens[j]);
						//System.out.println("if-6-B pushing op " + 
							    	    //op.peek().the_op);
					}
				}
				//7 found op ^ and top of op stack is also ^
				if(myTokens[j].op_or_val==1 
			   	   && myTokens[j].precedence == op.peek().precedence 
			   	   && myTokens[j].the_op=='^')
					{
						op.push(myTokens[j]);
					//System.out.println("if-7 pushing val " + 
							    //val.peek().the_val);
						continue;	
					}
			}	
		}
		//8 run out of tokens so flush op stack
		while(!(op.empty()) && error==0)
		{
			token temp = new token();
			temp.op_or_val=2;
			double temp_1=0;
			double temp_2=0;
			char temp_char='0';
			if(!(val.empty()))
			{
				temp_1=val.pop().the_val;
			}
			else
			{
				error=1;
				string_error="Error : Not a valid expression!";	
			}
			if(!(val.empty()))
			{
				temp_2=val.pop().the_val;
			}
			else
			{
				error=1;
				string_error="Error : Not a valid expression!";	
			}
			temp_char=op.pop().the_op;
			if(temp_char=='(')
			{
				error=1;
				string_error="Error : Not a valid expression!";	
			}
			//System.out.println("if-8 doing op " + temp_char);
			if(error==0)
			{
				temp.the_val=doTheMath(temp_char, temp_1, temp_2);
				val.push(temp);
				//System.out.println("if-8 pushing val " + val.peek().the_val);
			}	
			
		}
		if(error==0)
		{
			return val.pop().the_val;
		}
		// flush stack
		else
		{
			while(!(op.empty()))
			{
				op.pop();
			}
			while(!(val.empty()))
			{
				val.pop();
			}
			return 0;
		}	
	}
	
	private static class token
	{
		private token()
		{
			this.op_or_val=3;
			this.the_op='0';
			this.precedence=5;
			this.the_val=1;
		}
		// 1 if op, 2 if val;
		private int op_or_val=0;
		private char the_op;
		private double the_val;
		// ( = 0, + - = 1, * / = 2, ^ = 3, ) = no value, do when found 
		private int precedence;
	}
	// not done in Asg5
	public static double evaluatePostFix(){return 0;}
	public static String infixToPostfix(String infix){return " ";}
}
