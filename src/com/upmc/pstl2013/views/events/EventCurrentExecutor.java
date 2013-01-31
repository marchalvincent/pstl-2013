package com.upmc.pstl2013.views.events;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.upmc.pstl2013.views.SwtView;

public class EventCurrentExecutor extends AbstractEventExecutor {

	private Table tabProperties;

	/**
	 * Constructor
	 * @param {{@link SwtView}
	 */
	public EventCurrentExecutor(SwtView swtView) {

		super(swtView);
		this.tabProperties = swtView.getTabProperties();
	}

	@Override
	protected Map<String, Map<String, String>> getProperties() {

		Map<String, Map<String, String>> properties = new HashMap<String, Map<String, String>>();
		for (TableItem item : tabProperties.getItems()) {
			if (item.getChecked()) {
				// TODO Récupéréer les clés valeurs.
				properties.put(item.getText(), null);
			}
		}
		return properties;
	}
}
