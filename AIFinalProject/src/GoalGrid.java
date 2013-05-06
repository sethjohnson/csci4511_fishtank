import processing.core.PApplet;
import processing.core.PVector;


public class GoalGrid extends Grid
{
	
	int goal_x;
	int goal_y;
	GoalGrid(float cH, float cW, int _columns, int _rows, PApplet p, String _name, boolean _noDecay)
	{

		super(cH, cW, _columns, _rows, p, _name, _noDecay);
		goal_x = 200;
		goal_y = 200;
	}

	void draw()
	{
		super.draw();
	}
	void recurse(int x, int y, float i){
		if(x >= columns || x < 0 || y >= rows || y < 0) return;
		
		if(cells[x][y].influence >= i) return;
		PVector p = new PVector((x+0.5f)*cellWidth,(y+0.5f)*cellHeight);

		if(((main)parent).screen.doesAnyComponentContainPoint(p)){
			return;
		}

		cells[x][y].influence = i;
		//System.out.println("Doing stuff");
		float dec = 0.005f;
		//recurse(x-1, y-1, i-dec*1.5f);
		recurse(x, 	 y-1, i-dec);
		//recurse(x+1, y-1, i-dec*1.5f);
		recurse(x-1, y, i-dec);
		//recurse(x, 	 y, i-0.01f);
		recurse(x+1, y, i-dec);	
		//recurse(x-1, y+1, i-dec*1.5f);
		recurse(x, 	 y+1, i-dec);
		//recurse(x+1, y+1, i-dec*1.5f);	
	}
	void update()
	{
		// Decay influence over cells
		//recurse(0,0, 1);

	}
	
	void mouseClicked()
	{
		for (int i = 0; i < columns; i++)
		{
			for (int j = 0; j < rows; j++)
			{

				cells[i][j].influence = 0;

			}
		}

		int i = goal_x = (int)(parent.mouseX / cellWidth);
		int j = goal_y = (int)(parent.mouseY / cellHeight);

		recurse(i,j,1);
	}	
}
