package com.upmc.pstl2013.views.events;

import java.io.File;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import com.upmc.pstl2013.alloyExecutor.IActivityResult;
import com.upmc.pstl2013.util.Utils;
import com.upmc.pstl2013.views.SwtView;
import edu.mit.csail.sdg.alloy4viz.VizGUI;

public class EventClickVisualisationAlloy extends MouseAdapter{

	private VizGUI viz = null;
	private SwtView swtView;
	private String dirDestination;

	/**
	 * Affiche la visualisation d'alloy du resultat de l'exécution obtenu avec le {@link IActivityResult}.
	 * l'{@link IActivityResult} est passé via le setData lors de sa création.
	 * @param {@link SwtView}
	 */
	public EventClickVisualisationAlloy(SwtView swtView)
	{
		this.dirDestination = swtView.getUserDir();
		this.swtView = swtView;
	}

	public void mouseDown(MouseEvent e) {

		IActivityResult activityResult =  swtView.getCurrentActivityResult();
		if (activityResult != null)
		{
			// Et on lance le visualisateur de solution
			if (viz == null) {
				viz = new VizGUI(false, activityResult.getPathXMLResult(), null);
				if (!viz.loadThemeFile(Utils.pluginPath + "theme" + File.separator + "theme.thm"))
					activityResult.appendLog("Le fichier theme n'a pas été pris en compte\n." +
							"Etes vous sûre d'avoir le fichier theme.thm dans le répertoire : " + dirDestination + "theme ?");
			} else {
				viz.loadXML(activityResult.getPathXMLResult(), true);
			}

		}

	}




}
