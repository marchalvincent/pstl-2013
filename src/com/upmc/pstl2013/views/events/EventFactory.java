package com.upmc.pstl2013.views.events;

import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Listener;

import com.upmc.pstl2013.views.SwtView;
import com.upmc.pstl2013.viewsDialog.DialogBuisiness;
import com.upmc.pstl2013.viewsDialog.EventClickSubmit;
import com.upmc.pstl2013.viewsDialog.EventSelectType;


/**
 * 
 * La factory des Events.
 */
public class EventFactory {
	
	private static final EventFactory instance = new EventFactory();

	private EventFactory() {}

	/**
	 * Renvoie l'unique instance de la Factory.
	 * 
	 * @return {@link EventFactory}.
	 */
	public static EventFactory getInstance() {
		return instance;
	}
	
	/**
	 * Créé un {@link MyJobPoolExecutor}.
	 * @param nbMaxThread le nombre maximum de thread à lancer en même temps.
	 */
	public MyJobPoolExecutor newJobPoolExecutor(int nbMaxThread) {
		return new MyJobPoolExecutor(nbMaxThread);
	}
	
	
	public EventCheckAttributes newEventCheckAttributes(){
		return new EventCheckAttributes();
	}
	
	public EventChooseDir newEventChooseDir(SwtView swtView){
		return new EventChooseDir(swtView);
	}
	
	public EventChooseFile newEventChooseFile(SwtView swtView){
		return new EventChooseFile(swtView);
	}

	public MouseListener newEventCurrentExecutor(SwtView swtView) {
		return new EventCurrentExecutor(swtView);
	}

	public MouseListener newEventReadLogs(SwtView swtView, String namelogerror) {
		return new EventReadLogs(swtView, namelogerror) ;
	}

	public MouseListener newEventPersonalExecutor(SwtView swtView) {
		return new EventPersonalExecutor(swtView);
	}

	public MouseListener newEventChooseFolderExec(SwtView swtView) {
		return new EventChooseFolderExec(swtView);
	}

	public MouseListener newEventClickVisualisationAlloy(SwtView swtView) {
		return new EventClickVisualisationAlloy(swtView);
	}

	public Listener newEventSelectTreeProperty(SwtView swtView) {
		return new EventSelectTreeProperty(swtView);
	}

	public Listener newEventSelectTreeItemDetail(SwtView swtView) {
		return new EventSelectTreeItemDetail(swtView);
	}

	public MouseListener newEventClickAddBuisiness(SwtView swtView) {
		return new EventClickAddBuisiness(swtView);
	}

	public SelectionListener newEventClickValueAttributes(SwtView swtView) {
		return new EventClickValueAttributes(swtView);
	}

	public SelectionListener newEventSelectType(DialogBuisiness dialogBuisiness) {
		return new EventSelectType(dialogBuisiness);
	}

	public MouseListener newEventClickSubmit(SwtView swtView,
			DialogBuisiness dialogBuisiness) {
		return new EventClickSubmit(swtView, dialogBuisiness);
	}
	
	
	
	
}