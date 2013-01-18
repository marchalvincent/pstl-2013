package com.upmc.pstl2013.alloyGenerator.impl;

import java.util.List;

import org.eclipse.uml2.uml.Activity;

import com.upmc.pstl2013.fileContainer.IUMLFileContainer;

/**
 * L'interface d'un parser de fichier UML.
 */
public interface IUMLParser {
	
	/**
	 * Renvoie la liste des {@link Activity} contenues dans les fichiers du {@link IUMLFileContainer}.
	 * @param fileChooser
	 * @return
	 */
	List<Activity> getActivities(IUMLFileContainer fileChooser);
}
