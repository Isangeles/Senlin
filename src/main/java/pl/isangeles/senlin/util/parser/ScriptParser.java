/*
 * ScriptParser.java
 * 
 * Copyright 2018 Dariusz Sikora <dev@isangeles.pl>
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301, USA.
 * 
 * 
 */
package pl.isangeles.senlin.util.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.cli.Script;
import pl.isangeles.senlin.util.DConnector;

/**
 * Class for parsing engine scripts files
 * @author Isangeles
 *
 */
public final class ScriptParser 
{
	/**
	 * Private constructor to prevent initialization
	 */
	private ScriptParser() {}
	/**
	 * Parses specified scripts node to list with scenario scripts
	 * @param scriptsNode XML document node, scripts node
	 * @return List with scripts
	 * @throws FileNotFoundException
	 */
	public static List<Script> getScriptsFromNode(Node scriptsNode)
	{
		List<Script> scripts = new ArrayList<>();
		
		Element scriptsE = (Element)scriptsNode;
		NodeList sriptsNl = scriptsE.getElementsByTagName("script");
		for(int j = 0; j < sriptsNl.getLength(); j ++)
        {
            Node scriptNode = sriptsNl.item(j);
            if(scriptNode.getNodeType() == javax.xml.soap.Node.ELEMENT_NODE)
            {
            	Element scriptE = (Element)scriptNode;
                try 
                {
                    String scriptName = scriptE.getTextContent();
                    Script script = DConnector.getScript(scriptName);
                    if(scriptE.hasAttribute("target"))
                    	script.setTarget(scriptE.getAttribute("target"));
					scripts.add(script);
				} 
                catch (FileNotFoundException e) 
                {
                	Log.addSystem("scenario_builder_fail-msg///script node corrupted:" + scriptE.getTextContent());
					break;
				}
            }
        }
		return scripts;
	}
	/**
	 * Parses content of specified file to engine CLI script
	 * @param scriptFile File with script
	 * @return Engine script
	 * @throws FileNotFoundException If specified file does not exists
	 */
	public static Script getScriptFromFile(File scriptFile) throws FileNotFoundException
	{
	    Scanner scann = new Scanner(scriptFile, "UTF-8");
		String scriptFileName = scriptFile.getName().replaceAll(".ssg", "");
		
	    String scriptCode = "";
	    List<String> ifOr = new ArrayList<>();
	    //String ifCode = "";
	    String endCode = "";
	    
	    while(scann.hasNextLine())
	    {
	    	String line = scann.nextLine().replaceFirst("^\\s*", "");
	    	if(!line.startsWith("#"))
	    	{
	    		if(line.startsWith("script:"))
	    		{
	    			while(scann.hasNextLine())
	    			{
	    				line = scann.nextLine().replaceFirst("^\\s*", "");
		    			if(line.startsWith("if:") || line.startsWith("or:") || line.startsWith("end:"))
		    			{
		    				break;
		    			}
		    			if(!line.startsWith("#"))
		    			{
		    				scriptCode += line;
		    			}
	    			}
	    		}
	    		if(line.startsWith("if:") || line.startsWith("or:"))
	    		{
	    			String ifCode = "";
	    			while(scann.hasNextLine())
	    			{
	    				line = scann.nextLine().replaceFirst("^\\s*", "");
		    			if(line.startsWith("script:") || line.startsWith("end:") || line.startsWith("or:"))
		    			{
		    				break;
		    			}
		    			if(!line.startsWith("#"))
		    			{
		    				//ifCode += line;
		    				ifCode += line;
		    			}
	    			}
	    			ifOr.add(ifCode);
	    		}
	    		if(line.startsWith("or:"))
	    		{
	    			String orCode = "";
	    			while(scann.hasNextLine())
	    			{
	    				line = scann.nextLine().replaceFirst("^\\s*", "");
		    			if(line.startsWith("script:") || line.startsWith("end:") || line.startsWith("if:"))
		    			{
		    				break;
		    			}
		    			if(!line.startsWith("#"))
		    			{
		    				//ifCode += line;
		    				orCode += line;
		    			}
	    			}
	    			ifOr.add(orCode);
	    		}
	    		if(line.startsWith("end:"))
	    		{
	    			while(scann.hasNextLine())
	    			{
	    				line = scann.nextLine().replaceFirst("^\\s*", "");
		    			if(line.startsWith("script:"))
		    			{
		    				break;
		    			}
		    			if(!line.startsWith("#"))
		    			{
		    				endCode += line;
		    			}
	    			}
	    		}
	    	}
	    }
	    scann.close();
	    return new Script(scriptFileName, scriptCode, ifOr, endCode);
	}
}
