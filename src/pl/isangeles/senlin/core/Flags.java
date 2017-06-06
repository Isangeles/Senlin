package pl.isangeles.senlin.core;

import java.util.ArrayList;
import java.util.List;

import pl.isangeles.senlin.data.Log;
import pl.isangeles.senlin.quest.Quest;
/**
 * Container for character flags 
 * @author Isangeles
 *
 */
public class Flags extends ArrayList<String>
{
	private static final long serialVersionUID = 1L;

	public Flags()
	{
	}
	/**
	 * Updates flags container by adding and removing requested flags
	 * @param quests List of all character quests
	 */
	public void update(List<Quest> quests)
	{
		for(Quest quest : quests)
		{
			if(quest.hasFlag())
			{
				for(String flag : quest.getFlagsToSet())
				{
					if(flag != "" && !this.contains(flag))
					{
						this.add(flag);
						quest.clearFlag(flag);
						Log.addSystem(flag + " flag added");
					}
				}
				
				for(String flag : quest.getFlagsToRemove())
				{
					if(flag != "" && this.contains(flag))
					{
						this.remove(flag);
						quest.clearFlag(flag);
						Log.addSystem(flag + " flag removed");
					}
				}
			}
		}
	}
	/**
	 * Lists all flags in container to string
	 * @return String with all flags IDs listed
	 */
	public String list()
	{
		String flags = "";
		for(String flag : this)
		{
			flags += flag + ";";
		}
		return flags;
	}
	/**
	 * Checks if container contains specified flag
	 * @param flag Flag ID
	 * @return True if container have specified flag, false otherwise
	 */
	public boolean contains(String flag)
	{
		for(String ele : this)
		{
			if(ele.equals(flag))
				return true;
		}
		return false;
	}
}
