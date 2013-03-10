package com.upmc.pstl2013.views.events;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TreeItem;

import com.upmc.pstl2013.properties.IProperties;
import com.upmc.pstl2013.properties.PropertiesFactory;
import com.upmc.pstl2013.properties.impl.PropertiesException;
import com.upmc.pstl2013.views.ETreeType;
import com.upmc.pstl2013.views.SwtView;
import com.upmc.pstl2013.viewsDialog.DialogBuisiness;

public class EventClickAddBuisiness extends MouseAdapter {

	private SwtView swtView;
	private Logger log = Logger.getLogger(EventClickAddBuisiness.class);

	/**
	 *Évènement permettant de d'ajouter dynamiquement une propriété à la famille BUISINESS
	 * @param {@link SwtView}
	 */
	public EventClickAddBuisiness(SwtView swtView) {
		super();
		this.swtView = swtView;
	}

	@Override
	public void mouseDown(MouseEvent e) {

		List<IProperties> properties = new ArrayList<IProperties>();
		for (TreeItem item : swtView.getTreeProperties().getItems()) {
			for (TreeItem prop : item.getItems()) {
				if (prop.getChecked()) {
					try {
						if (prop.getData() != null && prop.getData() == ETreeType.DYNAMIC_PROPERTY)
							properties.add(swtView.getListDynamicBuisiness(prop.getText()));
						else		
							properties.add(PropertiesFactory.getInstance().getProperty(prop.getText()));
					} catch (PropertiesException ex) {
						log.error(ex.getMessage());
					}
				}
			}
		}
		
		swtView.saveProperties(properties);
		DialogBuisiness window = new DialogBuisiness(new Shell(),swtView);
		window.setBlockOnOpen(true);
		window.open();
	}
}