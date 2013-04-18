package com.upmc.pstl2013.alloyExecutor;


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
	 * Renvoie une liste de résultat. Un résultat correspond à une activité UML.
	 * @return l'{@link IActivityResult}.
	 */
	IActivityResult getActivityResult();

}
