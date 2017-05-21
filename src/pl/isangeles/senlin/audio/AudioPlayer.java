package pl.isangeles.senlin.audio;

import java.io.IOException;
import java.util.ArrayList;
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
public class AudioPlayer extends ArrayList<Music>
{
	private static final long serialVersionUID = 1L;

	public AudioPlayer() 
	{
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
			this.add(track);
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
				String trackName = scann.next();
				Music track = new Music(AConnector.getInput(category + "/" + trackName), trackName);
				this.add(track);
			}
			catch(IOException | SlickException e)
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
	public void play(float pitch, float volume)
	{
		if(this.size() == 1)
			this.get(0).play(pitch, volume);
	}
	/**
	 * Stops audio player
	 */
	public void stop()
	{
		for(Music track : this)
		{
			if(track.playing())
				track.stop();
		}
	}
}
