import processing.core.PApplet;
import processing.core.PVector;

import java.util.Collections;
import java.util.LinkedList;
import java.util.ListIterator;

public class Screen 
{
	PApplet parent;
	Grid grid;
	int cellCount = 100;
	int frame;
	
	LinkedList<Component> cList;
	LinkedList<Grid> gList;
	
	Screen(PApplet p)
	{
		parent = p;
		frame = 0;
		
		cList = new LinkedList<Component>();
		gList = new LinkedList<Grid>();

		//grid = new Danger((parent.width / cellCount), (parent.height / cellCount), cellCount, cellCount, parent, "1i", true);
		grid = new GoalGrid((parent.width / cellCount), (parent.height / cellCount), cellCount, cellCount, parent, "1i", true);
	}
	
	void setup()
	{	
		cList.add(new Roomba(parent, new PVector(600, 600), 20, grid));
		cList.add(new Roomba(parent, new PVector(200, 200), 20, grid));
		cList.add(new Roomba(parent, new PVector(600, 200), 20, grid));

		cList.add(new Rectangle(parent, grid, new PVector(100, 400), (int)grid.cellWidth*4, (int)grid.cellHeight*4));
		cList.add(new Rectangle(parent, grid, new PVector(300, 500), (int)grid.cellWidth*4, (int)grid.cellHeight*4));
		cList.add(new Rectangle(parent, grid, new PVector(400, 300), (int)grid.cellWidth*4, (int)grid.cellHeight*4));
		cList.add(new Rectangle(parent, grid, new PVector(400, 600), (int)grid.cellWidth*4, (int)grid.cellHeight*4));
		cList.add(new Rectangle(parent, grid, new PVector(400, 400), (int)grid.cellWidth*4, (int)grid.cellHeight*4));
		cList.add(new Rectangle(parent, grid, new PVector(200, 300), (int)grid.cellWidth*8, (int)grid.cellHeight*32));
		cList.add(new Rectangle(parent, grid, new PVector(200, 300), (int)grid.cellWidth*32, (int)grid.cellHeight*6));
		cList.add(new Rectangle(parent, grid, new PVector(400, 300), (int)grid.cellWidth*8, (int)grid.cellHeight*32));

		gList.add(grid);
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
		if(!cList.isEmpty())
		{
			//Iterate through the list backwards
			//This way things on top have priority for consuming a mouse click
			ListIterator<Component> itr = cList.listIterator(cList.size());
			while(itr.hasPrevious())
			{
				Component c = itr.previous();
				if(c.mouseClicked())
				{
					return;
				}
			}
		}

		if(grid.mouseClicked())
		{
			//Reset the influence map
			((GoalGrid)gList.get(0)).mouseClicked();
			return;			
		}
	}
	
	void mousePressed()
	{
		if(!cList.isEmpty())
		{
			//Iterate through the list backwards
			//This way things on top have priority for consuming a mouse press
			ListIterator<Component> itr = cList.listIterator(cList.size());
			while(itr.hasPrevious())
			{
				Component c = itr.previous();
				if(c.mousePressed())
				{
					//Moves the currently pressed terrain to the bottom of the list
					//This subsequently makes it so it is drawn last, and therefore on top
					Collections.rotate(cList.subList(itr.nextIndex(), cList.size()), -1);
					break;
				}
			}
		}
	}
	
	boolean doesAnyComponentContainPoint(PVector p) {
		ListIterator<Component> itr = cList.listIterator(cList.size());

		while(itr.hasPrevious())
		{
			Component c = itr.previous();
			if(c.containsPoint(p))
			{
				return true;
			}
		}
		return false;
	}
	
	boolean doesAnyComponentIntersectLine(PVector origin, PVector terminal) {
		ListIterator<Component> itr = cList.listIterator(cList.size());

		while(itr.hasPrevious())
		{
			Component c = itr.previous();
			if(c.intersectsLine(origin, terminal))
			{
				return true;
			}
		}
		return false;
	}
}
