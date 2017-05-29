package pl.isangeles.senlin.quest;
/**
 * Enumeration for quests objectives types
 * @author Isangeles
 *
 */
public enum ObjectiveType
{
    TALK, KILL, GATHER;
    
    public static ObjectiveType fromString(String typeName)
    {
        switch(typeName)
        {
        case "talk":
            return ObjectiveType.TALK;
        case "kill":
            return ObjectiveType.KILL;
        case "gather":
            return ObjectiveType.GATHER;
        }
        
        return null;
    }
}
