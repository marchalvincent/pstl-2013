package com.upmc.pstl2013.alloyGenerator;

import java.util.List;

import org.eclipse.uml2.uml.Activity;

import com.upmc.pstl2013.interfaces.IUMLFileChooser;

/**
 * L'interface d'un parser de fichier UML.
 */
public interface IUMLParser {
	
	/**
	 * Renvoie la liste des {@link Activity} contenues dans les fichiers du {@link IUMLFileChooser}.
	 * @param fileChooser
	 * @return
	 */
	List<Activity> getActivities(IUMLFileChooser fileChooser);
}
