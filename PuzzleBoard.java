import java.util.PriorityQueue;
import java.util.Comparator;

public class PuzzleBoard 
{
	private int rowSize;
	private int[][] board;
	private int numMoves;
	
	/**
	*Constructor that makes a PuzzleBoard that matches an input 2D array.
	*
	*@param 2D array that will make up the content of the PuzzleBoard
	*/
	public PuzzleBoard(int[][] input)
	{
		rowSize = input.length;
		board = new int[rowSize][rowSize];
		numMoves = 0;
		
		for(int i = 0; i < input.length; i++)
		{
			for(int j = 0; j < input[i].length; j++)
			{
				board[i][j] = input[i][j];
			}
		}	
		
	}
	
	private void addNumMoves()
	{
		numMoves ++;
	}
	
	public int getNumMoves()
	{
		return numMoves;
	}
	
	
	/**
	*Returns a string representation of the PuzzleBoard
	*
	*@return String representation of the PuzzleBoard
	*/
	public String toString()
	{
		String output = "";
		
		for(int i = 0; i < rowSize; i++)
		{
			output = output + "\n";
			for(int j = 0; j < rowSize; j++)
				output = output + board[i][j];
		}
		return output;
	}
	
	/**
	*Returns the size of each row/column in the PuzzleBoard
	*
	*@return size of each row/column in the PuzzleBoard
	*/
	public int size()
	{
		return rowSize;
	}
	
	/**
	*Returns the number of out of place tiles.  Used in the Hamming priority function.
	*
	*@return Number of out of place tiles
	*/
	public int hamming()
	{
		int outOfPlaceCount = 0;
		for(int i = 0; i < rowSize; i++)
		{
			for(int j = 0; j < rowSize; j++)
			{
				if(board[i][j] != 0 && board[i][j]!=  ((i * 3) + (j + 1)))
					outOfPlaceCount ++;
			}
		}
		return outOfPlaceCount;
				
	}
	
	/**
	*Returns the sum of the distances of each tile from their target positions.
	*Used in the New York priority function.
	*
	*@return Sum of distances of each tile from target positions.
	*/
	public int manhattan()
	{
		int sumDistance = 0;
		for(int i = 0; i < rowSize; i++)
		{
			for(int j = 0; j < rowSize; j++)
			{
				if(board[i][j] != 0)
				{
					int targetRow = board[i][j] / 3;
					int targetCol = (board[i][j] % 3) - 1;
					sumDistance += Math.abs(i - targetRow);
					sumDistance += Math.abs(j - targetRow);	
				}		 
			}
		}
		
		return sumDistance;
	}
	
	/**
	*Returns whether or not the puzzle is in a solved state.  A solved state is defined as
	*a board in which each number is lined up sequentially, left to right, starting at 1
	*in the top left corner.  0 represents a lack of tiles, so it is not counted.
	*
	*@return whether or not the puzzle is in a solved state.
	*/
	public boolean isGoal()
	{
		for(int i = 0; i < rowSize; i++)
		{
			for(int j = 0; j < rowSize; j++)
			{
				int target = ((i * 3) + (j + 1));
				if(board[i][j] != target && board[i][j] != 0)
					return false;
			}
		}
		return true;
	}
	
	/**
	*Evaluates whether a PuzzleBoard can be solved.  Only works for boards with odd size rows.
	*A PuzzleBoard is not solvable if the number of "inversions" is odd.  An inversion is any 
	*pair of tiles where the higher value tile comes before the lowest value tile.  Position
	*is evaluated in left to right, row by row order.
	*
	*@return whether of not this PuzzleBoard is solvable.
	*/
	public boolean isSolvable()
	{
		int numInversions = 0;
		for(int firstInd = 0; firstInd < (Math.pow(rowSize, 2) - 2); firstInd++)
		{
			for(int secondInd = firstInd + 1; secondInd < (Math.pow(rowSize,2 )) - 1; secondInd ++)
			{
				int firstVal = board[firstInd / 3][firstInd % 3];
				int secondVal = board[secondInd / 3][secondInd % 3];
				if(firstVal > secondVal)
					numInversions ++;
			}
		}
		if(numInversions % 2 == 1)
			return false;
		else
			return true;
	}
	
