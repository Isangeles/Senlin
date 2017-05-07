package pl.isangeles.senlin.core.skill;

import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.core.Targetable;

public class Buff extends Skill 
{

	public Buff(Character character, String id, String name, String info, String imgName, int magickaCost, int castTime, int cooldown) 
	{
		super(character, id, name, info, imgName, magickaCost, castTime, cooldown);
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
