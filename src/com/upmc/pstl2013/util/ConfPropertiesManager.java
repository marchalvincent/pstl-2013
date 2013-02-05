package com.upmc.pstl2013.util;

import java.io.IOException;
import java.util.Properties;

public class ConfPropertiesManager {

	private static Properties prop;

	public static void loadConfProperties()
	{
		try {
			prop = new Properties();
			//load a properties file
			prop.load(ConfPropertiesManager.class.getClassLoader().getResourceAsStream("build.properties"));

			//get the property value and print it out
			System.out.println(prop.getProperty("database"));
			System.out.println(prop.getProperty("dbuser"));
			System.out.println(prop.getProperty("dbpassword"));

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
