package com.upmc.pstl2013.views;

import java.io.File;
import java.io.FileNotFoundException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.upmc.pstl2013.alloyExecutor.IAlloyExecutor;
import com.upmc.pstl2013.alloyGenerator.IAlloyGenerator;
import com.upmc.pstl2013.factory.Factory;
import com.upmc.pstl2013.infoGenerator.IInfoGenerator;
import com.upmc.pstl2013.infoParser.IInfoParser;
import com.upmc.pstl2013.properties.impl.AbstractProperties;
import com.upmc.pstl2013.umlParser.IUMLParser;
import com.upmc.pstl2013.views.events.EventChooseDir;
import com.upmc.pstl2013.views.events.EventChooseFile;
import com.upmc.pstl2013.views.events.EventCurrentExecutor;
import com.upmc.pstl2013.views.events.EventPersonalExecutor;
import com.upmc.pstl2013.views.events.EventReadLogs;
import com.upmc.pstl2013.views.events.EventSelectPropertie;

public class SwtView extends Composite {

	private Button btnChooseDir;
	private Text txtDirectory;

	private Text txtLogs;
	private Button btnChooserFile;
	private Button btnExcuterAlloy;
	private Button btnReadLogs;

	private IInfoParser infoParser;
	private IInfoGenerator infoGenerator;
	private IAlloyExecutor alloyExecutor;

	private String separator = File.separator;
	private final String userDir = System.getProperty("user.home") + separator + ".pstl2013" + separator;
	//private static Logger log = Logger.getLogger(SwtView.class);

