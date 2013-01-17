package com.upmc.pstl2013.factory;

import com.upmc.pstl2013.alloyGenerator.AlloyGenerator;
import com.upmc.pstl2013.alloyGenerator.IUMLParser;
import com.upmc.pstl2013.alloyGenerator.UMLParser;
import com.upmc.pstl2013.alloyGenerator.interfaces.IAlloyGenerator;
import com.upmc.pstl2013.fileContainer.interfaces.IUMLFileContainer;

/**
 * La factory. Implémente le Design Pattern Singleton.
 */
public class Factory implements IFactory {
	
	private static final Factory instance = new Factory();
	
	private Factory() {}
	
	/**
	 * Renvoie l'unique instance de la Factory.
	 * @return {@link Factory}.
	 */
	public static Factory getInstance() {
		return instance;
	}
	
	@Override
	public IUMLFileContainer newFileChooser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IUMLParser newParser() {
		return new UMLParser();
	}

	@Override
	public IAlloyGenerator newAlloyGenerator() {
		return new AlloyGenerator();
	}

}
