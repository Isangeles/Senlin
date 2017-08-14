package pl.isangeles.senlin.core.skill;

import java.util.List;

import pl.isangeles.senlin.core.Bonuses;
import pl.isangeles.senlin.core.Targetable;
import pl.isangeles.senlin.core.character.Character;
import pl.isangeles.senlin.core.effect.Effect;
import pl.isangeles.senlin.core.effect.EffectType;
import pl.isangeles.senlin.core.out.CharacterOut;
import pl.isangeles.senlin.core.req.Requirement;

public class Buff extends Skill 
{
    private Bonuses statsBuff;
    private int hpBuff;
    private int time;
    private int duration;
    
	public Buff(Character character, String id, String imgName, EffectType type, List<Requirement> reqs, int castTime, int cooldown, int duration, boolean useWeapon, List<Effect> effects) 
	{
		super(character, id, imgName, type, reqs, castTime, cooldown, effects);
		this.duration = duration;
	}
	
	public void update(int delta)
	{
	    if(active)
	    {
	        time += delta;
	        if(time >= duration)
	            active = false;
	    }
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
