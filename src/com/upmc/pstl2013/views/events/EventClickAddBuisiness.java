package com.upmc.pstl2013.views.events;

import java.util.List;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Shell;
import com.upmc.pstl2013.properties.IProperties;
import com.upmc.pstl2013.views.SwtView;
import com.upmc.pstl2013.viewsDialog.DialogBuisiness;

public class EventClickAddBuisiness extends MouseAdapter {

	private SwtView swtView;

	/**
	 *Évènement permettant de d'ajouter dynamiquement une propriété à la famille BUISINESS
	 * @param {@link SwtView}
	 */
	public EventClickAddBuisiness(SwtView swtView) {
		super();
		this.swtView = swtView;
	}

	@Override
	public void mouseDown(MouseEvent e) {

		List<IProperties> properties = swtView.getDataView().getCurrentProperties();
		swtView.getDataView().saveProperties(properties);
		DialogBuisiness window = new DialogBuisiness(new Shell(),swtView);
		window.setBlockOnOpen(true);
		window.open();
	}
}