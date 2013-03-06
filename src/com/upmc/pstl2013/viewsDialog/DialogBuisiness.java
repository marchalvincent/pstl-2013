package com.upmc.pstl2013.viewsDialog;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class DialogBuisiness extends ApplicationWindow {
	private Text txtNom;
	private Text txtDescription;
	private Button btnSubmit;
	private Combo cboType;
	private Composite composite;

	public DialogBuisiness(Shell parentShell) {
		super(parentShell);
	}

	protected Control createContents(Composite parent) {
		composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout(2, false));

		txtNom = new Text(composite, SWT.BORDER);
		txtNom.setText("Nom");
		txtNom.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

		cboType = new Combo(composite, SWT.NONE);
		cboType.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

		txtDescription = new Text(composite, SWT.BORDER);
		txtDescription.setEditable(false);
		txtDescription.setEnabled(false);
		txtDescription.setText("Description");
		GridData gd_txtDescription = new GridData(SWT.FILL, SWT.FILL, true, false, 2, 2);
		gd_txtDescription.heightHint = 40;
		txtDescription.setLayoutData(gd_txtDescription);

		fillCboType();

		return super.createContents(parent);
	}

	private void fillCboType() {

		int nbNodes = 7;
		String [] tab = {"A","B","C","D"};
		List<Combo> listCombo = new ArrayList<Combo>();
		String desc = "Voila la descriptionk";
		for (String type : tab) {
			cboType.add(type);
		}
		txtDescription.setText(desc);

		for (int i = 0; i < nbNodes; i++) {
			Combo cbo = new Combo(composite, SWT.NONE);
			addListToCombo(tab, cbo);
			cbo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
			listCombo.add(cbo);
		}
		
		btnSubmit = new Button(composite, SWT.NONE);
		btnSubmit.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 2, 1));
		btnSubmit.setText("Submit");
		

	}

	private void addListToCombo(String[] listNode, Combo cbo) {
		for (String node : listNode) {
			cbo.add(node);
		}
		
	}

}
