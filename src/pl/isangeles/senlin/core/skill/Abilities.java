package pl.isangeles.senlin.core.skill;

import java.util.ArrayList;
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
	/**
	 * Returns skill with specified ID
	 * @param skillId String with desired skill ID
	 * @return Skill from character skills list
	 */
	public Skill get(String skillId)
	{
		for(Skill skill : this)
		{
			if(skill.getId().equals(skillId))
			{
				return skill;
			}
		}
		
		return null;
	}
	/**
	 * Returns all attacks from character skills list
	 * @return ArrayList with attacks
	 */
	public List<Attack> getAttacks()
	{
		List<Attack> attacks = new ArrayList<>();
		
		for(Skill skill : this)
		{
			if(Attack.class.isInstance(skill))
				attacks.add((Attack)skill);
		}
		
		return attacks;
	}
}
