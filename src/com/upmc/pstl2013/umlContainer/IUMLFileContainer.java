package com.upmc.pstl2013.umlContainer;

import java.util.List;

import org.eclipse.core.resources.IFile;

import com.upmc.pstl2013.IProcess;

public interface IUMLFileContainer extends IProcess {

	/**
	 * Ajoute un fichier à la liste.
	 * @param file {@link IFile}.
	 */
	void addFile(IFile file);
	
	/**
	 * Renvoie la liste des fichiers sélectionnés.
	 * @return une {@link List} d'{@link IFile}.
	 */
	List<IFile> getselectedUMLFiles();
	
}
