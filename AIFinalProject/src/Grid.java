import processing.core.PApplet;

import java.util.LinkedList;
import java.util.ListIterator;

public class Grid 
{
	PApplet parent;
	
	Cell[][] grid;
	
	LinkedList <Influence> iList = new LinkedList<Influence>();
	
	float cellHeight;
	float cellWidth;
	
	float decay;
	int decay_interval;

	int propogation_interval;

	int columns;
	int rows;

	Grid(float cH, float cW, int _columns, int _rows, PApplet p)
	{
		parent = p;
		
		cellHeight = cH;
		cellWidth = cW;
		columns = _columns;
		rows = _rows;

		propogation_interval = 1; // In frames
		decay_interval = 1; // In milliseconds
		decay = 0.025f;
		
		grid = new Cell[columns][rows];
		for(int i = 0; i < columns; i++)
		{
			for(int j = 0; j < rows; j++)
			{
				grid[i][j] = new Cell(i*cellWidth, j*cellHeight,cellWidth,cellHeight, p);
			}
		}
	}
	
	void draw()
	{
		parent.background(0);
		// The counter variables i and j are also the column and row numbers and 
		// are used as arguments to the constructor for each object in the grid.		
		for (int i = 0; i < columns; i++)
		{
			for (int j = 0; j < rows; j++)
			{
				grid[i][j].display();
				
				if((parent.frameCount % decay_interval) == 0 && grid[i][j].influence > 0.15)
				{
					grid[i][j].influence -= grid[i][j].influence*decay;
				}
				else if((parent.frameCount % decay_interval) == 0 && grid[i][j].influence > 0.0)
				{
					grid[i][j].influence -= decay;
				}
			}
		}
		
		if(!iList.isEmpty() && (parent.frameCount % propogation_interval) == 0)
		{
			ListIterator<Influence> itr = iList.listIterator();
			while(itr.hasNext())
			{
				Influence influencePoint = itr.next();
				
				if(influencePoint.influence > 0.0)
				{
					influencePoint.propogateInfluence();
				}
				else
				{
					itr.remove();
				}
			}
		}
		
		int i = (int)(parent.mouseX / cellWidth);
		int j = (int)(parent.mouseY / cellHeight);
		
		if(i < columns && i > 0 && j < rows && j > 0)
		{
			String str = Float.toString(grid[i][j].influence);
			parent.fill(255);
			parent.text(str, parent.mouseX, parent.mouseY);
		}
	}
	
	void update()
	{
		int i = (int)(parent.mouseX / cellWidth);
		int j = (int)(parent.mouseY / cellHeight);

		if(i < columns && i > 0 && j < rows && j > 0)
		{
			iList.add(new Influence(parent, this, i, j));
		}
	}	
}