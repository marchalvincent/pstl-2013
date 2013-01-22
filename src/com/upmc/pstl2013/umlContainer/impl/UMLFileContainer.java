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
		return files;
	}

	@Override
	public void addFile(IFile file) {
		files.add(file);
	}

	@Override
	public void reset() {
		files = new ArrayList<IFile>();
	}
}