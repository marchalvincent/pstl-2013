package com.upmc.pstl2013.views.events;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
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

public class EventChooseFile extends MouseAdapter {

	private Logger log = Logger.getLogger(EventChooseFile.class);
	private SwtView swtView;
	
	/**
	 * Constructor
	 * @param {{@link SwtView}
	 */
	public EventChooseFile(SwtView swtView) {
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
		IFile file[] = WorkspaceResourceDialog.openFileSelection(new Shell(), "Selectionnez les fichiers UML", null, true, null, filters);
		
		StringBuilder sb = new StringBuilder();
		if (file.length > 0)
			sb.append("Selection des fichiers suivant : ");
		else
			sb.append("Aucun fichier n'a été sélectionné.");
		
		List<Activity> activitiesSelected = swtView.getActivitiesSelected();
		activitiesSelected.clear();
		
		// pour chaque process UML, on parse l'activité à la volée.
		StringBuilder parsing = new StringBuilder();
		for (IFile iFile : file) {
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
			
			//Rend possible l'ajout de Property dynamiquement pour Buisiness
			//Si le nombre d'activity == 1
			swtView.setEnabledAddActivity(activitiesSelected.size()==1);

			//Clear des noeuds dynamique apres le changement de fichier.
			swtView.clearDynamicBuisiness();
		}
		sb.append("\n");
		sb.append(parsing.toString());
		log.info(sb.toString());
		swtView.getTxtLogs().append(sb.toString());
	}
}
