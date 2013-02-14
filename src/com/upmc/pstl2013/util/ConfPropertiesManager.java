package com.upmc.pstl2013.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import org.apache.log4j.Logger;

public class ConfPropertiesManager {

	private Properties prop;
	private File file;
	private Logger log = Logger.getLogger(ConfPropertiesManager.class);
	private static final ConfPropertiesManager instance = new ConfPropertiesManager();
	
	/**
	 * Renvoie l'unique instance du Singleton.
	 * 
	 * @return {@link ConfPropertiesManager}.
	 */
	public static ConfPropertiesManager getInstance() {
		return instance;
	}
	
	private ConfPropertiesManager() {
		
		try {
			file = new File(Utils.pluginPath + "pstl2013.properties");
			if (!file.exists())
				file.createNewFile();
			
			prop = new Properties();
			//load a properties file
			InputStream in = new FileInputStream(file);
			prop.load(in);
			in.close();
			
			// on créé par défaut les propriété si elles n'y sont pas
			if (prop.getProperty("pathFolder") == null) {
				try {
					this.setPathFolder("");
				} catch (Exception e) {}
			}
			if (prop.getProperty("poperties") == null) {
				try {
					this.setProperties("");
				} catch (Exception e) {}
			}
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public String getPathFolder() {
		return prop.getProperty("pathFolder");
	}

	public void setPathFolder(String pathFolder) throws Exception {
		prop.setProperty("pathFolder",pathFolder);
		try {
			OutputStream out = new FileOutputStream(file);
			prop.store(out, null);
			out.close();
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}
	
	public String getProperties() {
		return prop.getProperty("poperties");
	}
	
	public void setProperties(String properties) throws Exception {
		prop.setProperty("poperties", properties);
		try {
			OutputStream out = new FileOutputStream(file);
			prop.store(out, null);
			out.close();
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}

}
