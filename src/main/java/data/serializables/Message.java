package data.serializables;

/**
 * Represents a serializable chat message.
 * @author Thomas Peters
 */
public class Message {
	
	private String name;
	private String avatar;
	private String msg;
	
	public String getName() {
		return name;
	}
	
	public String getAvatar() {
		return avatar;
	}
	
	public String getMsg() {
		return msg;
	}
}
