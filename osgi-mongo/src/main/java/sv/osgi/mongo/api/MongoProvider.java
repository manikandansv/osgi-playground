package sv.osgi.mongo.api;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

public interface MongoProvider {
	
	String FACTORY = "sv.osgi.mongo.api.MongoProvider";

	MongoClient getMongoClient();
	
	MongoDatabase getMongoDatabase();

	MongoClientConfiguration getMongoClientConfiguration();
	
}
