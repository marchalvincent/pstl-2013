package com.upmc.pstl2013.views;

import java.awt.event.MouseAdapter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.upmc.pstl2013.util.Console;

public class ContentViewMouseListener extends MouseAdapter 
{
	private final String separator = File.separator;
	private final String userDir = System.getProperty("user.home") + separator + ".pstl2013" + separator;
	private String contentLog;
	
	public ContentViewMouseListener (String content)
	{
		contentLog = content;
	}
	
	@Override
	public void mouseClicked(java.awt.event.MouseEvent e) 
	{
		Console.debug("Génération du fichier logs.", this.getClass());

		try {
			// on créé le fichier a générer
			File fichier = new File(userDir + "logs" + ".txt");

			// puis on écrit le contenu dedans
			FileOutputStream out = new FileOutputStream(fichier);
			out.write(contentLog.getBytes());
			out.close();

			Console.debug("Génération terminée.", this.getClass());
		}
		catch (FileNotFoundException ex) {
			Console.warning("Impossible de trouver le fichier : " + ex.toString(), this.getClass());
		}
		catch (IOException ex2) {
			Console.warning("Impossible de créer le fichier : " + ex2.toString(), this.getClass());
		}
		Console.debug("Générations finies.", this.getClass());
	}

	@Override
	public void mouseEntered(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
