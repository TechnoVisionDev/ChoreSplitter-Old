package data;

public class Chore {
	
	private String name;
	private String description;
	private int points;
	private int time;
	
	public Chore(String name, String description, int points, int time) {
		this.name = name;
		this.description = description;
		this.points = points;
		this.time = time;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public int getPoints() {
		return points;
	}
	
	public int getTime() {
		return time;
	}

}
