import java.util.ListIterator;

import processing.core.PApplet;


public class Danger extends Grid
{
	Danger(float cH, float cW, int _columns, int _rows, PApplet p, String _name, boolean _noDecay)
	{
		super(cH, cW, _columns, _rows, p, _name, _noDecay);
	}
	
	void draw()
	{
		super.draw();
	}
	
	void update()
	{
		// Decay influence over cells
		if(!noDecay)
		{
			for (int i = 0; i < columns; i++)
			{
				for (int j = 0; j < rows; j++)
				{
					if((parent.frameCount % decay_interval) == 0 && cells[i][j].influence > 0.0)
					{
						cells[i][j].influence -= decay;
					}
				}
			}
		}
		
		if(!iList.isEmpty() && (parent.frameCount % propogation_interval) == 0)
		{
			ListIterator<Influence> itr = iList.listIterator();
			while(itr.hasNext())
			{
				Influence influencePoint = itr.next();
				
				if(influencePoint.influence > 0.0)
				{
					influencePoint.propogateInfluence();
				}
				else
				{
					itr.remove();
				}
			}
		}
	}
	
	void mouseClicked()
	{
		int i = (int)(parent.mouseX / cellWidth);
		int j = (int)(parent.mouseY / cellHeight);
		if(i < columns && i > 0 && j < rows && j > 0)
		{
			iList.add(new Influence(parent, this, i, j));
		}
	}	
}
