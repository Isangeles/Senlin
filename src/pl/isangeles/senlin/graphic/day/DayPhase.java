package pl.isangeles.senlin.graphic.day;

import java.io.IOException;

import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.graphic.Sprite;
import pl.isangeles.senlin.util.GConnector;
/**
 * Class for day phases
 * @author Isangeles
 *
 */
class DayPhase 
{
	public static final int MORNING = 0,
							MIDDAY = 1,
							AFTERNOON = 2,
							NIGHT = 3;
	private Sprite morningFilter,
				   middayFilter,
				   afternoonFilter,
				   nightFilter;
	private int phaseId;
	/**
	 * Day phase constructor
	 * @throws SlickException
	 * @throws IOException
	 */
	public DayPhase() throws SlickException, IOException 
	{
		middayFilter = new Sprite(GConnector.getInput("day/midday.png"), "dayFilterMidday", false);
		afternoonFilter = new Sprite(GConnector.getInput("day/afternoon.png"), "dayFilterAfternoon", false);
		nightFilter = new Sprite(GConnector.getInput("day/night.png"), "dayFilterNight", false);
	}
	/**
	 * Draws day phase filter
	 * @param x Position on x-axis
	 * @param y Position on y-axis
	 */
	public void draw(float x, float y)
	{
		switch(phaseId)
		{
		case MORNING:
			break;
		case MIDDAY:
			middayFilter.draw(x, y, false);
			break;
		case AFTERNOON:
			afternoonFilter.draw(x, y, false);
			break;
		case NIGHT:
			nightFilter.draw(x, y, false);
			break;
		}
	}
	/**
	 * Changes current day phase 
	 * @param phaseId Day phase ID (0-morning, 1-midday, 2-afternoon, 3-night)
	 */
	public void changePhase(int phaseId)
	{
		this.phaseId = phaseId;
	}
	/**
	 * Switches to next day phase
	 */
	public void nextPhase()
	{
		if(phaseId < 3)
			phaseId ++;
		if(phaseId >= 3)
			phaseId = 0;
	}
}
