package pl.isangeles.senlin.core.skill;

import pl.isangeles.senlin.core.Character;

public class Buff extends Skill 
{

	public Buff(String id, String name, String info, String imgName, int magickaCost) 
	{
		super(id, name, info, imgName, magickaCost);
	}

	@Override
	public String getInfo() 
	{
		return null;
	}

	@Override
	public void activate(Character user, Character target) 
	{
	}

}
