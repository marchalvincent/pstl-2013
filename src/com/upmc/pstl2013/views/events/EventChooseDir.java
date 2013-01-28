package com.upmc.pstl2013.views.events;

import java.io.File;

import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.upmc.pstl2013.infoGenerator.IInfoGenerator;

public class EventChooseDir extends MouseAdapter
{
	private String separator = File.separator;
	private Text txtChooseDirectory;
	private IInfoGenerator infoGenerator;
	
	public EventChooseDir (Text txtChooseDirectory, IInfoGenerator infoGenerator)
	{
		this.txtChooseDirectory = txtChooseDirectory;
		this.infoGenerator = infoGenerator;
	}
	
	@Override
	public void mouseDown(MouseEvent e) 
	{
		DirectoryDialog directoryD = new DirectoryDialog(new Shell());
		String chemin = directoryD.open();
		if (chemin != null) 
		{
			chemin += separator;
			// on met a jour l'IU et l'info générateur.
			txtChooseDirectory.setText(chemin);
			infoGenerator.setDestinationDirectory(chemin);
		}
	}

}
