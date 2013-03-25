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
import org.eclipse.uml2.uml.ActivityNode;

import com.upmc.pstl2013.properties.dynamic.DynamicBusiness;
import com.upmc.pstl2013.properties.dynamic.EDynamicBusiness;
import com.upmc.pstl2013.properties.dynamic.EParamType;
import com.upmc.pstl2013.views.SwtView;
import com.upmc.pstl2013.views.events.EventFactory;

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
	private List<Text> listText;
	private List<Text> listNum;
	private SwtView swtView;


	/**
	 * Dialogue (fenetre) permettant d'jouter dynamiquement des property dans la famille Buisiness.
	 * @param parentShell
	 * @param swtView
	 */
	public DialogBuisiness(Shell parentShell, SwtView swtView) {
		super(parentShell);
		parentShell.setMinimumSize(300,300);
		this.swtView = swtView;

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
		cboType.addSelectionListener(EventFactory.getInstance().newEventSelectType(this));

		txtDescription = new Text(composite, SWT.BORDER);
		txtDescription.setEditable(false);
		txtDescription.setEnabled(false);
		txtDescription.setText("Description");
		GridData gd_txtDescription = new GridData(SWT.FILL, SWT.FILL, true, false, 2, 2);
		gd_txtDescription.minimumWidth = 400;
		gd_txtDescription.minimumHeight = 40;
		gd_txtDescription.heightHint =60;
		txtDescription.setLayoutData(gd_txtDescription);

		EDynamicBusiness[] tabEnum = EDynamicBusiness.values();
		for (EDynamicBusiness type : tabEnum) {
			cboType.add(type.toString());
		}
		listCombo = new ArrayList<Combo>();
		listNum = new ArrayList<Text>();
		listText = new ArrayList<Text>();

		composite.setSize(300,300);
		composite.pack();
		return super.createContents(parent);
	}

	//ajoute dynamiquement le contenu de la fenetre en fonction du type choisie
	public void changeDialog(EDynamicBusiness enumBuisiness) {

		clearUI ();
		listCombo = new ArrayList<Combo>();
		String desc = enumBuisiness.getStrategy().getExample();
		txtDescription.setText(desc);

		for (EParamType params : enumBuisiness.getStrategy().getParams()) {

			if (params.name().equals(EParamType.NODE.toString())){
				Combo cbo = new Combo(composite, SWT.NONE);
				List<String> listNode = new ArrayList<String>();
				for (ActivityNode activity : swtView.getActivitiesSelected().get(0).getNodes()) {
					listNode.add(activity.getName());
				}
				addListToCombo(listNode, cbo);
				cbo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
				listCombo.add(cbo);
			}
			else if(params.name().equals(EParamType.TEXT.toString())){
				Combo cboText = new Combo(composite, SWT.NONE);
				addListToCombo(enumBuisiness.getStrategy().getTextsList() , cboText);
				cboText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
				listCombo.add(cboText);
			}
			else{
				Text txt = new Text(composite, SWT.NONE);
				txt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
				listText.add(txt);
			}

		}

		btnSubmit = new Button(composite, SWT.NONE);
		btnSubmit.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 2, 1));
		btnSubmit.setText("Submit");
		btnSubmit.addMouseListener(EventFactory.getInstance().newEventClickSubmit(swtView, this));

		composite.pack();
	}

	//Ajout les elements au combo.
	private void addListToCombo(List<String> list, Combo cbo) {
		for (String value : list) {
			cbo.add(value);
		}
	}


	private void clearUI ()
	{
		for (Combo cbo : listCombo) {
			cbo.dispose();
		}
		for (Text txt : listText) {
			txt.dispose();
		}
		for (Text num : listNum) {
			num.dispose();
		}
		if (btnSubmit != null)
			btnSubmit.dispose();
		listCombo.clear();
		listNum.clear();
		listText.clear();
	}

	public DynamicBusiness getSelectedBuisiness() {
		DynamicBusiness dBuisiness = new DynamicBusiness(txtNom.getText(), EDynamicBusiness.valueOf(cboType.getText()));
		int cpt = 0;
		for (Combo cbo : listCombo) {
			dBuisiness.addDataParam(cpt, cbo.getText());
			cpt++;
		}
		return dBuisiness;
	}

}
