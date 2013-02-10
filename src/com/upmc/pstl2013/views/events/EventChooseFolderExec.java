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

import com.upmc.pstl2013.views.SwtView;

public class EventChooseFolderExec extends MouseAdapter{

	private Logger log = Logger.getLogger(EventChooseFolderExec.class);
	private SwtView swtView;
	private List<IFile> UMLFilesSelected;
	private StringBuilder sb;

	/**
	 * Constructor
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
		try {
			sb = new StringBuilder();
			if (container.length > 0)
				sb.append("Selection des fichiers suivant : ");
			else
				sb.append("Aucun fichier n'a été sélectionné.");

			UMLFilesSelected = swtView.getUMLFilesSelected();

			for (IContainer iContainer : container) {	
				addFolder(iContainer.members());
			}

			sb.append("\n");
			log.info(sb.toString());
			swtView.getTxtLogs().append(sb.toString());

		} catch (CoreException e1) {
			log.error(e1.getMessage());
		}
	}

	private void addFolder(IResource[] resources) throws CoreException
	{
		for (IResource resource : resources) {

			if (resource.getType() == IResource.FILE)
			{
				IFile iFile = (IFile)resource;
				UMLFilesSelected.add(iFile);
				sb.append(iFile.getName());
				sb.append(" ");
			}
			if (resource.getType() == IResource.FOLDER)
			{
				IFolder iFolder = (IFolder)resource;
				System.out.println("folder");
				addFolder(iFolder.members());
			}
		}

	}

}
