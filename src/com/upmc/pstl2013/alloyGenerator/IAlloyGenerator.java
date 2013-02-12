package com.upmc.pstl2013.alloyGenerator;

import java.util.Iterator;
import com.upmc.pstl2013.alloyGenerator.jet.JetException;

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
	 * Informe le générateur que le fichier généré est satisfaisable ou non.
	 * @param bool
	 */
	void setSatisfiable(boolean bool);

	/**
	 * Renvoie le nombre de state de la propriété du generator.
	 */
	String getNbState();
}
