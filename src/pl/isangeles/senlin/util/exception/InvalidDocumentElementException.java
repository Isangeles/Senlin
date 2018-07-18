/*
 * InvalidDocumentElementException.java
 * 
 * Copyright 2018 Dariusz Sikora <dev@isangeles.pl>
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
package pl.isangeles.senlin.util.exception;

/**
 * Class for invalid document element exception
 * @author Isangeles
 *
 */
public class InvalidDocumentElementException extends Exception 
{
	private static final long serialVersionUID = -9032811444897112598L;
	private final String msg;
	/**
	 * Invalid document exception constructor
	 */
	public InvalidDocumentElementException()
	{
		this.msg = "XML document elemen is invalid";
	}
	/**
	 * Invalid document exception constructor
	 * @param msg String with error message
	 */
	public InvalidDocumentElementException(String msg)
	{
		this.msg = msg;
	}
	
	@Override
	public String getMessage()
	{
		return msg;
	}
	
}
