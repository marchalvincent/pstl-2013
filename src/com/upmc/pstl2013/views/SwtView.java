package com.upmc.pstl2013.views;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.upmc.pstl2013.alloyExecutor.IAlloyExecutor;
import com.upmc.pstl2013.alloyGenerator.IAlloyGenerator;
import com.upmc.pstl2013.factory.Factory;
import com.upmc.pstl2013.infoGenerator.IInfoGenerator;
import com.upmc.pstl2013.infoParser.IInfoParser;
import com.upmc.pstl2013.properties.impl.AbstractProperties;
import com.upmc.pstl2013.umlParser.IUMLParser;
import com.upmc.pstl2013.util.ConfPropertiesManager;
import com.upmc.pstl2013.views.events.EventChooseDir;
import com.upmc.pstl2013.views.events.EventChooseFile;
import com.upmc.pstl2013.views.events.EventCurrentExecutor;
import com.upmc.pstl2013.views.events.EventPersonalExecutor;
import com.upmc.pstl2013.views.events.EventReadLogs;
import com.upmc.pstl2013.views.events.EventSelectProperty;

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
	private final String userDir;
	private static Logger log = Logger.getLogger(SwtView.class);
	private TabFolder tabFolder;
	private TabItem itemAlloyUse, itemAlloyProperty;
	private Composite cpItemAlloyUse, cpdItemAlloyProp;
	private Table tabProperties;
	private Table tabValuePropertiesString,tabValuePropertiesBool;
	private final TableEditor editorString, editorBool;
	private Text txtPersonalPropertie;
	private Button btnPersonalPropertie;
	private Text txtTimeOut;
	private Tree treeExecResult;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public SwtView(Composite parent, int style) {

		super(parent, style);

		if (ConfPropertiesManager.getInstance().getPathFolder().equals("")) {
			userDir = System.getProperty("user.home") + separator + ".pstl2013" + separator;
			try {
				ConfPropertiesManager.getInstance().setPathFolder(userDir);
			} catch (Exception e) {
				txtLogs.append(e.getMessage());
			}
		}
		else {
			userDir = ConfPropertiesManager.getInstance().getPathFolder();
		}

		infoParser = Factory.getInstance().newInfoParser();
		infoGenerator = Factory.getInstance().newInfoGenerator();
		IUMLParser parser = Factory.getInstance().newParser(infoParser);
		IAlloyGenerator alloyGenerator = Factory.getInstance().newAlloyGenerator(infoGenerator, parser);
		alloyExecutor = Factory.getInstance().newAlloyExecutor(alloyGenerator,userDir);

		infoGenerator.setDestinationDirectory(userDir);

		// TabFolder
		tabFolder = new TabFolder(this, SWT.NONE);
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		//Items et composite pour la partie utilisation du tabeFolder
		itemAlloyUse = new TabItem(tabFolder, SWT.NONE);
		itemAlloyUse.setText("Utilisation");
		cpItemAlloyUse = new Composite(tabFolder, SWT.BORDER);
		cpItemAlloyUse.setLayout(new GridLayout(3, false));
		itemAlloyUse.setControl(cpItemAlloyUse);
		//Items et composite pour la partie propriete du tabeFolder
		itemAlloyProperty = new TabItem(tabFolder, SWT.NONE);
		itemAlloyProperty.setText("Properties");
		cpdItemAlloyProp = new Composite(tabFolder, SWT.BORDER);
		cpdItemAlloyProp.setLayout(new GridLayout(3, false));
		itemAlloyProperty.setControl(cpdItemAlloyProp);

		/*
		 * Debut contenu de la parite property 
		 */
		//Table des proprietes 
		tabProperties = new Table(cpdItemAlloyProp, SWT.BORDER | SWT.CHECK | SWT.FULL_SELECTION | SWT.MULTI);
		tabProperties.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tabProperties.setLinesVisible(true);
		tabProperties.setHeaderVisible(true);
		//Table des attributs de la partie proprieteString
		tabValuePropertiesString = new Table(cpdItemAlloyProp, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		tabValuePropertiesString.setTouchEnabled(true);
		tabValuePropertiesString.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tabValuePropertiesString.setHeaderVisible(true);
		tabValuePropertiesString.setLinesVisible(true);

		editorString = new TableEditor(tabValuePropertiesString);
		String[] titlesVP = { "Key", "Value" };
		for (int i = 0; i < titlesVP.length; i++) {
			TableColumn column = new TableColumn(tabValuePropertiesString, SWT.NONE);
			column.setText(titlesVP[i]);
		}

		//Table des attributs de la partie proprieteBoolean
		tabValuePropertiesBool = new Table(cpdItemAlloyProp,  SWT.BORDER | SWT.CHECK | SWT.FULL_SELECTION | SWT.MULTI);
		tabValuePropertiesBool.setTouchEnabled(true);
		tabValuePropertiesBool.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tabValuePropertiesBool.setHeaderVisible(true);
		tabValuePropertiesBool.setLinesVisible(true);
		editorBool = new TableEditor(tabValuePropertiesBool);
		TableColumn column = new TableColumn(tabValuePropertiesBool, SWT.NONE);
		column.setText("Key");

		addProperties();
		GridLayout gridLayout = new GridLayout(1, false);
		setLayout(gridLayout);
		/*
		 * Debut contenu de la parite utilisation
		 */
		btnChooseDir = new Button(cpItemAlloyUse, SWT.NONE);
		btnChooseDir.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		btnChooseDir.setText("Choose Dir");

		txtDirectory = new Text(cpItemAlloyUse, SWT.BORDER);
		txtDirectory.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtDirectory.setText(userDir);

		treeExecResult = new Tree(cpItemAlloyUse, SWT.BORDER);
		treeExecResult.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 5));

		btnChooserFile = new Button(cpItemAlloyUse, SWT.NONE);
		btnChooserFile.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		btnChooserFile.setText("Choose File");
		btnChooserFile.addMouseListener(new EventChooseFile(this));

		txtLogs = new Text(cpItemAlloyUse, SWT.BORDER | SWT.READ_ONLY | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		txtLogs.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 4));

		btnExcuterAlloy = new Button(cpItemAlloyUse, SWT.NONE);
		btnExcuterAlloy.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		btnExcuterAlloy.setText("Execute Alloy");

		//Ajout des events
		btnExcuterAlloy.addMouseListener(new EventCurrentExecutor(this));

		txtTimeOut = new Text(cpItemAlloyUse, SWT.BORDER);
		txtTimeOut.setText("Time Out");
		txtTimeOut.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));

		btnReadLogs = new Button(cpItemAlloyUse, SWT.NONE);
		btnReadLogs.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, false, false, 1, 1));
		btnReadLogs.setText("Read logs");
		btnReadLogs.addMouseListener(new EventReadLogs(this));

		btnPersonalPropertie = new Button(cpItemAlloyUse, SWT.NONE);
		btnPersonalPropertie.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		btnPersonalPropertie.setText("Exec Perso");
		btnPersonalPropertie.addMouseListener(new EventPersonalExecutor(this));

		txtPersonalPropertie = new Text(cpItemAlloyUse, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
		GridData gd_txtPersonalPropertie = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
		gd_txtPersonalPropertie.heightHint = 65;
		txtPersonalPropertie.setLayoutData(gd_txtPersonalPropertie);
		btnChooseDir.addMouseListener(new EventChooseDir(this));

		// on finit par une petite vérification...
		this.checkDirectory(alloyGenerator);

		//Suppression des anciens logs
		deleteOldLogs();
		
		updaTreeExecResult();
	}

	/**
	 * Vérifie que le dossiers de destinations des générations est correcte.
	 * 
	 * @param alloyGenerator {@link IAlloyGenerator}.
	 */
	private void checkDirectory(IAlloyGenerator alloyGenerator) {

		// on vérifie que le dossier de génération alloy est correct
		try {
			alloyGenerator.fichiersPresents();
			txtLogs.append("Prêt pour la vérification de process.\n");
		} catch (FileNotFoundException e2) {
			MessageDialog dialog = new MessageDialog(new Shell(), "Des fichiers sont manquants", null,
					e2.toString(), MessageDialog.WARNING, new String[] { "Ok" }, 1);
			dialog.open();
			log.error(e2.getMessage());
		}
	}

	/**
	 * Affiche toutes les proprietes dans la view.
	 */
	private void addProperties() {

		TableColumn column = new TableColumn(tabProperties, SWT.NONE);
		column.setText("Properties");
		for (String prop : AbstractProperties.getProperties()) {
			TableItem item = new TableItem(tabProperties, SWT.NONE);
			item.setText(0, prop);

			// si la propriété est dans les préférences, on coche par défaut
			String prefs = ConfPropertiesManager.getInstance().getProperties();
			if (prefs.contains(prop)) {
				item.setChecked(true);
			}
		}
		tabProperties.getColumn(0).pack();
		tabProperties.addListener(SWT.Selection, new EventSelectProperty(this));
	}

	/**
	 * Supprime tous les logs générés à la derniere utilisation du plugin.
	 */
	private void deleteOldLogs()
	{
		log.debug("Suppression des anciens logs");
		File logInfo = new File(userDir + "logInfo.html");
		File logDebug = new File(userDir + "logDebug.html");
		logInfo.delete();
		logDebug.delete();
	}
	
	public void updaTreeExecResult()
	{
		for (int loopIndex1 = 0; loopIndex1 < 5; loopIndex1++) {
		      TreeItem item0 = new TreeItem(treeExecResult, 0);
		      item0.setText("Level 0 Item " + loopIndex1);
		      for (int loopIndex2 = 0; loopIndex2 < 5; loopIndex2++) {
		        TreeItem item1 = new TreeItem(item0, 0);
		        item1.setText("Level 1 Item " + loopIndex2);
		        for (int loopIndex3 = 0; loopIndex3 < 5; loopIndex3++) {
		          TreeItem item2 = new TreeItem(item1, 0);
		          item2.setText("Level 2 Item " + loopIndex3);
		        }
		      }
		    }
	}

	public Text getTxtDirectory() {
		return txtDirectory;
	}

	public Text getTxtLogs() {
		return txtLogs;
	}

	public IInfoParser getInfoParser() {
		return infoParser;
	}

	public IInfoGenerator getInfoGenerator() {
		return infoGenerator;
	}

	public IAlloyExecutor getAlloyExecutor() {
		return alloyExecutor;
	}

	public Table getTabProperties() {
		return tabProperties;
	}

	public Table getTabValuePropertiesString() {
		return tabValuePropertiesString;
	}

	public Table getTabValuePropertiesBool() {
		return tabValuePropertiesBool;
	}

	public Text getTxtPersonalPropertie() {
		return txtPersonalPropertie;
	}

	public TableEditor getEditorString() {
		return editorString;
	}

	public TableEditor getEditorBool() {
		return editorBool;
	}
}
