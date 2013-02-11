package com.upmc.pstl2013.alloyGenerator.jet;


/**
 * L'interface du template Jet.
 * 
 */
public interface IJetTemplate {

	/**
	 * La méthode est dans la possibilité de renvoyer une exception en cas de problème.
	 * 
	 * @param argument l'argument passé au template. Normalement un [@link IJetTempalte}.
	 * @return String renvoie le texte Alloy sous forme de String.
	 * @throws JetException en cas d'erreur lors de la génération.
	 */
	String generate(Object argument) throws JetException;
}
