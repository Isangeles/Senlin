/*
 * ScriptProcessor.java
 * 
 * Copyright 2017-2018 Dariusz Sikora <darek@pc-solus>
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
package pl.isangeles.senlin.cli;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;

import pl.isangeles.senlin.util.Stopwatch;

/**
 * Class for scripts processor
 * @author Isangeles
 *
 */
public class ScriptProcessor
{
    private CommandInterface cli;
    /**
     * Script processor constructor
     * @param cli CLI for script commands execution
     */
    public ScriptProcessor(CommandInterface cli)
    {
        this.cli = cli;
    }
    /**
     * Executes specified script
     * @param script Script object
     * @return True if script was successfully executed, false otherwise
     */
    public boolean process(Script script)
    {
        boolean out = true;
        List<String> ifOrCode = script.getIfOrCode();
        
        if(!script.isFinished() && !script.isWaiting())
        {
        	try
        	{
        		while(script.hasNext() && checkConditions(script, ifOrCode))
                {
                	//Log.addSystem(script.getName() +  "-active command:" + script.getActiveCommand()); //DEBUG
                	String command = script.getActiveCommand();
                	if(command.matches("@wait [1-9]+[0-9]?"))
                    {
                        int time = Integer.parseInt(command.split(" ")[1]);
                        long timeSec = Stopwatch.sec(time);
                        script.pause(timeSec);
                        script.next();
                        break;
                    }
                    else
                    {
                    	String[] output = cli.executeCommand(command);
                    	if(!output[0].equals("0"))
                    	{
                            out = false;
                            Log.addSystem("ssp: " + script.getName() + " processing fail[" + output[0] + "] - corrupted at line:" + script.getActiveIndex());
                            break;	
                    	}
                    	else
                        	script.next();
                    }
                }
            	if(!script.hasNext())
            	{
            		script.used();
            		script.restart();
                	if(checkEndCondition(script))
                		script.finish();
            	}
        	}
        	catch(NumberFormatException | IndexOutOfBoundsException e)
        	{
        		out = false;
                Log.addSystem("ssp: " + script.getName() + " processing fail - corrupted at line:" + script.getActiveIndex());
        	}
        }
        return out;
    }
    /**
     * Executes 'if code' of the script
     * @param script Script object
     * @param ifCode Code after if
     * @return True if conditions from if code are met, false otherwise 
     * @throws IndexOutOfBoundsException
     */
    private boolean checkCondition(Script script, String ifCode) throws IndexOutOfBoundsException
    {
        boolean out = false;
        
    	Scanner scann = new Scanner(ifCode);
    	scann.useDelimiter(";");

        while(scann.hasNext())
        {
            String command = scann.next().replaceFirst("^\\s*", "");
            command.replaceAll(";", "");
            if(!command.equals(""))
            {
            	if(!command.startsWith("!"))
                {
                    String bool = cli.executeExpression(command)[1];
                    //Log.addSystem("sp_check_true: " + command + " - " + bool); //DEBUG
                	out = bool.equals("true");
                    if(!out)
                    	break;
                }
                else
                {
                    String bool = cli.executeExpression(command.replaceFirst("!", ""))[1];
                    //Log.addSystem("sp_check_false: " + command + " - " + bool); //DEBUG
                	out = bool.equals("false");
                    if(!out)
                    	break;
                }
            }
        }
        scann.close();
        //Log.addSystem(ifCode + "-check:" + out); //DEBUG            
        
        return out;
    }
    /**
     * Executes 'if/or code' of the script
     * @param script Script object
     * @param ifCode Code after 'if' and 'or' 
     * @return True if conditions from 'if' or 'or' code are met, false otherwise 
     * @throws IndexOutOfBoundsException
     */
    private boolean checkConditions(Script script, List<String> ifOrCode) throws IndexOutOfBoundsException
    {
        boolean out = false;
        for(String ifCode : ifOrCode)
        {
        	out = checkCondition(script, ifCode);
        	if(out)
        		break;
        }
        return out;
    }
    /**
     * Executes code after end statement
     * @param script Script object
     * @return True if conditions of end code are met, false otherwise
     * @throws IndexOutOfBoundsException
     */
    private boolean checkEndCondition(Script script) throws IndexOutOfBoundsException
    {
        boolean out = false;
        Scanner scann = new Scanner(script.getEndCode());
        scann.useDelimiter(":|(:\r?\n)");
        
        String statement = scann.next(); 
        if(statement.equals("if"))
        {
            String ifCommand = scann.next();
            ifCommand.replaceFirst("^\\s*", "");
            out = checkCondition(script, ifCommand);
        }
        else if(statement.equals("now;"))
        	out = true;
        
        scann.close();
        return out;
    }
}
