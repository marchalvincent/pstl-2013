package com.upmc.pstl2013.views;

import java.awt.Event;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.ui.dialogs.WorkspaceResourceDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
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
import com.upmc.pstl2013.umlParser.IUMLParser;

import edu.mit.csail.sdg.alloy4.Err;

public class SwtView extends Composite {

	private Button btnChooseDir;
	private Text textDirectory;

	private Text text;
	private Button btnChooserFile;
	private Button btnExcuterAlloy;

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


		itemAlloyUse = new TabItem(tabFolder, SWT.NONE);
		itemAlloyUse.setText("Utilisation");
		cpItemAlloyUse = new Composite(tabFolder, SWT.BORDER);
		cpItemAlloyUse.setLayout(new GridLayout(2, false));
		itemAlloyUse.setControl(cpItemAlloyUse);

		itemAlloyProperty = new TabItem(tabFolder, SWT.NONE);
		itemAlloyProperty.setText("Properties");
		cpdItemAlloyProp= new Composite(tabFolder, SWT.BORDER);
		cpdItemAlloyProp.setLayout(new GridLayout(3, false));
		itemAlloyProperty.setControl(cpdItemAlloyProp);
		
		tabProperties = new Table(cpdItemAlloyProp, SWT.BORDER | SWT.CHECK | SWT.FULL_SELECTION | SWT.MULTI );
		tabProperties.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tabProperties.setLinesVisible(true);
		tabProperties.setHeaderVisible(true);
		addProperties();

		tabValueProperties = new Table(cpdItemAlloyProp,  SWT.MULTI |SWT.BORDER | SWT.FULL_SELECTION);
		tabValueProperties.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tabValueProperties.setHeaderVisible(true);
		tabValueProperties.setLinesVisible(true);
		
		String[] titlesVP = { "Key", "Value" };
		for (int i = 0; i < titlesVP.length; i++) {
			TableColumn column = new TableColumn(tabValueProperties, SWT.NONE);
			column.setText(titlesVP[i]);
		}
		
		new Label(cpdItemAlloyProp, SWT.NONE);


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
					textDirectory.setText(chemin);
					infoGenerator.setDestinationDirectory(chemin);
				}
			}
		});
		btnChooseDir.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		btnChooseDir.setText("Choose Dir");

		textDirectory = new Text(cpItemAlloyUse, SWT.BORDER);
		textDirectory.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textDirectory.setText(userDir);



		btnChooserFile = new Button(cpItemAlloyUse, SWT.NONE);
		GridData gd_btnChooserFile = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnChooserFile.widthHint = 87;
		btnChooserFile.setLayoutData(gd_btnChooserFile);
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
		btnChooserFile.setText("Chooser File");

		text = new Text(cpItemAlloyUse, SWT.BORDER | SWT.READ_ONLY | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		GridData gd_text = new GridData(SWT.FILL, SWT.TOP, true, true, 1, 6);
		gd_text.widthHint = 297;
		gd_text.heightHint = 192;
		text.setLayoutData(gd_text);

		btnExcuterAlloy = new Button(cpItemAlloyUse, SWT.NONE);
		GridData gd_btnExcuterAlloy = new GridData(SWT.LEFT, SWT.TOP, false,
				false, 1, 1);
		gd_btnExcuterAlloy.widthHint = 87;
		btnExcuterAlloy.setLayoutData(gd_btnExcuterAlloy);
		btnExcuterAlloy.setText("Exécuter Alloy");
		new Label(cpItemAlloyUse, SWT.NONE);
		new Label(cpItemAlloyUse, SWT.NONE);
		new Label(cpItemAlloyUse, SWT.NONE);
		new Label(cpItemAlloyUse, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		btnExcuterAlloy.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				// on définit les propriétés...
				infoGenerator.setProperties(null);

				log.debug("Ce bouton génère les fichiers Alloy et lance l'éxecution.");
				StringBuilder result = new StringBuilder();
				try {
					result.append(alloyExecutor.executeFiles());
					result.append("Fin d'exécution des fichiers Alloy.");
					log.debug(result.toString());
					text.setText(result.toString());
				} catch (JetException e1) {
					log.error(e1.getMessage());
					text.setText(e1.getMessage());
				} catch (Err e1) {
					log.debug(e1.toString());
					text.setText(e1.toString());
				}
				alloyExecutor.reset();
				System.out.println(log.getAllAppenders().toString());
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
			text.setText("Prêt pour la vérification de process.");
		} catch (FileNotFoundException e2) {
			MessageDialog dialog = new MessageDialog(new Shell(), 
					"Des fichiers sont manquants", null, 
					e2.toString(), 
					MessageDialog.WARNING, new String[]{"Ok"}, 1);
			dialog.open();
			text.setText(e2.getMessage());
		}
	}



	private void addProperties()
	{
		String[] titlesP = { " ", "Properties" };
		for (int i = 0; i < titlesP.length; i++) {
			TableColumn column = new TableColumn(tabProperties, SWT.NONE);
			column.setText(titlesP[i]);
		}

		for (int i = 0; i < 10; i++) {
			TableItem item = new TableItem(tabProperties, SWT.NONE);
			item.setText(0, "" + i);
			item.setText(1, "y");
		}

		for (int i=0; i<titlesP.length; i++) {
			tabProperties.getColumn (i).pack ();
		}     

		tabProperties.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(org.eclipse.swt.widgets.Event  e) {
				String string = "";
				TableItem[] selection = tabProperties.getSelection();
				for (int i = 0; i < selection.length; i++)
					string += selection[i] + " ";
				showValueProperties(string);
			}
		});
	}

	private void showValueProperties(String nameProperty)
	{

		tabValueProperties.removeAll();
		for (int i = 0; i < 10; i++) {
			TableItem item = new TableItem(tabValueProperties, SWT.NONE);
			item.setText(0, nameProperty);
			item.setText(1, "y");
		}

		for (int i=0; i<2; i++) {
			tabValueProperties.getColumn (i).pack ();
		}     
	}
	
}
