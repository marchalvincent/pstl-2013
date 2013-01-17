package com.upmc.pstl2013.alloyGenerator.test;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;

import com.upmc.pstl2013.interfaces.IUMLFileChooser;

public class UMLFileChooserBouchon implements IUMLFileChooser {

	List<IFile> files;

	public UMLFileChooserBouchon() {

		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject project = root.getProject("pstl-2013");
		IFile file1 = project.getFile("model/InitialABCfinal.uml");
		System.out.println(file1.getName());
		files = new ArrayList<IFile>();
		files.add(file1);
	}

	@Override
	public List<IFile> getselectedUMLFiles() {
		return files;
	}
	
	public static void main(String[] args) {
		new UMLFileChooserBouchon();
	}
}
