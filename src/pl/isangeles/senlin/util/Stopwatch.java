package pl.isangeles.senlin.util;

import java.util.concurrent.TimeUnit;

/**
 * This class make slick time management easier 
 * @author Isangles
 *
 */
public class Stopwatch
{
	/**
	 * Private constructor to prevent initialization
	 */
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
    
    public static String timeFromMillis(long timeInMillis)
    {
    	String hmsTime = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(timeInMillis),
    								   TimeUnit.MILLISECONDS.toMinutes(timeInMillis) % TimeUnit.HOURS.toMinutes(1),
    								   TimeUnit.MILLISECONDS.toSeconds(timeInMillis) % TimeUnit.MINUTES.toSeconds(1));
    	return hmsTime;
    }
}
