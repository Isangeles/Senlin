package pl.isangeles.senlin.core;

import pl.isangeles.senlin.inter.Portrait;

/**
 * Interface for targetable objects
 * @author Isangeles
 *
 */
public interface Targetable 
{
	public void setTarget(Targetable target);
	public Targetable getTarget();
	
	public String getName();
	public Portrait getPortrait();
	public int getHealth();
	public int getMaxHealth();
	public int getMagicka();
	public int getMaxMagicka();
	public int getExperience();
	public int getMaxExperience();
	public int getLevel();
	public int[] getPosition();
	
	public void takeHealth(int value);
	public void takeMagicka(int value);
	
	public boolean isLive();
}
