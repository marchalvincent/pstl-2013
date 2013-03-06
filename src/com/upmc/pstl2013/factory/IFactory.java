package com.upmc.pstl2013.factory;

import java.io.File;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityNode;
import com.upmc.pstl2013.alloyExecutor.IActivityResult;
import com.upmc.pstl2013.alloyExecutor.IAlloyExecutor;
import com.upmc.pstl2013.alloyExecutor.IFileResult;
import com.upmc.pstl2013.alloyExecutor.impl.MyA4Reporter;
import com.upmc.pstl2013.alloyGenerator.IAlloyGenerated;
import com.upmc.pstl2013.alloyGenerator.IAlloyGenerator;
import com.upmc.pstl2013.alloyGenerator.jet.IJetHelper;
import com.upmc.pstl2013.alloyGenerator.jet.IJetTemplate;
import com.upmc.pstl2013.properties.IAttribute;
import com.upmc.pstl2013.properties.IProperties;
import com.upmc.pstl2013.properties.impl.Attribute;
import com.upmc.pstl2013.properties.impl.PropertiesException;
import com.upmc.pstl2013.strategyExecution.IStrategyExecution;
import com.upmc.pstl2013.strategyExecution.impl.IncrementalExecutionStrategy;
import com.upmc.pstl2013.strategyExecution.impl.SimpleExecutionStrategy;
import com.upmc.pstl2013.strategyParcours.IStrategyParcours;
import com.upmc.pstl2013.strategyParcours.impl.PathStrategy;
import com.upmc.pstl2013.strategyParcours.impl.VoidStrategy;
import com.upmc.pstl2013.umlParser.IUMLParser;
import com.upmc.pstl2013.views.SwtView;
import com.upmc.pstl2013.views.events.JobExecutor;

/**
 * Représente les objets que la factory doit savoir créer.
 */
public interface IFactory {

	//-------------------------PARSER-------------------------
	/**
	 * Créé un {@link IUMLParser}.
	 * 
	 * @param UMLFile un {@link IFile} correspondant au fichier UML.
	 */
	IUMLParser newParser(IFile UMLFile);
	
	
	//-------------------------GENERATOR-------------------------
	/**
	 * Créé un {@link IAlloyGenerator}.
	 * 
	 * @param UMLFile un {@link IFile} correspondant au fichier UML.
	 * @param dirDestination un String pour le dossier de travail du plugin.
	 * @param property la {@link IProperties} de génération Alloy.
	 */
	IAlloyGenerator newAlloyGenerator(IFile UMLFile, String dirDestination, IProperties property);
	
	/**
	 * Créé un {@link IJetHelper}.
	 * @param nodes la liste des {@link ActivityNode} à générer.
	 * @param edges la liste des {@link ActivityEdge} à générer.
	 * @param prop la {@link IProperties}.
	 */
	IJetHelper newJetHelper(EList<ActivityNode> nodes, EList<ActivityEdge> edges, IProperties prop);

	/**
	 * Créé un {@link IJetTemplate}.
	 */
	IJetTemplate newJetTemplate();
	
	/**
	 * Créé un {@link IAlloyGenerated} qui sera utilisé par le {@link IAlloyExecutor}.
	 * @param file le {@link File} généré.
	 * @param la {@link IProperties} qui a permis de générer ce fichier.
	 */
	IAlloyGenerated newAlloyGenerated(File file, IProperties property);

	
	//-------------------------EXECUTOR-------------------------
	/**
	 * Créé un {@link IAlloyExecutor}.
	 * 
	 * @param UMLFile un {@link IFile} correspondant au fichier UML.
	 * @param dirDestination un String pour le dossier de travail du plugin.
	 * @param property la {@link IProperties} de génération Alloy.
	 * @param counterExecution le numéro de l'exécution.
	 */
	IAlloyExecutor newAlloyExecutor(IFile UMLFile, String dirDestination, IProperties property, int counterExecution);
	
	/**
	 * Créé un {@link IActivityResult}.
	 * @param nom le nom de l'activité
	 */
	IActivityResult newActivityResult(String nom);
	
	/**
	 * Créé un {@link IFileResult}.
	 * @param nom le nom du fichier exécuté.
	 * @param activityResults le {@link IActivityResult} du fichier.
	 */
	IFileResult newFileResult(String nom, IActivityResult activityResults);
	
	/**
	 * Créé un reporter Alloy, pour récupérer des infos sur la génération de la solution Alloy.
	 */
	MyA4Reporter newReporter();
	
	
	//-------------------------PROPERTIES-------------------------
	/**
	 * Créé la {@link IProperties} associée au nom passé en paramètre.
	 * 
	 * @param name le nom de la propriété.
	 * @throws PropertiesException si le nom de la propertie est incorrect.
	 */
	IProperties getProperty(String name) throws PropertiesException;
	
	/**
	 * Créé un {@link Attribute}
	 * @param key la clé.
	 * @param value la valeur.
	 * @param isPrivate booléen qui spécifie si l'attribut doit être affiché dans l'IU.
	 */
	IAttribute newAttribute(String key, Object value, Boolean isPrivate);
	
	
	//-------------------------STRATEGIES-------------------------
	/**
	 * Créé un {@link PathStrategy}.
	 */
	IStrategyParcours newPathStrategy();
	
	/**
	 * Créé un {@link VoidStrategy}.
	 */
	IStrategyParcours newVoidStrategy();
	
	/**
	 * Créé un {@link IncrementalExecutionStrategy}.
	 */
	IStrategyExecution newIncrementalExecutionStrategy();
	
	/**
	 * Créé un {@link SimpleExecutionStrategy}.
	 */
	IStrategyExecution newSimpleExecutionStrategy();
	
	
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
	JobExecutor newJobExecutor(String name, SwtView swtView, IFile UMLFile, IProperties property, JobExecutor jobToWait, int counterExecution);
}
