package com.upmc.pstl2013.viewsDialog;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;
import com.upmc.pstl2013.properties.dynamic.EDynamicBusiness;

public class EventSelectType extends SelectionAdapter {

	private DialogBuisiness dialog;

	/**
	 * Evenement appelé pour la mise à jour du contenu du {@link DialogBuisiness} en fonction du {@link EDynamicBusiness}.
	 * @param {@link DialogBuisiness}
	 */
	public EventSelectType(DialogBuisiness dialog){
		this.dialog = dialog;
	}

	public void widgetSelected(SelectionEvent e) { 
		
		Combo cbo = (Combo)e.getSource();

		for (EDynamicBusiness enumeration : EDynamicBusiness.values()) {
			if (cbo.getText().equals(enumeration.toString())){
				dialog.changeDialog(enumeration);
				break;
			}
		}
	}


}
