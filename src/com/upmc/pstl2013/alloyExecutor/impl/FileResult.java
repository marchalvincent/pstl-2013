package com.upmc.pstl2013.alloyExecutor.impl;

import com.upmc.pstl2013.alloyExecutor.IActivityResult;
import com.upmc.pstl2013.alloyExecutor.IFileResult;

/**
 * Représente le résultat d'un exécuteur pour un fichier donné.
 *
 */
public class FileResult implements IFileResult {

	private String nom;
	private IActivityResult activityResult;
	
	public FileResult(String nom, IActivityResult activityResults) {
		super();
		this.nom = nom;
		this.activityResult = activityResults;
	}
	
	@Override
	public String getNom() {
		return nom;
	}

	@Override
	public IActivityResult getActivityResult() {
		return activityResult;
	}
}
