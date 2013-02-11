package com.upmc.pstl2013.alloyExecutor;

import java.util.List;

/**
 * Le résultat d'une exécution doit implémenter ces méthodes pour l'affichage graphique.
 *
 */
public interface IFileResult {
	
	/**
	 * Renvoie le nom du fichier Alloy.
	 */
	String getNom();
	
	/**
	 * Renvoie une liste de résultat. Un résultat pour une activité.
	 * @return une liste d'{@link IActivityResult}.
	 */
	List <IActivityResult> getListActivityResult();

}
