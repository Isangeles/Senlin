package pl.isangeles.senlin.inter.ui;
/**
 * Interface for ui elements
 * @author Darek
 *
 */
interface UiElement
{
    /**
     * Draws UI element
     * @param x Position on X-axis
     * @param y Position on Y-axis
     */
    public void draw(float x, float y);
    /**
     * Updates UI element
     */
    public void update();
    /**
     * Resets UI element
     */
    public void reset();
}
