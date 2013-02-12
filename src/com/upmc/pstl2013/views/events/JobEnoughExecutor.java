package com.upmc.pstl2013.views.events;

import org.eclipse.core.resources.IFile;
import com.upmc.pstl2013.properties.IProperties;
import com.upmc.pstl2013.views.SwtView;


public class JobEnoughExecutor extends JobExecutor {

	public JobEnoughExecutor(String name, SwtView swtView, IFile UMLFile, IProperties property) {
		super(name, swtView, UMLFile, property);
	}
	
	// TODO redéfinir le run pour faire le join, le set etc...
}
