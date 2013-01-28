package com.upmc.pstl2013.views;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.ui.dialogs.WorkspaceResourceDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.upmc.pstl2013.alloyExecutor.IAlloyExecutor;
import com.upmc.pstl2013.alloyGenerator.IAlloyGenerator;
import com.upmc.pstl2013.alloyGenerator.impl.JetException;
import com.upmc.pstl2013.factory.Factory;
import com.upmc.pstl2013.infoGenerator.IInfoGenerator;
import com.upmc.pstl2013.infoParser.IInfoParser;
import com.upmc.pstl2013.properties.impl.AbstractProperties;
import com.upmc.pstl2013.strategy.IStrategy;
import com.upmc.pstl2013.strategy.impl.PathStrategy;
import com.upmc.pstl2013.umlParser.IUMLParser;

import edu.mit.csail.sdg.alloy4.Err;
import org.eclipse.swt.widgets.Label;

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
	private static Logger log = Logger.getLogger(SwtView.class);

	private TabFolder tabFolder;
	private TabItem itemAlloyUse,itemAlloyProperty;
	private Composite cpItemAlloyUse,cpdItemAlloyProp ;
	private Table tabProperties;
	private Table tabValueProperties;
	private final TableEditor editor;
	private final int EDITABLECOLUMN = 1;
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
		addProperties();

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

		GridLayout gridLayout = new GridLayout(1, false);
		setLayout(gridLayout);

		btnChooseDir = new Button(cpItemAlloyUse, SWT.NONE);
		btnChooseDir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				DirectoryDialog directoryD = new DirectoryDialog(new Shell());
				String chemin = directoryD.open();
				if (chemin != null) {
					chemin += separator;
					// on met a jour l'IU et l'info générateur.
					txtDirectory.setText(chemin);
					infoGenerator.setDestinationDirectory(chemin);
				}
			}
		});
		btnChooseDir.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		btnChooseDir.setText("Choose Dir");

		txtDirectory = new Text(cpItemAlloyUse, SWT.BORDER);
		txtDirectory.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtDirectory.setText(userDir);

		btnChooserFile = new Button(cpItemAlloyUse, SWT.NONE);
		btnChooserFile.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		btnChooserFile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				// on créé un filtre pour les fichiers .uml
				List<ViewerFilter> filters = new ArrayList<ViewerFilter>();
				filters.add(new ViewerFilter() {
					@Override
					public boolean select(Viewer viewer, Object parentElement, Object element) {
						if (element instanceof IFile)
							return ((IFile)element).getFileExtension().equals("uml");
						else return true;
					}
				});
				IFile file[] = WorkspaceResourceDialog.openFileSelection(new Shell(), "Selectionnez les fichiers UML", 
						null, true, null, filters);
				for (IFile iFile : file) {
					infoParser.addFile(iFile);
				}
			}
		});
		btnChooserFile.setText("Choose File");

		txtLogs = new Text(cpItemAlloyUse, SWT.BORDER | SWT.READ_ONLY | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		txtLogs.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 5));

		btnExcuterAlloy = new Button(cpItemAlloyUse, SWT.NONE);
		btnExcuterAlloy.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false,
				false, 1, 1));
		btnExcuterAlloy.setText("Execute Alloy");

		btnReadLogs = new Button(cpItemAlloyUse, SWT.NONE);
		btnReadLogs.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				try {
					Desktop.getDesktop().open(new File(txtDirectory.getText()+"log.html"));
				} catch (IOException e1) {
					log.error(e1.toString());
				}
			}
		});
		btnReadLogs.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		btnReadLogs.setText("Read logs");
		new Label(cpItemAlloyUse, SWT.NONE);
		new Label(cpItemAlloyUse, SWT.NONE);
		
		btnPersonalPropertie = new Button(cpItemAlloyUse, SWT.NONE);
		btnPersonalPropertie.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				// on définit la propriété personnalisé...
				infoGenerator.setProperties(getPersonnalPropertie());
				
				// on définit les strategies de parcours
				//TODO voir comment on génère les strategy
				List<IStrategy> strategies = new ArrayList<IStrategy>();
				strategies.add(new PathStrategy());
				
				log.debug("Génération et exécution des fichiers Alloy.");
				StringBuilder result = new StringBuilder();
				try {
					result.append(alloyExecutor.executeFiles(strategies));
					result.append("Fin d'exécution des fichiers Alloy.");
					log.debug(result.toString());
					txtLogs.setText(result.toString());
				} catch (JetException e1) {
					log.error(e1.getMessage());
					txtLogs.setText(e1.getMessage());
				} catch (Err e1) {
					log.debug(e1.toString());
					txtLogs.setText(e1.toString());
				}
				alloyExecutor.reset();
				
				//Création du fichier de log
				try {
					LogCreator.createLog(txtDirectory.getText());
				} catch (IOException e1) {
					log.error(e1.getMessage());
					txtLogs.setText(e1.getMessage());
				}
			}
		});
		btnPersonalPropertie.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		btnPersonalPropertie.setText("Exec Perso");
		
		txtPersonalPropertie = new Text(cpItemAlloyUse, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
		GridData gd_txtPersonalPropertie = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtPersonalPropertie.heightHint = 65;
		txtPersonalPropertie.setLayoutData(gd_txtPersonalPropertie);
		
		btnExcuterAlloy.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				// on définit les propriétés...
				infoGenerator.setProperties(getPropertiesSelected());
				
				// on définit les strategies de parcours
				//TODO voir comment on génère les strategy
				List<IStrategy> strategies = new ArrayList<IStrategy>();
				strategies.add(new PathStrategy());
				
				log.debug("Génération et exécution des fichiers Alloy.");
				StringBuilder result = new StringBuilder();
				try {
					result.append(alloyExecutor.executeFiles(strategies));
					result.append("Fin d'exécution des fichiers Alloy.");
					log.debug(result.toString());
					txtLogs.setText(result.toString());
				} catch (JetException e1) {
					log.error(e1.getMessage());
					txtLogs.setText(e1.getMessage());
				} catch (Err e1) {
					log.debug(e1.toString());
					txtLogs.setText(e1.toString());
				}
				alloyExecutor.reset();
				
				//Création du fichier de log
				try {
					LogCreator.createLog(txtDirectory.getText());
				} catch (IOException e1) {
					log.error(e1.getMessage());
					txtLogs.setText(e1.getMessage());
				}
			}
		});

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
		tabProperties.addListener(SWT.Selection, new Listener() 
		{
			@Override
			public void handleEvent(org.eclipse.swt.widgets.Event  e) 
			{
				TableItem[] selection = tabProperties.getSelection();
				
				if (selection.length > 0)
					showValueProperties(selection[0].getText());
			}
		});
	}

	/***
	 * Affiche toutes les attributs de la propriété séléctionné.
	 */
	private void showValueProperties(String nameProperty)
	{
		tabValueProperties.removeAll();

		tabValueProperties.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				// Clean up any previous editor control
				Control oldEditor = editor.getEditor();
				if (oldEditor != null)
					oldEditor.dispose();

				// Identify the selected row
				TableItem item = (TableItem) e.item;
				if (item == null)
					return;

				// The control that will be the editor must be a child of the
				// Table
				Text newEditor = new Text(tabValueProperties, SWT.NONE);
				newEditor.setText(item.getText(EDITABLECOLUMN));
				newEditor.addModifyListener(new ModifyListener() 
				{
					@Override
					public void modifyText(ModifyEvent e) {
						Text text = (Text) editor.getEditor();
						editor.getItem()
						.setText(EDITABLECOLUMN, text.getText());
					}
				});
				newEditor.selectAll();
				newEditor.setFocus();
				editor.setEditor(newEditor, item, EDITABLECOLUMN);
			}
		});

		tabValueProperties.getColumn(0).setText("Attributes : " + nameProperty);

		
		for (int i = 0; i < 10; i++) {
			TableItem item = new TableItem(tabValueProperties, SWT.NONE);
			item.setText(0, nameProperty);
			item.setText(1, "y");
		}

		for (int i=0; i<2; i++) {
			tabValueProperties.getColumn(i).pack ();
		}     
	}

	
	/**
	 * Récupère toutes les attributs des propriétés cochés.
	 */
	private Map<String, Map<String, String>> getPropertiesSelected() {
		Map<String, Map<String, String>> properties = new HashMap<String, Map<String,String>>();
		
		for (TableItem item : tabProperties.getItems()) {
			if(item.getChecked()) {
				//TODO Récupéréer les clés valeurs.
				properties.put(item.getText(), null);
			}
		}
		return properties;
	}
	
	/**
	 * Construit la super map avec le code sélectionné par l'utilisateur
	 * @return
	 */
	private Map<String, Map<String, String>> getPersonnalPropertie() {
		Map<String, Map<String, String>> properties = new HashMap<String, Map<String,String>>();
		
		String name = "personnalPropertie";
		Map<String, String> valeurs = new HashMap<String, String>();
		valeurs.put("alloyCode", txtPersonalPropertie.getText());
		System.out.println(txtPersonalPropertie.getText());
		
		properties.put(name, valeurs);
		
		return properties;
	}

}
