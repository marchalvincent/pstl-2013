package com.upmc.pstl2013;

/**
 * Chaque élément de la chaine (parser, generator, executor) est capable de se réinitialiser.
 */
public interface IProcess {

	/**
	 * Remet a zéro la sélection/génération des fichiers UML.
	 */
	public void reset();
}
