package com.upmc.pstl2013.alloyExecutor;

import org.eclipse.uml2.uml.Activity;
import com.upmc.pstl2013.alloyExecutor.impl.ActivityResult;
import com.upmc.pstl2013.alloyExecutor.impl.AlloyExecutor;
import com.upmc.pstl2013.alloyExecutor.impl.FileResult;
import com.upmc.pstl2013.alloyExecutor.impl.MyA4Reporter;
import com.upmc.pstl2013.properties.IProperties;


/**
 * Représente la factory pour les objets utilisés par le {@link IAlloyExecutor}.
 *
 */
public class ExecutorFactory {
	
	private static ExecutorFactory instance = new ExecutorFactory();
	
	public static ExecutorFactory getInstance() {
		return instance;
	}
	
	private ExecutorFactory() {}

	/**
	 * Créé un {@link IAlloyExecutor}.
	 * 
	 * @param activity un {@link Activity} correspondant à l'activité parsé du process UML.
	 * @param dirDestination un String pour le dossier de travail du plugin.
	 * @param property la {@link IProperties} de génération Alloy.
	 * @param counterExecution le numéro de l'exécution.
	 */
	public IAlloyExecutor newAlloyExecutor(Activity activity, String dirDestination, IProperties property, int counterExecution) {
		return new AlloyExecutor(activity, dirDestination, property, counterExecution);
	}
	
	/**
	 * Créé un {@link IActivityResult}.
	 * @param nom le nom de l'activité
	 */
	public IActivityResult newActivityResult(String nom) {
		return new ActivityResult(nom);
	}

	/**
	 * Créé un {@link IFileResult}.
	 * @param nom le nom du fichier exécuté.
	 * @param activityResults le {@link IActivityResult} du fichier.
	 */
	public IFileResult newFileResult(String nom, IActivityResult activityResults) {
		return new FileResult(nom, activityResults);
	}
	
	/**
	 * Créé un {@link MyA4Reporter} Alloy, pour récupérer des infos sur la génération de la solution Alloy.
	 */
	public MyA4Reporter newReporter() {
		return new MyA4Reporter();
	}
}
