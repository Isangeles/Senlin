package pl.isangeles.senlin.util;
/**
 * This class make slick time management easier 
 * @author Isangles
 *
 */
public class Stopwatch
{

    private Stopwatch() {}
    
    /**
     * Converts seconds to milliseconds
     * @param sec Time in seconds
     * @return Time in milliseconds
     */
    public static int sec(int sec)
    {
        return sec * 1000;
    }
    /**
     * Converts minutes to milliseconds
     * @param sec Time in minutes
     * @return Time in milliseconds
     */
    public static int min(int min)
    {
        return min * 10000;
    }
}
