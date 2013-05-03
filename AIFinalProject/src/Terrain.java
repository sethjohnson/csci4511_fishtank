import processing.core.PApplet;
import processing.core.PVector;


public abstract class Terrain extends Component
{	
	enum Corner {TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT, CENTER, NONE};

	Corner grabbed;

	PApplet parent;
	
	PVector position;
	PVector oldMousePosition;
	PVector oldTerrainPosition;
	
	Grid grid;

	Terrain(PApplet _parent, Grid _grid, PVector _position)
	{
		parent = _parent;
		grid = _grid;
		position = _position;
		grabbed = Corner.NONE;
		oldMousePosition = new PVector(-1, -1);
		oldTerrainPosition = new PVector(-1, -1);
		snapTo();
	}
	
	void update()
	{
		// Reset if we let go of mouse
		if(!parent.mousePressed)
			grabbed = Corner.NONE;
		else if(grabbed == Corner.CENTER && oldMousePosition.x > -1 && oldMousePosition.y > -1)
		{
			changeInPosition();
			snapTo();
			changeInMouse();	
		}
				
		oldMousePosition.x = parent.mouseX;
		oldMousePosition.y = parent.mouseY;
	}
		
	void changeInPosition()
	{
		float deltaX = parent.mouseX - oldMousePosition.x;
		float deltaY = parent.mouseY - oldMousePosition.y;
		
		position.x += deltaX;
		position.y += deltaY;
	}
	
	void changeInMouse()
	{
		float deltaX = position.x - oldTerrainPosition.x;
		float deltaY = position.y - oldTerrainPosition.y;
		
		parent.mouseX += deltaX;
		parent.mouseY += deltaY;
	}
	
	void snapTo()
	{
		int i = (int)(position.x / grid.cellWidth);
		int j = (int)(position.y / grid.cellHeight);
		
		oldTerrainPosition.x = position.x;
		oldTerrainPosition.y = position.y;
		
		if(i < grid.columns && i > 0 && j < grid.rows && j > 0)
		{
			position.x = grid.grid[i][j].x;
			position.y = grid.grid[i][j].y;
		}
	}
}
