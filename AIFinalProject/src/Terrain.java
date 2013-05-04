import processing.core.PApplet;
import processing.core.PVector;


public abstract class Terrain extends Component
{	
	enum Corner {TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT, CENTER, NONE};

	Corner grabbed;

	PApplet parent;
	main pMain;
	
	PVector position;
	PVector oldMousePosition;
	PVector oldTerrainPosition;
	
	Grid grid;

	Terrain(PApplet _parent, Grid _grid, PVector _position)
	{		
		parent = _parent;
		pMain = (main)_parent;
		grid = _grid;
		position = _position;
		grabbed = Corner.NONE;
		oldMousePosition = new PVector(-1, -1);
		oldTerrainPosition = new PVector(_position.x, _position.y);
		
		snapTo();
		PVector newPos = snapTo();
		position.x = newPos.x;
		position.y = newPos.y;
	}
	
	void update()
	{
		// Reset if we let go of mouse
		if(!parent.mousePressed)
			grabbed = Corner.NONE;
		//Using explicit corner cases for clarity. Could simplify but might make this condition confusing
		else if((grabbed == Corner.BOTTOM_RIGHT || grabbed == Corner.BOTTOM_LEFT || grabbed == Corner.TOP_RIGHT || grabbed == Corner.TOP_LEFT) && oldMousePosition.x > -1 && oldMousePosition.y > -1)
		{
			resize();
		}
		else if(grabbed == Corner.CENTER && oldMousePosition.x > -1 && oldMousePosition.y > -1)
		{
			PVector deltaPos = changeInPosition();
			position.x += deltaPos.x;
			position.y += deltaPos.y;
			PVector newPos = snapTo();
			position.x = newPos.x;
			position.y = newPos.y;
			changeInMouse();
		}
				
		oldMousePosition.x = parent.mouseX;
		oldMousePosition.y = parent.mouseY;
	}
	
	void resize(){/*Implementation in derived classes*/}
			
	PVector changeInPosition()
	{
		float deltaX = parent.mouseX - oldMousePosition.x;
		float deltaY = parent.mouseY - oldMousePosition.y;
		
		return new PVector(deltaX, deltaY);		
	}
		
	void changeInMouse()
	{
		float deltaX = position.x - oldTerrainPosition.x;
		float deltaY = position.y - oldTerrainPosition.y;
		
		parent.mouseX += deltaX;
		parent.mouseY += deltaY;
	}
		
	PVector snapTo()
	{
		int i = (int)(position.x / grid.cellWidth);
		int j = (int)(position.y / grid.cellHeight);
		
		oldTerrainPosition.x = position.x;
		oldTerrainPosition.y = position.y;
		
		if(i < grid.columns && i > 0 && j < grid.rows && j > 0)
		{
			return new PVector(grid.grid[i][j].x, grid.grid[i][j].y);
		}
		return null;
	}
}
