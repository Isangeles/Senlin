/*
 * AConnector.java
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
package pl.isangeles.senlin.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * This static class provide access to external audio files
 *
 * @author Isangeles
 */
public class AConnector {
  /** Private constructor to prevent initialization */
  private AConnector() {}
  /**
   * Returns input stream to specified file in audio arch file
   *
   * @param pathInArch Path to audio file inside audio archive
   * @return Input stream to specified audio file
   * @throws IOException If aData was not found
   */
  public static InputStream getInput(String pathInArch) throws IOException, NullPointerException {
    try {
      String fullPath = "audio/" + pathInArch;
      ZipFile aData = new ZipFile("data" + File.separator + "aData");
      ZipEntry aFile = aData.getEntry(fullPath);
      InputStream is = aData.getInputStream(aFile);
      return is;
    } catch (IOException | NullPointerException e) {
      System.err.println("aconnector_fail_path:" + pathInArch);
      throw e;
    }
  }
}
