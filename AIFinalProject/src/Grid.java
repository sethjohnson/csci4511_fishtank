import java.util.LinkedList;
import processing.core.PApplet;

public abstract class Grid
{
	PApplet parent;
	String name;
	Cell[][] cells;
	
	LinkedList <Influence> iList = new LinkedList<Influence>();
	
	float cellHeight;
	float cellWidth;
	
	float decay;
	int decay_interval;
	int propogation_interval;

	int columns;
	int rows;

	boolean noDecay;
	
	Grid(float cH, float cW, int _columns, int _rows, PApplet p, String _name, boolean _noDecay)
	{
		parent = p;
		
		this.name = _name;
		cellHeight = cH;
		cellWidth = cW;
		columns = _columns;
		rows = _rows;

		propogation_interval = 1; // In frames
		decay_interval = 1; // In frames
		decay = 0.025f;
		
		noDecay = _noDecay;
		
		cells = new Cell[columns][rows];
		for(int i = 0; i < columns; i++)
		{
			for(int j = 0; j < rows; j++)
			{
				cells[i][j] = new Cell(i*cellWidth, j*cellHeight,cellWidth,cellHeight, p);
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
				cells[i][j].display();
			}
		}
		
		// Draw our influence values
		int i = (int)(parent.mouseX / cellWidth);
		int j = (int)(parent.mouseY / cellHeight);
		
		if(i < columns && i > 0 && j < rows && j > 0)
		{
			parent.fill(255);
			if(cells[i][j].influence < 0.01 && cells[i][j].influence > -0.01)
			{
				parent.text("0.0", parent.mouseX, parent.mouseY);
			}
			else
			{
				String str = Float.toString(cells[i][j].influence);
				parent.text(str, parent.mouseX, parent.mouseY);
			}
		}
	}
	
	void update()
	{
	}
	
	void mouseClicked()
	{
	}
	
	boolean equals(Grid g)
	{
		if(g.name == this.name)
		{
			return true;
		}
		return false;
	}
}
