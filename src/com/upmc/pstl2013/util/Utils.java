package com.upmc.pstl2013.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.log4j.Logger;


public class Utils {

	private static Logger log = Logger.getLogger(Utils.class);
	public static String pluginPath = "AlloyAnalyzer" + File.separator;

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
