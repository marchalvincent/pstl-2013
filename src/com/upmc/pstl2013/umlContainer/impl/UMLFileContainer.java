package com.upmc.pstl2013.umlContainer.impl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;

import com.upmc.pstl2013.umlContainer.IUMLFileContainer;

public class UMLFileContainer implements IUMLFileContainer {

	private List<IFile> files;
	
	public UMLFileContainer() {
		super();
		files = new ArrayList<IFile>();
	}
	
	@Override
	public List<IFile> getselectedUMLFiles() {
		// une fois qu'on a renvoyé les fichiers sélectionnés, il faut les "déselectionner".
		// c'est pourquoi on passe par une list temporaire
		List<IFile> filesTemp = new ArrayList<IFile>();
		for (int i = 0; i < files.size(); i++) {
			filesTemp.add(files.remove(i));
		}
		return filesTemp;
	}

	@Override
	public void addFile(IFile file) {
		files.add(file);
	}
}
