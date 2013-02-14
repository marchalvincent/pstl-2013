package com.upmc.pstl2013.alloyExecutor;

import java.util.List;
import org.eclipse.core.resources.IFile;
import com.upmc.pstl2013.alloyExecutor.impl.ActivityResult;
import com.upmc.pstl2013.alloyExecutor.impl.AlloyExecutor;
import com.upmc.pstl2013.alloyExecutor.impl.FileResult;
import com.upmc.pstl2013.alloyExecutor.impl.MyA4Reporter;
import com.upmc.pstl2013.properties.IProperties;


/**
 * Représente la factory pour les objets utilisés par le {@link IAlloyExecutor}.
 *
 */
public class ExecutorFactory {
	
	private static ExecutorFactory instance = new ExecutorFactory();
	
	public static ExecutorFactory getInstance() {
		return instance;
	}
	
	private ExecutorFactory() {}

	public IAlloyExecutor newAlloyExecutor(IFile UMLFile, String dirDestination, IProperties property, int counterExecution) {
		return new AlloyExecutor(UMLFile, dirDestination, property, counterExecution);
	}
	
	public IActivityResult newActivityResult(String nom) {
		return new ActivityResult(nom);
	}

	public IFileResult newFileResult(String nom, List<IActivityResult> activityResults) {
		return new FileResult(nom, activityResults);
	}
	
	public MyA4Reporter newReporter() {
		return new MyA4Reporter();
	}
}
