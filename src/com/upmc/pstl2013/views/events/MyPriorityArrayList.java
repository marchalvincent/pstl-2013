package com.upmc.pstl2013.views.events;

import java.util.ArrayList;


public class MyPriorityArrayList extends ArrayList<JobExecutor> implements Comparable<MyPriorityArrayList> {

	private static final long serialVersionUID = 1L;
	private int priority;
	
	public MyPriorityArrayList(int priority) {
		super();
		this.priority = priority;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + priority;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (getClass() != obj.getClass()) return false;
		MyPriorityArrayList other = (MyPriorityArrayList) obj;
		if (priority != other.priority) return false;
		return (super.equals(obj));
	}


	public int getPriority() {
		return priority;
	}

	@Override
	public int compareTo(MyPriorityArrayList compare) {
		return getPriority() - compare.getPriority();
	}
}
