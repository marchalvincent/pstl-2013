package com.upmc.pstl2013.views.events;

import java.util.List;
import com.upmc.pstl2013.properties.IProperties;
import com.upmc.pstl2013.views.SwtView;

public class EventCurrentExecutor extends AbstractEventExecutor {

	private SwtView swtView;

	/**
	 * Évènement permettant l'exécution des fichiers selectionnées.
	 * @param {@link SwtView}
	 */
	public EventCurrentExecutor(SwtView swtView, boolean executed) {
		super(swtView, executed);
		this.swtView = swtView;
	}

	@Override
	protected List<IProperties> getProperties() {
		return swtView.getDataView().getCurrentProperties();
	}
}
