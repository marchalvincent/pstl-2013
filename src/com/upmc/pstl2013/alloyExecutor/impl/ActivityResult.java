package com.upmc.pstl2013.alloyExecutor.impl;

import com.upmc.pstl2013.alloyExecutor.IActivityResult;

/**
 * Les informations d'un résultat d'une activité UML exécutée par Alloy.
 *
 */
public class ActivityResult implements IActivityResult {

	private String nom;
	private StringBuilder logs;
	private String XMLFile;
	private boolean satifiable;
	
	public ActivityResult(String nom) {
		super();
		this.nom = nom;
		this.logs = new StringBuilder();
		this.XMLFile = null;
		this.satifiable = false;
	}
	
	@Override
	public String getNom() {
		return nom;
	}

	@Override
	public String getLogResult() {
		return logs.toString();
	}

	@Override
	public String getPathXMLResult() {
		return XMLFile;
	}

	@Override
	public void appendLog(String s) {
		logs.append(s);
	}

	@Override
	public void setPathXMLResult(String path) {
		XMLFile = path;
	}

	@Override
	public boolean isSatisfiable() {
		return satifiable;
	}

	@Override
	public void setSatisfiable(boolean bool) {
		satifiable = bool;
	}
}
