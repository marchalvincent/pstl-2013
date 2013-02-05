package com.upmc.pstl2013.factory;

import java.io.File;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityNode;
import com.upmc.pstl2013.alloyExecutor.IAlloyExecutor;
import com.upmc.pstl2013.alloyExecutor.impl.AlloyExecutor;
import com.upmc.pstl2013.alloyGenerator.GeneratorFactory;
import com.upmc.pstl2013.alloyGenerator.IAlloyGenerated;
import com.upmc.pstl2013.alloyGenerator.IAlloyGenerator;
import com.upmc.pstl2013.alloyGenerator.jet.IJetHelper;
import com.upmc.pstl2013.alloyGenerator.jet.IJetTemplate;
import com.upmc.pstl2013.infoGenerator.IInfoGenerator;
import com.upmc.pstl2013.infoGenerator.impl.InfoGenerator;
import com.upmc.pstl2013.infoParser.IInfoParser;
import com.upmc.pstl2013.infoParser.impl.InfoParser;
import com.upmc.pstl2013.properties.IAttribute;
import com.upmc.pstl2013.properties.IProperties;
import com.upmc.pstl2013.properties.PropertiesFactory;
import com.upmc.pstl2013.properties.impl.PropertiesException;
import com.upmc.pstl2013.strategy.IStrategy;
import com.upmc.pstl2013.strategy.impl.PathStrategy;
import com.upmc.pstl2013.umlParser.IUMLParser;
import com.upmc.pstl2013.umlParser.impl.UMLParser;
import com.upmc.pstl2013.views.SwtView;
import com.upmc.pstl2013.views.events.JobExecutor;

/**
 * La factory. Implémente le Design Pattern Singleton.
 */
public class Factory implements IFactory {

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

	@Override
	public IJetHelper newJetHelper(EList<ActivityNode> nodes, EList<ActivityEdge> edges, IProperties propertie) {
		return GeneratorFactory.getInstance().newJetHelper(nodes, edges, propertie);
	}

	@Override
	public IJetTemplate newJetTemplate() {
		return GeneratorFactory.getInstance().newJetTemplate();
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
		return GeneratorFactory.getInstance().newAlloyGenerator(infoGenerator, parser);
	}

	@Override
	public IAlloyExecutor newAlloyExecutor(IAlloyGenerator generator, String userDir) {
		return new AlloyExecutor(generator, userDir);
	}

	@Override
	public IProperties getPropertie(String name) throws PropertiesException {
		return PropertiesFactory.getInstance().createPropertie(name);
	}

	@Override
	public IAlloyGenerated newAlloyGenerated(File file, Boolean isCheck, IStrategy strategy) {
		return GeneratorFactory.getInstance().newAlloyGenerated(file, isCheck, strategy);
	}

	@Override
	public IStrategy newPathStrategy() {
		return new PathStrategy();
	}

	@Override
	public IAttribute newAttribute(String key, Object value, Boolean isPrivate) {
		return PropertiesFactory.getInstance().newAttribute(key, value, isPrivate);
	}

	@Override
	public JobExecutor newJobExecutor(String name, List<IProperties> properties, SwtView swtView) {
		return new JobExecutor(name, properties, swtView);
	}
}