	/**
	*Evaluates whether two PuzzleBoards have identical contents
	*
	*@param y Object to be compared to this object.
	*@return whether or not this and y are equal.
	*/
	public boolean equals(Object y)
	{
		if(y.toString().equals(this.toString()))
			return true;
		else
			return false;
	}
	
	/**
	*Returns an Iterable containing all possible boardstates available from the current board after
	*one move.  Possible boardstates include every board in which the space containing 0 is
	*swapped with all adjacent spaces.
	*
	*@return Iterable containing all possible boardstates possible in one move from current board.
	*/
	public Vector<PuzzleBoard> neighbors()
	{
		Vector neighbors = new Vector<PuzzleBoard>();
		
		int spaceIndX = 0;
		int spaceIndY = 0;
		
		for(int i = 0; i < rowSize; i++)
		{
			for(int j = 0; j < rowSize; j++)
			{
				if(board[i][j] == 0)
				{
					System.out.println(i);
					System.out.println(j);
					spaceIndX = i;
					spaceIndY = j;
				}
			}
			
		}
		
		if(spaceIndX != 0)
		{
			neighbors.add(swap(spaceIndX, spaceIndY, spaceIndX - 1, spaceIndY));
		}
		if(spaceIndX != rowSize - 1)
		{
			neighbors.add(swap(spaceIndX, spaceIndY, spaceIndX + 1, spaceIndY));
		}
		if(spaceIndY != 0)
		{
			neighbors.add(swap(spaceIndX, spaceIndY, spaceIndX, spaceIndY - 1));
		}
		if(spaceIndY != rowSize - 1)
		{
			System.out.println("hits2");
			System.out.println(swap(spaceIndX, spaceIndY, spaceIndX, spaceIndY + 1));

		}
		for(int i = 0; i < neighbors.size(); i++)
		{
			System.out.println(neighbors.get(i));
			PuzzleBoard add = (PuzzleBoard) neighbors.get(i);
			add.addNumMoves();
		}
		
		return neighbors;
	}
	
	/**
	*Returns a puzzle board with values at 2 given indexes swapped.
	*
	*@param swapAX X coordinate of first value to be swapped.
	*@param swapAY Y coordinate of first value to be swapped.
	*@param swapBX X coordinate of second value to be swapped.
	*@param swapBY Y coordinate of second value to be swapped.
	*@return PuzzleBoard with values at given coordinates swapped.
	*/
	private PuzzleBoard swap(int swapAX, int swapAY, int swapBX, int swapBY)
	{
		int valA = board[swapAX][swapAY];
		int valB = board[swapBX][swapBY];
		
		board[swapAX][swapAY] = valB;
		board[swapBX][swapBY] = valA;
		
		PuzzleBoard swapped = new PuzzleBoard(this.board);
				
		board[swapAX][swapAY] = valA;
		board[swapBX][swapBY] = valB;
		
		return swapped; 
	}
	
	/**
	*Returns a comparator that compares two boards by Manhattan Algorithm, along with how many moves
	*taken to get to the current state.
	*
	*@return an instance of PuzzleBoardManhattanComparator
	*/
	public Comparator manhattanComparator()
	{
		return new PuzzleBoardManhattanComparator();
	}
	
	/**
	*Returns a comparator that compares two boards by Hamming Algorithm, along with how many moves
	*taken to get to the current state.
	*
	*@return an instance of PuzzleBoardHammingComparator
	*/
	public Comparator hammingComparator()
	{
		return new PuzzleBoardHammingComparator();
	}
	
private class PuzzleBoardManhattanComparator implements Comparator<PuzzleBoard>
{
	public int compare(PuzzleBoard pOne, PuzzleBoard pTwo)
	{
		return (pOne.manhattan() + pOne.getNumMoves()) - (pTwo.manhattan() + pTwo.getNumMoves()) ;
	}
	
	public boolean equals (Object obj)
	{
		return true;
	}
}	

private class PuzzleBoardHammingComparator implements Comparator<PuzzleBoard>
{
	public int compare(PuzzleBoard pOne, PuzzleBoard pTwo)
	{
		return(pOne.hamming() - pTwo.hamming());
	}
	
	public boolean equals(Object obj)
	{
		return true;
	}
	
}
}
