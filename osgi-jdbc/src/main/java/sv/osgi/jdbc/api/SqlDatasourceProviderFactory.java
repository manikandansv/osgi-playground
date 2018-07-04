package sv.osgi.jdbc.api;

public interface SqlDatasourceProviderFactory {
	
	SqlDatasourceProvider getSqlDatasourceProvider(String datasourceName);

}
