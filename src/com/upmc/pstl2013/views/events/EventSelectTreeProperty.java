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
import com.upmc.pstl2013.properties.dynamic.DynamicBusiness;
import com.upmc.pstl2013.properties.impl.PropertiesException;
import com.upmc.pstl2013.views.ETreeType;
import com.upmc.pstl2013.views.SwtView;

public class EventSelectTreeProperty implements Listener {

	private Table tabValuePropertiesString,tabValuePropertiesBool;
	private SwtView swtView;
	private Logger log = Logger.getLogger(EventSelectTreeProperty.class);

	/**
	 * Sélectionne les properties et/ou affiche leurs attributs.
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
				checkerCheckBox(currentItem);
			}
		}
		//Selection d'une property
		else if (currentItem.getData()!=null && currentItem != null ){

			//Si c'est la selection d'un dynamique property on affiche ses infos
			if (currentItem.getData().equals(ETreeType.DYNAMIC_PROPERTY)) {
				showValueProperties(currentItem.getText());
			}
			else {
				if (currentItem.getText().equals("EnoughState") || currentItem.getText().equals("Wf"))
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

	private void checkerCheckBox(TreeItem currentItem){

		for (TreeItem item : currentItem.getItems())
		{
			if (item.getText().equals("EnoughState"))
				item.setChecked(true);
			else if(currentItem.getData() instanceof Boolean)
				item.setChecked(currentItem.getChecked() || (!(Boolean)currentItem.getData()));
			else
				item.setChecked(currentItem.getChecked());
			
			checkerCheckBox(item);
		}
	}

	/***
	 * Affiche toutes les attributs de la propriété séléctionné.
	 */
	private void showValueProperties(String nameProperty) {

		try {
			//TODO améliorer faire passer dans la factory, voir comment le faire de façon optimal
			//affiche les infos des prop dynamique
			if (null != swtView.getListDynamicBuisiness(nameProperty)){
				DynamicBusiness dynBusiness = swtView.getListDynamicBuisiness(nameProperty);
				if (tabValuePropertiesString != null) {
					tabValuePropertiesString.removeAll();
					tabValuePropertiesString.getColumn(0).setText("Informations : " + dynBusiness.getName());		
					tabValuePropertiesString.getColumn(1).setText("");
					TableItem item = new TableItem(tabValuePropertiesString, SWT.NONE);
					StringBuilder sb = new StringBuilder();
					sb.append(dynBusiness.getEnumType() + " ");
					for (String param : dynBusiness.getDataParams()) {
						sb.append(param + " ");
					}
					item.setText(0,sb.toString());
					item.setText(1, "");
					//item.setData(nameProperty);

					for (int i = 0; i < 2; i++) {
						tabValuePropertiesString.getColumn(i).pack();
					}
					tabValuePropertiesBool.removeAll();
				}


			}
			else{
				IProperties property = PropertiesFactory.getInstance().getProperty(nameProperty);
				Map <String,String> stringAttributes = property.getStringAttributes();
				if (tabValuePropertiesString != null) {
					tabValuePropertiesString.removeAll();
					tabValuePropertiesString.getColumn(0).setText("Attributes : " + nameProperty);
					if (stringAttributes.size()>0) {
						tabValuePropertiesString.addSelectionListener(EventFactory.getInstance().newEventClickValueAttributes(swtView.getTabValuePropertiesString(), swtView.getEditorString(), true));		
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
					tabValuePropertiesBool.getColumn(0).setText("Attributes true/false : " + nameProperty);
					if (boolAttributes.size()>0) {
						tabValuePropertiesBool.addListener(SWT.Selection, EventFactory.getInstance().newEventCheckAttributes());
						for (String key : boolAttributes.keySet()) {
							TableItem item = new TableItem(tabValuePropertiesBool, SWT.BORDER | SWT.CHECK | SWT.FULL_SELECTION | SWT.MULTI);
							item.setText(0, key);
							item.setData(nameProperty);
							item.setChecked(boolAttributes.get(key));
						}
						tabValuePropertiesBool.getColumn(0).pack();
					}
				}
			}

		} catch (PropertiesException e) {
			log.error(e.getMessage());
			swtView.getTxtLogs().append(e.getMessage());
		}


	}
}
