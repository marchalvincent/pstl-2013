package com.upmc.pstl2013.infoGenerator.impl;

import com.upmc.pstl2013.infoGenerator.IInfoGenerator;

/**
 * Cette classe va servir à passer des paramètres au générateur
 * de fichier Alloy.
 *
 */
public class InfoGenerator implements IInfoGenerator {

	private String directoryPath;
	
	public InfoGenerator() {
		super();
		directoryPath = "";
	}

	@Override
	public void setDestinationDirectory(String path) {
		directoryPath = path;
	}
	
	@Override
	public String getDestinationDirectory() {
		return directoryPath;
	}

}
