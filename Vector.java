import java.util.*;

/**
*@author Aaron Cooper
*@version 1.0
*
*Data structure that stores data of a generic type in a modified array.  Has no maximum capacity, and adjusts
*capacity to conform to new data input.
*/

public class Vector<E> implements Iterable
{
	private Object[] data;
	private int capacity;
	//other private variables
	
	/**
	*Constructs a default empty vector with size 10
	*/
	public Vector()
	{
		data = new Object[10];
		capacity = 0;
	}
	
	/**
	*Constructs an empty vector with a given size
	*
	*@param initCapacity size of Vector
	*/
	public Vector(int initCapacity)
	{
		data = new Object[initCapacity];
		capacity = initCapacity - 1;
	}
	
	/**
	*Constructs a copy vector identical to a given vector
	*
	*@param other Vector to be copied
	*/
	public Vector(Vector<E> other)
	{
		if(other == null)
			throw new NullPointerException("Other must be instantiated");
		data = new Object[other.size()];
		capacity = other.size() - 1;
		for(int i = 0; i < other.size(); i++)
		{
			data[i] = other.get(i);
		}
		
	}
	/**
	*Adds an item to the end of the vector
	*
	*@param ent item being added
	*/
	public void add(E ent)
	{
		if(capacity == data.length - 1)
			resize();
		data[capacity] = ent;
		capacity++;
	}
	
	/**
	*Adds an item at a specified index, shifting all items ahead of it down.
	*
	*@param index Index at which item will be inserted
	*@param toAdd Item being added
	*/
	public void add(int index, E toAdd)
	{
		if(index < 0 || index > capacity)
		{
			throw new IndexOutOfBoundsException("Index must be greater than 0");
		}
		else if(index == capacity)
		{
			add(toAdd);
		}
		else
		{
			E holder;
			E holder2;
			holder =(E) data[index];
			data[index] = toAdd;
			for(int i = index + 1; i < capacity; i++)
			{
				holder2 = (E) data[i];
				data[i] = holder;
				holder = holder2;
			}	
			capacity++;
		}
	}
	
	
	/**
	*Replaces data with an identical array of twice the length.  Has no impact on capacity.
	*/
	private void resize()
	{
		Object[] newData = new Object[data.length * 2];
		for(int i = 0; i < data.length; i++)
		{
			newData[i] = data[i];
		}
		data = newData;
	}
	
	/**
	*Returns String representation of the Vector
	*
	*@return String containing all items in the vector
	*/
	public String toString()
	{
		String returnStr = "";
		for(int i = 0; i < capacity; i ++)
		{
			returnStr += "\n";
			returnStr += data[i];
			
		}
		return returnStr;
	}
	
	/**
	*Returns the item stored at a given index.
	*
	*@param index index of item being returned
	*@ return Item stored at index
	*/
	public E get(int index)
	{
		if(index > capacity || index < 0)
			throw new IndexOutOfBoundsException();
		else
			return (E) data[index];
	}
	
	
	/**
	*Removes item at a given index, and shifts items ahead of it up.  Returns the removed item.
	*
	*@param index index of item being removed
	*
	*@return Removed index
	*/
	public E remove(int index)
	{
		if(index > capacity || index < 0)
			throw new IndexOutOfBoundsException();
		else if(index == capacity)
		{
			E holder = (E) data[capacity];
			data[capacity] = null;
			capacity --;
			return holder;
		}
		else
		{
			E holder = (E) data[index];
			data[index] = null;
			for(int i = index + 1; i <= capacity; i ++)
				data[i - 1] = data[i];
			capacity --;
			return holder;
		}
	}
	
	/**
	*Removes the first instance of a given object if possible.  Shifts all objects ahead of it up.   
	*Returns whether or not object could be removed.
	* 
	*@param obj Object to be removed
	*@return whether or not object could be removed
	*/
	public boolean remove(E obj)
	{
		int index = -1;
		for(int i = 0; i <= capacity; i++)
		{
			if(data[i] == obj)
			{
				index = i;
				break;
			}
		}
		
		if(index == -1)
			return false;
		remove(index);
		return true;
	}
	
	/**
	*Replaces the item at the specified position in this list with a given item.
	*
	*@param index index of the item to be replaced
	*@param obj item to be stored at the specified position
	*@return the element previously at the specified position
	*/
	public E set(int index, E obj)
	{
		if(index > capacity || index < 0)
			throw new IndexOutOfBoundsException();
		else
		{
			E holder = (E) data[index];
			data[index] = obj;
			return holder;
		}
	}
	
	/**
	*Returns the size of the list
	*
	*@return the size of the list
	*/
	public int size()
	{
		return(capacity + 1);
	}

	/**
	*Replaces current vector with an empty vector.
	*/
	public void clear()
	{
		data = new Object[10];
		capacity = 0;
	}	
	
	/**
	*Returns whether or not the vector has no items in it.
	*
	*@return whether or not the vector is empty
	*/
	public boolean isEmpty()
	{
		if(capacity == 0)
			return true;
		else
			return false;
	}
	
	/**
	*Returns whether or not the vector contains a given item.
	*
	*@param obj item being checked for in the Vector.
	*@return whether or not the Vector contains the given item.
	*/
	public boolean contains(E obj)
	{
		for(int i = 0; i < capacity; i++)
		{
			if(data[i] == obj)
				return true;
		}
		return false;
	}
	
	/**
	*Returns index of a given item.  Returns -1 if item is not in Vector.
	*
	*@param obj item being looked for int the list
	*@return index of the given item.
	*/
	public int indexOf(E obj)
	{
		int index = -1;
		for(int i = 0; i  <= capacity; i++)
		{
			if(data[i] == obj)
			{
				index = i;
				break;
			}
		}
		return index;
	}
	
	/**
	*instantiates new iterator for this Vector.
	*
	*@return The new iterator
	*/
	public Iterator<E> iterator()
	{
		return new VectorIterator(this);
	}		
}

