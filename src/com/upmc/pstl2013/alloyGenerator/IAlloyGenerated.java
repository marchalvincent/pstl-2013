package com.upmc.pstl2013.alloyGenerator;

import java.io.File;
import com.upmc.pstl2013.properties.IProperties;


public interface IAlloyGenerated {
	
	File getFile();
	
	Boolean isCheck();
	
	public IProperties getProperty();
}
