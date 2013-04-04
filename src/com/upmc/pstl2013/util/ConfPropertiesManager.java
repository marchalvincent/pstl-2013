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

	private static int DEFAUT_TIMEOUT = 180;
	private static int DEFAUT_NB_NODES_MAX = 100;
	private static int DEFAUT_NB_THREAD = 4;

//	private static int DEFAUT_MAX_STEP = 100;
//	private static int DEFAUT_INCR = 10;
//
//	private static boolean DEFAUT_FINAL = true;
//	private static boolean DEFAUT_INITIAL = true;
//	private static boolean DEFAUT_MERGE = true;

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
			file = new File(Utils.pluginPath + ".alloyAnalyzer-pstl2013.properties");
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
				try {this.setProperties("EnoughState");} catch (Exception e) {}
			}
			if (prop.getProperty("timeOut") == null) {
				try {this.setTimeOut(String.valueOf(ConfPropertiesManager.DEFAUT_TIMEOUT));} catch (Exception e) {}
			}
			if (prop.getProperty("nbNodesMax") == null) {
				try {this.setNbNodes(String.valueOf(ConfPropertiesManager.DEFAUT_NB_NODES_MAX));} catch (Exception e) {}
			}
			if (prop.getProperty("nbThreads") == null) {
				try {this.setNbThreads(String.valueOf(ConfPropertiesManager.DEFAUT_NB_THREAD));} catch (Exception e) {}
			}
			if (prop.getProperty("details") == null) {
				try {this.setDetails(false);} catch (Exception e) {}
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
		store();
	}

	public String getProperties() {
		return prop.getProperty("properties");
	}

	public void setProperties(String properties) throws Exception {
		prop.setProperty("properties", properties);
	}

	public int getTimeOut() {
		try {
			int nb = Integer.parseInt(prop.getProperty("timeOut"));
			if (nb < 1)
				nb = ConfPropertiesManager.DEFAUT_TIMEOUT;
			return nb;
		} catch (NumberFormatException e) {
			return ConfPropertiesManager.DEFAUT_TIMEOUT;
		}
	}

	public void setTimeOut(String timeout) throws Exception {
		prop.setProperty("timeOut", timeout);
	}

	public int getNbNodesMax() {
		try {
			int nb = Integer.parseInt(prop.getProperty("nbNodesMax"));
			if (nb < 1)
				nb = ConfPropertiesManager.DEFAUT_NB_NODES_MAX;
			return nb;
		} catch (NumberFormatException e) {
			return ConfPropertiesManager.DEFAUT_NB_NODES_MAX;
		}
	}

	public void setNbNodes(String nbNodes) throws Exception {
		prop.setProperty("nbNodesMax", nbNodes);
	}

	public int getNbThreads() {
		try {
			int nb = Integer.parseInt(prop.getProperty("nbThreads"));
			if (nb < 1)
				nb = ConfPropertiesManager.DEFAUT_NB_THREAD;
			return nb;
		} catch (NumberFormatException e) {
			return ConfPropertiesManager.DEFAUT_NB_THREAD;
		}
	}

	public void setNbThreads(String nbThreads) {
		prop.setProperty("nbThreads", nbThreads);
	}

	public boolean isDetails() {
		try {
			boolean bool = Boolean.parseBoolean(prop.getProperty("details"));
			return bool;
		} catch (Exception e) {
			return false;
		}
	}

	public void setDetails(boolean details) {
		prop.setProperty("details", String.valueOf(details));
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
