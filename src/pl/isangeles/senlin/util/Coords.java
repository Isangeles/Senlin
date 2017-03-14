package pl.isangeles.senlin.util;

public class Coords
{
    public static float[] get(String point, float disX, float disY)
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
    
    public static float getX(String point, float dis)
    {
        return get(point, dis, 0)[0];
    }
    
    public static float getY(String point, float dis)
    {
        return get(point, 0, dis)[1];
    }
}
