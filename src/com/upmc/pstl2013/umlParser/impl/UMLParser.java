package com.upmc.pstl2013.umlParser.impl;

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
import com.upmc.pstl2013.util.ConfPropertiesManager;

/**
 * Cette classe se charge de parser un fichier UML.
 */
public class UMLParser implements IUMLParser {

	private IFile UMLFile;
	private Activity activity;
	private Logger log = Logger.getLogger(UMLParser.class);

	public UMLParser(IFile file) {
		super();
		this.UMLFile = file;
		this.activity = null;
	}

	@Override
	public Activity getActivities() {
		if (activity != null) {
			return activity;
		}
		
		log.info("Debut du parsing du fichier : " + UMLFile.getName() + ".");

		if (UMLFile != null) {

			URI uri = URI.createFileURI(UMLFile.getRawLocationURI().getPath());
			ResourceSet resourceSet = new ResourceSetImpl();
			Resource resource = (Resource) resourceSet.getResource(uri, true);

			TreeIterator<EObject> tree = resource.getAllContents();
			while (tree.hasNext()) {
				EObject eo = tree.next();
				if (eo instanceof Activity) {
					log.debug("Une activité est trouvée.");
					activity = (Activity) eo;
					
					// On vérifie que notre fichier n'est pas trop grand...
					if (activity.getNodes().size() > ConfPropertiesManager.getInstance().getNbNodesMax()) {
						log.warn("Attention le fichier contient plus de noeud que le maximum spécifié.");
						return null;
					}
					break;
				}
			}
		}
		return activity;
	}
}
