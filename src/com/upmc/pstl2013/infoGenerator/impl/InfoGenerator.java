package com.upmc.pstl2013.infoGenerator.impl;

import java.util.Map;
import com.upmc.pstl2013.alloyGenerator.IAlloyGenerator;
import com.upmc.pstl2013.infoGenerator.IInfoGenerator;

/**
 * Cette classe va servir à passer des paramètres au {@link IAlloyGenerator}.
 * 
 */
public class InfoGenerator implements IInfoGenerator {

	private String directoryPath;
	private Map<String, Map<String, String>> properties;

	public InfoGenerator() {
		super();
		directoryPath = "";
		properties = null;
	}

	@Override
	public void setDestinationDirectory(String path) {
		directoryPath = path;
	}

	@Override
	public String getDestinationDirectory() {
		return directoryPath;
	}

	@Override
	public void setAttributes(Map<String, Map<String, String>> prop) {
		properties = prop;
	}

	@Override
	public Map<String, Map<String, String>> getAttributes() {
		return properties;
	}
}
