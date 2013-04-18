package com.upmc.pstl2013.views.events;

import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;

import com.upmc.pstl2013.views.SwtView;
import com.upmc.pstl2013.viewsDialog.DialogBuisiness;
import com.upmc.pstl2013.viewsDialog.DialogInitialState;
import com.upmc.pstl2013.viewsDialog.EventClickAccepteSetInitState;
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
	
	public EventCheckAttributes newEventCheckAttributes(){
		return new EventCheckAttributes();
	}
	
	public EventChooseDir newEventChooseDir(SwtView swtView){
		return new EventChooseDir(swtView);
	}
	
	public EventChooseFile newEventChooseFile(SwtView swtView){
		return new EventChooseFile(swtView);
	}

	public MouseListener newEventCurrentExecutor(SwtView swtView, boolean executed) {
		return new EventCurrentExecutor(swtView, executed);
	}

	public MouseListener newEventReadLogs(String namelogerror) {
		return new EventReadLogs(namelogerror) ;
	}

	public MouseListener newEventPersonalExecutor(SwtView swtView, boolean executed) {
		return new EventPersonalExecutor(swtView, executed);
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

	public SelectionListener newEventClickValueAttributes(Table table, TableEditor editor, boolean isPropertyUse) {
		return new EventClickValueAttributes(table, editor, isPropertyUse);
	}

	public SelectionListener newEventSelectType(DialogBuisiness dialogBuisiness) {
		return new EventSelectType(dialogBuisiness);
	}

	public MouseListener newEventClickSubmit(SwtView swtView,
			DialogBuisiness dialogBuisiness) {
		return new EventClickSubmit(swtView, dialogBuisiness);
	}

	public MouseListener newEventCliclSetInitialState(SwtView swtView) {
		return new EventClickSetInitialState(swtView);
	}

	public MouseListener newEventClickAccepteSetInitState(SwtView swtView, DialogInitialState dialogInitialState) {
		return new EventClickAccepteSetInitState(swtView, dialogInitialState);
	}
	
}
