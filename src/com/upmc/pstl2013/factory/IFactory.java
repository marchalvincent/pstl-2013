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
import com.upmc.pstl2013.umlParser.IUMLParser;
import com.upmc.pstl2013.umlParser.impl.UMLParser;

/**
 * Représente les objets que la factory doit savoir créer.
 */
public interface IFactory {
	
	/**
	 * Créé un {@link JetHelper}.
	 * @return
	 */
	IJetHelper newJetHelper(EList<ActivityNode> nodes, EList<ActivityEdge> edges);
	
	/**
	 * Créé un {@link JetTemplate}.
	 * @return
	 */
	JetTemplate newJetTemplate();

	/**
	 * Créé un {@link IUMLFileContainer}.
	 */
	IUMLFileContainer newFileContainer();
	
	/**
	 * Créé un {@link UMLParser}.
	 * @param parser un {@link IUMLFileContainer}.
	 */
	IUMLParser newParser(IUMLFileContainer fc);
	
	/**
	 * Créé un {@link AlloyGenerator}.
	 * @param parser un {@link IUMLParser}.
	 */
	IAlloyGenerator newAlloyGenerator(IUMLParser parser);
	
	/**
	 * Créé un {@link AlloyExecutor}.
	 * @param generator un {@link IAlloyGenerator}.
	 */
	IAlloyExecutor newAlloyExecutor(IAlloyGenerator generator);
}
