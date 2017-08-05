package pl.isangeles.senlin.core.skill;

import java.util.List;

import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.core.Targetable;
import pl.isangeles.senlin.core.effect.Effect;
import pl.isangeles.senlin.core.effect.EffectType;
import pl.isangeles.senlin.core.out.CharacterOut;

public class Passive extends Skill 
{

	public Passive(Character character, String id, String name, String info, String imgName, EffectType type, int magickaCost, boolean useWeapon, List<Effect> effects) 
	{
		super(character, id, name, info, imgName, type, magickaCost, 0, 0, useWeapon, effects);
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
    public CharacterOut prepare(Character user, Targetable target)
    {
        // TODO Auto-generated method stub
        return CharacterOut.UNKNOWN;
    }

}
