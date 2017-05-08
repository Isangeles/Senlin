package pl.isangeles.senlin.core;
/**
 * Class for game guilds
 * @author Isangeles
 *
 */
public class Guild 
{
	private final String name;
	private final int id;
	
	public Guild(int id, String name) 
	{
		this.id = id;
		this.name = name;
	}
	
	public String getName()
	{
		if(name != "")
			return "<" + name + ">";
		else
			return name;
	}
	
	public String toString()
	{
		return name;
	}

}
