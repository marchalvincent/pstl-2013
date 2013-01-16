package com.upmc.pstl2013.interfaces;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.uml2.uml.Activity;

/**
 * Un parser de fichier UML renvoie.
 */
public interface IUMLParser {

	/**
	 * Renvoie une liste d'{@link Activity} correspondant à celles
	 * contenues dans le fichier UML passé en paramètre.
	 * @param file le fichier UML ({@link IFile}).
	 * @return la liste des {@link Activity}.
	 */
	List<Activity> getActivities();
}
