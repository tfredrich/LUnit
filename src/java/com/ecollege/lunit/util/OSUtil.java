/**
 * 
 */
package com.ecollege.lunit.util;

/**
 * Provides foreign methods to acquire information about the OS on which the project is running.
 * 
 * @author toddf
 * @since Sept 25, 2009
 */
public abstract class OSUtil
{
	public static boolean isWindows()
	{
		return (getOS().toLowerCase().contains("windows"));
	}
	
	public static boolean isLinux()
	{
		return (getOS().toLowerCase().contains("linux"));
	}
	
	public static String getOS()
	{
		return System.getProperty("os.name");
	}
	
	public static String getOSVersion()
	{
		return System.getProperty("os.version");
	}
}
