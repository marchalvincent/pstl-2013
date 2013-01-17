package com.upmc.pstl2013.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.upmc.pstl2013.alloyGenerator.test.UMLFileChooserBouchon;

public class SwtView extends Composite {
	private Text text;
	private Button btnReset;
	private Button btnStart;
	private Button btnNewButton;

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
				new UMLFileChooserBouchon();
			}
		});
		btnReset.setText("Reset");
		
		text = new Text(this, SWT.BORDER | SWT.READ_ONLY | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		GridData gd_text = new GridData(SWT.FILL, SWT.TOP, true, true, 1, 3);
		gd_text.heightHint = 260;
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
		
		btnNewButton = new Button(this, SWT.NONE);
		btnNewButton.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		btnNewButton.setText("New Button");
		
		Display display = new Display();
		Shell shell = new Shell(display);
		FileDialog fileDialog = new FileDialog(shell, SWT.MULTI);

//        fileDialog.setFilterPath(fileFilterPath);
        
        fileDialog.setFilterExtensions(new String[]{"*.rtf", "*.html", "*.*"});
        fileDialog.setFilterNames(new String[]{ "Rich Text Format", "HTML Document", "Any"});
        
        String firstFile = fileDialog.open();

	}

}
