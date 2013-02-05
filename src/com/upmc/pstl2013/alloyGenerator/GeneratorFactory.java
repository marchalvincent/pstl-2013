package com.upmc.pstl2013.alloyGenerator;

import java.io.File;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityNode;
import com.upmc.pstl2013.alloyGenerator.impl.AlloyGenerated;
import com.upmc.pstl2013.alloyGenerator.impl.AlloyGenerator;
import com.upmc.pstl2013.alloyGenerator.jet.IJetHelper;
import com.upmc.pstl2013.alloyGenerator.jet.IJetTemplate;
import com.upmc.pstl2013.alloyGenerator.jet.impl.JetHelper;
import com.upmc.pstl2013.alloyGenerator.jet.impl.JetTemplate;
import com.upmc.pstl2013.infoGenerator.IInfoGenerator;
import com.upmc.pstl2013.properties.IProperties;
import com.upmc.pstl2013.strategy.IStrategy;
import com.upmc.pstl2013.umlParser.IUMLParser;

/**
 * Représente la factory des objets utilisés dans le générateur alloy.
 *
 */
public class GeneratorFactory {
	
	private static GeneratorFactory instance = new GeneratorFactory();
	
	public static GeneratorFactory getInstance() {
		return instance;
	}

	public IAlloyGenerator newAlloyGenerator(IInfoGenerator infoGenerator, IUMLParser parser) {
		return new AlloyGenerator(infoGenerator, parser);
	}
	
	public IJetHelper newJetHelper(EList<ActivityNode> nodes, EList<ActivityEdge> edges, IProperties propertie) {
		return new JetHelper(nodes, edges, propertie);
	}
	
	public IJetTemplate newJetTemplate() {
		return new JetTemplate();
	}
	
	public IAlloyGenerated newAlloyGenerated(File fi, Boolean isC, IStrategy strat) {
		return new AlloyGenerated(fi, isC, strat);
	}
}
