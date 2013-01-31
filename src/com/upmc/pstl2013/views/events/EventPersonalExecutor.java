package com.upmc.pstl2013.views.events;

import org.eclipse.swt.widgets.Text;
import com.upmc.pstl2013.factory.Factory;
import com.upmc.pstl2013.properties.IProperties;
import com.upmc.pstl2013.views.SwtView;

public class EventPersonalExecutor extends AbstractEventExecutor {

	private Text txtPersonalPropertie;

	/**
	 * Constructor
	 * @param {{@link SwtView}
	 */
	public EventPersonalExecutor(SwtView swtView) {

		super(swtView);
		this.txtPersonalPropertie = swtView.getTxtPersonalPropertie();
	}

	@Override
	protected IProperties getProperties() {
		IProperties prop = Factory.getInstance().newPropertie("personnalPropertie");
		prop.put("alloyCode", txtPersonalPropertie.getText());
		return prop;
	}
}
