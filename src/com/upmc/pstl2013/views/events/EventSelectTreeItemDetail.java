package com.upmc.pstl2013.views.events;


import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import com.upmc.pstl2013.alloyExecutor.IActivityResult;
import com.upmc.pstl2013.views.SwtView;

public class EventSelectTreeItemDetail implements Listener {

	private Text txtDetailsLogs;
	private Button btnVisualisationAlloy;
	private SwtView swtView;


	/**
	 * Affiche le resultat des l'activité séléctionné.
	 * @param {@link SwtView} la vue courrante
	 */
	public EventSelectTreeItemDetail(SwtView swtView) {
		this.txtDetailsLogs = swtView.getTxtDetailsLogs();
		this.btnVisualisationAlloy = swtView.getBtnVisualisationAlloy();
		this.swtView = swtView;
	}
	
	@Override
	public void handleEvent(Event event) {
		if (event.item.getData() != null) {
			IActivityResult activityResult = (IActivityResult)event.item.getData();
			txtDetailsLogs.setText(activityResult.getLogResult());
			if (activityResult.isSatisfiable()) {
				btnVisualisationAlloy.setEnabled(true);
				swtView.setCurrentActivityeResult(activityResult);
			}
			else {
				btnVisualisationAlloy.setEnabled(false);
			}
			
		}
	}

}
