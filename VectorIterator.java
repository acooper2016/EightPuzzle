import java.util.*;
/**
*Iterator for the Vector class
*
*@author Aaron Cooper
*@version 1.0
*/
public class VectorIterator<E> implements Iterator<E>
{
	Vector<E> v;
	int curr;
	/**
	*Constructs new Vector iterator
	*/
	public VectorIterator(Vector<E> vector)
	{
		v = vector;
		curr = 0;
	}
	
	/**
	*Returns whether or not the vector corresponding to this iterator has more values after
	*the currently examined value.
	*
	*@return whether or not the vector has any values after the current value.
	*/
	public boolean hasNext()
	{
		if(curr < v.size() - 1)
			return true;
		else
			return false;
	}
	
	/**
	*Shifts Current value of the iterator.
	*
	*@return new current value.
	*/
	public E next()
	{
		if(!hasNext())
			throw new NoSuchElementException();
		else
		{
			curr++;
			return v.get(curr);
		}
	}
	
	/**
	*Remarkably useful method.  Does all sorts of stuff!  
	*
	*@return a useful, functional piece of data that you can apply to all manner of computer science tasks/functions.
	*/
	public void remove()
	{
	}
}
