import java.util.LinkedList;

import processing.core.PApplet;

public class Influence 
{
	PApplet parent;
	Grid grid;
	LinkedList<Grid> grids = new LinkedList<Grid>();
	
	float momentum;
	float influence = 1.0f;
	int delta = 0;
	int area = 1;
	
	int c_index;
	int r_index;
	
	Influence(PApplet p, Grid _grid, int c, int r)
	{
		parent = p;
		grid = _grid;
		c_index = c;
		r_index = r;
		
		momentum = 0.07f;
	}
	
	void propogateInfluence()
	{
		for(int i = Math.max(c_index-area, 0); i < Math.min(c_index+area, grid.columns); i++) 
		{
			for(int j = Math.max(r_index-area, 0); j < Math.min(r_index+area, grid.rows); j++)
			{
				float radius = (i-c_index)*(i-c_index) + (j-r_index)*(j-r_index);
				
				if(radius < area*area) 
					grid.grid[i][j].influence += influence;
			}
		}
		
		delta++;
		area += 2;
		influence -= momentum;
	}
}