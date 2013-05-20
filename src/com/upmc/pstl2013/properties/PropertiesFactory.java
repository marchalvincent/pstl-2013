package com.upmc.pstl2013.properties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import com.upmc.pstl2013.properties.impl.Attribute;
import com.upmc.pstl2013.properties.impl.OptionToCompleteStrong;
import com.upmc.pstl2013.properties.impl.EnoughState;
import com.upmc.pstl2013.properties.impl.InitialState;
import com.upmc.pstl2013.properties.impl.OptionToCompleteWeak;
import com.upmc.pstl2013.properties.impl.Orga;
import com.upmc.pstl2013.properties.impl.PersonalPropertie;
import com.upmc.pstl2013.properties.impl.ProperCompletionStrong;
import com.upmc.pstl2013.properties.impl.ProperCompletionWeak;
import com.upmc.pstl2013.properties.impl.PropertiesException;
import com.upmc.pstl2013.properties.impl.Wf;

/**
 * Se charge de créer les propriétés.
 * 
 */
public class PropertiesFactory {

	private Logger log = Logger.getLogger(PropertiesFactory.class);
	private List<Class<? extends IProperties>> classList;
	private static PropertiesFactory instance = new PropertiesFactory();
	private Map<String, IProperties> properties = new HashMap<String, IProperties>();

	private PropertiesFactory() {
		classList = new ArrayList<Class<? extends IProperties>>();
		classList.add(OptionToCompleteStrong.class);
		classList.add(OptionToCompleteWeak.class);
		classList.add(EnoughState.class);
		classList.add(Orga.class);
		classList.add(Wf.class);
		classList.add(ProperCompletionStrong.class);
		classList.add(ProperCompletionWeak.class);
	}

	public static PropertiesFactory getInstance() {
		return instance;
	}

	public List<IProperties> getProperties() {
		List<IProperties> listeP = new ArrayList<IProperties>();
		for (Class<? extends IProperties> clazz : classList) {
			try {
				listeP.add(this.getProperty(clazz.getSimpleName()));
			} catch (PropertiesException e) {
				log.error("Erreur, impossible de récupérer les singletons des propriétés : " + e.getMessage());
			}
		}
		return listeP;
	}

	/**
	 * Méthode static pour la création des propriétés en singleton.
	 * 
	 * @param name le nom de la propriété.
	 * @return une {@link IProperties}.
	 * @throws PropertiesException si le nom de la propriété est introuvable.
	 */
	public IProperties getProperty(String name) throws PropertiesException {

		// si l'objet est déjà créé, on le renvoie
		IProperties prop = properties.get(name);
		if (prop != null) {
			return prop;
		}

		// sinon, on créer l'objet, on l'ajoute dans la map et on le renvoie
		for (Class<? extends IProperties> clazz : classList) {
			if (clazz.getSimpleName().equals(name)) {
				try {
					properties.put(name, clazz.newInstance());
					return properties.get(name);
				} catch (InstantiationException e) {
					log.error("Erreur, impossible d'instancier la propriété : " + e.getMessage());
				} catch (IllegalAccessException e) {
					log.error("Erreur d'accès pour l'instanciation de la propriété : " + e.getMessage());
				}
			}
		}
		
		// on a une exception, c'est si on a une PersonalPropertie
		if (name.equals("PersonalPropertie")) {
			return new PersonalPropertie();
		}

		// si on est ici, c'est que la classe n'a pas été mise dans le constructeur
		final String error = "Le nom de la propriété n'existe pas dans la PropertiesFactory : " + name;
		log.warn(error);
		throw new PropertiesException(error);
	}

	/**
	 * Créé un {@link IAttribute} d'une {@link IProperties}.
	 * @param key la clé.
	 * @param value la valeur.
	 * @param isPrivate spécifie si cet attribut doit être affiche dans l'IU.
	 */
	public IAttribute newAttribute(String key, Object value, Boolean isPrivate) {
		return new Attribute(key, value, isPrivate);
	}
	
	/**
	 * Renvoie un {@link InitialState}.
	 */
	public InitialState newEtatInitial(HashMap<String, Integer> nodes, HashMap<String, Integer> edges) {
		return new InitialState(nodes, edges);
	}
}
