import java.util.LinkedList;

import processing.core.PApplet;
import processing.core.PVector;

public class Influence 
{
	PApplet parent;
	Grid grid;
	LinkedList<Grid> grids = new LinkedList<Grid>();
	
	float momentum;
	float influence = 1.0f;
	int delta = 0;
	int area = 0;
	
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
					PVector p = new PVector((i+0.5f)*grid.cellWidth,(j+0.5f)*grid.cellHeight);
					PVector o = new PVector((c_index+0.5f)*grid.cellWidth,(r_index+0.5f)*grid.cellHeight);

					if(!((main)parent).screen.doesAnyComponentIntersectLine(o, p)){
						float radius = (i-c_index)*(i-c_index) + (j-r_index)*(j-r_index);
					
						if((area-2)*(area-2) <= radius && radius < area*area) 
							grid.cells[i][j].influence += influence;		
					}
				}
			}
		grids.remove(grid);
		grids.add(grid);
		delta++;
		area += 2;
		influence -= momentum;
	}
}