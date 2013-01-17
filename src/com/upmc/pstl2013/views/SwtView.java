package com.upmc.pstl2013.views;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.upmc.pstl2013.alloyGenerator.AlloyGenerator;
import com.upmc.pstl2013.alloyGenerator.interfaces.IAlloyGenerator;
import com.upmc.pstl2013.alloyGenerator.test.UMLFileChooserBouchon;
import com.upmc.pstl2013.interfaces.IUMLFileChooser;

public class SwtView extends Composite {
	private Text text;
	private Button btnReset;
	private Button btnStart;
	private Button btnChooserFile;
	//private JFileChooser dialog;
	private FileDialog dialog;
	private IFile currentFile;
	private Button btnTestvincent;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public SwtView(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(2, false));
		
		
		//dialog = new JFileChooser(); //Fonctionne 1
		dialog = new FileDialog(new Shell());
		
		
		//SimpleFileChooser dialog = new SimpleFileChooser();
		//dialog.setVisible(true);
		
		//WorkspaceResourceDialog dialog = new WorkspaceResourceDialog(new Shell(),new WorkbenchLabelProvider(), new WorkbenchContentProvider());
		//dialog.open();
		
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
		
		btnChooserFile = new Button(this, SWT.NONE);
		btnChooserFile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				String path = dialog.open();
				text.setText(path);
				if (path != null)
				{
					Platform.getLocation();
					//File f = new File(new File(Dialog_.getDirectory()), Dialog_.getFile());
					IWorkspace workspace= ResourcesPlugin.getWorkspace(); 
					IPath location = Path.fromOSString(path); 
					currentFile = workspace.getRoot().getFileForLocation(location);
					/*
					IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
					IProject project = root.getProject("MyProject");
					IFile file = project.getFile("src/my/package/MyClass.java");
					text.setText(currentFile.toString());*/
					//dialog.
				}
				
				
				/*
				int retVal = dialog.showOpenDialog(null);
				if (retVal == JFileChooser.APPROVE_OPTION) {
					File file = dialog.getSelectedFile();
					IWorkspace workspace= ResourcesPlugin.getWorkspace(); 
					IPath location= Path.fromOSString(file.getAbsolutePath()); 
					currentFile = workspace.getRoot().getFileForLocation(location);
			    }*/
				
				
				
			}
		});
		btnChooserFile.setText("Chooser File");
		
		btnTestvincent = new Button(this, SWT.NONE);
		btnTestvincent.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				IUMLFileChooser fcb = new UMLFileChooserBouchon();
				IAlloyGenerator alloyGenerator = new AlloyGenerator();
				alloyGenerator.generateFile(fcb);
			}
		});
		btnTestvincent.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnTestvincent.setText("TestVincent");
	}
}
