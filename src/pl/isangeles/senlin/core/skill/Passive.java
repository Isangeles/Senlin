package pl.isangeles.senlin.core.skill;

import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.core.Targetable;

public class Passive extends Skill 
{

	public Passive(Character character, String id, String name, String info, String imgName, String type, int magickaCost, boolean useWeapon) 
	{
		super(character, id, name, info, imgName, type, magickaCost, 0, 0, useWeapon);
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
    public boolean prepare(Character user, Targetable target)
    {
        // TODO Auto-generated method stub
        return false;
    }

}
