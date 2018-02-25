/*
 * ScriptParser.java
 * 
 * Copyright 2018 Dariusz Sikora <darek@pc-solus>
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
import java.util.Scanner;

import pl.isangeles.senlin.cli.Script;

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
	    String ifCode = "";
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
		    			if(line.startsWith("if:") || line.startsWith("end:"))
		    			{
		    				break;
		    			}
		    			if(!line.startsWith("#"))
		    			{
		    				scriptCode += line;
		    			}
	    			}
	    		}
	    		if(line.startsWith("if:"))
	    		{
	    			while(scann.hasNextLine())
	    			{
	    				line = scann.nextLine().replaceFirst("^\\s*", "");
		    			if(line.startsWith("script:") || line.startsWith("end:"))
		    			{
		    				break;
		    			}
		    			if(!line.startsWith("#"))
		    			{
		    				ifCode += line;
		    			}
	    			}
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
	    return new Script(scriptFileName, scriptCode, ifCode, endCode);
	}
}
