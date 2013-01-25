package com.upmc.pstl2013.factory;

import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityNode;

import com.upmc.pstl2013.alloyExecutor.IAlloyExecutor;
import com.upmc.pstl2013.alloyExecutor.impl.AlloyExecutor;
import com.upmc.pstl2013.alloyGenerator.IAlloyGenerator;
import com.upmc.pstl2013.alloyGenerator.impl.AlloyGenerator;
import com.upmc.pstl2013.alloyGenerator.impl.IJetHelper;
import com.upmc.pstl2013.alloyGenerator.impl.JetHelper;
import com.upmc.pstl2013.alloyGenerator.impl.JetTemplate;
import com.upmc.pstl2013.infoGenerator.IInfoGenerator;
import com.upmc.pstl2013.infoGenerator.impl.InfoGenerator;
import com.upmc.pstl2013.infoParser.IInfoParser;
import com.upmc.pstl2013.infoParser.impl.InfoParser;
import com.upmc.pstl2013.properties.IProperties;
import com.upmc.pstl2013.properties.impl.PropertiesFactory;
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
	public IJetHelper newJetHelper(EList<ActivityNode> nodes, EList<ActivityEdge> edges, 
			ActivityNode init, ActivityNode fin, IProperties prop) {
		return new JetHelper(nodes, edges, init, fin, prop);
	}

	@Override
	public JetTemplate newJetTemplate() {
		return new JetTemplate();
	}
	
	@Override
	public IInfoParser newInfoParser() {
		return new InfoParser();
	}

	@Override
	public IInfoGenerator newInfoGenerator() {
		return new InfoGenerator();
	}

	@Override
	public IUMLParser newParser(IInfoParser fileContainer) {
		return new UMLParser(fileContainer);
	}

	@Override
	public IAlloyGenerator newAlloyGenerator(IInfoGenerator infoGenerator, IUMLParser parser) {
		return new AlloyGenerator(infoGenerator, parser);
	}

	@Override
	public IAlloyExecutor newAlloyExecutor(IAlloyGenerator generator) {
		return new AlloyExecutor(generator);
	}

	@Override
	public IProperties newPropertie(Map<String, Map<String, String>> map) {
		return PropertiesFactory.createPropertie(map);
	}
}
