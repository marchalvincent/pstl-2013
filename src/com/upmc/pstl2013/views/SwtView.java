package com.upmc.pstl2013.views;

import java.io.FileNotFoundException;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.ui.dialogs.WorkspaceResourceDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.upmc.pstl2013.AlloyExecutor.IAlloyExecutor;
import com.upmc.pstl2013.alloyGenerator.IAlloyGenerator;
import com.upmc.pstl2013.factory.Factory;
import com.upmc.pstl2013.umlContainer.IUMLFileContainer;
import com.upmc.pstl2013.umlParser.IUMLParser;
import com.upmc.pstl2013.util.Console;

import edu.mit.csail.sdg.alloy4.Err;

public class SwtView extends Composite {

	private Text text;
	private Button btnChooserFile;
	private Button btnExcuterAlloy;

	private IUMLFileContainer fileContainer;
	private IAlloyExecutor alloyExecutor;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public SwtView(Composite parent, int style) {
		super(parent, style);

		fileContainer = Factory.getInstance().newFileContainer();
		IUMLParser parser = Factory.getInstance().newParser(fileContainer);
		IAlloyGenerator alloyGenerator = Factory.getInstance().newAlloyGenerator(parser);
		alloyExecutor = Factory.getInstance().newAlloyExecutor(alloyGenerator);

		setLayout(new GridLayout(2, false));

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

		text = new Text(this, SWT.BORDER | SWT.READ_ONLY | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		GridData gd_text = new GridData(SWT.FILL, SWT.TOP, true, true, 1, 5);
		gd_text.heightHint = 1000;
		text.setLayoutData(gd_text);

		// on vérifie que le dossier de génération alloy est correct
		try {
			alloyGenerator.fichiersPresents();
			text.setText("Prêt pour la vérification de process.");
		} catch (FileNotFoundException e2) {
			text.setText(e2.getMessage());
		}
		
				btnExcuterAlloy = new Button(this, SWT.NONE);
				btnExcuterAlloy.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false,
						false, 1, 1));
				btnExcuterAlloy.setText("Exécuter Alloy");
				btnExcuterAlloy.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseDown(MouseEvent e) {
						text.setText("Ce bouton génère les fichiers Alloy et lance l'éxecution.");
						try {
							alloyExecutor.executeFiles();
							Console.debug("Fin d'exécution des fichiers Alloy.", this.getClass());
						} catch (Err e1) {
							Console.warning(e1.toString(), this.getClass());
						}
					}
				});
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
	}
}
