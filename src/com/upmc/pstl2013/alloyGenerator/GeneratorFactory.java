package com.upmc.pstl2013.alloyGenerator;

import java.io.File;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityNode;
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

	public IAlloyGenerator newAlloyGenerator(IFile UMLFile, String dirDestination, IProperties property) {
		return new AlloyGenerator(UMLFile, dirDestination, property);
	}
	
	public IJetHelper newJetHelper(EList<ActivityNode> nodes, EList<ActivityEdge> edges, IProperties propertie) {
		return new JetHelper(nodes, edges, propertie);
	}
	
	public IJetTemplate newJetTemplate() {
		return new JetTemplate();
	}
	
	public IAlloyGenerated newAlloyGenerated(File file, IProperties property) {
		return new AlloyGenerated(file, property);
	}
}
