package pl.isangeles.senlin.gui;
/**
 * Interface for everything that can provide graphical representation in form of icon
 * @author Isangeles
 *
 */
public interface SlotContent 
{
	/**
	 * Returns icon
	 * @return Graphical icon
	 */
	public InterfaceTile getTile();
	/**
	 * Returns content ID
	 * @return String with content ID
	 */
	public String getId();
	/**
	 * Returns content serial ID 
	 * @return String with content serial ID 
	 */
	public String getSerialId();
	/**
	 * Returns maximal amount of this content in one stack
	 * @return Maximal amount of this content in one stack
	 */
	public int getMaxStack();
	/**
	 * Draws icon on specified position
	 * @param x
	 * @param y
	 * @param scaledPos
	 */
	public void draw(float x, float y, boolean scaledPos);
}
