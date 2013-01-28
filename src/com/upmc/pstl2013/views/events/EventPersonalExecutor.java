package com.upmc.pstl2013.views.events;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.swt.widgets.Text;
import com.upmc.pstl2013.alloyExecutor.IAlloyExecutor;
import com.upmc.pstl2013.infoGenerator.IInfoGenerator;

public class EventPersonalExecutor extends AbstractEventExecutor {

	private Text txtPersonalPropertie;

	public EventPersonalExecutor(Text txtLogs, Text textDirectory, IAlloyExecutor alloyExecutor,
			IInfoGenerator infoGenerator, Text txtPersonalPropertie) {

		super(txtLogs, textDirectory, alloyExecutor, infoGenerator);
		this.txtPersonalPropertie = txtPersonalPropertie;
	}

	@Override
	protected Map<String, Map<String, String>> getProperties() {

		Map<String, Map<String, String>> properties = new HashMap<String, Map<String, String>>();
		String name = "personnalPropertie";
		Map<String, String> valeurs = new HashMap<String, String>();
		valeurs.put("alloyCode", txtPersonalPropertie.getText());
		System.out.println(txtPersonalPropertie.getText());
		properties.put(name, valeurs);
		return properties;
	}
}
