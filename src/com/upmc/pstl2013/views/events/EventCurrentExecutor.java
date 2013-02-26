package com.upmc.pstl2013.views.events;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.upmc.pstl2013.factory.Factory;
import com.upmc.pstl2013.properties.IProperties;
import com.upmc.pstl2013.properties.impl.PropertiesException;
import com.upmc.pstl2013.views.SwtView;

public class EventCurrentExecutor extends AbstractEventExecutor {

	private Logger log = Logger.getLogger(EventCurrentExecutor.class);
	private Table tabProperties;

	/**
	 * Constructor
	 * @param {@link SwtView}
	 */
	public EventCurrentExecutor(SwtView swtView) {
		super(swtView);
		this.tabProperties = swtView.getTabProperties();
	}

	@Override
	protected List<IProperties> getProperties() {

		List<IProperties> properties = new ArrayList<IProperties>();
		for (TableItem item : tabProperties.getItems()) {
			if (item.getChecked()) {
				try {
					properties.add(Factory.getInstance().getProperty(item.getText()));
				} catch (PropertiesException e) {
					log.error(e.getMessage());
				}
			}
		}
		return properties;
	}
}
