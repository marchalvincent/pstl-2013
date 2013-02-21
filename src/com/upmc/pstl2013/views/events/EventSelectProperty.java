package com.upmc.pstl2013.views.events;

import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.upmc.pstl2013.factory.Factory;
import com.upmc.pstl2013.properties.IProperties;
import com.upmc.pstl2013.properties.impl.PropertiesException;
import com.upmc.pstl2013.views.SwtView;

public class EventSelectProperty implements Listener {

	private Table tabValuePropertiesString,tabValuePropertiesBool;
	private Table tabProperties;
	private SwtView swtView;
	private Logger log = Logger.getLogger(EventSelectProperty.class);

	/**
	 * Constructor
	 * @param {@link SwtView}
	 */
	public EventSelectProperty(SwtView swtView) {
		this.swtView = swtView;
		this.tabValuePropertiesString = swtView.getTabValuePropertiesString();
		this.tabValuePropertiesBool = swtView.getTabValuePropertiesBool();
		this.tabProperties = swtView.getTabProperties();
	}

	@Override
	public void handleEvent(Event event) {

		TableItem currentItem =(TableItem) event.item;
		if (currentItem != null) {
			tabProperties.setSelection(currentItem);
			if (currentItem.getText().equals("EnoughState"))
				currentItem.setChecked(true);
			showValueProperties(currentItem.getText());
		}
		
	}

	/***
	 * Affiche toutes les attributs de la propriété séléctionné.
	 */
	private void showValueProperties(String nameProperty) {

		try {
			IProperties property = Factory.getInstance().getPropertie(nameProperty);
			Map <String,String> stringAttributes = property.getStringAttributes();
			if (tabValuePropertiesString != null) {
				tabValuePropertiesString.removeAll();
				tabValuePropertiesString.getColumn(0).setText("Attributes : " + nameProperty);
				if (stringAttributes.size()>0) {
					tabValuePropertiesString.addSelectionListener(new EventClickValueAttributes(swtView));		
					for (String key : stringAttributes.keySet()) {
						TableItem item = new TableItem(tabValuePropertiesString, SWT.NONE);
						item.setText(0, key);
						item.setText(1, stringAttributes.get(key));
						item.setData(nameProperty);
					}
					for (int i = 0; i < 2; i++) {
						tabValuePropertiesString.getColumn(i).pack();
					}
				}
			}

			Map <String,Boolean> boolAttributes = property.getBooleanAttributes();
			if (tabValuePropertiesBool != null) {
				tabValuePropertiesBool.removeAll();
				tabValuePropertiesBool.getColumn(0).setText("Attributes Vrai/Faux : " + nameProperty);
				if (boolAttributes.size()>0) {
					tabValuePropertiesBool.addListener(SWT.Selection, new EventCheckAttributes());
					for (String key : boolAttributes.keySet()) {
						TableItem item = new TableItem(tabValuePropertiesBool, SWT.BORDER | SWT.CHECK | SWT.FULL_SELECTION | SWT.MULTI);
						item.setText(0, key);
						item.setData(nameProperty);
						item.setChecked(boolAttributes.get(key));
					}
					tabValuePropertiesBool.getColumn(0).pack();
				}
			}


		} catch (PropertiesException e) {
			log.error(e.getMessage());
			swtView.getTxtLogs().append(e.getMessage());
		}


	}
}
