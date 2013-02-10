package com.upmc.pstl2013.alloyGenerator;

import java.io.File;
import com.upmc.pstl2013.strategy.IStrategyParcours;


public interface IAlloyGenerated {
	
	File getFile();
	
	Boolean isCheck();
	
	IStrategyParcours getStrategy();
}
