package data.serializables;

public class Chore {
	
	private String name;
	private String description;
	private int points;
	private int time;
	private String claimed;
	private String avi;
	
	public Chore(String name, String description, int points, int time) {
		this.name = name;
		this.description = description;
		this.points = points;
		this.time = time;
		this.claimed = "";
		this.avi="";
	}
	
	public Chore(String name, String description, int points, int time, String claimed) {
		this.name = name;
		this.description = description;
		this.points = points;
		this.time = time;
		this.claimed = claimed;
		this.avi="";
	}
	
	public Chore(String name, String description, int points, int time, String claimed, String avi) {
		this.name = name;
		this.description = description;
		this.points = points;
		this.time = time;
		this.claimed = claimed;
		this.avi=avi;
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
	
	public String getClaimed() {
		return claimed;
	}
	
	public String getAvi() {
		return avi;
	}
}
