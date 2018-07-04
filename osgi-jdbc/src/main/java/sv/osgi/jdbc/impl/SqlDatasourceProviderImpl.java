package sv.osgi.jdbc.impl;

import javax.sql.DataSource;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.KeyedObjectPoolFactory;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;

import sv.osgi.jdbc.api.JdbcClientConfiguration;
import sv.osgi.jdbc.api.SqlDatasourceProvider;

@Component(service = SqlDatasourceProvider.class)
@Designate(ocd = JdbcClientConfiguration.class, factory = true)
public class SqlDatasourceProviderImpl implements SqlDatasourceProvider {

	private JdbcClientConfiguration jdbcClientConfiguration;

	private DataSource dataSource;

	@Activate
	protected void activate(JdbcClientConfiguration jdbcClientConfiguration) {

		this.jdbcClientConfiguration = jdbcClientConfiguration;

		try {

			setupDataSource(jdbcClientConfiguration);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public DataSource getDatasource() {

		return dataSource;

	}

	@Override
	public JdbcClientConfiguration getJdbcClientConfiguration() {

		return jdbcClientConfiguration;

	}

	void setupDataSource(JdbcClientConfiguration jdbcClientConfiguration) {

		initalizeDriver(jdbcClientConfiguration.jdbcDriverClass());

		GenericObjectPool connectionPool = new GenericObjectPool(null);

		connectionPool.setMaxActive(jdbcClientConfiguration.poolSize());

		if (jdbcClientConfiguration.maxWaitMSec() <= 0L) {
			throw new IllegalArgumentException("For now, DataSourceConfig.maxWaitMsec must be >= 0");
		}
		connectionPool.setWhenExhaustedAction((byte) 1);
		connectionPool.setMaxWait(jdbcClientConfiguration.maxWaitMSec());

		ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(
				jdbcClientConfiguration.jdbcConnectionUri(), jdbcClientConfiguration.jdbcUserName(),
				jdbcClientConfiguration.jdbcPassword());

		KeyedObjectPoolFactory kopf = null;

		new PoolableConnectionFactory(connectionFactory, connectionPool, kopf,
				jdbcClientConfiguration.jdbcValidationQuery(), jdbcClientConfiguration.isReadOnly(),
				jdbcClientConfiguration.isAutoCommit());

		this.dataSource = new PoolingDataSource(connectionPool);

	}

	private void initalizeDriver(String driverClassName) {

		if (driverClassName == null) {
			return;
		}

		try {
			ClassLoader loader = super.getClass().getClassLoader();
			Class driverClass = loader.loadClass(driverClassName);
			driverClass.newInstance();
		} catch (Exception e) {
			throw new IllegalArgumentException("Cannot initialize driver '" + driverClassName + "'", e);
		}
	}

}
