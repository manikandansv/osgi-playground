package sv.osgi.jdbc.api;

import javax.sql.DataSource;

public interface SqlDatasourceProvider {

	DataSource getDatasource();
	
	JdbcClientConfiguration getJdbcClientConfiguration();
}
