package pl.isangeles.senlin.core.skill;

import pl.isangeles.senlin.core.Character;

public class Passive extends Skill 
{

	public Passive(String id, String name, String info, String imgName) 
	{
		super(id, name, info, imgName);
	}

	@Override
	protected String getInfo() 
	{
		return null;
	}

	@Override
	public void activate(Character user, Character target) 
	{
		
	}

}
