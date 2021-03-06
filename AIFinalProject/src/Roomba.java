import java.util.LinkedList;
import java.util.ListIterator;

import processing.core.PApplet;
import processing.core.PVector;

public class Roomba extends Sprite
{
	LinkedList<Grid> grids = new LinkedList<Grid>();
	Grid grid;
	float threshold = 0.01f;
	main pMain;
	LinkedList<PVector> trail = new LinkedList<PVector>();
	
	Roomba(PApplet p, PVector pv, float r, Grid _grid)
	{
		super(p, pv);
		
		grid = _grid;		
		dimension = new PVector(r, r);
		pMain = (main)p;
	}
	
	Roomba(PApplet p, PVector pv, float r, LinkedList<Grid> _grids)
	{
		super(p, pv);
		
		grids = _grids;		
		dimension = new PVector(r, r);
	}

	void draw()
	{
		super.draw();
		if(trail.size() > 2) {
			ListIterator itr_a = trail.listIterator();
			ListIterator itr_b = trail.listIterator();
			itr_b.next();
			parent.stroke(255);
			while(itr_b.hasNext()) {
				PVector a = (PVector)itr_a.next();
				PVector b = (PVector)itr_b.next();
				
				parent.line(a.x, a.y, b.x, b.y);
			}
			System.out.println(trail.size());
		}


		//for( )
		parent.pushMatrix();
		parent.translate(position.x, position.y);
		parent.rotate(direction.heading());

		parent.fill(127);
		parent.stroke(0);
		parent.ellipse(0, 0, 2*dimension.x, 2*dimension.y);
		parent.line(0, 0, dimension.x, 0);
		parent.popMatrix();	
	}
	
	void update()
	{
		super.update();
		
		if(!pMain.paused)
		{
			trail.add(new PVector(position.x, position.y));
			Cell pull = pull();
			if(pull != null)
			{
				PVector newDirection = new PVector(pull.x, pull.y);
				direction.add(PVector.mult(PVector.fromAngle((PVector.sub(newDirection, position).heading())), turn_speed*5));
				
				if(direction.mag() > max_velocity)
				{
					direction.setMag(max_velocity);
				}
			}
			position.add(direction);
		}
	}

	boolean mousePressed()
	{
		if(parent.mouseX > position.x - dimension.x && parent.mouseX < position.x + dimension.x  && parent.mouseY > position.y - dimension.y && parent.mouseY < position.y + dimension.y)
		{
			grabbed = true;
			return true;
		}
		return false;
	}

	Cell pull()
	{
		int c_index, r_index;
		int i = c_index = (int)(position.x / grid.cellWidth);
		int j = r_index =(int)(position.y / grid.cellHeight);
		int delta = 3;
		
		float max = 0.0f;
		Cell pull = null;
		
		if(i < grid.columns && i > 0 && j < grid.rows && j > 0 && grid.cells[i][j].influence > threshold)
		{
			// Checks the influence around the Room-bah.
			for(int runner = 0; runner < 5; runner++)
			{
				if(c_index-delta >= 0 && r_index-delta+runner >= 0 && r_index-delta+runner < grid.rows)
				{
					if(grid.cells[c_index-delta][r_index-delta+runner].influence > max)
					{
						max = grid.cells[c_index-delta][r_index-delta+runner].influence;
						pull = grid.cells[c_index-delta][r_index-delta+runner];
					}
				}
				if(r_index+delta-runner >= 0 && r_index+delta-runner < grid.rows && c_index+delta < grid.columns)
				{
					if(grid.cells[c_index+delta][r_index+delta-runner].influence > max)
					{
						max = grid.cells[c_index+delta][r_index+delta-runner].influence;
						pull = grid.cells[c_index+delta][r_index+delta-runner];
					}
				}
			}
			
			for(int runner = 1; runner < 4; runner++)
			{
				if(c_index-delta+runner >= 0 && c_index-delta+runner < grid.columns && r_index-delta >= 0)
				{
					if(grid.cells[c_index-delta+runner][r_index-delta].influence > max)
					{
						max = grid.cells[c_index-delta+runner][r_index-delta].influence;
						pull = grid.cells[c_index-delta+runner][r_index-delta];
					}
				}
				
				if(c_index+delta-runner >= 0 && c_index+delta-runner < grid.columns && r_index+delta < grid.rows)
				{
					if(grid.cells[c_index+delta-runner][r_index+delta].influence > max)
					{
						max = grid.cells[c_index+delta-runner][r_index+delta].influence;
						pull = grid.cells[c_index+delta-runner][r_index+delta];
					}
				}			
			}
		}
		return pull;
	}
	
	//Update functions
	Cell pull2()
	{
		int c_index, r_index;
		int i;
		c_index = (int)(position.x / grids.getFirst().cellWidth);
		int j;
		r_index =(int)(position.y / grids.getFirst().cellHeight);
		
		float[][] options = new float[5][5];
		float max = 0.0f;
		int max_i = 0;
		int max_j = 0;
		int pull_c;
		int pull_r;
		Cell pull = null;
		
		//initialize options
		for(i = 0; i<5; i++)
		{
			for(j=0; j<5; j++)
			{
				options[i][j] = 0.0f;
			}
		}
		
		//add all grids together into the options array
		for(i = -2; i<3; i++)
		{
			for(j = -2; j<3; j++)
			{
				for(Grid g : grids)
				{
					if((c_index+i<=0) || (r_index+j<=0) || (c_index+i>=g.columns) || (r_index+j>=g.rows))
					{
						
					}
					else
					{
						options[i+2][j+2] += g.cells[c_index+i][r_index+j].influence;
					}
				}
			}
		}
		
		for(i = 0; i<5; i++)
		{
			for(j=0; j<5; j++)
			{
				if(options[i][j] >=max)
				{
					if((c_index+i-2<=0) || (r_index+j-2<=0) || (c_index+i-2>=grids.getFirst().columns) || (r_index+j-2>=grids.getFirst().rows))
					{
						
					}
					else
					{
						max = options[i][j];
						max_i = i;
						max_j = j;
					}
				}
			}
		}
		
		pull_c = max_i-2+c_index;
		pull_r = max_j-2+r_index;
		
		Grid pullGrid = grids.getFirst();
		pull = pullGrid.cells[pull_c][pull_r];
		return pull;
		
		
	}	
//	boolean containsPoint(PVector p){
//		
//		if(PVector.sub(p,position).magSq() < (dimension.x*dimension.x))
//			return true;
//		return false;
//	}
} // End of class

