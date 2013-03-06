package com.upmc.pstl2013.alloyGenerator;

import java.io.File;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityNode;
import com.upmc.pstl2013.alloyExecutor.IAlloyExecutor;
import com.upmc.pstl2013.alloyGenerator.impl.AlloyGenerated;
import com.upmc.pstl2013.alloyGenerator.impl.AlloyGenerator;
import com.upmc.pstl2013.alloyGenerator.jet.IJetHelper;
import com.upmc.pstl2013.alloyGenerator.jet.IJetTemplate;
import com.upmc.pstl2013.alloyGenerator.jet.impl.JetHelper;
import com.upmc.pstl2013.alloyGenerator.jet.impl.JetTemplate;
import com.upmc.pstl2013.properties.IProperties;

/**
 * Représente la factory des objets utilisés dans le générateur alloy.
 *
 */
public class GeneratorFactory {
	
	private static GeneratorFactory instance = new GeneratorFactory();
	
	public static GeneratorFactory getInstance() {
		return instance;
	}
	
	private GeneratorFactory() {}

	/**
	 * Créé un {@link IAlloyGenerator}.
	 * 
	 * @param activity un {@link Activity} correspondant à l'activité du process à vérifier.
	 * @param dirDestination un String pour le dossier de travail du plugin.
	 * @param property la {@link IProperties} de génération Alloy.
	 */
	public IAlloyGenerator newAlloyGenerator(Activity activity, String dirDestination, IProperties property) {
		return new AlloyGenerator(activity, dirDestination, property);
	}
	
	/**
	 * Créé un {@link IJetHelper}.
	 * @param nodes la liste des {@link ActivityNode} à générer.
	 * @param edges la liste des {@link ActivityEdge} à générer.
	 * @param prop la {@link IProperties}.
	 */
	public IJetHelper newJetHelper(EList<ActivityNode> nodes, EList<ActivityEdge> edges, IProperties propertie) {
		return new JetHelper(nodes, edges, propertie);
	}
	
	/**
	 * Créé un {@link IJetTemplate}.
	 */
	public IJetTemplate newJetTemplate() {
		return new JetTemplate();
	}
	
	/**
	 * Créé un {@link IAlloyGenerated} qui sera utilisé par le {@link IAlloyExecutor}.
	 * @param file le {@link File} généré.
	 * @param la {@link IProperties} qui a permis de générer ce fichier.
	 */
	public IAlloyGenerated newAlloyGenerated(File file, IProperties property) {
		return new AlloyGenerated(file, property);
	}
}
