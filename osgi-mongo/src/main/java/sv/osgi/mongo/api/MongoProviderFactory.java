package sv.osgi.mongo.api;

public interface MongoProviderFactory {

	MongoProvider getMongoProvider(String clientId);

}
