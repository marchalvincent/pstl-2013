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
		
		List<IFile> UMLFilesSelected = swtView.getUMLFilesSelected();
		for (IFile iFile : file) {
			UMLFilesSelected.add(iFile);
			sb.append(iFile.getName());
			sb.append(" ");
		}
		sb.append("\n");
		log.info(sb.toString());
		swtView.getTxtLogs().append(sb.toString());
	}
}
