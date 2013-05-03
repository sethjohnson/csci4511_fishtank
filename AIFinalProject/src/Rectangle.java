import processing.core.PApplet;
import processing.core.PVector;


public class Rectangle extends Terrain
{
	int width;
	int height;	
	
	Rectangle(PApplet _parent, PVector _position, int w, int h)
	{
		super(_position, _parent);
		
		width = w;
		height = h;
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
	
	void mousePressed()
	{
		if(parent.mouseX > position.x && parent.mouseX < position.x + width)
		{
			grabbed = Corner.CENTER;
		}		
	}
}