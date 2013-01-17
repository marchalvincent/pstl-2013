package com.upmc.pstl2013.views;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.ui.dialogs.WorkspaceResourceDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.upmc.pstl2013.alloyGenerator.AlloyGenerator;
import com.upmc.pstl2013.alloyGenerator.interfaces.IAlloyGenerator;
import com.upmc.pstl2013.fileContainer.UMLFileContainer;
import com.upmc.pstl2013.fileContainer.interfaces.IUMLFileContainer;
import org.eclipse.swt.widgets.Label;

public class SwtView extends Composite {
	
	private Text text;
	private Button btnReset;
	private Button btnStart;
	private Button btnChooserFile;
	private Button btnGnrerAlloy;

	private IUMLFileContainer fileContainer;
	private IAlloyGenerator alloyGenerator;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public SwtView(Composite parent, int style) {
		super(parent, style);
		fileContainer = new UMLFileContainer();
		alloyGenerator = new AlloyGenerator();
		
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
		GridData gd_text = new GridData(SWT.FILL, SWT.TOP, true, true, 1, 4);
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
		
		btnChooserFile = new Button(this, SWT.NONE);
		GridData gd_btnChooserFile = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnChooserFile.widthHint = 87;
		btnChooserFile.setLayoutData(gd_btnChooserFile);
		btnChooserFile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				IFile file[] = WorkspaceResourceDialog.openFileSelection(new Shell(), "Select the file", null, false, null, null);
				for (IFile iFile : file) {
					fileContainer.addFile(iFile);
				}
			}
		});
		btnChooserFile.setText("Chooser File");
		
		btnGnrerAlloy = new Button(this, SWT.NONE);
		GridData gd_btnGnrerAlloy = new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1);
		gd_btnGnrerAlloy.widthHint = 87;
		btnGnrerAlloy.setLayoutData(gd_btnGnrerAlloy);
		btnGnrerAlloy.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				alloyGenerator.generateFile(fileContainer);
			}
		});
		btnGnrerAlloy.setText("Générer Alloy");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

	}
}
