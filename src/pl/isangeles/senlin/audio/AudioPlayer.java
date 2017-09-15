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
import java.util.List;
import java.util.Random;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

import pl.isangeles.senlin.cli.Log;
/**
 * Class for playing game music and sound effects
 * @author Isangeles
 *
 */
public class AudioPlayer
{
	private static final long serialVersionUID = 1L;
	
	private Playlist mainList;
	private Playlist activePlaylist;
	private List<Playlist> playlists = new ArrayList<>();
	private Random rng = new Random();
	/**
	 * Audio player constructor
	 */
	public AudioPlayer() 
	{
		mainList = new Playlist("main");
		playlists.add(mainList);
		activePlaylist = mainList;
	}
	/**
	 * Adds track with specified name from specified category to playlist with specified name
	 * @param playlistName Playlist name
	 * @param category Category name
	 * @param trackName Audio file name
	 * @return True if track with specified name was successfully added to playlist, false otherwise
	 */
	public boolean addTo(String playlistName, String category, String trackName)
	{
		try
		{
			Playlist playlist = getPlaylist(playlistName);
			if(playlist != null)
			{
				playlist.add(category, trackName);
				mainList.add(category, trackName);
				return true;
			}
			else
				return false;
		}
		catch(NullPointerException | IOException | SlickException e)
		{
			Log.addSystem("audioPlayer_fail_msg///adding error: " + trackName);
			return false;
		}
	}
	/**
	 * Adds all tracks from specified category to playlist with specified name
	 * @param playlistName Playlist name
	 * @param category Category name
	 * @return True if tracks from specified category was successfully added to playlist, false otherwise
	 */
	public boolean addAllTo(String playlistName, String category)
	{
		try
		{
			Playlist playlist = getPlaylist(playlistName);
			if(playlist != null)
			{
				playlist.addAll(category);
				mainList.addAll(category);
				return true;
			}
			else
				return false;
		}
		catch(NullPointerException | IOException | SlickException e)
		{
			Log.addSystem("audioPlayer_fail_msg///category not found: " + category);
			return false;
		}
	}
	/**
	 * Adds one specified music track to player
	 * @param trackName Name of audio file in aData archive
	 * @throws SlickException If audio file format is wrong
	 * @throws IOException If audio archive not found
	 */
	public void add(String category, String trackName)
	{
		try
		{
			mainList.add(category, trackName);
		}
		catch(NullPointerException | IOException | SlickException e)
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
	public void addAll(String category)
	{
		try
		{
			mainList.addAll(category);
		}
		catch(NullPointerException | IOException | SlickException e)
		{
			Log.addSystem("audioPlayer_fail_msg///category not found: " + category);
		}
	}
	/**
	 * Creates new playlist
	 * @param name Name for new playlist
	 */
	public void createPlaylist(String name)
	{
		playlists.add(new Playlist(name));
	}
	/**
	 * Starts audio player
	 * @param pitch
	 * @param volume
	 */
	public void play(float pitch, float volume, String trackName)
	{
	    Music track = mainList.get(trackName);
		if(track != null)
		{
			track.play(pitch, volume);
			activePlaylist = mainList;
		}
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
		Music[] tracks = new Music[mainList.values().size()];
		mainList.values().toArray(tracks);
		
        if(tracks.length > 0)
        {
            int trackId = rng.nextInt(tracks.length);
            tracks[trackId].play(pitch, volume);
			activePlaylist = mainList;
        }
        else
        {
            Log.addSystem("audioPlayer_fail_msg///no tracks inside");
        }
	}
	/**
	 * Starts random track from playlist with specified name
	 * @param playlistName Playlist name
	 * @param pitch Pitch
	 * @param volume Volume
	 * @return True if random track was successfully started, false otherwise
	 */
	public boolean playRandomFrom(String playlistName, float pitch, float volume)
	{	
		Playlist playlist = getPlaylist(playlistName);
		if(playlist != null)
		{	
			Music[] tracks = new Music[playlist.values().size()];
			playlist.values().toArray(tracks);
			
	        if(tracks.length > 0)
	        {
	            int trackId = rng.nextInt(tracks.length);
	            tracks[trackId].play(pitch, volume);
				activePlaylist = playlist;
	            return true;
	        }
	        else
	        {
	            Log.addSystem("audioPlayer_fail_msg///no tracks inside");
	            return false;
	        }
		}
		else
			return false;
	}
	/**
	 * Checks if any track in playlists is active
	 * @return True if player plays any track, false otherwise
	 */
	public boolean isPlayling()
	{
		for(Playlist playlist : playlists)
		{
			for(Music track : playlist.values())
			{
				if(track.playing())
					return true;
			}
		}
		return false;
	}
	/**
	 * Stops audio player
	 */
	public void stop()
	{
		for(Playlist playlist : playlists)
		{
			Music track = playlist.getCurrentTrack();
			if(track != null)
				track.stop();
		}
		
	}
	/**
	 * Resets audio player
	 */
	public void reset()
	{
		stop();
		playlists.clear();
		mainList.clear();
		playlists.add(mainList);
	}
	/**
	 * Clears all playlists
	 */
	public void clearPlaylists()
	{
		stop();
		for(Playlist playlist : playlists)
		{
			playlist.clear();
		}
	}
	/**
	 * Returns active playlist name
	 * @return String with playlist name
	 */
	public String getActivePlaylist()
	{
		return activePlaylist.getName();
	}
	/**
	 * Returns currently played track
	 * @return Active track
	 */
	public Music getActiveTrack()
	{
		return activePlaylist.getCurrentTrack();
	}
	/**
	 * Returns playlist with specified name
	 * @param playlist Name of desired playlist
	 * @return Playlist with specified name or null if no such playlist found
	 */
	private Playlist getPlaylist(String playlistName)
	{
		for(Playlist playlist : playlists)
		{
			if(playlist.getName().equals(playlistName))
				return playlist;
				
		}
		return null;
	}
}
