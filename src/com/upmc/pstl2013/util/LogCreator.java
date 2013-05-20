package com.upmc.pstl2013.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class LogCreator {

	private static final String[] logsPath = {"logInfo.html","logDebug.html"};

	public static void createLog(String path) throws IOException {

		for (String logPath : logsPath) {
			
			File logs = new File(logPath);
			File destination = new File(path + logPath);
			FileInputStream in = null;
			FileOutputStream out = null;
			try {
				in = new FileInputStream(logs);
				out = new FileOutputStream(destination);
				byte buffer[] = new byte[512 * 1024];
				int nbLecture;
				while ((nbLecture = in.read(buffer)) != -1) {
					out.write(buffer, 0, nbLecture);
				}
			} catch (IOException e) {
				throw e;
			} finally {
				if (in != null) in.close();
				if (out != null) out.close();
			}
			logs.delete();
		}
	}

}
