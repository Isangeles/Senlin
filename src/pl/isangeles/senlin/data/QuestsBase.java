package pl.isangeles.senlin.data;

import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import pl.isangeles.senlin.quest.Quest;
import pl.isangeles.senlin.util.DConnector;

public class QuestsBase
{
    private static Map<String, Quest> questsMap;
    
    private QuestsBase(){}
    
    public static void load(String questsBaseName) throws ParserConfigurationException, SAXException, IOException
    {
        questsMap = DConnector.getQuests(questsBaseName);
    }
    
    public static Quest get(String id)
    {
        return questsMap.get(id);
    }
    
}
