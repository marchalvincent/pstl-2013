package com.upmc.pstl2013.views.events;

import java.util.List;
import org.eclipse.swt.widgets.Table;
import com.upmc.pstl2013.properties.IProperties;
import com.upmc.pstl2013.views.SwtView;

public class EventCurrentExecutor extends AbstractEventExecutor {

	@SuppressWarnings("unused")
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
	protected List<IProperties> getProperties() {

//		List<IProperties> properties = new HashMap<String, Map<String, String>>();
//		for (TableItem item : tabProperties.getItems()) {
//			if (item.getChecked()) {
//				// TODO Récupéréer les clés valeurs.
//				properties.put(item.getText(), null);
//			}
//		}
		//TODO MICHEL
		return null;
	}
}
