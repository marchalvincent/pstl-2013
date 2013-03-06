package com.upmc.pstl2013.alloyGenerator;

import java.util.Iterator;
import com.upmc.pstl2013.alloyGenerator.jet.JetException;
import edu.mit.csail.sdg.alloy4compiler.translator.A4Solution;

/**
 * L'interface d'un générateur de fichier Alloy.
 * 
 */
public interface IAlloyGenerator extends Iterator<IAlloyGenerated> {

	/**
	 * Génère et renvoie le fichier Alloy générés (à l'aide d'une classe conteneur
	 * pour passer des informations à l'éxecuteur).
	 * 
	 * @return {@link IAlloyGenerated}.
	 */
	IAlloyGenerated generateFile() throws JetException;
	
	/**
	 * Passe au générateur la solution de l'execution.
	 * @param A4Solution
	 */
	void setSolution(A4Solution solution);

	/**
	 * Renvoie le nombre de state de la propriété du generator.
	 */
	String getNbState();
}
