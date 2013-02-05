package com.upmc.pstl2013.infoParser;

import java.util.List;
import org.eclipse.core.resources.IFile;
import com.upmc.pstl2013.IProcess;
import com.upmc.pstl2013.umlParser.IUMLParser;

/**
 * Cette classe va permettre à l'IU de passer des paramètres au {@link IUMLParser}.
 * 
 */
public interface IInfoParser extends IProcess {

	/**
	 * Ajoute un fichier à la liste.
	 * 
	 * @param file {@link IFile}.
	 */
	void addFile(IFile file);

	/**
	 * Renvoie la liste des fichiers sélectionnés.
	 * 
	 * @return une {@link List} d'{@link IFile}.
	 */
	List<IFile> getSelectedUMLFiles();
}
