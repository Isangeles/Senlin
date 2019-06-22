/*
 * Playlist.java
 * 
 * Copyright 2017 Dariusz Sikora <dev@isangeles.pl>
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
import java.util.HashMap;
import java.util.Scanner;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.cli.Log;
import pl.isangeles.senlin.util.AConnector;

/**
 * Class for audio player playlists
 * @author Isangeles
 *
 */
class Playlist extends HashMap<String, Music>
{
	private static final long serialVersionUID = 1L;
	
	private final String name;
	/**
	 * Playlist constructor
	 * @param name Name for playlist
	 */
	public Playlist(String name)
	{
		this.name = name;
	}
	/**
	 * Adds one specified music track to playlist
	 * @param trackName Name of audio file in aData archive
	 * @throws SlickException If audio file format is wrong
	 * @throws IOException If audio archive not found
	 */
	public void add(String category, String trackName) throws SlickException, IOException
	{
		Music track = new Music(AConnector.getInput("music/" + category + "/" +  trackName), trackName);
		this.put(trackName, track);
	}
	/**
	 * Adds all tracks from specified category to playlist
	 * @param category Catalog name in audio archive
	 * @throws IOException If audio archive or playlist file not found
	 * @throws SlickException 
	 */
	public void addAll(String category) throws IOException, SlickException
	{
		Scanner scann = new Scanner(AConnector.getInput("music/" + category + "/playlist"));
		scann.useDelimiter(";|(;\r?\n)");
		while(scann.hasNext())
		{
			String trackName = scann.next().replaceFirst("^\\s*", "");
			if(trackName.endsWith(".ogg") || trackName.endsWith(".aif"))
			    add(category, trackName);
		}
		scann.close();
	}
	/**
	 * Returns playlist name
	 * @return String with name
	 */
	public String getName()
	{
		return name;
	}
	/**
	 * Returns currently played track from this playlist
	 * @return Currently played track from this playlist or null if all track in this playlist are stopped
	 */
	public Music getCurrentTrack()
	{
		for(Music track : this.values())
		{
			if(track.playing())
				return track;
		}
		return null;
	}
}
