package com.upmc.pstl2013.UMLtoAlloy;

import java.util.List;

import org.eclipse.uml2.uml.Activity;

import com.upmc.pstl2013.interfaces.IAlloyGenerator;
import com.upmc.pstl2013.interfaces.IUMLParser;

public class AlloyGenerator implements IAlloyGenerator {

	private final IUMLParser parser;
	
	/**
	 * Constructeur a partir d'un {@link IUMLParser}.
	 * @param p le parser de fichier UML.
	 */
	public AlloyGenerator (final IUMLParser p) {
		super();
		parser = p;
	}
	
	@Override
	public void generateFile() {
		
		@SuppressWarnings("unused")
		List<Activity> activities = parser.getActivities();
		//TODO la génération avec Jet
		
	}
}
