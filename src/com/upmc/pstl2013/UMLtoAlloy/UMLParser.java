/**
 * 
 */
package com.upmc.pstl2013.UMLtoAlloy;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.uml2.uml.Activity;

import com.upmc.pstl2013.interfaces.IUMLFileChooser;
import com.upmc.pstl2013.interfaces.IUMLParser;

/**
 * Cette classe se charge de parser un fichier UML.
 */
public class UMLParser implements IUMLParser {

	private IUMLFileChooser fileChooser;
	private List<Activity> activities;

	public UMLParser(IUMLFileChooser fc) {
		fileChooser = fc;
		activities = new ArrayList<Activity>();
	}

	@Override
	public List<Activity> getActivities() {

		List<IFile> files = fileChooser.getselectedUMLFiles();

		for (IFile file : files) {
			if (file != null) {
				final URI uri = URI.createFileURI(file.getRawLocationURI().getPath());

				ResourceSet resourceSet = new ResourceSetImpl();
				Resource resource = (Resource) resourceSet.getResource(uri, true);

				TreeIterator<EObject> tree = resource.getAllContents();
				while (tree.hasNext()) {
					EObject eo = tree.next();
					if (eo instanceof Activity) {
						Activity umlActivity = (Activity) eo;
						activities.add(umlActivity);

						System.out.println(umlActivity.getName());

						//umlActivity.getNodes()
						//umlActivity.getEdges()
					}
				}
				//TODO enlever??
				//EcoreUtil.getObjectsByType(pm.getProcessElements(), UMLPackage.eINSTANCE.getActivityFinalNode());
			}
		}
		return activities;
	}
}
