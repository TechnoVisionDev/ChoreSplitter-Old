package data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;

/**
 * Interfaces with the MongoDB database.
 * @author Thomas Peters
 */
public class Database {
	
	private final UpdateOptions upsert;
	
	private final MongoCollection<Document> users;
	private final MongoCollection<Document> groups;
	
	/**
     * Connect to database using MongoDB URI and
     * initialize any collections that don't exist.
	 */
	public Database() {
		// Register custom codecs
		upsert = new UpdateOptions().upsert(true);        
        CodecRegistry defaultCodecRegistry = MongoClientSettings.getDefaultCodecRegistry();
        CodecRegistry fromProvider = CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build());
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(defaultCodecRegistry, fromProvider);
        
        // Setup MongoDB client with URI and connect to main database.
        ConnectionString connectionString = new ConnectionString(System.getenv("DATABASE"));
        MongoClientSettings clientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .codecRegistry(pojoCodecRegistry)
                .build();
        
        // Initialize collections and indexes if they don't exist.
        MongoClient mongoClient = MongoClients.create(clientSettings);
        MongoDatabase database = mongoClient.getDatabase("ChoreSplitter").withCodecRegistry(pojoCodecRegistry);
           
        users = database.getCollection("users");
        users.createIndex(Filters.eq("email", 1), new IndexOptions().unique(true));
        users.createIndex(Filters.eq("group", 1));
            
        groups = database.getCollection("groups");
        groups.createIndex(Filters.eq("group", 1), new IndexOptions().unique(true));
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
		doc.append("avatar", user.getAvatar());
		users.insertOne(doc);
	}
	
	/**
	 * Retrieves user document to access values from.
	 * @param email email of the user to access.
	 * @return User document, null if not found.
	 */
	public Document getUser(String email) {
		Document user = users.find(Filters.eq("email", email)).first();
		if (user != null) {
			return user;
		}
		return null;
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
	 * Retrieves a value from the user document in database.
	 * @param email The email of the user.
	 * @param key The key of the value you are requesting.
	 * @return String value if found, otherwise null.
	 */
	public String getUserValue(String email, String key) {
		Document user = users.find(Filters.eq("email", email)).first();
		if (user != null) {
			return user.getString(key);
		}
		return null;
	}
	
	/**
	 * Adds a user to a housing group using a unique code.
	 * @param email of the user stored in session.
	 */
	public void addUserToGroup(String email, String code) {
		Document user = users.find(Filters.eq("email", email)).first();
		if (user != null) {
			users.updateOne(Filters.eq("email", email), Updates.set("group", code), upsert);
		}
	}
	
	/**
	 * Checks if the specified group code exists in database.
	 * @param unique group code.
	 * @return True if code points to a group, false if not.
	 */
	public boolean isGroup(String code) {
		return (users.countDocuments(Filters.eq("group", code)) > 0);
	}

    /**
	 * Creates a new group with code in database.
	 * @param unique group code.
	 */
	public void createGroup(String code) {
		Document group = new Document("group", code);
		group.append("chores", new LinkedList<Chore>());
		groups.insertOne(group);
	}
	
	/**
	 * Retrieves a list of users in a group sorted by points.
	 * @return list of users in same group.
	 */
	public List<User> getGroup(String code) {
		Bson match = Aggregates.match(Filters.eq("group", code));
		Bson sort = Aggregates.sort(Sorts.descending("points"));
		return users.aggregate(Arrays.asList(match, sort), User.class).into(new ArrayList<User>());
	}
	
	/**
	 * Adds a chore object to a group document's list of chores.
	 * @param unique group code.
	 * @param chore to be added to list.
	 */
	public void addChore(String code, Chore chore) {
		Bson update = Updates.push("chores", chore);
		groups.updateOne(Filters.eq("group", code), update);
	}
	
	/**
	 * Deletes a chore object from the group document's list of chores.
	 * @param unique group code.
	 * @param index of chore in array.
	 */
	public void deleteChore(String code, int index) {
		groups.updateOne(Filters.eq("group", code), Updates.unset("chores." + index));
		groups.updateOne(Filters.eq("group", code), Updates.pull("chores", null));
	}
	
	/**
	 * Retrieves a list of chore objects from a group document.
	 * @param unique group code.
	 * @return List of chore objects, null if group not found.
	 */
	public List<Chore> getChores(String code) {
		Document group = groups.find(Filters.eq("group", code)).first();
		if (group != null) {
			List<Chore> chores = new ArrayList<Chore>();
			for (Document doc : group.getList("chores", Document.class)) {
				chores.add(new Chore(doc.getString("name"), doc.getString("description"), doc.getInteger("points"), doc.getInteger("time"), doc.getString("claimed")));
			}
			return chores;
		}
		return null;
	}
	
	/**
	 * Marks a chore in group array as claimed by user email.
	 * @param email email of the user claiming the chore.
	 * @param code unique group code.
	 * @param index of the chore in array.
	 */
	public void claimChore(String email, String code, int index) {
		Bson update = Updates.set("chores." + index + ".claimed", email);
		groups.updateOne(Filters.eq("group", code), update);
	}
	
	/**
	 * Completes a chore, deleting it from database and awarding points to user.
	 * @param code unique group code.
	 * @param index of the chore in array.
	 */
	public void completeChore(String code, int index) {
		Document group = groups.find(Filters.eq("group", code)).first();
		if (group != null) {
			// Get chore data
			Document chore = group.getList("chores", Document.class).get(index);
			int points = chore.getInteger("points");
			String email = chore.getString("claimed");
			
			// Add points to user
			users.find(Filters.eq("email", email)).first();
			users.updateOne(Filters.eq("email", email), Updates.inc("points", points));
			
			// Delete chore from database
			groups.updateOne(Filters.eq("group", code), Updates.unset("chores." + index));
			groups.updateOne(Filters.eq("group", code), Updates.pull("chores", null));
		}
	}
}
