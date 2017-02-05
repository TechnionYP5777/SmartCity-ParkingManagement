package gui.manager.OrLearning;

import gui.manager.OrLearning.CreatingABasicWindow.Color;

public class parkingAreas {
	private String name;
	private Color color;
	private int spots;

	// Constructors
	parkingAreas() {
		this.name = "";
		this.color = Color.GRAY;
		this.spots = 0;
	}

	parkingAreas(String name, Color color, int spots) {
		this.name = name;
		this.color = color;
		this.spots = spots;
	}

	// Getters & Setters
	public String getName() {
		return this.name;
	}

	void setName(String name) {
		this.name = name;
	}

	public Color getColor() {
		return this.color;
	}

	void setColor(Color ¢) {
		this.color = ¢;
	}

	public int getSpots() {
		return this.spots;
	}

	void setSpots(int spots) {
		this.spots = spots;
	}
}
