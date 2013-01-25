package com.upmc.pstl2013.factory;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityNode;

import com.upmc.pstl2013.alloyExecutor.IAlloyExecutor;
import com.upmc.pstl2013.alloyExecutor.impl.AlloyExecutor;
import com.upmc.pstl2013.alloyGenerator.IAlloyGenerator;
import com.upmc.pstl2013.alloyGenerator.impl.AlloyGenerator;
import com.upmc.pstl2013.alloyGenerator.impl.IJetHelper;
import com.upmc.pstl2013.alloyGenerator.impl.JetTemplate;
import com.upmc.pstl2013.infoGenerator.IInfoGenerator;
import com.upmc.pstl2013.infoParser.IInfoParser;
import com.upmc.pstl2013.umlParser.IUMLParser;
import com.upmc.pstl2013.umlParser.impl.UMLParser;

/**
 * Représente les objets que la factory doit savoir créer.
 */
public interface IFactory {

	IJetHelper newJetHelper(EList<ActivityNode> nodes,
			EList<ActivityEdge> edges, ActivityNode init);

	/**
	 * Créé un {@link JetTemplate}.
	 */
	JetTemplate newJetTemplate();

	/**
	 * Créé un {@link IInfoParser}.
	 */
	IInfoParser newInfoParser();
	
	/**
	 * Créé un {@link IInfoGenerator}.
	 */
	IInfoGenerator newInfoGenerator();

	/**
	 * Créé un {@link UMLParser}.
	 * @param parser un {@link IInfoParser}.
	 */
	IUMLParser newParser(IInfoParser fc);

	/**
	 * Créé un {@link AlloyGenerator}.
	 * @param infoGenerator un {@link IInfoGenerator}.
	 * @param parser un {@link IUMLParser}.
	 */
	IAlloyGenerator newAlloyGenerator(IInfoGenerator infoGenerator, IUMLParser parser);

	/**
	 * Créé un {@link AlloyExecutor}.
	 * @param generator un {@link IAlloyGenerator}.
	 */
	IAlloyExecutor newAlloyExecutor(IAlloyGenerator generator);
}
