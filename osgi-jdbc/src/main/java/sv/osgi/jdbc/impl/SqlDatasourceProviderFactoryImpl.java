package sv.osgi.jdbc.impl;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

import sv.osgi.jdbc.api.SqlDatasourceProvider;
import sv.osgi.jdbc.api.SqlDatasourceProviderFactory;

@Component(service=SqlDatasourceProviderFactory.class, enabled=true, immediate=true)
public class SqlDatasourceProviderFactoryImpl implements SqlDatasourceProviderFactory {
	
	private Map<String, SqlDatasourceProvider> map;


	@Override
	public SqlDatasourceProvider getSqlDatasourceProvider(String datasourceName) {

		if (map != null && map.containsKey(datasourceName)) {

			return map.get(datasourceName);

		}

		return null;
	}
	
	@Reference(name = "SqlDatasourceProvider", cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
	protected synchronized void bindSqlDatasourceProvider(final SqlDatasourceProvider sqlDatasourceProvider) {
		
		if (map == null) {

			map = new HashMap<String, SqlDatasourceProvider>();

		}

		map.put(sqlDatasourceProvider.getJdbcClientConfiguration().datasourceName(), sqlDatasourceProvider);
		
		System.out.println(map);

	}

	@Reference(name = "SqlDatasourceProvider", cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
	protected synchronized void unbindSqlDatasourceProvider(final SqlDatasourceProvider sqlDatasourceProvider) {

		if (map != null) {

			map.remove(sqlDatasourceProvider.getJdbcClientConfiguration().datasourceName());

		}

	}

}
