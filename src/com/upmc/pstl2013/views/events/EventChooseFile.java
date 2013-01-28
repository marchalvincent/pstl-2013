package com.upmc.pstl2013.views.events;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.ui.dialogs.WorkspaceResourceDialog;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Shell;

import com.upmc.pstl2013.infoParser.IInfoParser;

public class EventChooseFile extends MouseAdapter
{
	private IInfoParser infoParser;
	
	public EventChooseFile (IInfoParser infoParser)
	{
		this.infoParser = infoParser;
	}
	
	@Override
	public void mouseDown(MouseEvent e) {

		// on créé un filtre pour les fichiers .uml
		List<ViewerFilter> filters = new ArrayList<ViewerFilter>();
		filters.add(new ViewerFilter() {
			@Override
			public boolean select(Viewer viewer, Object parentElement, Object element) {
				if (element instanceof IFile)
					return ((IFile)element).getFileExtension().equals("uml");
				else return true;
			}
		});
		IFile file[] = WorkspaceResourceDialog.openFileSelection(new Shell(), "Selectionnez les fichiers UML", 
				null, true, null, filters);
		for (IFile iFile : file) {
			infoParser.addFile(iFile);
		}
	}

}
