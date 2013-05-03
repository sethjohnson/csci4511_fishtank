import processing.core.PApplet;

public class Influence 
{
	PApplet parent;
	Grid grid;
	
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
		
		momentum = 0.025f;
	}
	
	void propogateInfluence()
	{
		for(int i = Math.max(c_index-area, 0); i < Math.min(c_index+area, grid.columns); i++) {
			for(int j = Math.max(r_index-area, 0); j < Math.min(r_index+area, grid.rows); j++){
				float radius = (i-c_index)*(i-c_index) + (j-r_index)*(j-r_index);
				if(radius < area*area) grid.grid[i][j].influence += influence;
			}
		}
		/*
		for(int runner = 0; runner < area; runner++)
		{
			if(c_index-delta >= 0 && r_index-delta+runner >= 0 && r_index-delta+runner < grid.rows)
			{
				grid.grid[c_index-delta][r_index-delta+runner].influence += influence;
			}
			if(r_index+delta-runner >= 0 && r_index+delta-runner < grid.rows && c_index+delta < grid.columns)
			{
				grid.grid[c_index+delta][r_index+delta-runner].influence += influence;
			}
		}
		
		for(int runner = 1; runner < area - 1; runner++)
		{
			if(c_index-delta+runner >= 0 && c_index-delta+runner < grid.columns && r_index-delta >= 0)
			{
				grid.grid[c_index-delta+runner][r_index-delta].influence += influence;
			}
			
			if(c_index+delta-runner >= 0 && c_index+delta-runner < grid.columns && r_index+delta < grid.rows)
			{
				grid.grid[c_index+delta-runner][r_index+delta].influence += influence;
			}			
		}
		*/
		delta++;
		area += 2;
		influence -= momentum;
	}
}
