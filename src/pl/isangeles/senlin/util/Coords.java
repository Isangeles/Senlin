package pl.isangeles.senlin.util;
/**
 * Static class with methods returning scaled positions for current resolution
 * @author Isangeles
 *
 */
public class Coords
{
	public static final int UP = 0,
							RIGHT = 1,
							DOWN = 2,
							LEFT = 3;
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
     * Returns distance corrected by scale
     * @param rawDistance Distance on 1920x1080
     * @return Distance scaled to current resolution
     */
    public static int getDis(int rawDistance)
    {
    	return Math.round(rawDistance * Coords.getScale());
    }
    /**
     * Returns scale based on current resolution
     * @return Float scale value
     */
    public static float getScale()
    {

        float defResX = 1920;
        float defResY = 1080;
        float resX = Settings.getResolution()[0];
        float resY = Settings.getResolution()[1];
        float proportionX = resX / defResX;
        float proportionY = resY / defResY;
        return Math.round(Math.min(proportionX, proportionY) * 10f) / 10f;
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
