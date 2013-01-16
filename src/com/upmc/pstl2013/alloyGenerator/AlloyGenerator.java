package com.upmc.pstl2013.alloyGenerator;

import java.util.List;

import org.eclipse.uml2.uml.Activity;

import com.upmc.pstl2013.alloyGenerator.interfaces.IAlloyGenerator;
import com.upmc.pstl2013.factory.Factory;
import com.upmc.pstl2013.interfaces.IUMLFileChooser;

/**
 * Cette classe se charge de générer le fichier Alloy à partir 
 *
 */
public class AlloyGenerator implements IAlloyGenerator {

	/**
	 * Constructeur
	 */
	public AlloyGenerator () {
		super();
	}
	
	@Override
	public void generateFile(IUMLFileChooser fileChooser) {
		
		IUMLParser parser = Factory.getInstance().newParser();
		@SuppressWarnings("unused")
		List<Activity> activities = parser.getActivities(fileChooser);
		//TODO la génération avec Jet

		//umlActivity.getNodes()
		//umlActivity.getEdges()
		
	}
}
