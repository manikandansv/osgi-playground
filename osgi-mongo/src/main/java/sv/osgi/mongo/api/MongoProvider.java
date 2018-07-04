package sv.osgi.mongo.api;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

public interface MongoProvider {

	MongoClient getMongoClient();
	
	MongoDatabase getMongoDatabase();

	MongoClientConfiguration getMongoClientConfiguration();
	
}
