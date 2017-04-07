package pl.isangeles.senlin.core;

import pl.isangeles.senlin.core.item.Armor;
import pl.isangeles.senlin.core.item.Trinket;
import pl.isangeles.senlin.core.item.Weapon;

/**
 * Class for character equipment
 * @author Isangeles
 *
 */
public class Equipment 
{
	private Weapon weaponMain;
	private Weapon weaponSec;
	private Armor boots;
	private Armor gloves;
	private Armor shield;
	private Armor chest;
	private Armor helmet;
	private Trinket ring;
	private Trinket ringSec;
	private Trinket amulet;
	private Trinket artifact;
	
	public Equipment() 
	{
	}

	public boolean setMainWeapon(Weapon weapon)
	{ 
		if(this.weaponMain == null) 
		{
			weaponMain = weapon;
			return true;
		}
		return false;
	
	}
	
	public boolean setSecWeapon(Weapon weapon)
	{ 
		if(shield == null) 
		{
			weaponSec = weapon;
			return true;
		}
		return false;
	}
	
	public boolean setBoots(Armor boots)
	{ 
		if(boots.type() == Armor.FEET)
		{
			this.boots = boots;
			return true;
		}
		return false;
	}
	
	public boolean setGloves(Armor gloves)
	{ 
		if(gloves.type() == Armor.HANDS)
		{
			this.gloves = gloves;
			return true;
		}
		return false; 
	}
	
	public boolean setShield(Armor shield)
	{ 
		if(shield.type() == Armor.OFFHAND)
		{
			this.shield = shield;
			return true;
		}
		return false; 
	}
	
	public boolean setChest(Armor chest)
	{ 
		if(chest.type() == Armor.CHEST)
		{
			this.chest = chest;
			return true;
		}
		return false; 
	}
	
	public boolean setHelmet(Armor helmet)
	{ 
		if(helmet.type() == Armor.HEAD)
		{
			this.helmet = helmet;
			return true;
		}
		return false; 
	}
	
	public boolean setRing(Trinket ring)
	{ 
		if(ring.type() == Trinket.FINGER)
		{
			this.ring = ring;
			return true;
		}
		return false; 
	}
	
	public boolean setSecRing(Trinket ring)
	{ 
		if(ring.type() == Trinket.FINGER)
		{
			this.ringSec = ring;
			return true;
		}
		return false; 
	}
	
	public boolean setAmulet(Trinket amulet)
	{ 
		if(amulet.type() == Trinket.NECK)
		{
			this.amulet = amulet;
			return true;
		}
		return false; 
	}
	
	public boolean setArtifact(Trinket artifact)
	{ 
		if(artifact.type() == Trinket.ARTIFACT)
		{
			this.artifact = artifact;
			return true;
		}
		return false;  
	}
	
	public void removeMainWeapon()
	{
		weaponMain = null;
	}
	
	public Weapon getMainWeapon()
	{
		return weaponMain;
	}
	
	public int[] getDamage()
	{
		if(weaponMain != null)
		{
			return weaponMain.getDamage();
		}
		else
			return new int[]{0, 0};
	}
}
