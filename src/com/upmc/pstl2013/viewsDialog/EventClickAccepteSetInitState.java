package com.upmc.pstl2013.viewsDialog;

import org.apache.log4j.Logger;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

import com.upmc.pstl2013.properties.impl.InitialState;
import com.upmc.pstl2013.views.SwtView;

public class EventClickAccepteSetInitState extends MouseAdapter {

	private DialogInitialState dialog;
	private SwtView swtview;
	private Logger log = Logger.getLogger(EventClickSubmit.class);

	/**
	 * Event du click sur le bouton submit : Met à jour la vue, ajoute les nouvelles properties et ferme la fenetre.
	 * @param {@link SwtView}
	 */
	public EventClickAccepteSetInitState(SwtView swtview, DialogInitialState dialog) {
		super();
		this.dialog = dialog;
		this.swtview = swtview;
	}

	@Override
	public void mouseDown(MouseEvent e) {


		InitialState initState = dialog.getInitalStat();
		//Modification des property avec le nouvel EtatInitial
		swtview.setInitState(initState);
		if (!dialog.close())
			log.error("The dialog can't be closed.");

	}

}
