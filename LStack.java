// Dale Heuser
// UserName: dheuser
// Password: wiT4seal
package myUtil;
import java.util.*;

public class LStack<T> implements  Stack<T>
{
	private class Node<E>
	{
		private E Data;
		private Node<E> Next;
		
		private Node(E Data, Node<E> Next)
		{
			this.Data=Data;
			this.Next=Next;
		}
	}
	
	private Node<T> top=null;
	private int NumOfItems;
	
	public LStack()
	{
		NumOfItems=0;
	}
	
	public boolean empty()
	{
		return top==null;
	}
	
	public T pop()
	{
		if(empty())
		{
			throw new EmptyStackException();
		}
		T returnData= top.Data;
		top=top.Next;
		NumOfItems--;
		return returnData;
	} 
	
	public T push(T Data)
	{
		top = new Node<T>(Data, top);
		NumOfItems++;
		return top.Data;
	}
	
	public int size()
	{
		return NumOfItems;
	}
	
	public T peek()
	{
		return top.Data;
	}
}
