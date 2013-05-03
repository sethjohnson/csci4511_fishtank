import processing.core.PApplet;
import processing.core.PVector;
import java.util.LinkedList;
import java.util.ListIterator;

public class Screen 
{
	PApplet parent;
	Grid grid;
	int cellCount = 100;
	int frame;
	
	LinkedList<Component> cList;
	
	Screen(PApplet p)
	{
		parent = p;
		frame = 0;
		
		cList = new LinkedList<Component>();
		grid = new Grid((parent.width / cellCount), (parent.height / cellCount), cellCount, cellCount, parent);
	}
	
	void setup()
	{	
		cList.add(new Roomba(parent, new PVector(100, 200), 20, grid));
		cList.add(new Rectangle(parent, new PVector(parent.width/2, parent.height/2), 25, 25));
	}
	
	void draw()
	{
		grid.draw();
		
		if(!cList.isEmpty())
		{
			ListIterator<Component> itr = cList.listIterator();
			while(itr.hasNext())
			{
				Component c = itr.next();
				c.draw();
			}
		}
	}
	
	void update()
	{
		grid.update();

		if(!cList.isEmpty())
		{
			ListIterator<Component> itr = cList.listIterator();
			while(itr.hasNext())
			{
				Component c = itr.next();
				c.update();
			}
		}
	}
	
	void mouseClicked()
	{
		grid.mouseClicked();
		
		if(!cList.isEmpty())
		{
			ListIterator<Component> itr = cList.listIterator();
			while(itr.hasNext())
			{
				Component c = itr.next();
				c.mouseClicked();
			}
		}
	}
	
	void mousePressed()
	{
		if(!cList.isEmpty())
		{
			ListIterator<Component> itr = cList.listIterator();
			while(itr.hasNext())
			{
				Component c = itr.next();
				c.mousePressed();
			}
		}
	}
}
