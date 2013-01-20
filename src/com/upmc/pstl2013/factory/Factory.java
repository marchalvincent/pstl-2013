package com.upmc.pstl2013.factory;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityNode;

import com.upmc.pstl2013.AlloyExecutor.IAlloyExecutor;
import com.upmc.pstl2013.AlloyExecutor.impl.AlloyExecutor;
import com.upmc.pstl2013.alloyGenerator.IAlloyGenerator;
import com.upmc.pstl2013.alloyGenerator.impl.AlloyGenerator;
import com.upmc.pstl2013.alloyGenerator.impl.IJetHelper;
import com.upmc.pstl2013.alloyGenerator.impl.JetHelper;
import com.upmc.pstl2013.alloyGenerator.impl.JetTemplate;
import com.upmc.pstl2013.umlContainer.IUMLFileContainer;
import com.upmc.pstl2013.umlContainer.impl.UMLFileContainer;
import com.upmc.pstl2013.umlParser.IUMLParser;
import com.upmc.pstl2013.umlParser.impl.UMLParser;

/**
 * La factory. Implémente le Design Pattern Singleton.
 */
public class Factory implements IFactory {
	
	private static final Factory instance = new Factory();
	
	private Factory() {}
	
	/**
	 * Renvoie l'unique instance de la Factory.
	 * @return {@link Factory}.
	 */
	public static Factory getInstance() {
		return instance;
	}

	@Override
	public IJetHelper newJetHelper(EList<ActivityNode> nodes,
			EList<ActivityEdge> edges) {
		return new JetHelper(nodes, edges);
	}

	@Override
	public JetTemplate newJetTemplate() {
		return new JetTemplate();
	}
	
	@Override
	public IUMLFileContainer newFileContainer() {
		return new UMLFileContainer();
	}

	@Override
	public IUMLParser newParser(IUMLFileContainer fileContainer) {
		return new UMLParser(fileContainer);
	}

	@Override
	public IAlloyGenerator newAlloyGenerator(IUMLParser parser) {
		return new AlloyGenerator(parser);
	}

	@Override
	public IAlloyExecutor newAlloyExecutor(IAlloyGenerator generator) {
		return new AlloyExecutor(generator);
	}
}
