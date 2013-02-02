package com.upmc.pstl2013.factory;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityNode;
import com.upmc.pstl2013.alloyExecutor.IAlloyExecutor;
import com.upmc.pstl2013.alloyGenerator.IAlloyGenerator;
import com.upmc.pstl2013.alloyGenerator.impl.IJetHelper;
import com.upmc.pstl2013.alloyGenerator.jet.IJetTemplate;
import com.upmc.pstl2013.infoGenerator.IInfoGenerator;
import com.upmc.pstl2013.infoParser.IInfoParser;
import com.upmc.pstl2013.properties.IProperties;
import com.upmc.pstl2013.properties.impl.PropertiesException;
import com.upmc.pstl2013.umlParser.IUMLParser;

/**
 * Représente les objets que la factory doit savoir créer.
 */
public interface IFactory {

	/**
	 * Créé un {@link IJetHelper}.
	 * @param nodes la liste des {@link ActivityNode} à générer.
	 * @param edges la liste des {@link ActivityEdge} à générer.
	 * @param prop la {@link IProperties}.
	 * @return
	 */
	IJetHelper newJetHelper(EList<ActivityNode> nodes, EList<ActivityEdge> edges, IProperties prop);

	/**
	 * Créé un {@link IJetTemplate}.
	 */
	IJetTemplate newJetTemplate();

	/**
	 * Créé un {@link IInfoParser}.
	 */
	IInfoParser newInfoParser();

	/**
	 * Créé un {@link IInfoGenerator}.
	 */
	IInfoGenerator newInfoGenerator();

	/**
	 * Créé un {@link IUMLParser}.
	 * 
	 * @param parser
	 *            un {@link IInfoParser}.
	 */
	IUMLParser newParser(IInfoParser fc);

	/**
	 * Créé un {@link IAlloyGenerator}.
	 * 
	 * @param infoGenerator
	 *            un {@link IInfoGenerator}.
	 * @param parser
	 *            un {@link IUMLParser}.
	 */
	IAlloyGenerator newAlloyGenerator(IInfoGenerator infoGenerator, IUMLParser parser);

	/**
	 * Créé un {@link IAlloyExecutor}.
	 * 
	 * @param generator
	 *            un {@link IAlloyGenerator}.
	 */
	IAlloyExecutor newAlloyExecutor(IAlloyGenerator generator);

	/**
	 * Renvoie la {@link IProperties} associée au nom passé en paramètre.
	 * 
	 * @param name le nom de la propriété.
	 * @throws PropertiesException si le nom de la propertie est incorrect.
	 */
	IProperties getPropertie(String name) throws PropertiesException;
}