	private TabFolder tabFolder;
	private TabItem itemAlloyUse,itemAlloyProperty;
	private Composite cpItemAlloyUse,cpdItemAlloyProp ;
	private Table tabProperties;
	private Table tabValueProperties;
	private final TableEditor editor;
	private Text txtPersonalPropertie;
	private Button btnPersonalPropertie;


	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public SwtView(Composite parent, int style) {
		super(parent, style);

		infoParser = Factory.getInstance().newInfoParser();
		infoGenerator = Factory.getInstance().newInfoGenerator();
		IUMLParser parser = Factory.getInstance().newParser(infoParser);
		IAlloyGenerator alloyGenerator = Factory.getInstance().newAlloyGenerator(infoGenerator, parser);
		alloyExecutor = Factory.getInstance().newAlloyExecutor(alloyGenerator);

		infoGenerator.setDestinationDirectory(userDir);

		//TabFolder
		tabFolder = new TabFolder(this, SWT.NONE);
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));


		itemAlloyUse = new TabItem(tabFolder, SWT.NONE);
		itemAlloyUse.setText("Utilisation");
		cpItemAlloyUse = new Composite(tabFolder, SWT.BORDER);
		cpItemAlloyUse.setLayout(new GridLayout(2, false));
		itemAlloyUse.setControl(cpItemAlloyUse);

		itemAlloyProperty = new TabItem(tabFolder, SWT.NONE);
		itemAlloyProperty.setText("Properties");
		cpdItemAlloyProp= new Composite(tabFolder, SWT.BORDER);
		cpdItemAlloyProp.setLayout(new GridLayout(2, false));
		itemAlloyProperty.setControl(cpdItemAlloyProp);

		tabProperties = new Table(cpdItemAlloyProp, SWT.BORDER | SWT.CHECK | SWT.FULL_SELECTION | SWT.MULTI );
		tabProperties.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tabProperties.setLinesVisible(true);
		tabProperties.setHeaderVisible(true);

		tabValueProperties = new Table(cpdItemAlloyProp,  SWT.MULTI |SWT.BORDER | SWT.FULL_SELECTION);
		tabValueProperties.setTouchEnabled(true);
		tabValueProperties.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tabValueProperties.setHeaderVisible(true);
		tabValueProperties.setLinesVisible(true);
		editor = new TableEditor(tabValueProperties);

		String[] titlesVP = { "Key", "Value" };
		for (int i = 0; i < titlesVP.length; i++) {
			TableColumn column = new TableColumn(tabValueProperties, SWT.NONE);
			column.setText(titlesVP[i]);
		}
		
		addProperties();

		GridLayout gridLayout = new GridLayout(1, false);
		setLayout(gridLayout);

		btnChooseDir = new Button(cpItemAlloyUse, SWT.NONE);
		btnChooseDir.addMouseListener(new EventChooseDir(txtDirectory, infoGenerator));
		btnChooseDir.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		btnChooseDir.setText("Choose Dir");

		txtDirectory = new Text(cpItemAlloyUse, SWT.BORDER);
		txtDirectory.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtDirectory.setText(userDir);

		btnChooserFile = new Button(cpItemAlloyUse, SWT.NONE);
		btnChooserFile.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		btnChooserFile.addMouseListener(new EventChooseFile(infoParser));
		btnChooserFile.setText("Choose File");

		txtLogs = new Text(cpItemAlloyUse, SWT.BORDER | SWT.READ_ONLY | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		txtLogs.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 5));

		btnExcuterAlloy = new Button(cpItemAlloyUse, SWT.NONE);
		btnExcuterAlloy.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false,
				false, 1, 1));
		btnExcuterAlloy.setText("Execute Alloy");

		btnReadLogs = new Button(cpItemAlloyUse, SWT.NONE);
		btnReadLogs.addMouseListener(new EventReadLogs(txtDirectory));
		btnReadLogs.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		btnReadLogs.setText("Read logs");
		new Label(cpItemAlloyUse, SWT.NONE);
		new Label(cpItemAlloyUse, SWT.NONE);
		
		btnPersonalPropertie = new Button(cpItemAlloyUse, SWT.NONE);
		btnPersonalPropertie.addMouseListener(new EventPersonalExecutor(txtLogs, txtDirectory, alloyExecutor, infoGenerator, txtPersonalPropertie));
		btnPersonalPropertie.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		btnPersonalPropertie.setText("Exec Perso");
		
		txtPersonalPropertie = new Text(cpItemAlloyUse, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
		GridData gd_txtPersonalPropertie = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtPersonalPropertie.heightHint = 65;
		txtPersonalPropertie.setLayoutData(gd_txtPersonalPropertie);
		
		btnExcuterAlloy.addMouseListener(new EventCurrentExecutor(txtLogs, txtDirectory, alloyExecutor, infoGenerator, tabProperties));

		// on finit par une petite vérification...
		this.checkDirectory(alloyGenerator);
	}

	/**
	 * Vérifie que le dossiers de destinations des générations est correcte.
	 * @param alloyGenerator {@link IAlloyGenerator}.
	 */
	private void checkDirectory(IAlloyGenerator alloyGenerator) {

		// on vérifie que le dossier de génération alloy est correct
		try {
			alloyGenerator.fichiersPresents();
			txtLogs.setText("Prêt pour la vérification de process.");
		} catch (FileNotFoundException e2) {
			MessageDialog dialog = new MessageDialog(new Shell(), 
					"Des fichiers sont manquants", null, 
					e2.toString(), 
					MessageDialog.WARNING, new String[]{"Ok"}, 1);
			dialog.open();
			txtLogs.setText(e2.getMessage());
		}
	}

	/***
	 * Affiche toutes les proprietes dans la view.
	 */
	private void addProperties()
	{
		TableColumn column = new TableColumn(tabProperties, SWT.NONE);
		column.setText( "Properties");

		for (String prop : AbstractProperties.getProperties()) {
			TableItem item = new TableItem(tabProperties, SWT.NONE);
			item.setText(0, prop);
		}

		tabProperties.getColumn(0).pack ();
		tabProperties.addListener(SWT.Selection, new EventSelectPropertie(editor, tabValueProperties, tabProperties));
	}

}
