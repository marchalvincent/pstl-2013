package com.upmc.pstl2013.viewsDialog;

import org.apache.log4j.Logger;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

import com.upmc.pstl2013.properties.dynamic.DynamicBusiness;
import com.upmc.pstl2013.views.SwtView;

public class EventClickSubmit extends MouseAdapter {

	private SwtView swtView;
	private DialogBuisiness dialog;
	private Logger log = Logger.getLogger(EventClickSubmit.class);

	/**
	 * Event du click sur le bouton submit : Met à jour la vue, ajoute les nouvelles properties et ferme la fenetre.
	 * @param {@link SwtView}
	 */
	public EventClickSubmit(SwtView swtView, DialogBuisiness dialog) {
		super();
		this.swtView = swtView;
		this.dialog = dialog;
	}

	@Override
	public void mouseDown(MouseEvent e) {

		DynamicBusiness buisiness;
		buisiness = dialog.getSelectedBuisiness();
		swtView.updateTreePropety(buisiness);
		if (!dialog.close())
			log.error("The dialog can't be closed.");
		
	}


}
