package com.upmc.pstl2013.fileContainer.interfaces;

import java.util.List;

import org.eclipse.core.resources.IFile;

public interface IUMLFileContainer {

	/**
	 * Pour l'instant tu implémente ça avec une Liste d'1 seul élément.
	 * Mais sa devra pouvoir être plusieurs fichiers pour plus tard.
	 * Tu peux supprimer cette doc et en mettre une vraie
	 * @return
	 */
	List<IFile> getselectedUMLFiles();
	
	/**
	 * Ajoute un fichier à la liste.
	 * @param file {@link IFile}.
	 */
	void addFile(IFile file);

	/***
	 * Permet de retourner le nombre d'éléments présent dans la liste
	 * @return le nombre de IFile present dans la liste
	 */
	int getLength();
}
