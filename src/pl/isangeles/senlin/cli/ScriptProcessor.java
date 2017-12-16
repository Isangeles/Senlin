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
        	while(script.hasNext() && checkCondition(script, ifCode))
            {
            	//Log.addSystem(script.getName() +  "-active command:" + script.getActiveCommand());
            	String command = script.getActiveCommand();
            	if(command.matches("@wait [1-9]+"))
                {
                    int time = Integer.parseInt(command.split(" ")[1]);
                    long timeSec = Stopwatch.sec(time);
                    script.pause(timeSec);
                    script.next();
                    break;
                }
                else
                {
                	if(cli.executeCommand(command).equals("1"))
                	{
                        out = false;
                        Log.addSystem("ssp: " + script.getName() + " processing fail - corrupted at line:" + script.getActiveIndex());
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
        //scann.useDelimiter("\r?\n");
        scann.useDelimiter(";");
        
        while(scann.hasNext())
        {
            String command = scann.next().replaceFirst("^\\s*", "");
            if(command.equals("true;"))
                out = true;
            if(command.startsWith("use="))
            {
                int value = Integer.parseInt(command.substring(command.indexOf("=")+1, command.indexOf(";")));
                if(script.getUseCount() >= value)
                    out = true;
                else
                {
                    out = false;
                    break;
                }
            }
            if(command.startsWith("has"))
            {
                String[] hasCommand = command.split(" ");
                if(hasCommand[1].equals("flag"))
                {
                    String flag = hasCommand[2];
                    if(hasCommand.length < 4 || hasCommand[3].equals("player;"))
                    {
                        if(cli.getPlayer().getFlags().contains(flag))
                            out = true;
                        else
                        {
                            out = false;
                            break;
                        }
                    }
                }
            }
            if(command.startsWith("!has"))
            {
                String[] hasCommand = command.split(" ");
                if(hasCommand[1].equals("flag"))
                {
                    String flag = hasCommand[2];
                    if(hasCommand.length < 4 || hasCommand[3].equals("player"))
                    {
                        if(!cli.getPlayer().getFlags().contains(flag)) 
                        {
                            out = true;
                        }
                        else 
                        {
                            out = false;
                            break;
                        }
                    }
                }
            }
            if(command.startsWith("dis"))
            {
                String[] disCommand = command.split(" ");
                int disToCheck = Integer.parseInt(disCommand[5].replaceAll(";", ""));
                
                String disOut = cli.executeCommand("$charman " + disCommand[1] + " show -dis " + disCommand[3]);
                int dis = Integer.parseInt(disOut.split(":")[1]);
                
                if(disCommand[4].equals("less"))
                    out = dis < disToCheck;
                
                if(!out)
                    break;
            }
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
