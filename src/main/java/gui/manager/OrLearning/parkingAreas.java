package gui.manager.OrLearning;

import gui.manager.OrLearning.CreatingABasicWindow.Color;

public class parkingAreas {
	private String name;
	private Color color;
	private int spots;

	// Constructors
	parkingAreas() {
		name = "";
		color = Color.GRAY;
		spots = 0;
	}

	parkingAreas(final String name, final Color color, final int spots) {
		this.name = name;
		this.color = color;
		this.spots = spots;
	}

	// Getters & Setters
	public String getName() {
		return name;
	}

	void setName(final String name) {
		this.name = name;
	}

	public Color getColor() {
		return color;
	}

	void setColor(final Color ¢) {
		color = ¢;
	}

	public int getSpots() {
		return spots;
	}

	void setSpots(final int spots) {
		this.spots = spots;
	}
}
