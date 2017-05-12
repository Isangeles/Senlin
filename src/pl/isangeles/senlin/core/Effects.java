package pl.isangeles.senlin.core;

import java.util.ArrayList;
/**
 * Container for character effects
 * @author Isangeles
 *
 */
public class Effects extends ArrayList<Effect>
{
	private static final long serialVersionUID = 1L;

	public Effects() 
	{
	}
	/**
	 * Updates all effects in container
	 * @param delta Time (in milliseconds) from last update
	 * @param character Container owner
	 */
	public void update(int delta, Character character)
	{
		for(Effect effect : this)
		{
			if(effect.isOn())
			{
				effect.updateTime(delta);
				effect.affect(character);
			}
			else
			{
				effect.removeFrom(character);
				this.remove(effect);
			}
		}
	}
}
