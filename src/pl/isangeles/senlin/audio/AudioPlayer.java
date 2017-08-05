/*
 * AudioPlayer.java
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
package pl.isangeles.senlin.audio;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.util.AConnector;
/**
 * Class for playing game music and sound effects
 * @author Isangeles
 *
 */
public class AudioPlayer extends HashMap<String, Music>
{
	private static final long serialVersionUID = 1L;
	
	private Random rng = new Random();

	public AudioPlayer() 
	{
	}
	/**
	 * Adds one specified music track to player
	 * @param trackName Name of audio file in aData archive
	 * @throws SlickException If audio file format is wrong
	 * @throws IOException If audio archive not found
	 */
	public void add(String category, String trackName) throws SlickException, IOException
	{
		
		try
		{
			Music track = new Music(AConnector.getInput("music/" + category + "/" +  trackName), trackName);
			this.put(trackName, track);
		}
		catch(IOException | SlickException e)
		{
			Log.addSystem("audioPlayer_fail_msg///adding error: " + trackName);
		}
	}
	/**
	 * Adds all tracks from specified category to player
	 * @param category Catalog name in audio archive
	 * @throws IOException If audio archive or playlist file not found
	 * @throws SlickException 
	 */
	public void addAll(String category) throws IOException, SlickException
	{
		Scanner scann = new Scanner(AConnector.getInput("music/" + category + "/playlist"));
		scann.useDelimiter(";\r?\n");
		while(scann.hasNext())
		{
		    add(category, scann.next());
		}
		scann.close();
	}
	/**
	 * Starts audio player
	 * @param pitch
	 * @param volume
	 */
	public void play(float pitch, float volume, String trackName)
	{
	    Music track = this.get(trackName);
		if(track != null)
			track.play(pitch, volume);
		else
		    Log.addSystem("audioPlayer_fail_msg///no such track: " + trackName);
	}
	/**
	 * Starts audio player
	 * @param pitch
	 * @param volume
	 */
	public void playRandom(float pitch, float volume)
	{	
		Music[] tracks = new Music[this.values().size()];
		this.values().toArray(tracks);
		
        if(tracks.length > 0)
        {
            int trackId = rng.nextInt(tracks.length);
            tracks[trackId].play(pitch, volume);
        }
        else
        {
            Log.addSystem("audioPlayer_fail_msg///no tracks inside");
        }
	}
	/**
	 * Stops audio player
	 */
	public void stop()
	{
		for(Music track : this.values())
		{
			if(track.playing())
				track.stop();
		}
	}
}
