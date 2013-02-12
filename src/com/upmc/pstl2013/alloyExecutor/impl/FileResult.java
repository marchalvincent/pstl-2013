package com.upmc.pstl2013.alloyExecutor.impl;

import java.util.List;
import com.upmc.pstl2013.alloyExecutor.IActivityResult;
import com.upmc.pstl2013.alloyExecutor.IFileResult;

/**
 * Représente le résultat d'un exécuteur pour un fichier donné.
 *
 */
public class FileResult implements IFileResult {

	private String nom;
	private List<IActivityResult> activityResults;
	
	public FileResult(String nom, List<IActivityResult> activityResults) {
		super();
		this.nom = nom;
		this.activityResults = activityResults;
	}
	
	@Override
	public String getNom() {
		return nom;
	}

	@Override
	public List<IActivityResult> getListActivityResult() {
		return activityResults;
	}
}
