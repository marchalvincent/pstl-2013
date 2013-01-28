package com.upmc.pstl2013.views.events;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Text;

import com.upmc.pstl2013.alloyExecutor.IAlloyExecutor;
import com.upmc.pstl2013.infoGenerator.IInfoGenerator;
import com.upmc.pstl2013.strategy.IStrategy;
import com.upmc.pstl2013.strategy.impl.PathStrategy;
import com.upmc.pstl2013.views.LogCreator;

public abstract class AbstractEventExecutor extends MouseAdapter 
{
	private Text textArea,textDirectory;
	private IAlloyExecutor alloyExecutor;
	private IInfoGenerator infoGenerator;
	private static Logger log = Logger.getLogger(AbstractEventExecutor.class);

	public AbstractEventExecutor(Text textArea,Text textDirectory,IAlloyExecutor alloyExecutor,IInfoGenerator infoGenerator)
	{
		this.textArea = textArea;
		this.textDirectory = textDirectory;
		this.alloyExecutor = alloyExecutor;
		this.infoGenerator = infoGenerator;
	}
	
	@Override
	public void mouseDown(MouseEvent evt) 
	{
		infoGenerator.setProperties(getProperties());
		// on définit les strategies de parcours
		//TODO voir comment on génère les strategy
		List<IStrategy> strategies = new ArrayList<IStrategy>();
		strategies.add(new PathStrategy());

		log.debug("Génération et exécution des fichiers Alloy.");
		StringBuilder result = new StringBuilder();
		try {
			result.append(alloyExecutor.executeFiles(strategies));
			result.append("Fin d'exécution des fichiers Alloy.");
			log.debug(result.toString());
			textArea.setText(result.toString());
		} catch (Exception e) {
			log.debug(e.getMessage());
			textArea.setText(e.getMessage());
		}
		alloyExecutor.reset();
		System.out.println(log.getAllAppenders().toString());

		//Création du fichier de log
		try {
			LogCreator.createLog(textDirectory.getText());
		} catch (IOException e1) {
			log.error(e1.getMessage());
			textArea.setText(e1.getMessage());
		}
	}

	protected abstract Map<String, Map<String, String>> getProperties();
}