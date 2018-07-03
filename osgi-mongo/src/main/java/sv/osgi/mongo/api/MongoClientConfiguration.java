package sv.osgi.mongo.api;

import org.apache.commons.lang3.StringUtils;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "MongoClientConfiguration", description = "The configuration for a mongo client")
public @interface MongoClientConfiguration {

	@AttributeDefinition(name = "Client ID", type = AttributeType.STRING, required=true)
	String clientId() default StringUtils.EMPTY;

	@AttributeDefinition(name = "Client URI", type = AttributeType.STRING, required=true)
	String clientURI() default StringUtils.EMPTY;

	@AttributeDefinition(name = "Always Use MBeans?", type = AttributeType.BOOLEAN)
	boolean alwaysUseMBeans() default false;

	@AttributeDefinition(name = "Connections PerHost", type = AttributeType.INTEGER)
	int connectionsPerHost() default 0;

	@AttributeDefinition(name = "Connection Timeout", type = AttributeType.INTEGER)
	int connectionTimeout() default 1000; 

	@AttributeDefinition(name = "Cursor Finalizer Enabled?", type = AttributeType.BOOLEAN)
	boolean cursorFinalizerEnabled() default false;

	@AttributeDefinition(name = "Client Description", type = AttributeType.STRING)
	String description() default StringUtils.EMPTY;

	@AttributeDefinition(name = "Heartbeat Connect Timeout", type = AttributeType.INTEGER)
	int heartbeatConnectTimeout() default 1000;

	@AttributeDefinition(name = "Heartbeat Frequency", type = AttributeType.INTEGER)
	int heartbeatFrequency() default 0;

	@AttributeDefinition(name = "Heartbeat Socket Timeout", type = AttributeType.INTEGER)
	int heartbeatSocketTimeout() default 1000;

	@AttributeDefinition(name = "Local Threshold", type = AttributeType.INTEGER)
	int localThreshold() default 0;

	@AttributeDefinition(name = "Max Connection Idle Time", type = AttributeType.INTEGER)
	int maxConnectionIdleTime() default 0;

	@AttributeDefinition(name = "Max Connection LifeTime", type = AttributeType.INTEGER)
	int maxConnectionLifeTime() default 0;

	@AttributeDefinition(name = "Max WaitTime", type = AttributeType.INTEGER)
	int maxWaitTime() default 0;
	
	@AttributeDefinition(name = "Max Wait Queue Size", type = AttributeType.INTEGER)
	int maxWaitQueueSize() default 10;

	@AttributeDefinition(name = "Min Connections PerHost", type = AttributeType.INTEGER)
	int minConnectionsPerHost() default 1;
	
	@AttributeDefinition(name = "Max Connections PerHost", type = AttributeType.INTEGER)
	int maxConnectionsPerHost() default 1;

	@AttributeDefinition(name = "Min Heartbeat Frequency", type = AttributeType.INTEGER)
	int minHeartbeatFrequency() default 0;

	@AttributeDefinition(name = "Read PreferenceType", type = AttributeType.INTEGER)
	int readPreferenceType() default 0;

	@AttributeDefinition(name = "Read PreferenceTags", type = AttributeType.STRING)
	String[] readPreferenceTags() default StringUtils.EMPTY;

	@AttributeDefinition(name = "Required Replicaset Name", type = AttributeType.STRING)
	String requiredReplicaSetName() default StringUtils.EMPTY;

	@AttributeDefinition(name = "Server SelectionTimeout", type = AttributeType.INTEGER)
	int serverSelectionTimeout() default 1000;

	@AttributeDefinition(name = "Socket Keep Alive", type = AttributeType.BOOLEAN)
	boolean socketKeepAlive() default false;

	@AttributeDefinition(name = "Socket Timeout", type = AttributeType.INTEGER)
	int socketTimeout() default 1000;

	@AttributeDefinition(name = "SSL Enabled?", type = AttributeType.BOOLEAN)
	boolean sslEnabled() default false;

	@AttributeDefinition(name = "SSL Invalid HostName Allowed?", type = AttributeType.BOOLEAN)
	boolean sslInvalidHostNameAllowed() default false;

	@AttributeDefinition(name = "Threads Allowed To Block For Connection Multiplier", type = AttributeType.INTEGER)
	int threadsAllowedToBlockForConnectionMultiplier() default 0;

	@AttributeDefinition(name = "Write ConcernW", type = AttributeType.INTEGER)
	int writeConcernW() default 0;

	@AttributeDefinition(name = "Write ConcernW Timeout", type = AttributeType.INTEGER)
	int writeConcernWtimeout() default 1000;

	@AttributeDefinition(name = "Write ConcernFsync", type = AttributeType.BOOLEAN)
	boolean writeConcernFsync() default false;

	@AttributeDefinition(name = "Write ConcernJ", type = AttributeType.BOOLEAN)
	boolean writeConcernJ() default false;
	
	@AttributeDefinition(name = "Database Name", type = AttributeType.STRING, required=true)
	String dbName() default StringUtils.EMPTY; 
	
	@AttributeDefinition(name = "Retry Writes", type = AttributeType.BOOLEAN)
	boolean retryWrites() default false;
	
	@AttributeDefinition(name = "Application Name", type = AttributeType.STRING, required=true)
	String applicationName() default StringUtils.EMPTY;
	
	@AttributeDefinition(name = "Maintenance Frequency", type = AttributeType.LONG)
	long maintenanceFrequency() default 1; 
	
	@AttributeDefinition(name = "Maintenance Initial Delay", type = AttributeType.LONG)
	long maintenanceInitialDelay() default 0;
	
	@AttributeDefinition(name = "User Name", type = AttributeType.STRING, required=true)
	String userName() default StringUtils.EMPTY;
	
	@AttributeDefinition(name = "Password", type = AttributeType.STRING, required=true)
	String password() default StringUtils.EMPTY;
	
	@AttributeDefinition(name = "Server Addresses", type = AttributeType.STRING, required=true, description="Give server addresses in this format e.g: host1:27017")
	String serverAddresses() default StringUtils.EMPTY;
	
	@AttributeDefinition(name = "Read BufferSize", type = AttributeType.INTEGER)
	int readBufferSize() default 0;
	
	@AttributeDefinition(name = "Send BufferSize", type = AttributeType.INTEGER)
	int sendBufferSize() default 0;

}
