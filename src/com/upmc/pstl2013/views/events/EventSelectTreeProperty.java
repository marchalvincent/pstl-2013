package com.upmc.pstl2013.views.events;

import java.util.Map;
import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.TreeItem;
import com.upmc.pstl2013.properties.IProperties;
import com.upmc.pstl2013.properties.PropertiesFactory;
import com.upmc.pstl2013.properties.impl.PropertiesException;
import com.upmc.pstl2013.views.ETreeType;
import com.upmc.pstl2013.views.SwtView;

public class EventSelectTreeProperty implements Listener {

	private Table tabValuePropertiesString,tabValuePropertiesBool;
	private SwtView swtView;
	private Logger log = Logger.getLogger(EventSelectTreeProperty.class);

	/**
	 * Constructor
	 * @param {@link SwtView}
	 */
	public EventSelectTreeProperty(SwtView swtView) {
		this.swtView = swtView;
		this.tabValuePropertiesString = swtView.getTabValuePropertiesString();
		this.tabValuePropertiesBool = swtView.getTabValuePropertiesBool();
	}

	@Override
	public void handleEvent(Event event) {

		TreeItem currentItem =(TreeItem) event.item;
		//Permet de ne rien faire si l'on clique sur une famille
		if((currentItem.getData()!=null && currentItem.getData().equals(ETreeType.FAMILY))) {
			if (event.detail == SWT.CHECK){
				for (TreeItem item : currentItem.getItems()) {
					item.setChecked(currentItem.getChecked() || (!(Boolean)item.getData()));
				}
			}
		}
		//Selection d'une property
		else if (currentItem.getData()!=null && !currentItem.getData().equals(ETreeType.DYNAMIC_PROPERTY)){
			if (currentItem != null) {
				if (currentItem.getText().equals("EnoughState"))
					currentItem.setChecked(true);
				showValueProperties(currentItem.getText());
			}
			//Si tous les elements sont selectionnés alors on selectionne la famille.
			boolean allChecked = true;
			for (TreeItem item : currentItem.getParentItem().getItems()) {
				if (!item.getChecked()){
					allChecked = false;
					break;
				}
			}
			currentItem.getParentItem().setChecked(allChecked);
		}
	}

	/***
	 * Affiche toutes les attributs de la propriété séléctionné.
	 */
	private void showValueProperties(String nameProperty) {

		try {
			IProperties property = PropertiesFactory.getInstance().getProperty(nameProperty);
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
