package sv.osgi.cxf.rest.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("")
@Consumes({  "application/json" })
@Produces({  "application/json" })
public interface TaskResource {

	@GET
	@Path("/{id}")
	Task get(@PathParam("id") Integer id);

	@POST
	void add(Task task);

	@PUT
	@Path("/{id}")
	void update(Integer id, Task task);

	@DELETE
	@Path("/{id}")
	void delete(Integer id);


	@GET
	Task[] getAll();
}
