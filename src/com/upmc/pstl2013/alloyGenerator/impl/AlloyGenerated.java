package com.upmc.pstl2013.alloyGenerator.impl;

import java.io.File;
import com.upmc.pstl2013.alloyExecutor.IAlloyExecutor;
import com.upmc.pstl2013.alloyGenerator.IAlloyGenerated;
import com.upmc.pstl2013.alloyGenerator.IAlloyGenerator;
import com.upmc.pstl2013.strategy.IStrategy;

/**
 * Cette classe représente un fichier généré par le {@link IAlloyGenerator} 
 * ainsi que toutes les informations nécessaires pour le {@link IAlloyExecutor}.
 *
 */
public class AlloyGenerated implements IAlloyGenerated {
	
	private File file;
	private Boolean isCheck;
	private IStrategy strategie;
	
	public AlloyGenerated(File fi, Boolean isC, IStrategy strat) {
		super();
		file = fi;
		isCheck = isC;
		strategie = strat;
	}

	@Override
	public File getFile() {
		return file;
	}
	
	@Override
	public Boolean isCheck() {
		return isCheck;
	}

	@Override
	public IStrategy getStrategy() {
		return strategie;
	}
}
