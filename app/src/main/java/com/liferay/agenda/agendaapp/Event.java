package com.liferay.agenda.agendaapp;

import java.io.Serializable;

public class Event implements Serializable {

	private final String hour;
	private final String name;

	public Event(String hour, String name) {
		this.hour = hour;
		this.name = name;
	}

	public String getHour() {
		return hour;
	}

	public String getName() {
		return name;
	}
}
