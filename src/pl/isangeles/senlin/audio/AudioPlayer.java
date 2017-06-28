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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.data.Log;
import pl.isangeles.senlin.util.AConnector;
/**
 * Class for playing game music and sound effects
 * @author Isangeles
 *
 */
public class AudioPlayer extends HashMap<String, Music>
{
	private static final long serialVersionUID = 1L;
	
	private Map<String, List<String>> playlist = new HashMap<>();
	private Random rng = new Random();

	public AudioPlayer() 
	{
		playlist.put("menu", new ArrayList<String>());
		playlist.put("world", new ArrayList<String>());
		playlist.put("combat", new ArrayList<String>());
		playlist.put("none", new ArrayList<String>());
	}
	/**
	 * Adds one specified music track to player
	 * @param trackName Name of audio file in aData archive
	 * @throws SlickException If audio file format is wrong
	 * @throws IOException If audio archive not found
	 */
	public void add(String trackName) throws SlickException, IOException
	{
		
		try
		{
			Music track = new Music(AConnector.getInput("music/" +  trackName), trackName);
			this.put(trackName, track);
		}
		catch(IOException | SlickException e)
		{
			Log.addSystem("audio_player_fail msg///playlist error");
		}
	}
	/**
	 * Adds all tracks from specified category to player
	 * @param category Catalog name in audio archive
	 * @throws IOException If audio archive or playlist file not found
	 */
	public void addAll(String category) throws IOException
	{
		Scanner scann = new Scanner(AConnector.getInput(category + "/playlist"));
		scann.useDelimiter(";\r?\n");
		while(scann.hasNext())
		{
			try
			{
				String catAndTrack[] = scann.next().split(";");
				System.out.println(catAndTrack[0] + "||" + catAndTrack[1]);
				switch(catAndTrack[0])
				{
				case "menu":
					playlist.get("menu").add(catAndTrack[1]);
					break;
				case "world":
					playlist.get("world").add(catAndTrack[1]);
					break;
				case "combat":
					playlist.get("combat").add(catAndTrack[1]);
					break;
				default:
					playlist.get("none").add(catAndTrack[1]);
					break;
				}
				Music track = new Music(AConnector.getInput(category + "/" + catAndTrack[1]), catAndTrack[1]);
				this.put(catAndTrack[1], track);
			}
			catch(IOException | SlickException | ArrayIndexOutOfBoundsException e)
			{
				Log.addSystem("audio_player_" + category + "_fail msg///playlist error");
				break;
			}
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
		if(this.size() == 1)
			this.get(trackName).play(pitch, volume);
	}
	/**
	 * Starts audio player
	 * @param pitch
	 * @param volume
	 */
	public void playRandom(float pitch, float volume, String category)
	{
		switch(category)
		{
		case "menu":
			List<String> menuTracks = playlist.get("menu");
			this.get(menuTracks.get(rng.nextInt(menuTracks.size()))).play(pitch, volume);
			break;
		case "world":
			List<String> worldTracks = playlist.get("world");
			this.get(worldTracks.get(rng.nextInt(worldTracks.size()))).play(pitch, volume);
			break;
		case "combat":
			List<String> combatTracks = playlist.get("combat");
			this.get(combatTracks.get(rng.nextInt(combatTracks.size()))).play(pitch, volume);
			break;
		default:
			break;
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
