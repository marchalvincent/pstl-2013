package com.upmc.pstl2013.views.events;


/**
 * 
 * La factory des Events.
 */
public class EventFactory {
	
	private static final EventFactory instance = new EventFactory();

	private EventFactory() {}

	/**
	 * Renvoie l'unique instance de la Factory.
	 * 
	 * @return {@link EventFactory}.
	 */
	public static EventFactory getInstance() {
		return instance;
	}
	
	/**
	 * Créé un {@link MyJobPoolExecutor}.
	 * @param nbMaxThread le nombre maximum de thread à lancer en même temps.
	 */
	public MyJobPoolExecutor newJobPoolExecutor(int nbMaxThread) {
		return new MyJobPoolExecutor(nbMaxThread);
	}
}
