package pl.isangeles.senlin.core.skill;

import pl.isangeles.senlin.core.Character;

public class Passive extends Skill 
{

	public Passive(Character character, String id, String name, String info, String imgName, int magickaCost) 
	{
		super(character, id, name, info, imgName, magickaCost, 0);
	}

	@Override
	protected String getInfo() 
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
        // TODO Auto-generated method stub
        return false;
    }

}