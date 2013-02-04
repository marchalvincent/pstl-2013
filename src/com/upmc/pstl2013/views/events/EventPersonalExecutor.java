package com.upmc.pstl2013.views.events;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.swt.widgets.Text;
import com.upmc.pstl2013.factory.Factory;
import com.upmc.pstl2013.properties.IProperties;
import com.upmc.pstl2013.properties.impl.PersonalPropertie;
import com.upmc.pstl2013.properties.impl.PropertiesException;
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
	protected List<IProperties> getProperties() throws PropertiesException {
		IProperties prop = Factory.getInstance().getPropertie(PersonalPropertie.class.getSimpleName());
		prop.putPrivate("alloyCode", txtPersonalPropertie.getText());
		List<IProperties> liste = new ArrayList<IProperties>();
		liste.add(prop);
		return liste;
	}
}
