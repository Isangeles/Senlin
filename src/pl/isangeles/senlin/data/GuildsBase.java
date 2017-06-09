package pl.isangeles.senlin.data;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import pl.isangeles.senlin.core.Guild;
import pl.isangeles.senlin.util.DConnector;

public class GuildsBase 
{
	private static Map<Integer, Guild> guildsBase = new HashMap<>();
	
	static
	{
		
	}
	
	private GuildsBase() 
	{}
	
	public static Guild getGuild(int guildId) throws NoSuchElementException
	{
		Guild guild = new Guild(0, "None");
		
		if(guildId == 0)
			return guild;
		else
		{
			guild = guildsBase.get(guildId);
			if(guild == null)
				throw new NoSuchElementException();
			else
				return guild;
		}
	}

	public static void load() throws ParserConfigurationException, SAXException, IOException
	{
		guildsBase = DConnector.getGuildsMap("guilds");
	}
}
