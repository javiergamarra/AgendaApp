package com.liferay.agenda.agendaapp;

public class Event {

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
