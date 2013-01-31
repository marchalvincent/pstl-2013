/**
 * 
 */
package com.upmc.pstl2013.umlParser.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.uml2.uml.Activity;
import com.upmc.pstl2013.infoParser.IInfoParser;
import com.upmc.pstl2013.umlParser.IUMLParser;

/**
 * Cette classe se charge de parser un fichier UML.
 */
public class UMLParser implements IUMLParser {

	private IInfoParser fileContainer;
	private Map<String, List<Activity>> activities;
	private int nbFic, nbActivity;
	private static Logger log = Logger.getLogger(UMLParser.class);

	public UMLParser(IInfoParser fc) {

		super();
		fileContainer = fc;
		activities = new HashMap<String, List<Activity>>();
		nbFic = 0;
		nbActivity = 0;
	}

	@Override
	public Map<String, List<Activity>> getActivities() {

		log.info("Debut du parsing.");
		// pour chaque fichier on récupère la liste de ses activities
		List<IFile> files = fileContainer.getSelectedUMLFiles();
		for (IFile file : files) {
			List<Activity> act = this.getActivities(file);
			String name = file.getName();
			activities.put(name.substring(0, name.length() - 4), act);
		}
		log.info("Bilan du parsing : " + nbFic + " fichiers, " + nbActivity + " activités.");
		return activities;
	}

	@Override
	public void reset() {
		fileContainer.reset();
		activities.clear();
		nbFic = 0;
		nbActivity = 0;
	}
	
	/**
	 * Renvoie la liste des {@link Activity} pour un {@link IFile} donné.
	 * @param iFile le fichier a parcourir.
	 * @return une liste d'{@link Activity}.
	 */
	private List<Activity> getActivities(IFile iFile) {
		nbFic++;
		log.debug("Fichier n°" + nbFic + ".");
		List<Activity> list = new ArrayList<Activity>();
		if (iFile != null) {
			
			URI uri = URI.createFileURI(iFile.getRawLocationURI().getPath());
			ResourceSet resourceSet = new ResourceSetImpl();
			Resource resource = (Resource) resourceSet.getResource(uri, true);
			
			TreeIterator<EObject> tree = resource.getAllContents();
			while (tree.hasNext()) {
				EObject eo = tree.next();
				if (eo instanceof Activity) {
					nbActivity++;
					log.debug("Une activité est trouvée.");
					list.add((Activity) eo);
				}
			}
		}
		return list;
	}
}
