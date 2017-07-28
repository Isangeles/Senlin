package pl.isangeles.senlin.gui;
/**
 * Interface for everything that can provide graphical representation in form of interface tile
 * @author Isangeles
 *
 */
public interface SlotContent 
{
	public InterfaceTile getTile();
	
	public String getId();
	
	public String getSerialId();
	
	public int getMaxStack();
	
	public void draw(float x, float y, boolean scaledPos);
}
