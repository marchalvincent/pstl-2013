/**
 * 
 */
package com.upmc.pstl2013.umlParser.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
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

/**
 * Cette classe se charge de parser un fichier UML.
 */
public class UMLParser implements IUMLParser {

	private IUMLFileContainer fileContainer;
	private List<Activity> activities;
	private static Logger log = Logger.getLogger(UMLParser.class);

	public UMLParser(IUMLFileContainer fc) {
		super();
		fileContainer = fc;
		activities = new ArrayList<Activity>();
	}

	@Override
	public List<Activity> getActivities() {

		log.debug("Debut du parsing.");
		List<IFile> files = fileContainer.getselectedUMLFiles();
		int i = 1, nbFic = 0, nbActivity = 0;
		for (IFile file : files) {
			nbFic++;
			log.debug("Fichier n°" + i + ".");
			if (file != null) {

				URI uri = URI.createFileURI(file.getRawLocationURI().getPath());
				ResourceSet resourceSet = new ResourceSetImpl();
				Resource resource = (Resource) resourceSet.getResource(uri, true);

				TreeIterator<EObject> tree = resource.getAllContents();
				while (tree.hasNext()) {
					EObject eo = tree.next();
					if (eo instanceof Activity) {
						nbActivity++;
						log.debug("Une activité est trouvée.");
						Activity umlActivity = (Activity) eo;
						activities.add(umlActivity);
					}
				}
			}
			i++;
		}
		log.debug("Bilan du parsing : " + nbFic + " fichiers, " + nbActivity + " activités.");
		return activities;
	}

	@Override
	public void reset() {
		activities = new ArrayList<Activity>();
		fileContainer.reset();
	}
}
