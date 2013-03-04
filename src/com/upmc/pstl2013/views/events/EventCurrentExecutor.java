package com.upmc.pstl2013.views.events;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.upmc.pstl2013.factory.Factory;
import com.upmc.pstl2013.properties.IProperties;
import com.upmc.pstl2013.properties.impl.PropertiesException;
import com.upmc.pstl2013.views.SwtView;

public class EventCurrentExecutor extends AbstractEventExecutor {

	private Logger log = Logger.getLogger(EventCurrentExecutor.class);
	private Tree treeProperties;

	/**
	 * Constructor
	 * @param {@link SwtView}
	 */
	public EventCurrentExecutor(SwtView swtView) {
		super(swtView);
		this.treeProperties = swtView.getTreeProperties();
	}

	@Override
	protected List<IProperties> getProperties() {

		List<IProperties> properties = new ArrayList<IProperties>();
		for (TreeItem item : treeProperties.getItems()) {
			for (TreeItem prop : item.getItems()) {
				if (prop.getChecked()) {
					try {
						properties.add(Factory.getInstance().getProperty(prop.getText()));
					} catch (PropertiesException e) {
						log.error(e.getMessage());
					}
				}
			}
			
		}
		return properties;
	}
}
