package pl.isangeles.senlin.core;
/**
 * Wrapped integer class, for mutability
 * @author Isangeles
 *
 */
public class Atribute 
{
	int value;
	
	public Atribute()
	{
		value = 0;
	}
	
	public Atribute(int value)
	{
		this.value = value;
	}
	
	public void setValue(int value)
	{ this.value = value; }
	
	public int getValue()
	{ return value; }
	
	public void increment()
	{ value ++; }
	
	public void decrement()
	{ value --; }
	
	@Override
	public String toString()
	{ return value+""; }
}
