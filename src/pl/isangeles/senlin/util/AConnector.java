package pl.isangeles.senlin.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * This static class provide access to external audio files
 * @author Isangeles
 *
 */
public class AConnector 
{
	/**
	 * Private constructor to prevent initialization
	 */
	private AConnector() {}
	/**
	 * Returns input stream to specified file in audio arch file
	 * @param pathInArch Path to audio file inside audio archive
	 * @return Input stream to specified audio file
	 * @throws IOException If aData was not found
	 */
	public static InputStream getInput(String pathInArch) throws IOException
	{
		String fullPath = "audio/" + pathInArch;
		ZipFile aData = new ZipFile("data" + File.separator + "aData");
		ZipEntry aFile = aData.getEntry(fullPath);
		InputStream is = aData.getInputStream(aFile);
		return is;
	}
}
