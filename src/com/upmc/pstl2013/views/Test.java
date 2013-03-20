package com.upmc.pstl2013.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;


public class Test extends Composite {
	private Text txtAllerLa;

	public Test(Composite parent, int style) {

		super(parent, style);
		setLayout(new GridLayout(2, false));
		
		Button btnAllerLa = new Button(this, SWT.NONE);
		btnAllerLa.setText("Aller la !");
		
		txtAllerLa = new Text(this, SWT.BORDER);
		txtAllerLa.setText("Aller la !");
		txtAllerLa.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	}
}
