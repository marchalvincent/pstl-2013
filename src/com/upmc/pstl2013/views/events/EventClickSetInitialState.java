package com.upmc.pstl2013.views.events;

import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Shell;

import com.upmc.pstl2013.views.SwtView;
import com.upmc.pstl2013.viewsDialog.DialogInitialState;

public class EventClickSetInitialState extends MouseAdapter {

	private SwtView swtView;

	/**
	 *Évènement permettant d'afficher la fenetre de modification des etats initiaux et des edges.
	 * @param {@link SwtView}
	 */
	public EventClickSetInitialState(SwtView swtView) {
		super();
		this.swtView = swtView;
	}

	@Override
	public void mouseDown(MouseEvent e) {

		DialogInitialState window = new DialogInitialState(new Shell(),swtView);
		window.setBlockOnOpen(true);
		window.open();
	}

}
