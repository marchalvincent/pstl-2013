package com.upmc.pstl2013.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class SwtView extends Composite {
	private Text text;
	private Button btnReset;
	private Button btnStart;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public SwtView(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(2, false));
		
		btnReset = new Button(this, SWT.NONE);
		btnReset.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnReset.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				text.setText("reset");
			}
		});
		btnReset.setText("Reset");
		
		text = new Text(this, SWT.BORDER | SWT.READ_ONLY | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		GridData gd_text = new GridData(SWT.FILL, SWT.TOP, true, true, 1, 2);
		gd_text.heightHint = 1000;
		text.setLayoutData(gd_text);
		text.setText("Appuyez sur démarrer pour lancer la vérification Alloy.");
		
		btnStart = new Button(this, SWT.NONE);
		btnStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				text.setText("start");
			}
		});
		btnStart.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		btnStart.setText("Start");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

	}

}
