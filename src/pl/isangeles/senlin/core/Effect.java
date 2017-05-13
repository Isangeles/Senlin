package pl.isangeles.senlin.core;
/**
 * Class for effects used by items, skills, etc.
 * @author Isangeles
 *
 */
public class Effect 
{
    private String id;
    private String name;
	private EffectType type;
	private int hpMod;
	private int manaMod;
	private Attributes attMod;
	private float hasteMod;
	private float dodgeMod;
	private int dmgMod;
	private int duration;
	private int time;
	private boolean on;
	
	public Effect(String id, String name, int hpMod, int manaMod, Attributes attMod, float hasteMod, float dodgeMod, int dmgMod, int duration, EffectType type) 
	{
	    this.id = id;
	    this.name = name;
		this.type = type;
		this.hpMod = hpMod;
		this.manaMod = manaMod;
		this.attMod = attMod;
		this.hasteMod = hasteMod;
		this.dodgeMod = dodgeMod;
		this.dmgMod = dmgMod;
		this.duration = duration;
	}
	
	public void affect(Character character)
	{
		character.modHealth(hpMod);
		character.modMgicka(manaMod);
		character.modAttributes(attMod);
	}
	
	public void removeFrom(Character character)
	{
		character.modHealth(-hpMod);
		character.modMgicka(-manaMod);
		character.modAttributes(attMod.nagative());
	}
	
	public int getHpMod()
	{
		return hpMod;
	}
	
	public int getManaMod()
	{
		return manaMod;
	}
	
	public Attributes getAttributesMod()
	{
		return attMod;
	}
	
	public float getHasteMod()
	{
		return hasteMod;
	}
	
	public float getDodgeMod()
	{
		return dodgeMod;
	}
	
	public int getDmgMod()
	{
		return dmgMod;
	}
	
	public int getDuration()
	{
		return duration;
	}
	
	public boolean isOn()
	{
		return on;
	}
	
	public void updateTime(int delta)
	{
		time += delta;
		if(time >= duration)
			on = false;
	}
	
	public void turnOn()
	{
		on = true;
	}

}
