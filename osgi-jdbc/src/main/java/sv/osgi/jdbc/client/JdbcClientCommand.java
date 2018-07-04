package sv.osgi.jdbc.client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import sv.osgi.jdbc.api.SqlDatasourceProvider;
import sv.osgi.jdbc.api.SqlDatasourceProviderFactory;


@Component(enabled = true, immediate = true, service = JdbcClientCommand.class)
public class JdbcClientCommand {

	@Reference
	private SqlDatasourceProviderFactory sqlDatasourceProviderFactory;
	
	public String testConnection() throws SQLException {
		
		SqlDatasourceProvider dsProvider = sqlDatasourceProviderFactory.getSqlDatasourceProvider("nigazhchi");
		
		DataSource ds = dsProvider.getDatasource();
		
		Connection conn = ds.getConnection();
		
		PreparedStatement ps = conn.prepareStatement("select * from venue");
		
		ResultSet rs = ps.executeQuery();
		
		int count = 0;
		while(rs.next()) {
			count++;
		}
		
		return String.valueOf(count==0?"Empty":count);
		
	}
	
	
}
