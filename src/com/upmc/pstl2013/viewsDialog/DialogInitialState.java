package com.upmc.pstl2013.viewsDialog;

import java.util.HashMap;
import org.apache.log4j.Logger;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityNode;
import org.eclipse.uml2.uml.InitialNode;
import com.upmc.pstl2013.properties.PropertiesFactory;
import com.upmc.pstl2013.properties.impl.InitialState;
import com.upmc.pstl2013.views.SwtView;
import com.upmc.pstl2013.views.events.EventFactory;

public class DialogInitialState  extends ApplicationWindow {
	private Text txtDescription;
	private Composite composite;

	private SwtView swtView;
	private GridData currentLayout;
	private Table tableSetInitState,tableSetInitEdge;
	private TableEditor editorStringEdge,editorStringState;
	private Button btnAccepte;
	private Logger log = Logger.getLogger(DialogInitialState.class);

	public DialogInitialState(Shell parentShell, SwtView swtView) {
		super(parentShell);
		parentShell.setMinimumSize(300,300);
		this.swtView = swtView;
	}
	protected Control createContents(Composite parent) {

		//TODO : setDefaultImage(ResourceManager.getPluginImage("pstl-2013", "icons/properties.gif"));
		composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout(2, false));

		txtDescription = new Text(composite, SWT.BORDER);
		txtDescription.setEditable(false);
		txtDescription.setEnabled(false);
		txtDescription.setText("Change the initial state of the process with the number of tokens (nodes) and offers (edges).");
		GridData gd_txtDescription = new GridData(SWT.FILL, SWT.FILL, true, false, 2, 2);
		gd_txtDescription.minimumWidth = 400;
		gd_txtDescription.minimumHeight = 40;
		gd_txtDescription.heightHint =60;
		txtDescription.setLayoutData(gd_txtDescription);


		composite.setSize(300,300);


		//Table des attributs de la partie proprieteString
		tableSetInitState = new Table(composite, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		tableSetInitState.setTouchEnabled(true);
		tableSetInitState.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tableSetInitState.setHeaderVisible(true);
		tableSetInitState.setLinesVisible(true);

		editorStringState = new TableEditor(tableSetInitState);


		String[] titlesVP = { "States :", "Value" };
		for (int i = 0; i < titlesVP.length; i++) {
			TableColumn column = new TableColumn(tableSetInitState, SWT.NONE);
			column.setText(titlesVP[i]);
		}
		if (swtView.getActivitiesSelected().get(0).getNodes().size()>0) {

			tableSetInitState.addSelectionListener(EventFactory.getInstance().newEventClickValueAttributes(tableSetInitState, editorStringState, false));	
			for (ActivityNode activity : swtView.getActivitiesSelected().get(0).getNodes()) {
				TableItem item = new TableItem(tableSetInitState, SWT.NONE);
				item.setText(0, activity.getName());
				if (activity instanceof InitialNode)
					item.setText(1, "1");
				else 
					item.setText(1, "0");
			}
			for (int i = 0; i < 2; i++) {
				tableSetInitState.getColumn(i).pack();
			}
		}

		//Table des attributs de la partie proprieteString
		tableSetInitEdge = new Table(composite, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		tableSetInitEdge.setTouchEnabled(true);
		tableSetInitEdge.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tableSetInitEdge.setHeaderVisible(true);
		tableSetInitEdge.setLinesVisible(true);

		editorStringEdge = new TableEditor(tableSetInitEdge);
		
		btnAccepte = new Button(composite, SWT.NONE);
		btnAccepte.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, false, 2, 1));
		btnAccepte.setText("Submit");
		btnAccepte.addMouseListener(EventFactory.getInstance().newEventClickAccepteSetInitState(swtView, this));

		String[] titlesVPEdges = { "Edges :", "Value" };
		for (int i = 0; i < titlesVPEdges.length; i++) {
			TableColumn column = new TableColumn(tableSetInitEdge, SWT.NONE);
			column.setText(titlesVPEdges[i]);
		}
		if (swtView.getActivitiesSelected().get(0).getEdges().size()>0) {
			tableSetInitEdge.addSelectionListener(EventFactory.getInstance().newEventClickValueAttributes(tableSetInitEdge, editorStringEdge, false));		
			for (ActivityEdge activity : swtView.getActivitiesSelected().get(0).getEdges()) {
				TableItem item = new TableItem(tableSetInitEdge, SWT.NONE);
				item.setText(0, activity.getName());
				item.setText(1, "0");
			}
			for (int i = 0; i < 2; i++) {
				tableSetInitEdge.getColumn(i).pack();
			}
		}
		composite.redraw();
		composite.pack(true);
		currentLayout = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		currentLayout.heightHint = 22;
		return super.createContents(parent);
	}
	public InitialState getInitalStat() {
		HashMap<String, Integer> nodes = new HashMap<String, Integer>();
		for (TableItem item : tableSetInitState.getItems()) {
			try {
				nodes.put(item.getText(0), Integer.valueOf(item.getText(1)));
			} catch (NumberFormatException e) {
				final String message = "Warning, the integer for the token is not correct (" + item.getText(1) + "). Default value set is 0.\n";
				log.warn(message);
				swtView.getDataView().showToViewUse(message);
				nodes.put(item.getText(0), 0);
			}
		}
		
		HashMap<String, Integer> edges = new HashMap<String, Integer>();
		for (TableItem item : tableSetInitEdge.getItems()) {
			try {
				edges.put(item.getText(0), Integer.valueOf(item.getText(1)));
			} catch (NumberFormatException e) {
				final String message = "Warning, the integer for the offer is not correct (" + item.getText(1) + "). Default value set is 0.\n";
				log.warn(message);
				swtView.getDataView().showToViewUse(message);
				edges.put(item.getText(0), 0);
			}
		}
		
		return PropertiesFactory.getInstance().newEtatInitial(nodes, edges);
	}



}
