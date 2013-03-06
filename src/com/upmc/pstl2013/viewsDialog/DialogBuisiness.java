package com.upmc.pstl2013.viewsDialog;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import com.upmc.pstl2013.bouchon.Bouchon;
import com.upmc.pstl2013.properties.dynamic.EDynamicBusiness;

/**
 * Popup permettant de saisir de nouvelles proprietes pour la famille BUISINESS
 *
 */
public class DialogBuisiness extends ApplicationWindow {
	private Text txtNom;
	private Text txtDescription;
	private Button btnSubmit;
	private Combo cboType;
	private Composite composite;
	private List<Combo> listCombo;

	public DialogBuisiness(Shell parentShell) {
		super(parentShell);
		parentShell.setMinimumSize(300,300);
		
	}

	protected Control createContents(Composite parent) {
		composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout(2, false));
		
		txtNom = new Text(composite, SWT.BORDER);
		txtNom.setText("Name");
		txtNom.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

		cboType = new Combo(composite, SWT.NONE);
		cboType.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		cboType.setText("Type of Buisiness");
		cboType.addSelectionListener(new EventSelectType(this));
		
		txtDescription = new Text(composite, SWT.BORDER);
		txtDescription.setEditable(false);
		txtDescription.setEnabled(false);
		txtDescription.setText("Description");
		GridData gd_txtDescription = new GridData(SWT.FILL, SWT.FILL, true, false, 2, 2);
		gd_txtDescription.heightHint =40;
		txtDescription.setLayoutData(gd_txtDescription);
		
		EDynamicBusiness[] tabEnum = EDynamicBusiness.values();
		for (EDynamicBusiness type : tabEnum) {
			cboType.add(type.toString());
		}
		listCombo = new ArrayList<Combo>();

		composite.setSize(300,300);
		composite.pack();
		return super.createContents(parent);
	}

	public void changeDialog(EDynamicBusiness enumBuisiness) {
		
		clearUI ();
		listCombo = new ArrayList<Combo>();
		String desc = enumBuisiness.getStrategy().getExample();
		txtDescription.setText(desc);

		for (int i = 0; i < enumBuisiness.getNbNodes(); i++) {
			Combo cbo = new Combo(composite, SWT.NONE);
			addListToCombo(Bouchon.getListNodesBouchons(), cbo);
			cbo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
			listCombo.add(cbo);
		}

		btnSubmit = new Button(composite, SWT.NONE);
		btnSubmit.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 2, 1));
		btnSubmit.setText("Submit");
		//TODO : Michel Add listener
		
		
		composite.pack();
	}
	
	private void addListToCombo(List<String> listNode, Combo cbo) {
		for (String node : listNode) {
			cbo.add(node);
		}
	}
	
	
	private void clearUI ()
	{
		for (Combo cbo : listCombo) {
			cbo.dispose();
		}
		if (btnSubmit != null)
			btnSubmit.dispose();
	}

}
