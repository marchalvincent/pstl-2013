package com.upmc.pstl2013.views;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.wb.swt.ResourceManager;

import com.upmc.pstl2013.alloyExecutor.IActivityResult;
import com.upmc.pstl2013.alloyExecutor.IFileResult;
import com.upmc.pstl2013.properties.Family;
import com.upmc.pstl2013.properties.IProperties;
import com.upmc.pstl2013.properties.dynamic.DynamicBusiness;
import com.upmc.pstl2013.properties.impl.AbstractProperties;
import com.upmc.pstl2013.properties.impl.InitialState;
import com.upmc.pstl2013.util.ConfPropertiesManager;
import com.upmc.pstl2013.views.events.EventFactory;

public class SwtView extends Composite {

	private Button btnChooseDir;
	private Text txtDirectory;
	private Text txtLogs;
	private Button btnChooserFile;
	private Button btnExcuterAlloy;
	private Button btnLogsInfos;
	private TabFolder tabFolder;
	private TabItem itemAlloyUse, itemAlloyProperty, itemDetails, itemOptions;
	private Composite cpItemAlloyUse, cpItemAlloyProp, cpItemDetails, cpItemOptions;
	private Table tabValuePropertiesString,tabValuePropertiesBool;
	private final TableEditor editorString, editorBool;
	private Text txtPersonalPropertie;
	private Button btnPersonalPropertie;
	private Tree treeFilesExecuted;
	private Text txtDetailsLogs;
	private Button btnAlloyVisualisation;
	private Button btnChooseFolderExec;
	private Button btnLogsErrors;
	private Text txtTimeout;
	private Label lblTimeout;
	private Label lblNbNodeMax;
	private Text txtNbNodesMax;
	private Label lblNbThreads;
	private Text txtNbThreads;
	private Tree treeProperties;
	private Label lblSeparator;
	private Button chkDetails;
	private Button btnAddbuisiness;
	private Button btnBtncleardetails;
	private Button btnSetInitialState;
	private Button btnGenerate;

	private IActivityResult currentActivityeResult;
	private String separator = File.separator;
	private String userDir;
	private List<Activity> activities;
	private HashMap<String,DynamicBusiness> listDynamicBuisiness;
	private DataView dataView;
	private InitialState initState;
	private Logger log = Logger.getLogger(SwtView.class);
	private DynamicBusiness lastBusiness = null;

	private static final String nameLogInfo = "logInfo.html";
	private static final String nameLogError = "logDebug.html";



	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public SwtView(Composite parent, int style) {

		super(parent, style);


		activities = new ArrayList<Activity>();
		listDynamicBuisiness = new HashMap<String, DynamicBusiness>();
		dataView = RunFactory.getInstance().newDataView(this);

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

		//Suppression des anciens logs
		deleteOldLogs();

		// TabFolder
		tabFolder = new TabFolder(this, SWT.NONE);
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		//Items et composite pour la partie utilisation du tabeFolder
		cpItemAlloyUse = new Composite(tabFolder, SWT.BORDER);
		cpItemAlloyUse.setLayout(new GridLayout(3, false));

		itemAlloyUse = new TabItem(tabFolder, SWT.NONE);
		itemAlloyUse.setImage(ResourceManager.getPluginImage("pstl-2013", "icons/start_cheatsheet.gif"));
		itemAlloyUse.setText("Use");
		itemAlloyUse.setControl(cpItemAlloyUse);

		//Items et composite pour la partie propriete du tabeFolder
		cpItemAlloyProp = new Composite(tabFolder, SWT.BORDER);
		cpItemAlloyProp.setLayout(new GridLayout(3, false));

		itemAlloyProperty = new TabItem(tabFolder, SWT.NONE);
		itemAlloyProperty.setImage(ResourceManager.getPluginImage("pstl-2013", "icons/properties.gif"));
		itemAlloyProperty.setText("Properties");
		itemAlloyProperty.setControl(cpItemAlloyProp);

		//Items et composite pour la partie Details du tabeFolder
		cpItemDetails = new Composite(tabFolder, SWT.BORDER);
		cpItemDetails.setLayout(new GridLayout(2, false));

		itemDetails = new TabItem(tabFolder, SWT.NONE);
		itemDetails.setImage(ResourceManager.getPluginImage("pstl-2013", "icons/console_view.gif"));
		itemDetails.setText("Details");
		itemDetails.setControl(cpItemDetails);

		//Items et composite pour la partie Options du tabeFolder
		cpItemOptions= new Composite(tabFolder, SWT.BORDER);
		cpItemOptions.setLayout(new GridLayout(2, false));

		itemOptions = new TabItem(tabFolder, SWT.NONE);
		itemOptions.setImage(ResourceManager.getPluginImage("pstl-2013", "icons/bkmrk_nav.gif"));
		itemOptions.setText("Options");
		itemOptions.setControl(cpItemOptions);

		treeProperties = new Tree(cpItemAlloyProp, SWT.CHECK | SWT.BORDER);
		treeProperties.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		//Table des attributs de la partie proprieteString
		tabValuePropertiesString = new Table(cpItemAlloyProp, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		tabValuePropertiesString.setTouchEnabled(true);
		tabValuePropertiesString.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2));
		tabValuePropertiesString.setHeaderVisible(true);
		tabValuePropertiesString.setLinesVisible(true);

