package com.upmc.pstl2013.properties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class PropertyManager implements Iterator<String> {

	private List<String> propertyNamesToExecute;
	private Map<String, IProperties> propertyNames;
	
	public PropertyManager(List<IProperties> properties) {
		super();
		propertyNamesToExecute = new ArrayList<String>();
		propertyNames = new HashMap<String, IProperties>();
		
		for (IProperties property : properties) {
			String name = property.getClass().getSimpleName();
			propertyNamesToExecute.add(name);
			propertyNames.put(name, property);
		}
	}

	@Override
	public boolean hasNext() {
		return !propertyNamesToExecute.isEmpty();
	}

	@Override
	public String next() {
		System.out.print("avant next : ");
		for (String n : propertyNamesToExecute) {
			System.out.print(n + ", ");
		}
		System.out.println();
		
		// on récupère un nom au hazard
		String name = propertyNamesToExecute.get(0);
		
		// on récupère la propriété
		IProperties prop = propertyNames.get(name);
		
		// on remonte jusqu'à trouver sa dépendance
		String dependance = prop.getDependance();
		while (dependance != null) {
			// si la dépendance n'est plus dans la liste à exécuter, on sors de la boucle
			// car on doit exécuter cette propriété
			if (!propertyNamesToExecute.contains(dependance)) {
				break;
			}
			
			prop = propertyNames.get(prop.getDependance());
			dependance = prop.getDependance();
		}
		
		name = prop.getClass().getSimpleName();
		// on la supprime de la liste 
		propertyNamesToExecute.remove(name);
		System.out.println("on supprime " + name);

		System.out.print("apres next : ");
		for (String n : propertyNamesToExecute) {
			System.out.print(n + ", ");
		}
		System.out.println();
		
		// on construit le string qui sera parsé par l'objet qui lance les Jobs
		return name + "~" + dependance;
	}

	@Override
	public void remove() {}
}
