package sv.osgi.mongo.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.metatype.annotations.Designate;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.Tag;
import com.mongodb.TagSet;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.connection.ClusterSettings;
import com.mongodb.connection.ConnectionPoolSettings;
import com.mongodb.connection.ServerSettings;
import com.mongodb.connection.SocketSettings;

import sv.osgi.mongo.api.MongoClientConfiguration;
import sv.osgi.mongo.api.MongoProvider;

@Component(name="MongoProvider",service = MongoProvider.class)
@Designate(ocd = MongoClientConfiguration.class, factory = true)
public class MongoProviderImpl implements MongoProvider {

	private MongoClient mongoClient;

	private String dbName;

	private MongoClientConfiguration mongoClientConfiguration;

	@Activate
	protected void activate(final MongoClientConfiguration mongoClientConfiguration) {

		this.mongoClientConfiguration = mongoClientConfiguration;

		this.dbName = mongoClientConfiguration.dbName();

		try {
			this.mongoClient = createMongoClient(mongoClientConfiguration);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

	private MongoClient createMongoClient(MongoClientConfiguration mongoClientConfiguration) {

		WriteConcern writeConcern = new WriteConcern(mongoClientConfiguration.writeConcernW(),
				mongoClientConfiguration.writeConcernWtimeout());

		writeConcern.withJournal(mongoClientConfiguration.writeConcernJ());

		ConnectionPoolSettings connectionPoolSettings = ConnectionPoolSettings.builder()
				.minSize(mongoClientConfiguration.minConnectionsPerHost())
				.maxWaitTime(mongoClientConfiguration.maxWaitTime(), TimeUnit.MILLISECONDS)
				.maxWaitQueueSize(mongoClientConfiguration.maxWaitQueueSize())
				.maxSize(mongoClientConfiguration.maxConnectionsPerHost())
				.maxConnectionLifeTime(mongoClientConfiguration.maxConnectionLifeTime(), TimeUnit.MILLISECONDS)
				.maxConnectionIdleTime(mongoClientConfiguration.maxConnectionIdleTime(), TimeUnit.MILLISECONDS)
				.maintenanceInitialDelay(mongoClientConfiguration.maintenanceInitialDelay(), TimeUnit.MILLISECONDS)
				.maintenanceFrequency(mongoClientConfiguration.maintenanceFrequency(), TimeUnit.MILLISECONDS).build();

		String serverAddresses = mongoClientConfiguration.serverAddresses();

		List<ServerAddress> serverAddressList = new ArrayList<>();

		// for (int i = 0; i < serverAddresses.length; i++) {

		ServerAddress serverAddress = new ServerAddress(serverAddresses.split(":")[0],
				Integer.parseInt(serverAddresses.split(":")[1]));

		serverAddressList.add(serverAddress);

		// }

		ClusterSettings clusterSettings = ClusterSettings.builder().hosts(serverAddressList).build();

		MongoCredential credential = MongoCredential.createCredential(mongoClientConfiguration.userName(),
				mongoClientConfiguration.dbName(), mongoClientConfiguration.password().toCharArray());

		SocketSettings socketSettings = SocketSettings.builder()
				.connectTimeout(mongoClientConfiguration.connectionTimeout(), TimeUnit.MILLISECONDS)
				.readTimeout(mongoClientConfiguration.socketTimeout(), TimeUnit.MILLISECONDS)
				.receiveBufferSize(mongoClientConfiguration.readBufferSize())
				.sendBufferSize(mongoClientConfiguration.sendBufferSize()).build();

		ServerSettings serverSettings = ServerSettings.builder()
				.heartbeatFrequency(mongoClientConfiguration.heartbeatFrequency(), TimeUnit.MILLISECONDS)
				.minHeartbeatFrequency(mongoClientConfiguration.minHeartbeatFrequency(), TimeUnit.MILLISECONDS).build();

		List<Tag> tags = new ArrayList<>();

		if (mongoClientConfiguration.readPreferenceTags() != null) {
			for (String tag : mongoClientConfiguration.readPreferenceTags()) {
				if (StringUtils.isNotEmpty(tag)) {
					String[] elements = tag.split("=");
					tags.add(new Tag(elements[0], elements[1]));
				}
			}
		}

		ReadPreference readPreference = ReadPreference.primary();
		if (!tags.isEmpty()) {

			TagSet tagSet = new TagSet(tags);

			int readPreferenceType = mongoClientConfiguration.readPreferenceType();

			switch (readPreferenceType) {
			case 1:
				readPreference = ReadPreference.nearest(tagSet);
				break;
			case 2:
				readPreference = ReadPreference.primary();
				break;
			case 3:
				readPreference = ReadPreference.primaryPreferred(tagSet);
				break;
			case 4:
				readPreference = ReadPreference.secondary(tagSet);
				break;
			case 5:
				readPreference = ReadPreference.secondaryPreferred(tagSet);
				break;
			default:
				readPreference = ReadPreference.primary();
				break;
			}
		}

		MongoClientSettings settings = MongoClientSettings.builder()
				.applicationName(mongoClientConfiguration.applicationName())
				.applyToConnectionPoolSettings(builder -> builder.applySettings(connectionPoolSettings))
				.applyToSslSettings(builder -> builder.enabled(mongoClientConfiguration.sslEnabled()))
				.applyToClusterSettings(builder -> builder.applySettings(clusterSettings))
				.applyToSocketSettings(builder -> builder.applySettings(socketSettings))
				.applyToServerSettings(builder -> builder.applySettings(serverSettings))
				.retryWrites(mongoClientConfiguration.retryWrites()).writeConcern(writeConcern).credential(credential)
				.readPreference(readPreference).build();

		MongoClient mongoClient = MongoClients.create(settings);

		return mongoClient;

	}

	@Deactivate
	protected void deactivate() {

		if (mongoClient != null) {

			mongoClient.close();

			mongoClient = null;

		}

	}

	@Override
	public MongoClient getMongoClient() {

		return mongoClient;

	}

	@Override
	public MongoClientConfiguration getMongoClientConfiguration() {

		return mongoClientConfiguration;

	}

	@Override
	public MongoDatabase getMongoDatabase() {

		if (mongoClient != null && StringUtils.isNotEmpty(dbName)) {

			return mongoClient.getDatabase(dbName);

		}

		return null;

	}

}
