package pl.isangeles.senlin.core.skill;

import java.util.List;

import pl.isangeles.senlin.core.Character;
import pl.isangeles.senlin.core.Targetable;
import pl.isangeles.senlin.core.effect.Effect;
import pl.isangeles.senlin.core.effect.EffectType;
import pl.isangeles.senlin.core.exc.CharacterOut;

public class Buff extends Skill 
{

	public Buff(Character character, String id, String name, String info, String imgName, EffectType type, int magickaCost, int castTime, int cooldown, boolean useWeapon, List<Effect> effects) 
	{
		super(character, id, name, info, imgName, type, magickaCost, castTime, cooldown, useWeapon, effects);
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
    public CharacterOut prepare(Character user, Targetable target)
    {
        return CharacterOut.UNKNOWN;
    }

}
