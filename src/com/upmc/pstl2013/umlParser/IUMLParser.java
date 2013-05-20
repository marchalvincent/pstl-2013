package com.upmc.pstl2013.umlParser;

import org.eclipse.uml2.uml.Activity;
import com.upmc.pstl2013.umlParser.impl.ParserException;

/**
 * L'interface d'un parser de fichier UML.
 */
public interface IUMLParser {

	/**
	 * Renvoie l'{@link Activity} que peut récupérer le parseur.
	 * 
	 * @return une {@link Activity}.
	 */
	Activity getActivity() throws ParserException;
}
