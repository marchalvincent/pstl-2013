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
import com.upmc.pstl2013.umlParser.IUMLParser;

/**
 * Cette classe se charge de parser un fichier UML.
 */
public class UMLParser implements IUMLParser {

	private IFile UMLFile;
	private static Logger log = Logger.getLogger(UMLParser.class);

	public UMLParser(IFile file) {
		super();
		this.UMLFile = file;
	}

	@Override
	public List<Activity> getActivities() {
		log.info("Debut du parsing du fichier : " + UMLFile.getName() + ".");

		List<Activity> activities = new ArrayList<Activity>();
		if (UMLFile != null) {

			URI uri = URI.createFileURI(UMLFile.getRawLocationURI().getPath());
			ResourceSet resourceSet = new ResourceSetImpl();
			Resource resource = (Resource) resourceSet.getResource(uri, true);

			TreeIterator<EObject> tree = resource.getAllContents();
			while (tree.hasNext()) {
				EObject eo = tree.next();
				if (eo instanceof Activity) {
					log.debug("Une activité est trouvée.");
					activities.add((Activity) eo);
				}
			}
		}
		log.info("Bilan du parsing : " + activities.size() + " activité(s) trouvée(s).");
		return activities;
	}
}
