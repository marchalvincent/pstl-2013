package com.upmc.pstl2013.views.events;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import com.upmc.pstl2013.alloyExecutor.IAlloyExecutor;
import com.upmc.pstl2013.infoGenerator.IInfoGenerator;

public class EventCurrentExecutor extends AbstractEventExecutor {

	private Table tabProperties;

	public EventCurrentExecutor(Text txtLogs, Text textDirectory, IAlloyExecutor alloyExecutor,
			IInfoGenerator infoGenerator, Table tabProperties) {

		super(txtLogs, textDirectory, alloyExecutor, infoGenerator);
		this.tabProperties = tabProperties;
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
