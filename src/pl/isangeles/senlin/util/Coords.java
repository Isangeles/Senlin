package pl.isangeles.senlin.util;

public class Coords
{
    public static float[] get(String point, float disX, float disY)
    {
        float resWidth = Settings.getResolution()[0];
        float resHeight = Settings.getResolution()[1];
        
        switch(point)
        {
        case "UR":
            return new float[]{disX, disY};
        case "UL":
            return new float[]{resWidth - disX, disY};
        case "LR":
            return new float[]{disX, resHeight - disY};
        case "LL":
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
