import processing.core.PApplet;
import processing.core.PVector;


public class CoverGrid extends Grid
{
	
	int goal_x;
	int goal_y;
	CoverGrid(float cH, float cW, int _columns, int _rows, PApplet p, String _name, boolean _noDecay)
	{

		super(cH, cW, _columns, _rows, p, _name, _noDecay);
		goal_x = 200;
		goal_y = 200;
	}

	void draw()
	{
		super.draw();
	}
	
	
	void update()
	{
		// Decay influence over cells
		//recurse(0,0, 1);

	}
	
	boolean mouseClicked()
	{
		goal_x = (int)(parent.mouseX / cellWidth);
		goal_y = (int)(parent.mouseY / cellHeight);
		for (int i = 0; i < columns; i++)
		{
			for (int j = 0; j < rows; j++)
			{
				PVector p = new PVector((i+0.5f)*cellWidth,(j+0.5f)*cellHeight);
				PVector o = new PVector((goal_x+0.5f)*cellWidth,(goal_y+0.5f)*cellHeight);
				if(((main)parent).screen.doesAnyComponentIntersectLine(o,p))
					cells[i][j].influence = 0;
				else
					cells[i][j].influence = 1- (float)Math.pow(PVector.sub(p,o).mag()/900,2);

			}
		}

		int i = goal_x = (int)(parent.mouseX / cellWidth);
		int j = goal_y = (int)(parent.mouseY / cellHeight);

		return false;
	}	
}
