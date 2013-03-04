package com.upmc.pstl2013.views;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
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
import org.eclipse.wb.swt.SWTResourceManager;
import com.upmc.pstl2013.alloyExecutor.IActivityResult;
import com.upmc.pstl2013.alloyExecutor.IFileResult;
import com.upmc.pstl2013.properties.IProperties;
import com.upmc.pstl2013.properties.impl.AbstractProperties;
import com.upmc.pstl2013.util.ConfPropertiesManager;
import com.upmc.pstl2013.util.Utils;
import com.upmc.pstl2013.views.events.EventChooseDir;
import com.upmc.pstl2013.views.events.EventChooseFile;
import com.upmc.pstl2013.views.events.EventChooseFolderExec;
import com.upmc.pstl2013.views.events.EventClickVisualisationAlloy;
import com.upmc.pstl2013.views.events.EventCurrentExecutor;
import com.upmc.pstl2013.views.events.EventPersonalExecutor;
import com.upmc.pstl2013.views.events.EventReadLogs;
import com.upmc.pstl2013.views.events.EventSelectTreeItemDetail;
import com.upmc.pstl2013.views.events.EventSelectTreeProperty;
import com.upmc.pstl2013.views.events.MyRejectedExecutionHandelerImpl;

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

	private IActivityResult currentActivityeResult;
	private String separator = File.separator;
	private String userDir;
	private List<IFile> UMLFilesSelected;
	private ThreadPoolExecutor threadPoolExecutor;
	private Logger log = Logger.getLogger(SwtView.class);

	private static final String nameLogInfo = "logInfo.html";
	private static final String nameLogError = "logDebug.html";
	private Tree treeProperties;
	private Label label;
	private Button chkDetails;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public SwtView(Composite parent, int style) {

		super(parent, style);

		UMLFilesSelected = new ArrayList<IFile>();
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

		// TabFolder
		tabFolder = new TabFolder(this, SWT.NONE);
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		//Items et composite pour la partie utilisation du tabeFolder
		itemAlloyUse = new TabItem(tabFolder, SWT.NONE);
		itemAlloyUse.setImage(SWTResourceManager.getImage(Utils.pluginPath + "icons" + File.separator + "start_cheatsheet.gif"));
		itemAlloyUse.setText("Use");
		cpItemAlloyUse = new Composite(tabFolder, SWT.BORDER);
		cpItemAlloyUse.setLayout(new GridLayout(3, false));
		itemAlloyUse.setControl(cpItemAlloyUse);
		//Items et composite pour la partie propriete du tabeFolder
		itemAlloyProperty = new TabItem(tabFolder, SWT.NONE);
		itemAlloyProperty.setImage(SWTResourceManager.getImage(Utils.pluginPath + "icons" + File.separator + "properties.gif"));
		itemAlloyProperty.setText("Properties");
		cpItemAlloyProp = new Composite(tabFolder, SWT.BORDER);
		cpItemAlloyProp.setLayout(new GridLayout(3, false));
		itemAlloyProperty.setControl(cpItemAlloyProp);
		//Items et composite pour la partie Details du tabeFolder
		itemDetails = new TabItem(tabFolder, SWT.NONE);
		itemDetails.setImage(SWTResourceManager.getImage(Utils.pluginPath + "icons" + File.separator + "console_view.gif"));
		itemDetails.setText("Details");
		cpItemDetails = new Composite(tabFolder, SWT.BORDER);
		cpItemDetails.setLayout(new GridLayout(2, false));
		itemDetails.setControl(cpItemDetails);
		//Items et composite pour la partie Options du tabeFolder
		itemOptions = new TabItem(tabFolder, SWT.NONE);
		itemOptions.setImage(SWTResourceManager.getImage(Utils.pluginPath + "icons" + File.separator + "bkmrk_nav.gif"));
		itemOptions.setText("Options");
		cpItemOptions= new Composite(tabFolder, SWT.BORDER);
		cpItemOptions.setLayout(new GridLayout(2, false));
		itemOptions.setControl(cpItemOptions);
		
				treeProperties = new Tree(cpItemAlloyProp, SWT.CHECK | SWT.BORDER);
				treeProperties.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		//Table des attributs de la partie proprieteString
		tabValuePropertiesString = new Table(cpItemAlloyProp, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
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
		tabValuePropertiesBool = new Table(cpItemAlloyProp,  SWT.BORDER | SWT.CHECK | SWT.FULL_SELECTION | SWT.MULTI);
		tabValuePropertiesBool.setTouchEnabled(true);
		tabValuePropertiesBool.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
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
		btnChooseDir.setImage(SWTResourceManager.getImage(Utils.pluginPath + "icons" + File.separator + "choose_folder.gif"));

		btnChooseDir.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
		btnChooseDir.setText("Choose dest.");

		txtDirectory = new Text(cpItemAlloyUse, SWT.BORDER);
		txtDirectory.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtDirectory.setText(userDir);

		btnChooserFile = new Button(cpItemAlloyUse, SWT.NONE);
		btnChooserFile.setToolTipText("Choose files for the execution");
		btnChooserFile.setImage(SWTResourceManager.getImage(Utils.pluginPath + "icons" + File.separator + "select_file.gif"));
		btnChooserFile.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		btnChooserFile.setText("File");

		btnChooseFolderExec = new Button(cpItemAlloyUse, SWT.NONE);
		btnChooseFolderExec.setToolTipText("Choose all files of one folder for the execution");
		btnChooseFolderExec.setImage(SWTResourceManager.getImage(Utils.pluginPath + "icons" + File.separator + "select_folder.gif"));

		btnChooseFolderExec.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnChooseFolderExec.setText("Folder");

		txtLogs = new Text(cpItemAlloyUse, SWT.BORDER | SWT.READ_ONLY | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		txtLogs.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 4));

		btnExcuterAlloy = new Button(cpItemAlloyUse, SWT.NONE);
		btnExcuterAlloy.setToolTipText("Run the execution Alloy");
		btnExcuterAlloy.setImage(SWTResourceManager.getImage(Utils.pluginPath + "icons" + File.separator + "run.gif"));
		btnExcuterAlloy.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
		btnExcuterAlloy.setText("Execute Alloy");
		new Label(cpItemAlloyUse, SWT.NONE);
		new Label(cpItemAlloyUse, SWT.NONE);

		btnLogsErrors = new Button(cpItemAlloyUse, SWT.NONE);
		btnLogsErrors.setImage(SWTResourceManager.getImage(Utils.pluginPath + "icons" + File.separator + "error_log.gif"));
		btnLogsErrors.setToolTipText("Open the errors logs");
		btnLogsErrors.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, false, false, 1, 1));
		btnLogsErrors.setText("Logs");

		btnLogsInfos = new Button(cpItemAlloyUse, SWT.NONE);
		btnLogsInfos.setImage(SWTResourceManager.getImage(Utils.pluginPath + "icons" + File.separator + "info_log.gif"));
		btnLogsInfos.setToolTipText("Open the infos logs");
		btnLogsInfos.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, false, false, 1, 1));
		btnLogsInfos.setText("Logs");
		btnLogsInfos.addMouseListener(new EventReadLogs(this,nameLogInfo));

		btnPersonalPropertie = new Button(cpItemAlloyUse, SWT.NONE);
		btnPersonalPropertie.setImage(SWTResourceManager.getImage(Utils.pluginPath + "icons" + File.separator + "run_perso.gif"));

		btnPersonalPropertie.setToolTipText("Execute the user alloy text");
		btnPersonalPropertie.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 2, 1));
		btnPersonalPropertie.setText("Exec Perso");


		txtPersonalPropertie = new Text(cpItemAlloyUse, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
		GridData gd_txtPersonalPropertie = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtPersonalPropertie.heightHint = 65;
		txtPersonalPropertie.setLayoutData(gd_txtPersonalPropertie);

		/*
		 *Debut de la partie Details
		 */
		treeFilesExecuted = new Tree(cpItemDetails, SWT.BORDER);
		GridData gd_treeFilesExecuted = new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1);
		gd_treeFilesExecuted.widthHint = 128;
		treeFilesExecuted.setLayoutData(gd_treeFilesExecuted);

		txtDetailsLogs = new Text(cpItemDetails, SWT.BORDER | SWT.READ_ONLY | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		txtDetailsLogs.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 2));

		btnAlloyVisualisation = new Button(cpItemDetails, SWT.NONE);
		btnAlloyVisualisation.setImage(SWTResourceManager.getImage(Utils.pluginPath + "icons" + File.separator + "insp_sbook.gif"));

		btnAlloyVisualisation.setEnabled(false);
		btnAlloyVisualisation.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		btnAlloyVisualisation.setText("Visualise");

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
		txtNbNodesMax.setText(String.valueOf(ConfPropertiesManager.getInstance().getNbNodes()));
		txtNbNodesMax.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		lblNbThreads = new Label(cpItemOptions, SWT.NONE);
		lblNbThreads.setAlignment(SWT.CENTER);
		lblNbThreads.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false, 1, 1));
		lblNbThreads.setText("Max number of threads");

		txtNbThreads = new Text(cpItemOptions, SWT.BORDER);
		txtNbThreads.setText(String.valueOf(ConfPropertiesManager.getInstance().getNbThreads()));
		txtNbThreads.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		label = new Label(cpItemOptions, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		new Label(cpItemOptions, SWT.NONE);
		
		chkDetails = new Button(cpItemOptions, SWT.CHECK);
		chkDetails.setSelection(ConfPropertiesManager.getInstance().isDetails());
		chkDetails.setText("Details");

		addPropertiesToTree();
		
		// ajout des events listener
		btnChooserFile.addMouseListener(new EventChooseFile(this));
		btnExcuterAlloy.addMouseListener(new EventCurrentExecutor(this));
		btnLogsErrors.addMouseListener(new EventReadLogs(this, nameLogError));
		btnPersonalPropertie.addMouseListener(new EventPersonalExecutor(this));
		btnChooseFolderExec.addMouseListener(new EventChooseFolderExec(this));
		btnAlloyVisualisation.addMouseListener(new EventClickVisualisationAlloy(this));
		btnChooseDir.addMouseListener(new EventChooseDir(this));
		chkDetails.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ConfPropertiesManager.getInstance().setDetails(chkDetails.getSelection());
			}
		});


		//Suppression des anciens logs
		deleteOldLogs();

		startPoolExecutor();
	}


	/**
	 * Affiche toutes les proprietes dans la view.
	 */
	private void addPropertiesToTree() {

		HashMap<String, List<IProperties>> families = new HashMap<String, List<IProperties>>();
		treeProperties.addListener(SWT.Selection, new EventSelectTreeProperty(this));
		
		TreeItem lItem0 = null;
		TreeItem lItem1 = null;
		for (IProperties property : AbstractProperties.getProperties()) {
			
			//Ajout des familles dans la treeview si elle n'existe pas 
			if(!families.containsKey(property.getBehavior().toString()))
				families.put(property.getBehavior().toString(), new ArrayList<IProperties>());

			families.get(property.getBehavior().toString()).add(property);
		}

		for (String family : families.keySet()) {
			lItem0 = new TreeItem(treeProperties, SWT.READ_ONLY);
			lItem0.setText(family);
			lItem0.setData("FamilyItem");
			
			for (IProperties elem : families.get(family)) {
				
				lItem1 = new TreeItem(lItem0, SWT.READ_ONLY);
				lItem1.setText(elem.getClass().getSimpleName());
				boolean isModif = elem.isModifiable();
				lItem1.setData(isModif);
				if (!isModif) {
					lItem1.setChecked(true);
				} else if (ConfPropertiesManager.getInstance().getProperties().contains(elem.getClass().getSimpleName())) {
					lItem1.setChecked(true);
				}
			}
		}
	}


	/**
	 * Supprime tous les logs générés à la derniere utilisation du plugin.
	 */
	private void deleteOldLogs() {
		log.debug("Suppression des anciens logs");
		File logInfo = new File(userDir + "logInfo.html");
		File logDebug = new File(userDir + "logDebug.html");
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
		treeFilesExecuted.addListener(SWT.Selection, new EventSelectTreeItemDetail(this));

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
		for (IActivityResult actResult : fileResult.getListActivityResult()) {
			TreeItem item1 = new TreeItem(item0, 0);
			item1.setText(actResult.getNom());
			item1.setData(actResult);	
		}
	}

	private void startPoolExecutor() {
		BlockingQueue<Runnable> worksQueue = new ArrayBlockingQueue<Runnable>(30);
		RejectedExecutionHandler executionHandler = new MyRejectedExecutionHandelerImpl();
		int poolSize = ConfPropertiesManager.getInstance().getNbThreads();
		threadPoolExecutor = new ThreadPoolExecutor(poolSize, poolSize, 10, TimeUnit.SECONDS, worksQueue, executionHandler);
		threadPoolExecutor.allowCoreThreadTimeOut(true);

		// Starting the monitor thread as a daemon
		/*Thread monitor = new Thread(new MyMonitorThread(threadPoolExecutor));
		monitor.setDaemon(true);
		monitor.start();*/
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

	public List<IFile> getUMLFilesSelected() {
		return UMLFilesSelected;
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

	public ThreadPoolExecutor getThreadPoolExecutor() {
		return threadPoolExecutor;
	}
}
