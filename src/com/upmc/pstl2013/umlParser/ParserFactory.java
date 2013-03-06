package com.upmc.pstl2013.umlParser;

import org.eclipse.core.resources.IFile;
import com.upmc.pstl2013.umlParser.impl.UMLParser;



public class ParserFactory {

	private static final ParserFactory instance = new ParserFactory();

	private ParserFactory() {}

	/**
	 * Renvoie l'unique instance de la Factory.
	 * 
	 * @return {@link ParserFactory}.
	 */
	public static ParserFactory getInstance() {
		return instance;
	}
	
	/**
	 * Créé un {@link IUMLParser}.
	 * 
	 * @param UMLFile un {@link IFile} correspondant au fichier UML.
	 */
	public IUMLParser newParser(IFile UMLFile) {
		return new UMLParser(UMLFile);
	}
}
