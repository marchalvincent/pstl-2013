package com.upmc.pstl2013.views.events;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.swt.widgets.Text;
import com.upmc.pstl2013.properties.IProperties;
import com.upmc.pstl2013.properties.PropertiesFactory;
import com.upmc.pstl2013.properties.impl.PersonalPropertie;
import com.upmc.pstl2013.properties.impl.PropertiesException;
import com.upmc.pstl2013.views.SwtView;

public class EventPersonalExecutor extends AbstractEventExecutor {

	private Text txtPersonalPropertie;

	/**
	 * Évènement permettant de lancer une exéxution personnalisé en ajoutant de l'onformation saisie dans le plugin.
	 * @param {{@link SwtView}
	 */
	public EventPersonalExecutor(SwtView swtView, boolean executed) {

		super(swtView, executed);
		this.txtPersonalPropertie = swtView.getTxtPersonalPropertie();
	}

	@Override
	protected List<IProperties> getProperties() throws PropertiesException {
		IProperties prop = PropertiesFactory.getInstance().getProperty(PersonalPropertie.class.getSimpleName());
		prop.putPrivate("alloyCode", txtPersonalPropertie.getText());
		List<IProperties> liste = new ArrayList<IProperties>();
		liste.add(prop);
		return liste;
	}
}
