package com.upmc.pstl2013.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfPropertiesManager {

	private Properties prop;
	
	private static final ConfPropertiesManager instance = new ConfPropertiesManager();

	private ConfPropertiesManager() {	
		try {
			prop = new Properties();
			//load a properties file
			prop.load(ConfPropertiesManager.class.getClassLoader().getResourceAsStream("build.properties"));

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Renvoie l'unique instance de la Factory.
	 * 
	 * @return {@link ConfPropertiesManager}.
	 */
	public static ConfPropertiesManager getInstance() {
		return instance;
	}

	public String getPathFolder() {
		return prop.getProperty("pathFolder") ;
	}

	public void setPathFolder(String pathFolder) {
		prop.setProperty("pathFolder",pathFolder);
		try {
			//ConfPropertiesManager.class.getClassLoader().getResource("")
			prop.store(new FileOutputStream("build.properties"), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	

}