		editorString = new TableEditor(tabValuePropertiesString);
		String[] titlesVP = { "Key", "Value" };
		for (int i = 0; i < titlesVP.length; i++) {
			TableColumn column = new TableColumn(tabValuePropertiesString, SWT.NONE);
			column.setText(titlesVP[i]);
		}

		//Table des attributs de la partie proprieteBoolean
		tabValuePropertiesBool = new Table(cpItemAlloyProp,  SWT.BORDER | SWT.CHECK | SWT.FULL_SELECTION | SWT.MULTI);
		tabValuePropertiesBool.setTouchEnabled(true);
		tabValuePropertiesBool.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2));
		tabValuePropertiesBool.setHeaderVisible(true);
		tabValuePropertiesBool.setLinesVisible(true);

		editorBool = new TableEditor(tabValuePropertiesBool);
		TableColumn column = new TableColumn(tabValuePropertiesBool, SWT.NONE);
		column.setText("Key");

		GridLayout gridLayout = new GridLayout(1, false);
		setLayout(gridLayout);

		/*
		 * Debut contenu de la parite utilisation
		 */
		btnChooseDir = new Button(cpItemAlloyUse, SWT.NONE);
		btnChooseDir.setToolTipText("Choose the directory path for the generated alloy files");
		btnChooseDir.setImage(ResourceManager.getPluginImage("pstl-2013", "icons/choose_folder.gif"));
		btnChooseDir.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
		btnChooseDir.setText("Choose dest.");

		txtDirectory = new Text(cpItemAlloyUse, SWT.BORDER);
		txtDirectory.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtDirectory.setText(userDir);

		btnChooserFile = new Button(cpItemAlloyUse, SWT.NONE);
		btnChooserFile.setToolTipText("Choose files for the execution");
		btnChooserFile.setImage(ResourceManager.getPluginImage("pstl-2013", "icons/select_file.gif"));
		btnChooserFile.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		btnChooserFile.setText("File");

		btnChooseFolderExec = new Button(cpItemAlloyUse, SWT.NONE);
		btnChooseFolderExec.setToolTipText("Choose all files of one folder for the execution");
		btnChooseFolderExec.setImage(ResourceManager.getPluginImage("pstl-2013", "icons/select_folder.gif"));

		btnChooseFolderExec.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnChooseFolderExec.setText("Folder");

		txtLogs = new Text(cpItemAlloyUse, SWT.BORDER | SWT.READ_ONLY | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		txtLogs.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 4));

		btnSetInitialState = new Button(cpItemAlloyUse, SWT.NONE);
		btnSetInitialState.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
		btnSetInitialState.setText("Set Initial State");
		btnSetInitialState.setEnabled(false);

		btnGenerate = new Button(cpItemAlloyUse, SWT.NONE);
		btnGenerate.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		btnGenerate.setText("Generate Alloy");

		btnExcuterAlloy = new Button(cpItemAlloyUse, SWT.NONE);
		btnExcuterAlloy.setToolTipText("Run the execution Alloy");
		btnExcuterAlloy.setImage(ResourceManager.getPluginImage("pstl-2013", "icons/run.gif"));
		btnExcuterAlloy.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		btnExcuterAlloy.setText("Execute Alloy");

		btnLogsErrors = new Button(cpItemAlloyUse, SWT.NONE);
		btnLogsErrors.setImage(ResourceManager.getPluginImage("pstl-2013", "icons/error_log.gif"));
		btnLogsErrors.setToolTipText("Open the errors logs");
		btnLogsErrors.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, false, false, 1, 1));
		btnLogsErrors.setText("Logs");

		btnLogsInfos = new Button(cpItemAlloyUse, SWT.NONE);
		btnLogsInfos.setImage(ResourceManager.getPluginImage("pstl-2013", "icons/info_log.gif"));
		btnLogsInfos.setToolTipText("Open the infos logs");
		btnLogsInfos.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, false, false, 1, 1));
		btnLogsInfos.setText("Logs");

		btnPersonalPropertie = new Button(cpItemAlloyUse, SWT.NONE);
		btnPersonalPropertie.setImage(ResourceManager.getPluginImage("pstl-2013", "icons/run_perso.gif"));
		btnPersonalPropertie.setToolTipText("Execute the user alloy text");
		btnPersonalPropertie.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 2, 1));
		btnPersonalPropertie.setText("Exec Perso");

		GridData gd_txtPersonalPropertie = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtPersonalPropertie.heightHint = 65;

		txtPersonalPropertie = new Text(cpItemAlloyUse, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
		txtPersonalPropertie.setTouchEnabled(true);
		txtPersonalPropertie.setLayoutData(gd_txtPersonalPropertie);
		//Empeche de faire perdre le focus dans la text box
		txtPersonalPropertie.addTraverseListener(new TraverseListener () {
			public void keyTraversed(TraverseEvent e) {
				e.doit = false;
			}
		});

		/*
		 * Debut de la partie Properties
		 */
		GridData gd_treeFilesExecuted = new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1);
		gd_treeFilesExecuted.widthHint = 128;

		treeFilesExecuted = new Tree(cpItemDetails, SWT.BORDER);
		treeFilesExecuted.setLayoutData(gd_treeFilesExecuted);

		btnAddbuisiness = new Button(cpItemAlloyProp, SWT.NONE);
		btnAddbuisiness.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnAddbuisiness.setText("Add Buisiness");
		btnAddbuisiness.setEnabled(false);

		/*
		 * Debut de la partie Details
		 */
		txtDetailsLogs = new Text(cpItemDetails, SWT.BORDER | SWT.READ_ONLY | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		txtDetailsLogs.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 3));

		btnAlloyVisualisation = new Button(cpItemDetails, SWT.NONE);
		btnAlloyVisualisation.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnAlloyVisualisation.setImage(ResourceManager.getPluginImage("pstl-2013", "icons/insp_sbook.gif"));
		btnAlloyVisualisation.setEnabled(false);
		btnAlloyVisualisation.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		btnAlloyVisualisation.setText("Visualise");

		btnBtncleardetails = new Button(cpItemDetails, SWT.NONE);
		btnBtncleardetails.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		btnBtncleardetails.setText("Clear");
		btnBtncleardetails.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				treeFilesExecuted.removeAll();
				txtDetailsLogs.setText("");
			}
		});

		/*
		 * Debut de la partie Options
		 */
		lblTimeout = new Label(cpItemOptions, SWT.NONE);
		lblTimeout.setAlignment(SWT.CENTER);
		lblTimeout.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false, 1, 1));
		lblTimeout.setText("Time Out (sec.)");

		txtTimeout = new Text(cpItemOptions, SWT.BORDER);
		txtTimeout.setText(String.valueOf(ConfPropertiesManager.getInstance().getTimeOut()));
		txtTimeout.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));

		lblNbNodeMax = new Label(cpItemOptions, SWT.NONE);
		lblNbNodeMax.setAlignment(SWT.CENTER);
		lblNbNodeMax.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false, 1, 1));
		lblNbNodeMax.setText("Max number of nodes to parse (UML file)");

		txtNbNodesMax = new Text(cpItemOptions, SWT.BORDER);
		txtNbNodesMax.setText(String.valueOf(ConfPropertiesManager.getInstance().getNbNodesMax()));
		txtNbNodesMax.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		lblNbThreads = new Label(cpItemOptions, SWT.NONE);
		lblNbThreads.setAlignment(SWT.CENTER);
		lblNbThreads.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false, 1, 1));
		lblNbThreads.setText("Max number of threads");

		txtNbThreads = new Text(cpItemOptions, SWT.BORDER);
		txtNbThreads.setText(String.valueOf(ConfPropertiesManager.getInstance().getNbThreads()));
		txtNbThreads.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		lblSeparator = new Label(cpItemOptions, SWT.SEPARATOR | SWT.HORIZONTAL);
		lblSeparator.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		new Label(cpItemOptions, SWT.NONE);

		chkDetails = new Button(cpItemOptions, SWT.CHECK);
		chkDetails.setSelection(ConfPropertiesManager.getInstance().isDetails());
		chkDetails.setText("Details");

		addPropertiesToTree(false);

		// ajout des events listener
		btnChooserFile.addMouseListener(EventFactory.getInstance().newEventChooseFile(this));
		btnChooseFolderExec.addMouseListener(EventFactory.getInstance().newEventChooseFolderExec(this));
		btnSetInitialState.addMouseListener(EventFactory.getInstance().newEventCliclSetInitialState(this));
		btnAlloyVisualisation.addMouseListener(EventFactory.getInstance().newEventClickVisualisationAlloy(this));
		btnChooseDir.addMouseListener(EventFactory.getInstance().newEventChooseDir(this));
		btnAddbuisiness.addMouseListener(EventFactory.getInstance().newEventClickAddBuisiness(this));
		btnExcuterAlloy.addMouseListener(EventFactory.getInstance().newEventCurrentExecutor(this, true));
		btnGenerate.addMouseListener(EventFactory.getInstance().newEventCurrentExecutor(this, false));
		btnPersonalPropertie.addMouseListener(EventFactory.getInstance().newEventPersonalExecutor(this, true));
		//TODO generate perso
		btnLogsInfos.addMouseListener(EventFactory.getInstance().newEventReadLogs(nameLogInfo));
		btnLogsErrors.addMouseListener(EventFactory.getInstance().newEventReadLogs(nameLogError));
		chkDetails.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ConfPropertiesManager.getInstance().setDetails(chkDetails.getSelection());
			}
		});

	}
	/**
	 * Affiche toutes les proprietes dans la view.
	 * @param addBuisiness True si l'on veux que l'onglet BUISINESS soit ouvert par defaut. False sinon.
	 */
	private void addPropertiesToTree(boolean addBuisiness) {

		treeProperties.removeAll();

		HashMap<String, List<IProperties>> families = new HashMap<String, List<IProperties>>();
		treeProperties.addListener(SWT.Selection, EventFactory.getInstance().newEventSelectTreeProperty(this));

		
		
		for (IProperties property : AbstractProperties.getProperties()) {

			//Ajout des familles dans la treeview si elle n'existe pas 
			if(!families.containsKey(property.getBehavior().toString()))
				families.put(property.getBehavior().toString(), new ArrayList<IProperties>());

			families.get(property.getBehavior().toString()).add(property);
		}
		
		//Ajoute la famille DYNAMICBUSINESS si des property dynamique ont été crées
		for (IProperties property : listDynamicBuisiness.values()) {

			//Ajout des familles dans la treeview si elle n'existe pas 
			if(!families.containsKey(property.getBehavior().toString()))
				families.put(property.getBehavior().toString(), new ArrayList<IProperties>());

			families.get(property.getBehavior().toString()).add(property);
		}
			
		HashMap<String,TreeItem> alreadyAdded = new HashMap<String,TreeItem>();
		for (Family family : Family.values()) {
			
			addPropertyOfFamily(family, families, addBuisiness,alreadyAdded);
			
		}
	}
	
	private TreeItem addPropertyOfFamily (Family family, HashMap<String, List<IProperties>> families, boolean addBuisiness,HashMap<String,TreeItem> alreadyAdded){
		
		TreeItem lItem0 = null;
		TreeItem itemParent = null;
		
		String nameFamily = family.toString();
		boolean isDynamicBusiness = nameFamily.equals(Family.BUISINESS.toString());
		
		//Partie recursive
		if (family.hasParent() && !alreadyAdded.containsKey(family.getParent().toString())){
			itemParent = addPropertyOfFamily(family.getParent(), families, addBuisiness, alreadyAdded);
			lItem0 = new TreeItem(itemParent, SWT.READ_ONLY);
		}
		else if(family.hasParent() && alreadyAdded.containsKey(family.getParent().toString())){
			itemParent = alreadyAdded.get(family.getParent().toString());
			lItem0 = new TreeItem(itemParent, SWT.READ_ONLY);
		}
		else // si family.hasParent() == false
			lItem0 = new TreeItem(treeProperties, SWT.READ_ONLY);
		
		alreadyAdded.put(nameFamily,lItem0);
		lItem0.setText(nameFamily);
		lItem0.setData(ETreeType.FAMILY);
		
		TreeItem lItem1 = null;
		if (families.containsKey(nameFamily)){
			//permet de checker la famille si tous les property sont selectionnées
			boolean allChecked = (families.get(nameFamily).size()>0);
			for (IProperties elem : families.get(nameFamily)) {

				lItem1 = new TreeItem(lItem0, SWT.READ_ONLY);
				lItem1.setText(elem.getName());
				boolean isModif = elem.isModifiable();
				
				if (isDynamicBusiness)
					lItem1.setData(ETreeType.DYNAMIC_PROPERTY);		
					if(lastBusiness == elem)
						lItem1.setChecked(true);
				else
					lItem1.setData(isModif);
				
				if (!isModif) 
					lItem1.setChecked(true);
				else if (ConfPropertiesManager.getInstance().getProperties().contains(elem.getName()))
					lItem1.setChecked(true);

				if (lItem1.getChecked() != true)
					allChecked = false;
			}
			lItem0.setChecked(allChecked);
		}
		return lItem0;
	}


	/**
	 * Supprime tous les logs générés à la derniere utilisation du plugin.
	 */
	private void deleteOldLogs() {
		log.debug("Suppression des anciens logs");
		File logInfo = new File("AlloyAnalyzer" + File.separator + "logInfo.html");
		File logDebug = new File("AlloyAnalyzer" + File.separator + "logDebug.html");
		logInfo.delete();
		logDebug.delete();
		try {
			logDebug.createNewFile();
			logInfo.createNewFile();
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * Afficher tous les resultats {@link IActivityResult} des tous les fichiers {@link IFileResult}
	 * sous la forme d'un treeView.
	 * @param {@link IFileResult} resultat de l'execution des fichiers.
	 */
	public void updateTreeExecResult(IFileResult fileResult) {
		treeFilesExecuted.addListener(SWT.Selection, EventFactory.getInstance().newEventSelectTreeItemDetail(this));

		TreeItem item0 = null;
		// on regarde s'il n'y a pas déjà un résultat pour ce fichier
		for (int i = 0; i < treeFilesExecuted.getItemCount(); i++) {
			// S'il existe on le récupère
			if (treeFilesExecuted.getItem(i).getText().equals(fileResult.getNom())) {
				item0 = treeFilesExecuted.getItem(i);
			}
		}

		// sinon on n'a rien trouvé, on le créé
		if (item0 == null) {
			item0 = new TreeItem(treeFilesExecuted, 0);
			item0.setText(fileResult.getNom());
		}

		// et enfin, on ajoute les IActivityResult
		IActivityResult actResult = fileResult.getActivityResult();
		TreeItem item1 = new TreeItem(item0, 0);
		item1.setText(actResult.getNom());
		item1.setData(actResult);
	}

	public void updateTreePropety(DynamicBusiness buisiness) {
		listDynamicBuisiness.put(buisiness.getName(), buisiness);
		lastBusiness = buisiness;
		addPropertiesToTree(true);
		lastBusiness = null;
	}

	public void clearDynamicBuisiness(){
		listDynamicBuisiness.clear();
		addPropertiesToTree(false);
	}

	public Text getTxtDirectory() {
		return txtDirectory;
	}

	public Text getTxtLogs() {
		return txtLogs;
	}

	public Tree getTreeProperties() {
		return treeProperties;
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

	public List<Activity> getActivitiesSelected() {
		return activities;
	}

	public String getUserDir() {
		return userDir;
	}

	public void setUserDir(String userDir) {
		this.userDir = userDir;
	}

	public IActivityResult getCurrentActivityResult() {
		return currentActivityeResult;
	}

	public void setCurrentActivityeResult(IActivityResult currentActivityeResult) {
		this.currentActivityeResult= currentActivityeResult ;
	}

	public Text getTxtDetailsLogs() {
		return txtDetailsLogs;
	}

	public Button getBtnVisualisationAlloy() {
		return btnAlloyVisualisation;
	}

	public int getTimeout() {
		try {
			int resultat = Integer.parseInt(txtTimeout.getText());
			return resultat;
		} catch (NumberFormatException e) {
			log.error(e.getMessage());
			return 3 * 60;
		}
	}

	public String getNbNodesMax() {
		return txtNbNodesMax.getText();
	}

	public String getNbThread() {
		return txtNbThreads.getText();
	}

	public void setEnabledAddActivity(boolean isEnabled) {
		btnAddbuisiness.setEnabled(isEnabled);
		btnSetInitialState.setEnabled(isEnabled);
	}

	public DynamicBusiness getListDynamicBuisiness(String namePropBuisiness) {
		return listDynamicBuisiness.get(namePropBuisiness);
	}

	public DataView getDataView() {
		return dataView;
	}

	public InitialState getInitState() {
		return initState;
	}

	public void setInitState(InitialState initState) {
		this.initState = initState;
	}


}
