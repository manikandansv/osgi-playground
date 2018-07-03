package sv.osgi.mongo.impl;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

import sv.osgi.mongo.api.MongoProvider;
import sv.osgi.mongo.api.MongoProviderFactory;

@Component(enabled = true, immediate = true, service = MongoProviderFactory.class)
public class MongoProviderFactoryImpl implements MongoProviderFactory {

	private Map<String, MongoProvider> map;

	@Override
	public MongoProvider getMongoProvider(String clientId) {

		if (map != null && map.containsKey(clientId)) {

			return map.get(clientId);

		}

		return null;

	}

	@Reference(name = "MongoProvider", cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
	protected synchronized void bindMongoProvider(final MongoProvider mongoProvider) {
		
		if (map == null) {

			map = new HashMap<String, MongoProvider>();

		}

		map.put(mongoProvider.getMongoClientConfiguration().clientId(), mongoProvider);
		
		System.out.println(map);

	}

	@Reference(name = "MongoProvider", cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
	protected synchronized void unbindMongoProvider(final MongoProvider mongoProvider) {

		if (map != null) {

			map.remove(mongoProvider.getMongoClientConfiguration().clientId());

		}

	}

}
