package com.upmc.pstl2013.alloyGenerator.impl;

import java.io.File;
import com.upmc.pstl2013.alloyExecutor.IAlloyExecutor;
import com.upmc.pstl2013.alloyGenerator.IAlloyGenerated;
import com.upmc.pstl2013.alloyGenerator.IAlloyGenerator;
import com.upmc.pstl2013.properties.IProperties;

/**
 * Cette classe représente un fichier généré par le {@link IAlloyGenerator} 
 * ainsi que toutes les informations nécessaires pour le {@link IAlloyExecutor}.
 *
 */
public class AlloyGenerated implements IAlloyGenerated {
	
	private File file;
	private IProperties property;
	
	public AlloyGenerated(File file, IProperties property) {
		super();
		this.file = file;
		this.property = property;
	}

	@Override
	public File getFile() {
		return file;
	}
	
	@Override
	public Boolean isCheck() {
		return property.isCheck();
	}

	@Override
	public IProperties getProperty() {
		return property;
	}
}
