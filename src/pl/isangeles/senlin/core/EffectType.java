package pl.isangeles.senlin.core;
/**
 * Enumeration for effect types used by items, skills, etc.
 * @author Isangeles
 *
 */
public enum EffectType 
{
	FIRE, ICE, NATURE, MAGIC, NORMAL;
	
	public static EffectType fromString(String typeName)
	{
		switch(typeName)
		{
		case "fire":
			return EffectType.FIRE;
		case "ice":
			return EffectType.ICE;
		case "nature":
			return EffectType.NATURE;
		case "magic":
			return EffectType.MAGIC;
		default:
			return EffectType.NORMAL;
		}
	}
}
