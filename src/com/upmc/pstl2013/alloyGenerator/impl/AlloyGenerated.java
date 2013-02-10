package com.upmc.pstl2013.alloyGenerator.impl;

import java.io.File;
import com.upmc.pstl2013.alloyExecutor.IAlloyExecutor;
import com.upmc.pstl2013.alloyGenerator.IAlloyGenerated;
import com.upmc.pstl2013.alloyGenerator.IAlloyGenerator;
import com.upmc.pstl2013.strategy.IStrategyParcours;

/**
 * Cette classe représente un fichier généré par le {@link IAlloyGenerator} 
 * ainsi que toutes les informations nécessaires pour le {@link IAlloyExecutor}.
 *
 */
public class AlloyGenerated implements IAlloyGenerated {
	
	private File file;
	private Boolean isCheck;
	private IStrategyParcours strategie;
	
	public AlloyGenerated(File fi, Boolean isC, IStrategyParcours strat) {
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
	public IStrategyParcours getStrategy() {
		return strategie;
	}
}
