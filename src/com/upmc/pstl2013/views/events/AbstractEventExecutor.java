package com.upmc.pstl2013.views.events;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Display;
import com.upmc.pstl2013.factory.Factory;
import com.upmc.pstl2013.properties.IProperties;
import com.upmc.pstl2013.properties.impl.PropertiesException;
import com.upmc.pstl2013.util.ConfPropertiesManager;
import com.upmc.pstl2013.views.SwtView;

public abstract class AbstractEventExecutor extends MouseAdapter {

	private Logger log = Logger.getLogger(AbstractEventExecutor.class);
	private SwtView swtView;
	
	/**
	 * Constructor
	 * @param {@link SwtView} 
	 */
	public AbstractEventExecutor(SwtView swtView) {
		super();
		this.swtView = swtView;
	}

	@Override
	public void mouseDown(MouseEvent evt) {
		JobExecutor jobExec;
		List<JobExecutor> listJobsExec = new ArrayList<JobExecutor>();
		
		// 1. On récupère tous les fichiers UML
		List<IFile> UMLFileSelected = swtView.getUMLFilesSelected();

		// 2. On récupère toutes les propriétés seléctionnées
		List<IProperties> properties = null;
		IProperties TMPProperty = null;
		try {
			properties = this.getProperties();
			// 3a. Pour chaque fichier
			for (IFile iFile : UMLFileSelected) {
				// 3b. Pour chaque propriété
				if (properties != null) {
					for (IProperties property : properties) {
						//4. On créé une copie de la propriété pour des raisons de concurrence
						TMPProperty = property.clone();
						assert TMPProperty != property;
								
						// 5. On lance le job
						jobExec = Factory.getInstance().newJobExecutor("Execution Alloy en cours...", swtView, iFile, TMPProperty);
						jobExec.setUser(true);
						jobExec.schedule();
						listJobsExec.add(jobExec);
					}
				}
			}
//			ThreadTimeout threadTimeout = new ThreadTimeout(listJobsExec, swtView.getTimeout());
//			threadTimeout.start();
		} catch (PropertiesException e) {
			showToView(e.getMessage());
		}
		// 4. On enregistre dans les préférences les propriétés
		this.saveProperties(properties);
	}

	/**
	 * Méthode qui récupère la liste de IProperties à exécuter sur le(s) fichier(s) UML.
	 */
	protected abstract List<IProperties> getProperties() throws PropertiesException;
	
	/**
	 * Met à jour les préférences des propriétés.
	 * @param la liste des {@link IProperties}.
	 */
	private void saveProperties(List<IProperties> properties) {
		if (properties != null) {
			StringBuilder sb = new StringBuilder();
			for (IProperties prop : properties) {
				sb.append(prop.getClass().getSimpleName());
				sb.append("|");
			}
			try {
				ConfPropertiesManager.getInstance().setProperties(sb.toString());
			} catch (Exception e) {
				showToView(e.getMessage());
			}
		}
	}
	
	private void showToView(String msg){
		log.error(msg);
		Display.getDefault().asyncExec(new RunnableUpdateExecutor(swtView, msg));
	}
}
