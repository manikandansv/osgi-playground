package sv.cxf.rest.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import sv.osgi.cxf.rest.api.Task;
import sv.osgi.cxf.rest.api.TaskResource;
import sv.osgi.jdbc.client.JdbcClientCommand;
import sv.osgi.mongo.client.MongoClientCommand;

@Component//
(//
		immediate = true, //
		name = "TaskResource", //
		enabled = true,
		property = //
		{ "service.exported.interfaces=*", //
		  "service.exported.configs=org.apache.cxf.rs", //
		  "org.apache.cxf.rs.address=/tasks", //
		  "org.apache.cxf.rs.httpservice.context=/api"
		} //
)

public class TaskResourceImpl implements TaskResource {
	
	Map<Integer, Task> taskMap;

	public TaskResourceImpl() {
		taskMap = new HashMap<Integer, Task>();
		Task task = new Task();
		task.setId(1);
		task.setTitle("Buy some coffee");
		task.setDescription("Take the extra strong");
		add(task);
		task = new Task();
		task.setId(2);
		task.setTitle("Finish DOSGi example");
		task.setDescription("");
		add(task);
	}
	
	@Reference
	private MongoClientCommand mongoClientCommand;
	
	@Reference
	private JdbcClientCommand jdbcClientCommand;

	@Override
	public Task get(Integer id) {
		return taskMap.get(id);
	}

	@Override
	public void add(Task task) {
		taskMap.put(task.getId(), task);
	}

	@Override
	public void update(Integer id, Task task) {
		taskMap.put(id, task);
	}

	@Override
	public Task[] getAll() {
		System.out.println("Inside getAll");
		Task task = new Task();
		task.setId(3);
		task.setTitle(mongoClientCommand==null?"NULL":mongoClientCommand.testConnection());
		task.setDescription("");
		add(task);
		update(3, task);
		Task task1 = new Task();
		try {
			task1.setId(4);
			task1.setTitle(jdbcClientCommand==null?"NULL":jdbcClientCommand.testConnection());
			task1.setDescription("");
			add(task1);
			update(4, task1);
		} catch (SQLException e) {
			
			System.out.println(e.getMessage());
		}
	
		return taskMap.values().toArray(new Task[] {});
	}

	@Override
	public void delete(Integer id) {
		taskMap.remove(id);
	}

//	private Swagger2Feature createSwaggerFeature() {
//		Swagger2Feature swagger = new Swagger2Feature();
//		swagger.setDescription("Sample jaxrs application to organize taks");
//		swagger.setTitle("Tasks sample");
//		swagger.setUsePathBasedConfig(true); // Necessary for OSGi
//		// swagger.setScan(false); // Must be set for cxf < 3.2.x
//		swagger.setPrettyPrint(true);
//		swagger.setSupportSwaggerUi(true);
//		return swagger;
//	}
}
