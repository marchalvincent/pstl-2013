package com.upmc.pstl2013.umlParser;

import java.util.List;

import org.eclipse.uml2.uml.Activity;

/**
 * L'interface d'un parser de fichier UML.
 */
public interface IUMLParser {
	
	/**
	 * Renvoie la liste des {@link Activity} que peut récupérer le parseur.
	 * @return une {@link List} d'{@link Activity}.
	 */
	List<Activity> getActivities();
}
