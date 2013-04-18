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
	private String nbState;
	
	public ActivityResult(String nom) {
		super();
		this.nom = nom;
		this.logs = new StringBuilder();
		this.XMLFile = null;
		this.satifiable = false;
		nbState = "";
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
	public void resetLog() {
		logs.delete(0, logs.length());
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
	
	public String getNbState() {
		return nbState;
	}

	@Override
	public void setNbState(String nbState) {
		this.nbState = nbState;
	}
}
