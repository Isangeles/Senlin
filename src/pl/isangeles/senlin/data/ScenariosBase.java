package pl.isangeles.senlin.data;

import java.io.FileNotFoundException;
import java.util.Map;

import pl.isangeles.senlin.util.DConnector;
/**
 * Static base for game scenarios
 * @author Isangles
 *
 */
public class ScenariosBase 
{
	private static Map<String, Scenario> scenarios;
	
	private ScenariosBase() {}
	
	public static Scenario getScenario(String id)
	{
		return scenarios.get(id);
	}

	public static void load() throws FileNotFoundException
	{
		scenarios = DConnector.getScenarios("scenariosList");
	}
}
