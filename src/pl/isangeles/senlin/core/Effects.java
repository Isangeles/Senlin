package pl.isangeles.senlin.core;

import java.util.ArrayList;
import java.util.List;
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
		List<Effect> effectsToRemove = new ArrayList<>();
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
				effectsToRemove.add(effect);
			}
		}
		this.removeAll(effectsToRemove);
	}
}
