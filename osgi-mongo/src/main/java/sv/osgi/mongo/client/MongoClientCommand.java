package sv.osgi.mongo.client;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.mongodb.client.MongoDatabase;

import sv.osgi.mongo.api.MongoProvider;
import sv.osgi.mongo.api.MongoProviderFactory;

@Component(enabled=true, immediate=true, service=MongoClientCommand.class)
public class MongoClientCommand {

	@Reference
	private MongoProviderFactory mongoProviderFactory;
	
	public String testConnection() {
		
		MongoProvider mp = mongoProviderFactory.getMongoProvider("nigazhchiClient");
	
		MongoDatabase mb = mp.getMongoDatabase();
	
		return mb.getName();
	
	}
	
}
