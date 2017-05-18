package pl.isangeles.senlin.graphic;

import java.io.IOException;

import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.util.GConnector;
/**
 * Class for day phases
 * @author Isangeles
 *
 */
public class DayPhase 
{
	public static final int MORNING = 0,
							MIDDAY = 1,
							AFTERNON = 2,
							NIGHT = 3;
	private Sprite morningFilter,
				   middayFilter,
				   afternonFilter,
				   nightFilter;
	private int phaseId;
	
	public DayPhase() throws SlickException, IOException 
	{
		middayFilter = new Sprite(GConnector.getInput("day/midday.png"), "dayFilterMidday", false);
		afternonFilter = new Sprite(GConnector.getInput("day/afternon.png"), "dayFilterAfternon", false);
		nightFilter = new Sprite(GConnector.getInput("day/night.png"), "dayFilterNight", false);
	}

	public void draw(float x, float y)
	{
		switch(phaseId)
		{
		case MORNING:
			break;
		case MIDDAY:
			middayFilter.draw(x, y, false);
			break;
		case AFTERNON:
			afternonFilter.draw(x, y, false);
			break;
		case NIGHT:
			nightFilter.draw(x, y, false);
			break;
		}
	}
	
	public void changePhase(int phaseId)
	{
		this.phaseId = phaseId;
	}
	
	public void nextPhase()
	{
		if(phaseId < 3)
			phaseId ++;
		if(phaseId >= 3)
			phaseId = 0;
	}
}
