package com.upmc.pstl2013.factory;

import java.io.File;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityNode;
import com.upmc.pstl2013.alloyExecutor.ExecutorFactory;
import com.upmc.pstl2013.alloyExecutor.IActivityResult;
import com.upmc.pstl2013.alloyExecutor.IAlloyExecutor;
import com.upmc.pstl2013.alloyExecutor.IFileResult;
import com.upmc.pstl2013.alloyExecutor.impl.MyA4Reporter;
import com.upmc.pstl2013.alloyGenerator.GeneratorFactory;
import com.upmc.pstl2013.alloyGenerator.IAlloyGenerated;
import com.upmc.pstl2013.alloyGenerator.jet.IJetHelper;
import com.upmc.pstl2013.alloyGenerator.jet.IJetTemplate;
import com.upmc.pstl2013.properties.IAttribute;
import com.upmc.pstl2013.properties.IProperties;
import com.upmc.pstl2013.properties.PropertiesFactory;
import com.upmc.pstl2013.properties.impl.Attribute;
import com.upmc.pstl2013.properties.impl.PropertiesException;
import com.upmc.pstl2013.strategyExecution.ExecutionFactory;
import com.upmc.pstl2013.strategyExecution.IStrategyExecution;
import com.upmc.pstl2013.strategyExecution.impl.IncrementalExecutionStrategy;
import com.upmc.pstl2013.strategyExecution.impl.SimpleExecutionStrategy;
import com.upmc.pstl2013.strategyParcours.IStrategyParcours;
import com.upmc.pstl2013.strategyParcours.ParcoursFactory;
import com.upmc.pstl2013.strategyParcours.impl.PathStrategy;
import com.upmc.pstl2013.strategyParcours.impl.VoidStrategy;
import com.upmc.pstl2013.umlParser.IUMLParser;
import com.upmc.pstl2013.umlParser.impl.UMLParser;
import com.upmc.pstl2013.views.SwtView;
import com.upmc.pstl2013.views.events.JobExecutor;

/**
 * La factory. Implémente le Design Pattern Singleton.
 */
public class Factory {

	private static final Factory instance = new Factory();

	private Factory() {}

	/**
	 * Renvoie l'unique instance de la Factory.
	 * 
	 * @return {@link Factory}.
	 */
	public static Factory getInstance() {
		return instance;
	}
	
	//-------------------------PARSER-------------------------
	/**
	 * Créé un {@link IUMLParser}.
	 * 
	 * @param UMLFile un {@link IFile} correspondant au fichier UML.
	 */
	public IUMLParser newParser(IFile UMLFile) {
		return new UMLParser(UMLFile);
	}

	
	//-------------------------GENERATOR-------------------------

	/**
	 * Créé un {@link IJetTemplate}.
	 */
	public IJetTemplate newJetTemplate() {
		return GeneratorFactory.getInstance().newJetTemplate();
	}

	/**
	 * Créé un {@link IAlloyGenerated} qui sera utilisé par le {@link IAlloyExecutor}.
	 * @param file le {@link File} généré.
	 * @param la {@link IProperties} qui a permis de générer ce fichier.
	 */
	public IAlloyGenerated newAlloyGenerated(File file, IProperties property) {
		return GeneratorFactory.getInstance().newAlloyGenerated(file, property);
	}
	

	//-------------------------EXECUTOR-------------------------
	/**
	 * Créé un {@link IAlloyExecutor}.
	 * 
	 * @param UMLFile un {@link IFile} correspondant au fichier UML.
	 * @param dirDestination un String pour le dossier de travail du plugin.
	 * @param property la {@link IProperties} de génération Alloy.
	 * @param counterExecution le numéro de l'exécution.
	 */
	public IAlloyExecutor newAlloyExecutor(IFile UMLFile, String dirDestination, IProperties property, int counterExecution) {
		return ExecutorFactory.getInstance().newAlloyExecutor(UMLFile, dirDestination, property, counterExecution);
	}
	
	/**
	 * Créé un {@link IActivityResult}.
	 * @param nom le nom de l'activité
	 */
	public IActivityResult newActivityResult(String nom) {
		return ExecutorFactory.getInstance().newActivityResult(nom);
	}

	/**
	 * Créé un {@link IFileResult}.
	 * @param nom le nom du fichier exécuté.
	 * @param activityResults le {@link IActivityResult} du fichier.
	 */
	public IFileResult newFileResult(String nom, IActivityResult activityResults) {
		return ExecutorFactory.getInstance().newFileResult(nom, activityResults);
	}

	/**
	 * Créé un reporter Alloy, pour récupérer des infos sur la génération de la solution Alloy.
	 */
	public MyA4Reporter newReporter() {
		return ExecutorFactory.getInstance().newReporter();
	}
	

	//-------------------------PROPERTIES-------------------------
	/**
	 * Créé la {@link IProperties} associée au nom passé en paramètre.
	 * 
	 * @param name le nom de la propriété.
	 * @throws PropertiesException si le nom de la propertie est incorrect.
	 */
	public IProperties getProperty(String name) throws PropertiesException {
		return PropertiesFactory.getInstance().createProperty(name);
	}

	/**
	 * Créé un {@link Attribute}
	 * @param key la clé.
	 * @param value la valeur.
	 * @param isPrivate booléen qui spécifie si l'attribut doit être affiché dans l'IU.
	 */
	public IAttribute newAttribute(String key, Object value, Boolean isPrivate) {
		return PropertiesFactory.getInstance().newAttribute(key, value, isPrivate);
	}

	
	//-------------------------STRATEGIES-------------------------
	/**
	 * Créé un {@link PathStrategy}.
	 */
	public IStrategyParcours newPathStrategy() {
		return ParcoursFactory.getInstance().newPathStrategy();
	}

	/**
	 * Créé un {@link VoidStrategy}.
	 */
	public IStrategyParcours newVoidStrategy() {
		return ParcoursFactory.getInstance().newVoidStrategy();
	}

	/**
	 * Créé un {@link IncrementalExecutionStrategy}.
	 */
	public IStrategyExecution newIncrementalExecutionStrategy() {
		return ExecutionFactory.getInstance().newIncrementalExecutionStrategy();
	}

	/**
	 * Créé un {@link SimpleExecutionStrategy}.
	 */
	public IStrategyExecution newSimpleExecutionStrategy() {
		return ExecutionFactory.getInstance().newSimpleExecutionStrategy();
	}

	
	//-------------------------OTHERS-------------------------
	/**
	 * Créé un {@link JobExecutor}
	 * @param name le nom de l'exécutor.
	 * @param swtView la vue de l'interface graphique.
	 * @param UMLFile le fichier UML a exécuter.
	 * @param property la propriété d'exécution.
	 * @param jobToWait le job qu'il faut attendre avant de s'exécuter ou null s'il n'y a pas besoin.
	 * @param counterExecution le numéro de l'exécution.
	 */
	public JobExecutor newJobExecutor(String name, SwtView swtView, IFile UMLFile, IProperties property, JobExecutor jobToWait, int counterExecution) {
		return new JobExecutor(name, swtView, UMLFile, property, jobToWait, counterExecution);
	}
}
