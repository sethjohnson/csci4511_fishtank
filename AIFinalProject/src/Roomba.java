import java.util.LinkedList;

import processing.core.PApplet;
import processing.core.PVector;

public class Roomba extends Sprite
{
	LinkedList<Grid> grids = new LinkedList<Grid>();
	Grid grid;
	float threshold = 0.01f;
	
	Roomba(PApplet p, PVector pv, float r, Grid _grid)
	{
		super(p, pv);
		
		grid = _grid;		
		dimension = new PVector(r, r);
	}
	
	void draw()
	{	
		parent.fill(parent.color(127, 127, 127));
		parent.stroke(0);
		parent.ellipse(0, 0, 2*dimension.x, 2*dimension.y);
		parent.line(0, 0, dimension.x, 0);
	}
	
	//Update functions
	//Work in Progress: trying to update based upon all the grids in the list of grids. -Conrad
	/*void update2(float d_t)
	{
		int c_index, r_index;
		int i = c_index = (int)(position.x / grid.cellWidth);
		int j = r_index =(int)(position.y / grid.cellHeight);
		int delta = 3;
		
		Cell[][] options = new Cell[5][5];
		float max = 0.0f;
		Cell pull = null;
		
		
		if(i < grid.columns && i > 0 && j < grid.rows && j > 0 && grid.grid[i][j].influence > threshold)
		{
		
			//fill up the options array
			for(Grid grid : grids)
			{
				int k;
				int l;
				for(int runner = 0; runner < 5; runner++)
				{
					if(c_index-delta >= 0 && r_index-delta+runner >= 0 && r_index-delta+runner < grid.rows)
					{
						if(grid.grid[c_index-delta][r_index-delta+runner].influence > max)
						{
							max = grid.grid[c_index-delta][r_index-delta+runner].influence;
							pull = grid.grid[c_index-delta][r_index-delta+runner];
						}
					}
					if(r_index+delta-runner >= 0 && r_index+delta-runner < grid.rows && c_index+delta < grid.columns)
					{
						if(grid.grid[c_index+delta][r_index+delta-runner].influence > max)
						{
							max = grid.grid[c_index+delta][r_index+delta-runner].influence;
							pull = grid.grid[c_index+delta][r_index+delta-runner];
						}
					}
				}
			}
			// Checks the influence around the Room-bah.
			for(int runner = 0; runner < 5; runner++)
			{
				if(c_index-delta >= 0 && r_index-delta+runner >= 0 && r_index-delta+runner < grid.rows)
				{
					if(grid.grid[c_index-delta][r_index-delta+runner].influence > max)
					{
						max = grid.grid[c_index-delta][r_index-delta+runner].influence;
						pull = grid.grid[c_index-delta][r_index-delta+runner];
					}
				}
				if(r_index+delta-runner >= 0 && r_index+delta-runner < grid.rows && c_index+delta < grid.columns)
				{
					if(grid.grid[c_index+delta][r_index+delta-runner].influence > max)
					{
						max = grid.grid[c_index+delta][r_index+delta-runner].influence;
						pull = grid.grid[c_index+delta][r_index+delta-runner];
					}
				}
			}
			
			for(int runner = 1; runner < 4; runner++)
			{
				if(c_index-delta+runner >= 0 && c_index-delta+runner < grid.columns && r_index-delta >= 0)
				{
					if(grid.grid[c_index-delta+runner][r_index-delta].influence > max)
					{
						max = grid.grid[c_index-delta+runner][r_index-delta].influence;
						pull = grid.grid[c_index-delta+runner][r_index-delta];
					}
				}
				
				if(c_index+delta-runner >= 0 && c_index+delta-runner < grid.columns && r_index+delta < grid.rows)
				{
					if(grid.grid[c_index+delta-runner][r_index+delta].influence > max)
					{
						max = grid.grid[c_index+delta-runner][r_index+delta].influence;
						pull = grid.grid[c_index+delta-runner][r_index+delta];
					}
				}			
			}
		}


		if(max > 0.0)
		{
			PVector mouse = new PVector(pull.x, pull.y);
			direction.add(PVector.mult(PVector.fromAngle((PVector.sub(mouse, position).heading())), turn_speed));
			
			if(direction.mag() > max_velocity)
			{
				direction.setMag(max_velocity);
			}
		}
		position.add(PVector.mult(direction, d_t));
				
		super.update(); // transformations
		
		
	}*/	
	void update(float d_t)
	{
		int c_index, r_index;
		int i = c_index = (int)(position.x / grid.cellWidth);
		int j = r_index =(int)(position.y / grid.cellHeight);
		int delta = 3;
		
		float max = 0.0f;
		Cell pull = null;
		
		if(i < grid.columns && i > 0 && j < grid.rows && j > 0 && grid.grid[i][j].influence > threshold)
		{
		
			// Checks the influence around the Room-bah.
			for(int runner = 0; runner < 5; runner++)
			{
				if(c_index-delta >= 0 && r_index-delta+runner >= 0 && r_index-delta+runner < grid.rows)
				{
					if(grid.grid[c_index-delta][r_index-delta+runner].influence > max)
					{
						max = grid.grid[c_index-delta][r_index-delta+runner].influence;
						pull = grid.grid[c_index-delta][r_index-delta+runner];
					}
				}
				if(r_index+delta-runner >= 0 && r_index+delta-runner < grid.rows && c_index+delta < grid.columns)
				{
					if(grid.grid[c_index+delta][r_index+delta-runner].influence > max)
					{
						max = grid.grid[c_index+delta][r_index+delta-runner].influence;
						pull = grid.grid[c_index+delta][r_index+delta-runner];
					}
				}
			}
			
			for(int runner = 1; runner < 4; runner++)
			{
				if(c_index-delta+runner >= 0 && c_index-delta+runner < grid.columns && r_index-delta >= 0)
				{
					if(grid.grid[c_index-delta+runner][r_index-delta].influence > max)
					{
						max = grid.grid[c_index-delta+runner][r_index-delta].influence;
						pull = grid.grid[c_index-delta+runner][r_index-delta];
					}
				}
				
				if(c_index+delta-runner >= 0 && c_index+delta-runner < grid.columns && r_index+delta < grid.rows)
				{
					if(grid.grid[c_index+delta-runner][r_index+delta].influence > max)
					{
						max = grid.grid[c_index+delta-runner][r_index+delta].influence;
						pull = grid.grid[c_index+delta-runner][r_index+delta];
					}
				}			
			}
		}


		if(max > 0.0)
		{
			PVector mouse = new PVector(pull.x, pull.y);
			direction.add(PVector.mult(PVector.fromAngle((PVector.sub(mouse, position).heading())), turn_speed));
			
			if(direction.mag() > max_velocity)
			{
				direction.setMag(max_velocity);
			}
		}
		position.add(PVector.mult(direction, d_t));
				
		super.update(); // transformations
		
		
//		PVector mouse = new PVector(parent.mouseX, parent.mouseY);
//		if(parent.mousePressed)
//		{
//			direction.add(PVector.mult(PVector.fromAngle((PVector.sub(mouse,position).heading())), turn_speed));
//			
//			if(direction.mag() > max_velocity)
//			{
//				direction.setMag(max_velocity);
//			}
//		}
//		position.add(PVector.mult(direction, d_t));
	}
}
