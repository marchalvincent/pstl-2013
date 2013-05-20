package com.upmc.pstl2013.views;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TreeItem;
import com.upmc.pstl2013.alloyExecutor.IFileResult;
import com.upmc.pstl2013.properties.IProperties;
import com.upmc.pstl2013.properties.PropertiesFactory;
import com.upmc.pstl2013.properties.impl.PropertiesException;
import com.upmc.pstl2013.util.ConfPropertiesManager;
import com.upmc.pstl2013.views.events.RunnableUpdateDetails;
import com.upmc.pstl2013.views.events.RunnableUpdateExecutor;


public class DataView {

	private Logger log = Logger.getLogger(DataView.class);
	private SwtView swtView;

	public DataView(SwtView swtView) {
		this.swtView = swtView;
	}

	/**
	 * Met à jour les préférences des propriétés.
	 * @param la liste des {@link IProperties}.
	 */
	public void saveProperties(List<IProperties> properties) {
		
		// 1. On créé le nouveau string
		if (properties != null) {
			StringBuilder sb = new StringBuilder();
			for (IProperties prop : properties) {
				sb.append(prop.getName());
				sb.append("|");
			}
			try {
				ConfPropertiesManager.getInstance().setProperties(sb.toString());
			} catch (Exception e) {
				log.error(e.getMessage());
				showToViewUse(e.getMessage());
			}
		}
		// 2. On enregistre dans le fichier les conf
		try {
			ConfPropertiesManager.getInstance().store();
		} catch (IOException e) {
			showToViewUse(e.getMessage());
			log.error(e.getMessage());
		}
	}

	/**
	 * Met à jour les préférences des options.
	 * @param swtView la {@link SwtView} de l'IHM.
	 */
	public void saveOption(SwtView swtView) {

		// 1. On spécifie les préférence à la ConfPropertiesManager
		try {
			ConfPropertiesManager.getInstance().setTimeOut(String.valueOf(swtView.getTimeout()));
			ConfPropertiesManager.getInstance().setNbNodes(swtView.getNbNodesMax());
			ConfPropertiesManager.getInstance().setNbThreads(swtView.getNbThread());
			
		} catch (Exception e) {
			log.error(e.getMessage());
			showToViewUse(e.getMessage());
		}

		// 2. On enregistre dans le fichier les conf
		try {
			ConfPropertiesManager.getInstance().store();
		} catch (IOException e) {
			showToViewUse(e.getMessage());
			log.error(e.getMessage());
		}
	}

	/**
	 * Renvoie la liste des propriétés de l'interface graphique sélectionnées par l'utilisateur.
	 * @return une {@link List} de {@link IProperties}.
	 */
	public List<IProperties> getCurrentProperties() {
		List<IProperties> properties = new ArrayList<IProperties>();
		
		for (TreeItem item : swtView.getTreeProperties().getItems()) 
			recurentGetPropSelected(item, properties);
		
		return properties;
	}
	
	public void recurentGetPropSelected(TreeItem item, List<IProperties> properties) {
		
		if (item.getItems().length > 0){
			for (TreeItem itemp : item.getItems()) {
				recurentGetPropSelected(itemp, properties);
			}
		}
		else{
			if (item.getChecked()) {
				try {
					if (item.getData() != null && item.getData() == ETreeType.DYNAMIC_PROPERTY)
						properties.add(swtView.getListDynamicBuisiness(item.getText()));
					else		
						properties.add(PropertiesFactory.getInstance().getProperty(item.getText()));
				} catch (PropertiesException ex) {
					log.error(ex.getMessage());
				}
			}
		}
	}

	public void showToViewUse(String msg) {
		Display.getDefault().asyncExec(new RunnableUpdateExecutor(swtView, msg));
	}

	public void showToViewDetails(IFileResult iFileResult){
		Display.getDefault().asyncExec(new RunnableUpdateDetails(swtView, iFileResult));
	}
}
