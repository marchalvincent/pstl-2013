package com.upmc.pstl2013.interfaces;

import java.util.List;

import org.eclipse.core.resources.IFile;

public interface IUMLFileChooser {

	/**
	 * Pour l'instant ce n'est qu'une Liste avec 1 élément.
	 * Tu peux supprimer cette doc et en mettre une vraie
	 * @return
	 */
	List<IFile> getselectedUMLFiles();
}
