package com.upmc.pstl2013.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import org.apache.log4j.Logger;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;


public class Utils {

	private static Logger log = Logger.getLogger(Utils.class);
	public static String pluginPath = "." + File.separator;

	static {
		final URL fileUrl = Platform.getBundle("pstl-2013").getEntry(File.separator);
		try {
			pluginPath = FileLocator.resolve(fileUrl).getFile();
		} catch (IOException e1) {
			log.error("Impossible de récupérer le chemin du plugin... " + e1.getMessage());
		}
	}

	/**
	 * Copie un fichier d'un endroit à un autre
	 * @param oldFile
	 * @param newFile
	 */
	public static void copyContentFile (File oldFile, File newFile) {
		try {
			if (!newFile.exists()) {
				newFile = new File(newFile.getAbsolutePath());
			}

			FileOutputStream out = new FileOutputStream(newFile);
			FileInputStream in = new FileInputStream(oldFile);

			int b;
			while ((b = in.read()) != -1) {
				out.write(b);
			}
			out.close();
			in.close();

		} catch (Exception e) {
			log.error("Impossible de copier le fichier : " + e.getMessage());
		}
	}


}
