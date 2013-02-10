package com.upmc.pstl2013.alloyGenerator;

import java.util.List;
import com.upmc.pstl2013.alloyGenerator.jet.JetException;

/**
 * L'interface d'un générateur de fichier Alloy.
 * 
 */
public interface IAlloyGenerator {

	/**
	 * Génère et renvoie le(s) fichier(s) Alloy générés (à l'aide d'une classe conteneur
	 * pour passer des informations à l'éxecuteur).
	 * 
	 * @return {@link List} de {@link IAlloyGenerated}.
	 */
	List<IAlloyGenerated> generateFile() throws JetException;
}
