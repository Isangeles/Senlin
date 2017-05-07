package pl.isangeles.senlin.core.skill;

import java.util.LinkedList;
import java.util.List;

/**
 * Class for all skills acquired by character
 * @author Isangeles
 *
 */
public class Abilities extends LinkedList<Skill>
{
	private static final long serialVersionUID = 1L;

	public Abilities() 
	{	
	}
	/**
	 * Updates all skills
	 * @param delta
	 */
	public void update(int delta)
	{
		for(Skill skill : this)
		{
			skill.update(delta);
		}
	}
	
	public Skill get(String skillId)
	{
		for(Skill skill : this)
		{
			if(skill.getId() == skillId)
			{
				return skill;
			}
		}
		
		return null;
	}
}
