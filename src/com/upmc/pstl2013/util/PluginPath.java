package com.upmc.pstl2013.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import org.apache.log4j.Logger;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;


public class PluginPath {
	
	public static String pluginPath = "";
	private static final Logger log = Logger.getLogger(PluginPath.class);
	
	static {
		final URL fileUrl = Platform.getBundle("pstl-2013").getEntry(File.separator);
		try {
			pluginPath = FileLocator.resolve(fileUrl).getFile();
		} catch (IOException e1) {
			log.error("Impossible de récupérer le chemin du plugin... " + e1.getMessage());
		}
	}
}
