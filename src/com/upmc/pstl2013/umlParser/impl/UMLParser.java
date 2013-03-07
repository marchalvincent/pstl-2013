package com.upmc.pstl2013.umlParser.impl;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityNode;
import com.upmc.pstl2013.properties.dynamic.EDynamicBusiness;
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
					int maxNb = ConfPropertiesManager.getInstance().getNbNodes();
					if (activity.getNodes().size() > maxNb) {
						log.warn("Attention le fichier contient plus de noeud que le maximum spécifié.");
						return null;
					}
					break;
				}
			}
		}
		
		return this.cleanActivity(activity);
	}
	
	private Activity cleanActivity(Activity activity) {
		
		// on traite les "sans noms"
		this.cleanNodesWithoutName(activity.getNodes());
		this.cleanEdgesWithoutName(activity.getEdges());
		
		// on clean les noms incorrects
		this.cleanNodes(activity.getNodes());
		this.cleanEdges(activity.getEdges());
		
		return activity;
	}
	
	/**
	 * Ajoute un nom par défaut aux noeuds qui n'en n'ont pas.
	 * @param nodes la liste des {@link ActivityNode}.
	 */
	private void cleanNodesWithoutName(EList<ActivityNode> nodes) {
		int i = 0;
		for (ActivityNode activityNode : nodes) {
			if (activityNode.getName() == null) {
				activityNode.setName(activityNode.getClass().getSimpleName() + i);
				i++;
				System.out.println(activityNode.getName());
			}
		}
	}
	
	/**
	 * Ajoute un nom par défaut aux edges qui n'en n'ont pas.
	 * @param edges la liste des {@link ActivityEdge}.
	 */
	private void cleanEdgesWithoutName(EList<ActivityEdge> edges) {
		int i = 0;
		for (ActivityEdge activityEdge : edges) {
			if (activityEdge.getName() == null) {
				activityEdge.setName(activityEdge.getClass().getSimpleName() + i);
				i++;
				System.out.println(activityEdge.getName());
			}
		}
	}
	

	/**
	 * Nettoie les noms des noeuds par rapport à la syntax d'Alloy.
	 * 
	 * @param nodes la liste des noeuds à nettoyer.
	 */
	private void cleanNodes(EList<ActivityNode> nodes) {
		for (ActivityNode activityNode : nodes) {
			activityNode.setName(activityNode.getName().replace("-", ""));
		}
	}

	/**
	 * Nettoie les noms des arcs par rapport à la syntax d'Alloy.
	 * 
	 * @param egdes la liste des arcs à nettoyer.
	 */
	private void cleanEdges(EList<ActivityEdge> egdes) {
		for (ActivityEdge activityEdges : egdes) {
			activityEdges.setName(activityEdges.getName().replace("-", ""));
		}
	}
}
