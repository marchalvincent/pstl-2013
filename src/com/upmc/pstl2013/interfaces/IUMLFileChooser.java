package com.upmc.pstl2013.interfaces;

import java.util.List;

import org.eclipse.core.resources.IFile;

public interface IUMLFileChooser {

	/**
	 * Pour l'instant tu implémente ça avec une Liste d'1 seul élément.
	 * Mais sa devra pouvoir être plusieurs fichiers pour plus tard.
	 * Tu peux supprimer cette doc et en mettre une vraie
	 * @return
	 */
	List<IFile> getselectedUMLFiles();
}
