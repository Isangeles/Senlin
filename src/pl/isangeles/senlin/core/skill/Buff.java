package pl.isangeles.senlin.core.skill;

import pl.isangeles.senlin.core.Character;

public class Buff extends Skill 
{

	public Buff(Character character, String id, String name, String info, String imgName, int magickaCost, int castTime) 
	{
		super(character, id, name, info, imgName, magickaCost, castTime);
	}

	@Override
	public String getInfo() 
	{
		return null;
	}

    @Override
    public void activate()
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean prepare(Character user, Character target)
    {
        return false;
    }

}
