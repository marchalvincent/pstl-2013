package com.upmc.pstl2013.views.events;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TableItem;

import com.upmc.pstl2013.factory.Factory;
import com.upmc.pstl2013.properties.IProperties;
import com.upmc.pstl2013.properties.impl.PropertiesException;

public class EventCheckAttributes implements Listener {

	private Logger log = Logger.getLogger(EventCheckAttributes.class);

	/**
	 * Constructor
	 */
	public EventCheckAttributes() {}

	@Override
	public void handleEvent(Event event) {

		try {
			if (event.detail == SWT.CHECK){
				
				TableItem tabItem = (TableItem)event.item;
				IProperties propertie = Factory.getInstance().getPropertie((String)event.item.getData());
				//Boolean value = propertie.getBoolean(tabItem.getText());
				propertie.put(tabItem.getText(), tabItem.getChecked());

			}

		} catch (PropertiesException e) {
			log.error(e.getMessage());
		}
	}


}
