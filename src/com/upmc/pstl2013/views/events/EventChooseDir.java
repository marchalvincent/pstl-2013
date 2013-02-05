package com.upmc.pstl2013.views.events;

import java.io.File;

import org.apache.log4j.Logger;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import com.upmc.pstl2013.infoGenerator.IInfoGenerator;
import com.upmc.pstl2013.util.ConfPropertiesManager;
import com.upmc.pstl2013.views.SwtView;

public class EventChooseDir extends MouseAdapter {

	private String separator = File.separator;
	private Text txtChooseDirectory;
	private Text txtLogs;
	private IInfoGenerator infoGenerator;
	private Logger log = Logger.getLogger(EventChooseDir.class);

	/**
	 * Constructor
	 * @param {{@link SwtView}
	 */
	public EventChooseDir(SwtView swtView) {

		this.txtChooseDirectory = swtView.getTxtDirectory();
		this.txtLogs = swtView.getTxtLogs();
		this.infoGenerator = swtView.getInfoGenerator();
		
	}

	@Override
	public void mouseDown(MouseEvent e) {

		DirectoryDialog directoryD = new DirectoryDialog(new Shell());
		String chemin = directoryD.open();
		if (chemin != null) {
			chemin += separator;
			// on met a jour l'IU et l'info générateur.
			txtChooseDirectory.setText(chemin);
			try {
				ConfPropertiesManager.getInstance().setPathFolder(chemin);
				log.info("Modification du répertoir : " + chemin);
				txtLogs.append("Modification du répertoir : " + chemin + "\n");
				infoGenerator.setDestinationDirectory(chemin);
			} catch (Exception e1) {
				txtLogs.append(e1.getMessage());
			}
			
		}
	}
}
