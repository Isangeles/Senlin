package pl.isangeles.senlin.core.skill;

import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.core.Targetable;

public class Buff extends Skill 
{

	public Buff(Character character, String id, String name, String info, String imgName, String type, int magickaCost, int castTime, int cooldown, boolean useWeapon) 
	{
		super(character, id, name, info, imgName, type, magickaCost, castTime, cooldown, useWeapon);
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
    public boolean prepare(Character user, Targetable target)
    {
        return false;
    }

}
