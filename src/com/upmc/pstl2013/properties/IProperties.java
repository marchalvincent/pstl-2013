package com.upmc.pstl2013.properties;

import java.util.Map;
import org.eclipse.uml2.uml.Activity;
import com.upmc.pstl2013.alloyGenerator.jet.JetException;
import com.upmc.pstl2013.properties.impl.InitialState;
import com.upmc.pstl2013.strategyExecution.IStrategyExecution;
import com.upmc.pstl2013.strategyParcours.IStrategyParcours;

/**
 * Représente une méthode de vérification d'un fichier Alloy.
 * 
 * Une propriété doit être capable de déléguer les opérations que doit savoir faire
 * une stratégie d'exécution à sa propre stratégie d'exécution. De même pour la stratégie
 * de parcours.
 * 
 * @extends IStrategyExecution, une propriété doit être capable de déléguer le travail d'une stratégie
 * d'exécution.
 * @extends IStrategyParcours, de même une propriété doit pouvoir déléguer le travail d'une stratégie
 * de parcours.
 * @extends Cloneable, une propriété doit pouvoir être clonée car l'exécution Alloy se fait en parallèle
 * pour chaque fichier. Les propriétés seront donc changées selon l'exécution du fichier, c'est pourquoi 
 * une copie doit être faite pour ne pas avoir de conflit.
 */
public interface IProperties extends IStrategyExecution, IStrategyParcours, Cloneable {

	/**
	 * Renvoie le code Alloy associé à cette propriété.
	 * 
	 * @return String du code alloy.
	 * @throws JetException En cas d'erreur lors de la génération Jet.
	 */
	String getAlloyCode() throws JetException;
	
	/**
	 * Ajoute ou modifie un attribut caché à l'interface utilisateur.
	 * 
	 * @param key le nom de l'attribut.
	 * @param value sa valeur.
	 */
	void putPrivate(String key, String value);
	
	/**
	 * Ajoute ou modifie un attribut de la propriété.
	 * 
	 * @param key le nom de l'attribut.
	 * @param value sa valeur.
	 */
	void put(String key, String value);
	
	/**
	 * Ajoute ou modifie un attribut caché à l'interface utilisateur.
	 * 
	 * @param key
	 * @param value
	 */
	public void putPrivate(String key, Boolean value);
	
	/**
	 * Ajoute ou modifie un attribut de la propriété.
	 * 
	 * @param key le nom de l'attribut.
	 * @param value sa valeur.
	 */
	void put(String key, Boolean value);
	
	/**
	 * Renvoie la valeur string associée à la clé passée en paramètre. Null si l'attribut n'existe pas.
	 * 
	 * @param key la clé.
	 * @return String la valeur associée ou null.
	 */
	String getString(String key);
	
	/**
	 * Renvoie la valeur booléenne associée à la clé passée en paramètre.
	 * 
	 * @param key la clé.
	 * @return Boolean la valeur associée.
	 */
	Boolean getBoolean(String key);
	
	/**
	 * Renvoie les attributs string qui doivent être affiché dans l'IU.
	 * 
	 * @return une {@link Map} de clé (string) valeur (string).
	 */
	Map<String, String> getStringAttributes();
	
	/**
	 * Renvoie les attributs booléens qui doivent être affiché dans l'IU.
	 * 
	 * @return une {@link Map} de clé (string) valeur (bool).
	 */
	Map<String, Boolean> getBooleanAttributes();
	
	/**
	 * Renvoie vrai si cette propriété est un "check" alloy. False si c'est un "run".
	 * @return Boolean.
	 */
	Boolean isCheck();
	
	/**
	 * Renvoie la {@link IStrategyExecution} d'exécution Alloy associée à cette propriété.
	 * @return
	 */
	IStrategyExecution getStrategyExecution();
	
	/**
	 * Renvoie la {@link IStrategyParcours} de parcours Alloy associée à cette propriété.
	 * @return
	 */
	IStrategyParcours getStrategyParcours();
	
	/**
	 * Clone la propriété.
	 */
	IProperties clone();
	
	/**
	 * Renvoie la famille a laquelle appartient la propriété.
	 * @return {@link Family}.
	 */
	Family getBehavior();
	
	/**
	 * Renvoie un booléen qui spécifie si cette propriété est modifiable par l'utilisateur ou non.
	 * @return
	 */
	boolean isModifiable();
	
	/**
	 * Renvoie le nom de la propriété.
	 * @return String.
	 */
	String getName();
	
	/**
	 * Setter pour spécifier l'état initial du process.
	 * @param etat {@link InitialState}.
	 */
	void setEtatInitial(InitialState etat);
	
	/**
	 * Getter sur l'état initial du process.
	 * @return {@link InitialState}.
	 */
	InitialState getEtatInitial();
	
	/**
	 * Permet de faire la réduction d'un diagramme d'activités si la propriété possède une ou plusieurs réductions.
	 * @return Activity le nouveau diagramme d'activité modifié
	 */
	Activity reduceActivityDiagram(Activity activity);
	
	/**
	 * Renvoie un string correspondant au nom de la classe dont la propriété dépend.
	 * Une propriété dépend d'une autre si elle ne peux pas s'exécuter avant la fin de cette deuxième.
	 * @return
	 */
	String getDependance();
}
