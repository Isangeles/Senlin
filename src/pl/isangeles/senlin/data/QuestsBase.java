package pl.isangeles.senlin.data;

import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import pl.isangeles.senlin.quest.Quest;
import pl.isangeles.senlin.util.DConnector;
/**
 * Static base class for game quests 
 * @author Isangeles
 *
 */
public class QuestsBase
{
    private static Map<String, Quest> questsMap;
    /**
     * Private constructor to prevent initialization
     */
    private QuestsBase(){}
    /**
     * Loads base
     * @param questsBaseName Quests base file name in data/quests 
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public static void load(String questsBaseName) throws ParserConfigurationException, SAXException, IOException
    {
        questsMap = DConnector.getQuests(questsBaseName);
    }
    /**
     * Returns quest with specified ID from base
     * @param id Desire quest ID
     * @return Quest with specified ID
     */
    public static Quest get(String id)
    {
        return questsMap.get(id);
    }
    
}
