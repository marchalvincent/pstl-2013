package com.upmc.pstl2013.umlParser;

import java.util.List;
import java.util.Map;
import org.eclipse.uml2.uml.Activity;
import com.upmc.pstl2013.IProcess;

/**
 * L'interface d'un parser de fichier UML.
 */
public interface IUMLParser extends IProcess {

	/**
	 * Renvoie la liste des {@link Activity} que peut récupérer le parseur groupée par fichier.
	 * 
	 * @return une {@link Map} qui associe un nom de fichier et une {@link List} d'{@link Activity}.
	 */
	Map<String, List<Activity>> getActivities();
}
