package pl.isangeles.senlin.util;
/**
 * Static class with methods returning scaled positions for current resolution
 * @author Isangeles
 *
 */
public class Coords
{
	/**
	 * Private constructor to prevent initialization
	 */
	private Coords(){}
    /**
     * Returns scaled position on x-axis related to specific point on screen
     * @param point "TL" - for top left, "BL" - for bottom left, "TR" - for top right, "BR" - for bottom right, "CE" - for center
     * @param dis Distance from specific point
     * @return Scaled position related to specific point
     */
    public static float getX(String point, float dis)
    {
        return get(point, dis * Settings.getScale(), 0)[0];
    }
    /**
     * Returns scaled position on y-axis related to specific point on screen
     * @param point "TL" - for top left, "BL" - for bottom left, "TR" - for top right, "BR" - for bottom right, "CE" - for center
     * @param dis Distance from specific point
     * @return Scaled position related to specific point
     */
    public static float getY(String point, float dis)
    {
        return get(point, 0, dis * Settings.getScale())[1];
    }
    /**
     * Returns table with positions on x and y axis related to specific point on screen
     * @param point "TL" - for top left, "BL" - for bottom left, "TR" - for top right, "BR" - for bottom right, "CE" - for center
     * @param disX Distance from specific point on x-axis
     * @param disY Distance from specific point on y-axis
     * @return Table with x position[0] and y position[1]
     */
    private static float[] get(String point, float disX, float disY)
    {
        float resWidth = Settings.getResolution()[0];
        float resHeight = Settings.getResolution()[1];
        
        switch(point)
        {
        case "TL":
            return new float[]{disX, disY};
        case "TR":
            return new float[]{resWidth - disX, disY};
        case "BL":
            return new float[]{disX, resHeight - disY};
        case "BR":
            return new float[]{resWidth - disX, resHeight - disY};
        case "CE":
        {
            float ceX = resWidth /2;
            float ceY = resHeight /2;
            return new float[]{ceX + disX, ceY + disY};
        }
        default:
            return new float[]{disX, disY};
        }
    }
}
