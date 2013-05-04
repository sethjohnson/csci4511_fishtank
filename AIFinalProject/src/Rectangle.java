import processing.core.PApplet;
import processing.core.PVector;


public class Rectangle extends Terrain
{
	int width;
	int height;	
	
	Rectangle(PApplet _parent, Grid _grid, PVector _position, int w, int h)
	{
		super(_parent, _grid,_position);
		
		width = w;
		height = h;
	}
	
	void draw()
	{
		parent.noStroke(); // For looks.
		parent.fill(127);
		parent.rect(position.x, position.y, width, height);
		parent.stroke(0);
	}

	void update()
	{
		super.update();
	}
	
	boolean mousePressed()
	{
		if(parent.mouseX > position.x && parent.mouseX < position.x + width && parent.mouseY > position.y && parent.mouseY < position.y + height)
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
		if(p.x > position.x && p.x < position.x+width && p.y > position.y && p.y < position.y + height)
			return true;		
		
		return false;
	
	}


}