package com.upmc.pstl2013.views.events;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

import com.upmc.pstl2013.alloyExecutor.IAlloyExecutor;
import com.upmc.pstl2013.infoGenerator.IInfoGenerator;
import com.upmc.pstl2013.strategy.IStrategy;
import com.upmc.pstl2013.strategy.impl.PathStrategy;
import com.upmc.pstl2013.views.SwtView;

public class JobExecutor extends Job {

	private Map<String, Map<String, String>> properties;
	private Text txtLogs;
	private IAlloyExecutor alloyExecutor;
	private IInfoGenerator infoGenerator;
	private static Logger log = Logger.getLogger(AbstractEventExecutor.class);

	public JobExecutor(String name, Map<String, Map<String, String>> properties,SwtView swtView) {
		
		super(name);
		this.properties = properties;
		this.txtLogs = swtView.getTxtLogs();
		this.alloyExecutor = swtView.getAlloyExecutor();
		this.infoGenerator = swtView.getInfoGenerator();
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {

		infoGenerator.setAttributes(properties);
		// on définit les strategies de parcours
		// TODO voir comment on génère les strategy
		List<IStrategy> strategies = new ArrayList<IStrategy>();
		strategies.add(new PathStrategy());
		log.info("Génération et exécution des fichiers Alloy.");
		StringBuilder result = new StringBuilder();

		try {
			result.append(alloyExecutor.executeFiles(strategies));
			result.append("Fin d'exécution des fichiers Alloy.");
			log.info(result.toString());
			showToView(result.toString());
		} catch (Exception e) {
			log.error(e.getMessage());
			showToView(e.getMessage());
		}
		alloyExecutor.reset();
		System.out.println(log.getAllAppenders().toString());

		return Status.OK_STATUS;
	}

	private void showToView(String msg){
		
		Display.getDefault().asyncExec(new RunnableUpDateExecutor(txtLogs, msg));
	}

}
