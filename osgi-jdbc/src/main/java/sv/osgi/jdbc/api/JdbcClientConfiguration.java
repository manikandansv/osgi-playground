package sv.osgi.jdbc.api;

import org.apache.commons.lang3.StringUtils;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "JdbcClientConfiguration", description = "The configuration for a jdbc client")
public @interface JdbcClientConfiguration {

	String jdbcDriverClass() default StringUtils.EMPTY;
	
	String jdbcConnectionUri() default StringUtils.EMPTY;
	
	String jdbcUserName() default StringUtils.EMPTY;
	
	String jdbcPassword() default StringUtils.EMPTY;
	
	String jdbcValidationQuery() default StringUtils.EMPTY;
	
	boolean isReadOnly() default false;
	
	boolean isAutoCommit() default false;
	
	int poolSize() default 10;
	
	long maxWaitMSec() default 1000;
	
	String datasourceName() default StringUtils.EMPTY;
	
}
