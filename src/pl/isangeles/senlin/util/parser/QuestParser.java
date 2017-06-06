package pl.isangeles.senlin.util.parser;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import pl.isangeles.senlin.quest.Objective;
import pl.isangeles.senlin.quest.ObjectiveType;
import pl.isangeles.senlin.quest.Quest;
import pl.isangeles.senlin.quest.Stage;
/**
 * Class for external quest base parsing
 * @author Isangeles
 *
 */
public class QuestParser
{
    /**
     * Private constructor to prevent initialization
     */
    private QuestParser(){}
    /**
     * Parses specified quest node from XML base to quest object
     * @param questNode Quest node from quests base
     * @return Quest object
     */
    public static Quest getQuestFromNode(Node questNode)
    {
        Element questE = (Element)questNode;
        
        String id = questE.getAttribute("id");
        String flag = questE.getAttribute("flagOn");
        List<Stage> stages = new ArrayList<>();
        
        Node stagesNode = questE.getElementsByTagName("stages").item(0);
        NodeList stagesList = stagesNode.getChildNodes();
        for(int i = 0; i < stagesList.getLength(); i ++)
        {
            Node stageNode = stagesList.item(i);
            if(stageNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
            	stages.add(getStageFromNode(stageNode));
        }
        
        return new Quest(id, flag, stages);
    }
    /**
     * Parses specified stage node
     * @param stageNode Stage node
     * @return Stage object
     */
    private static Stage getStageFromNode(Node stageNode)
    {
        Element stageE = (Element)stageNode;
        
        String id = stageE.getAttribute("id");
        String nextStage = stageE.getAttribute("next");
        String flagOn = stageE.getAttribute("flagOn");
        String flagOff = stageE.getAttribute("flagOff");
        List<Objective> objectives = new ArrayList<>();
        
        NodeList objectivesList = stageE.getElementsByTagName("objective");
        for(int i = 0; i < objectivesList.getLength(); i ++)
        {
            Node objectiveNode = objectivesList.item(i);
            if(objectiveNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
                objectives.add(getObjectiveFromNode(objectiveNode));
        }
        
        return new Stage(id, flagOn, flagOff, nextStage, objectives);
    }
    /**
     * Parses specified objective node
     * @param objectiveNode Objective node
     * @return Objective object
     */
    private static Objective getObjectiveFromNode(Node objectiveNode)
    {
        Element objectiveE = (Element)objectiveNode;
        
        String type = objectiveE.getAttribute("type");
        String target = objectiveE.getTextContent();
        
        return new Objective(ObjectiveType.fromString(type), target);
    }
}
