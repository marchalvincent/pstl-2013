/**
 * 
 */
package com.upmc.pstl2013.umlParser.impl;

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

import com.upmc.pstl2013.umlContainer.IUMLFileContainer;
import com.upmc.pstl2013.umlParser.IUMLParser;
import com.upmc.pstl2013.util.Console;

/**
 * Cette classe se charge de parser un fichier UML.
 */
public class UMLParser implements IUMLParser {

	private IUMLFileContainer fileContainer;
	private List<Activity> activities;

	public UMLParser(IUMLFileContainer fc) {
		super();
		fileContainer = fc;
		activities = new ArrayList<Activity>();
	}

	@Override
	public List<Activity> getActivities() {

		Console.debug("Debut du parsing.", this.getClass());
		List<IFile> files = fileContainer.getselectedUMLFiles();
		int i = 1, nbFic = 0, nbActivity = 0;
		for (IFile file : files) {
			nbFic++;
			Console.debug("Fichier n°" + i + ".", this.getClass());
			if (file != null) {

				URI uri = URI.createFileURI(file.getRawLocationURI().getPath());
				ResourceSet resourceSet = new ResourceSetImpl();
				Resource resource = (Resource) resourceSet.getResource(uri, true);

				TreeIterator<EObject> tree = resource.getAllContents();
				while (tree.hasNext()) {
					EObject eo = tree.next();
					if (eo instanceof Activity) {
						nbActivity++;
						Console.debug("Une activité est trouvée.", this.getClass());
						Activity umlActivity = (Activity) eo;
						activities.add(umlActivity);
					}
				}
			}
			i++;
		}
		Console.debug("Bilan du parsing : " + nbFic + " fichiers, " + nbActivity + " activités.", this.getClass());
		return activities;
	}

	@Override
	public void reset() {
		activities = new ArrayList<Activity>();
		fileContainer.reset();
	}
}
