package data;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexOptions;

/**
 * Interfaces with the MongoDB database.
 * @author Thomas Peters
 */
public class Database {
	
	private final MongoCollection<Document> users;
	
	/**
     * Connect to database using MongoDB URI and
     * initialize any collections that don't exist.
	 */
	public Database() {
		// Setup MongoDB client with URI and connect to main database.
        MongoClientURI clientURI = new MongoClientURI(System.getenv("DATABASE"));
        MongoClient mongoClient = new MongoClient(clientURI);
        MongoDatabase database = mongoClient.getDatabase("ChoreSplitter");
        
        // Initialize collections and indexes if they don't exist.
        users = database.getCollection("users");
        users.createIndex(Filters.eq("email", 1), new IndexOptions().unique(true));
	}
	
	/**
	 * Adds a user's data to the database upon registration.
	 * @param user object with registration data.
	 * @throws MongoWriteException if email is already in database
	 */
	public void registerUser(User user) throws MongoWriteException {
		Document doc = new Document();
		doc.append("email", user.getEmail());
		doc.append("name", user.getName());
		doc.append("password", user.getPassword());
		doc.append("points", user.getPoints());
		doc.append("avatar", "https://i.stack.imgur.com/l60Hf.png");
		users.insertOne(doc);
	}
	
	/**
	 * Checks email and password with database to validate login.
	 * @return true if email and password are valid combo, otherwise false.
	 */
	public boolean validateUser(String email, String password) {
		Document user = users.find(Filters.eq("email", email)).first();
		if (user != null) {
			return user.getString("password").equals(password);
		}
		return false;
	}
	
	/**
	 * Retrieves the name of the user from database.
	 * @return String of user's name if found, otherwise null.
	 */
	public String getName(String email) {
		Document user = users.find(Filters.eq("email", email)).first();
		if (user != null) {
			return user.getString("name");
		}
		return null;
	}
}
