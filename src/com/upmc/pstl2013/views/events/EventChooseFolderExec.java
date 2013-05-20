package com.upmc.pstl2013.views.events;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.ui.dialogs.WorkspaceResourceDialog;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.uml2.uml.Activity;
import com.upmc.pstl2013.umlParser.IUMLParser;
import com.upmc.pstl2013.umlParser.ParserFactory;
import com.upmc.pstl2013.umlParser.impl.ParserException;
import com.upmc.pstl2013.views.SwtView;

public class EventChooseFolderExec extends MouseAdapter{

	private Logger log = Logger.getLogger(EventChooseFolderExec.class);
	private SwtView swtView;
	private List<Activity> activitiesSelected;
	private StringBuilder sb;

	/**
	 * Évènement permettant de sélectionner un dossier (les fichiers contenu dedans) à exécuter.
	 * @param {{@link SwtView}
	 */
	public EventChooseFolderExec(SwtView swtView) {
		super();
		this.swtView = swtView;
	}

	@Override
	public void mouseDown(MouseEvent e) {

		// on créé un filtre pour les fichiers .uml
		List<ViewerFilter> filters = new ArrayList<ViewerFilter>();
		filters.add(new ViewerFilter() {
			@Override
			public boolean select(Viewer viewer, Object parentElement, Object element) {
				if (element instanceof IFile) 
					return ((IFile) element).getFileExtension().equals("uml");
				else 
					return true;
			}
		});

		IContainer[] container = WorkspaceResourceDialog.openFolderSelection(new Shell(), "Selectionnez le dossier ", null, true, null, filters);
		sb = new StringBuilder();
		try {
			if (container.length > 0)
				sb.append("Following files has been selected : ");
			else
				sb.append("No files has been selected.");

			activitiesSelected = swtView.getActivitiesSelected();
			activitiesSelected.clear();
			
			StringBuilder parsing = new StringBuilder();
			for (IContainer iContainer : container) {	
				parsing.append(addFolder(iContainer.members()));
			}

			sb.append("\n");
			sb.append(parsing.toString());
			
			log.info(sb.toString());
			swtView.getTxtLogs().append(sb.toString());

		} catch (CoreException e1) {
			log.error(e1.getMessage());
			swtView.getTxtLogs().append(sb.toString() + "\n" + e1.getMessage() + ".\n");
		}
		
		//Rend possible l'ajout de Property dynamiquement pour Buisiness
		//Si le nombre d'activity == 1
		swtView.setEnabledAddActivity(activitiesSelected.size()==1);
		//Clear des noeuds dynamique apres le changement de fichier.
		swtView.clearDynamicBuisiness();
	}

	private String addFolder(IResource[] resources) throws CoreException {
		StringBuilder parsing = new StringBuilder();
		for (IResource resource : resources) {
			if (resource.getType() == IResource.FILE) {
				
				IFile iFile = (IFile) resource;
				if (!iFile.getFileExtension().contains("uml")) 
					continue;
				String name = iFile.getName();
				IUMLParser parser = ParserFactory.getInstance().newParser(iFile);
				try {
					parsing.append("Parsing de " + name + ". ");
					Activity activity = parser.getActivity();
					activity.setName(name);
					activitiesSelected.add(activity);
				} catch (ParserException e1) {
					parsing.append(e1.getMessage());
				} finally {
					parsing.append("\n");
				}

				sb.append(name);
				sb.append(" ");
			}
			if (resource.getType() == IResource.FOLDER) {
				IFolder iFolder = (IFolder)resource;
				parsing.append(addFolder(iFolder.members()));
			}
		}
		return parsing.toString();
	}

}
