package com.upmc.pstl2013.views.events;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.upmc.pstl2013.views.SwtView;

public class EventSelectPropertie implements Listener {

	private Table tabValueProperties;
	private Table tabProperties;
	private SwtView swtView;

	/**
	 * Constructor
	 * @param {{@link SwtView}
	 */
	public EventSelectPropertie(SwtView swtView) {

		this.swtView = swtView;
		this.tabValueProperties = swtView.getTabValueProperties();
		this.tabProperties = swtView.getTabProperties();
	}

	@Override
	public void handleEvent(Event event) {

		TableItem[] selection = tabProperties.getSelection();
		if (selection.length > 0) showValueProperties(selection[0].getText());
	}

	/***
	 * Affiche toutes les attributs de la propriété séléctionné.
	 */
	private void showValueProperties(String nameProperty) {

		if (tabValueProperties != null) {
			tabValueProperties.removeAll();
			tabValueProperties.addSelectionListener(new EventClickValueProperty(swtView));
			tabValueProperties.getColumn(0).setText("Attributes : " + nameProperty);
			for (int i = 0; i < 10; i++) {
				TableItem item = new TableItem(tabValueProperties, SWT.NONE);
				item.setText(0, nameProperty);
				item.setText(1, "y");
			}
			for (int i = 0; i < 2; i++) {
				tabValueProperties.getColumn(i).pack();
			}
		}
	}
}
