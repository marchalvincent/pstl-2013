package com.upmc.pstl2013.views.events;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.upmc.pstl2013.properties.IProperties;
import com.upmc.pstl2013.properties.PropertiesFactory;
import com.upmc.pstl2013.properties.impl.PropertiesException;
import com.upmc.pstl2013.views.ETreeType;
import com.upmc.pstl2013.views.SwtView;

public class EventCurrentExecutor extends AbstractEventExecutor {

	private Logger log = Logger.getLogger(EventCurrentExecutor.class);
	private Tree treeProperties;
	private SwtView swtView;

	/**
	 * Évènement permettant l'exécution des fichiers selectionnées.
	 * @param {@link SwtView}
	 */
	public EventCurrentExecutor(SwtView swtView) {
		super(swtView);
		this.treeProperties = swtView.getTreeProperties();
		this.swtView = swtView;
	}

	@Override
	protected List<IProperties> getProperties() {

		List<IProperties> properties = new ArrayList<IProperties>();
		for (TreeItem item : treeProperties.getItems()) {
			for (TreeItem prop : item.getItems()) {
				if (prop.getChecked()) {
					try {
						if (prop.getData() != null && prop.getData() == ETreeType.DYNAMIC_PROPERTY)
							properties.add(swtView.getListDynamicBuisiness(prop.getText()));
						else		
							properties.add(PropertiesFactory.getInstance().getProperty(prop.getText()));
					} catch (PropertiesException e) {
						log.error(e.getMessage());
					}
				}
			}
			
		}
		return properties;
	}
}
