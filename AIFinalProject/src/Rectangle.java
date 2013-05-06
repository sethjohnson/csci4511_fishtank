import processing.core.PApplet;
import processing.core.PVector;
import processing.core.PApplet;
import processing.core.PVector;
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
		if(
				p.x > position.x-20
				&& p.x < position.x+width+20
				&& p.y > position.y -20
				&& p.y < position.y + height +20
				)
			return true;
		return false;
	}
	
	int aboveOrBelow(PVector a, PVector b, PVector point) {
		   float z = PVector.sub(a,b).cross(PVector.sub(a,point)).z;
		   if(z == 0) return 0;
		   return (int)(z/Math.abs(z));
		//return (int)(side/Math.abs(side));
	}
	
	boolean intersectsLine(PVector origin, PVector terminal){
		if(position.y > terminal.y && position.y > origin.y) return false;
		if(position.x > terminal.x && position.x > origin.x) return false;
		if(position.y+ height < terminal.y && position.y + height < origin.y) return false;
		if(position.x+ width < terminal.x && position.x + width < origin.x) return false;
		
		// Check to see which side of the line the initial point is on
		int side1 = aboveOrBelow(origin, terminal, position);
		int side2 = aboveOrBelow(origin, terminal, new PVector(position.x+width, position.y));
		int side3 = aboveOrBelow(origin, terminal, new PVector(position.x+width, position.y+height));
		int side4 = aboveOrBelow(origin, terminal, new PVector(position.x, 		 position.y+height));

		if(!(side1 == side2 && side2 == side3 && side3 == side4)){
			return true;
		}


		
		return false;
	}
}