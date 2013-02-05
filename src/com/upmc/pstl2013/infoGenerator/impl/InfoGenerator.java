package com.upmc.pstl2013.infoGenerator.impl;

import java.util.List;
import com.upmc.pstl2013.alloyGenerator.IAlloyGenerator;
import com.upmc.pstl2013.infoGenerator.IInfoGenerator;
import com.upmc.pstl2013.properties.IProperties;

/**
 * Cette classe va servir à passer des paramètres au {@link IAlloyGenerator}.
 * 
 */
public class InfoGenerator implements IInfoGenerator {

	private String directoryPath;
	private List<IProperties> properties;

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
	public void setProperties(List<IProperties> prop) {
		properties = prop;
	}

	@Override
	public List<IProperties> getProperties() {
		return properties;
	}
}
