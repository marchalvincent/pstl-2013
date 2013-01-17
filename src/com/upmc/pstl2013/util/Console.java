package com.upmc.pstl2013.util;

public class Console {

	public static void debug (String s, Class<?> c) {
		System.out.println("[DEBUG] " + c.getName() + " - " + s);
	}
	
	public static void warning (String s, Class<?> c) {
		System.err.println("[WARN] " + c.getName() + " - " + s);
	}
}
