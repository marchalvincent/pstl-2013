package com.upmc.pstl2013.viewsDialog;

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

	public DialogBuisiness(Shell parentShell) {
		super(parentShell);
	}

	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout(2, false));

		txtNom = new Text(composite, SWT.BORDER);
		txtNom.setText("Nom");
		txtNom.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

		Combo cboType = new Combo(composite, SWT.NONE);
		cboType.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

		txtDescription = new Text(composite, SWT.BORDER);
		txtDescription.setEditable(false);
		txtDescription.setEnabled(false);
		txtDescription.setText("Description");
		GridData gd_txtDescription = new GridData(SWT.FILL, SWT.FILL, true, false, 2, 2);
		gd_txtDescription.heightHint = 40;
		txtDescription.setLayoutData(gd_txtDescription);


		btnSubmit = new Button(composite, SWT.NONE);
		btnSubmit.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 2, 1));
		btnSubmit.setText("Submit");

		fillCboType();

		return super.createContents(parent);
	}

	private void fillCboType() {
		

	}

}
