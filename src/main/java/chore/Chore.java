package chore;

public class Chore {
	
	private String name;
	private String description;
	private int points;
	private int completion_time;
	private String state;
	
	public Chore(String name, String description, int points, int completion_time) {
		this.name = name;
		this.description = description;
		this.points = points;
		this.completion_time = completion_time;
		this.state = "Incomplete";
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String n) {
		name = n;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String d) {
		description = d;
	}
	
	public int getPoints() {
		return points;
	}
	
	public void setPoints(int p) { // need error checking somewhere else that p > 0
		points = p;
	}
	
	public int getCompletionTime() {
		return completion_time;
	}
	
	public void setCompletionTime(int t) {
		completion_time = t;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String s) {
		state = s;
	}

}
