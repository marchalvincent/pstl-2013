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
				try {this.setPathFolder("");} catch (Exception e) {}
			}
			if (prop.getProperty("properties") == null) {
				try {this.setProperties("");} catch (Exception e) {}
			}
			if (prop.getProperty("timeOut") == null) {
				try {this.setTimeOut("180");} catch (Exception e) {}
			}
			if (prop.getProperty("nbNodes") == null) {
				try {this.setNbNodes("100");} catch (Exception e) {}
			}
			if (prop.getProperty("nbThreads") == null) {
				try {this.setNbThreads("4");} catch (Exception e) {}
			}
			
			
			this.store();
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	public String getPathFolder() {
		return prop.getProperty("pathFolder");
	}

	public void setPathFolder(String pathFolder) throws Exception {
		prop.setProperty("pathFolder",pathFolder);
	}
	
	public String getProperties() {
		return prop.getProperty("properties");
	}
	
	public void setProperties(String properties) throws Exception {
		prop.setProperty("properties", properties);
	}

	
	public String getTimeOut() {
		return prop.getProperty("timeOut");
	}
	
	public void setTimeOut(String timeout) throws Exception {
		prop.setProperty("timeOut", timeout);
	}

	public String getNbNodes() {
		return prop.getProperty("nbNodes");
	}

	public void setNbNodes(String nbNodes) throws Exception {
		prop.setProperty("nbNodes", nbNodes);
	}

	public String getNbThreads() {
		return prop.getProperty("nbThreads");
	}
	
	public void setNbThreads(String nbThreads) {
		prop.setProperty("nbThreads", nbThreads);
	}
	
	public void store() throws IOException {
		try {
			OutputStream out = new FileOutputStream(file);
			prop.store(out, null);
			out.close();
		} catch (IOException e) {
			log.error(e.getMessage());
			throw e;
		}
	}
}
