import processing.core.PApplet;
import processing.core.PVector;


public class Rectangle extends Terrain
{
	int width;
	int height;	
	int cornerSize;
	
	Rectangle(PApplet _parent, Grid _grid, PVector _position, int w, int h)
	{
		super(_parent, _grid,_position);
		
		width = w;
		height = h;

		cornerSize = (int)pMain.screen.grid.cellWidth;
	}
	
	void draw()
	{
		parent.stroke(0);
		parent.fill(127);
		parent.rect(position.x, position.y, width, height);
	}

	void update()
	{
		super.update();
	}
	
	void resize()
	{
		if(width < grid.cellWidth * 2)
		{
			width = (int)grid.cellWidth * 2;
			return;
		}
		if(height < grid.cellHeight * 2)
		{
			height = (int)grid.cellHeight * 2;
			return;
		}

		PVector deltaPos = super.changeInPosition();					
		if(grabbed == Corner.TOP_LEFT)
		{
			width -= deltaPos.x;
			height -= deltaPos.y;
			position.x += deltaPos.x;
			position.y += deltaPos.y;
		}
		else if(grabbed == Corner.TOP_RIGHT)
		{
			width += deltaPos.x;
			height -= deltaPos.y;
			position.y += deltaPos.y;			
		}
		else if(grabbed == Corner.BOTTOM_LEFT)
		{			
			width -= deltaPos.x;
			height += deltaPos.y;
			position.x += deltaPos.x;
		}
		else if(grabbed == Corner.BOTTOM_RIGHT)
		{
			width += deltaPos.x;
			height += deltaPos.y;
		}
	}
	
	boolean mousePressed()
	{
		if(parent.mouseX > position.x && parent.mouseX < position.x + cornerSize && parent.mouseY > position.y && parent.mouseY < position.y + cornerSize)
		{
			grabbed = Corner.TOP_LEFT;
			return true;
		}
		else if(parent.mouseX > position.x + width - cornerSize && parent.mouseX < position.x + width && parent.mouseY > position.y && parent.mouseY < position.y + cornerSize)
		{
			grabbed = Corner.TOP_RIGHT;
			return true;
		}
		else if(parent.mouseX > position.x && parent.mouseX < position.x + cornerSize && parent.mouseY > position.y + height - cornerSize && parent.mouseY < position.y + height)
		{
			grabbed = Corner.BOTTOM_LEFT;
			return true;
		}
		else if(parent.mouseX > position.x + width - cornerSize && parent.mouseX < position.x + width && parent.mouseY > position.y + height - cornerSize && parent.mouseY < position.y + height)
		{
			grabbed = Corner.BOTTOM_RIGHT;
			return true;
		}
		else if(parent.mouseX > position.x && parent.mouseX < position.x + width && parent.mouseY > position.y && parent.mouseY < position.y + height)
		{
			grabbed = Corner.CENTER;
			return true;
		}
		return false;
	}
	boolean containsPoint(PVector p){
		if(p.x > position.x && p.x < position.x+width && p.y > position.y && p.y < position.y + height)
			return true;
		return false;
	}
	boolean intersectsLine(PVector origin, PVector terminal){
	
		return false;
	
	}


}