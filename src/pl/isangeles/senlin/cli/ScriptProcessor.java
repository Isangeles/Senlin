/*
 * ScriptProcessor.java
 * 
 * Copyright 2017 Dariusz Sikora <darek@darek-PC-LinuxMint18>
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
        String ifCode = script.getIfCode();
        
        if(!script.isFinished() && !script.isWaiting())
        {
        	try
        	{
        		while(script.hasNext() && checkCondition(script, ifCode))
                {
                	//Log.addSystem(script.getName() +  "-active command:" + script.getActiveCommand());
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
        	catch(NumberFormatException e)
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
            String bool = cli.executeCommand(command)[1];
            //Log.addSystem("check: " + command + " - " + out);
            out = bool.equals("true");
            if(!out)
            	break;
        }
        scann.close();
        
        //Log.addSystem(ifCode + "-check:" + out);
        
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
        if(scann.next().equals("if"))
        {
            String ifCommand = scann.next();
            ifCommand.replaceFirst("^\\s*", "");
            
            out = checkCondition(script, ifCommand);
        }
        
        scann.close();
        return out;
    }
}
