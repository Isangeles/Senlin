package pl.isangeles.senlin.util;
/**
 * Tuple for XY position 
 * @author Isangeles
 *
 */
public class Position 
{
	public int x;
	public int y;
	/**
	 * Position default constructor
	 */
	public Position()
	{
		this.x = 0;
		this.y = 0;
	}
	
	public Position(int x, int y) 
	{
		this.x = x;
		this.y = y;
	}
	
	public Position(int[] xy)
	{
		this.x = xy[0];
		this.y = xy[1];
	}
	
	public Position(String xSemicolonY) throws NumberFormatException
	{
		this.x = Integer.parseInt(xSemicolonY.split(";")[0]);
		this.y = Integer.parseInt(xSemicolonY.split(";")[1]);
	}

}
