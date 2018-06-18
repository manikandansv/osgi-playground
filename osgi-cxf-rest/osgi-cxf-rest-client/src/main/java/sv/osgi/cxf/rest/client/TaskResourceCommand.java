package sv.osgi.cxf.rest.client;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import sv.osgi.cxf.rest.api.Task;
import sv.osgi.cxf.rest.api.TaskResource;

@Component//
(//
	immediate=true,
	enabled=true,
    service = TaskResourceCommand.class,
    property = //
    {
     "osgi.command.scope=task", //
     "osgi.command.function=list", //
     "osgi.command.function=add", //
     "osgi.command.function=delete",//
    }//
)
public class TaskResourceCommand {

    private TaskResource taskService;

    public void list() {
        System.out.println("Open tasks:");
        Task[] tasks = taskService.getAll();
        for (Task task : tasks) {
            String line = String.format("%d %s", task.getId(), task.getTitle());
            System.out.println(line);
        }
    }
    
    public void add(Integer id, String title) {
        Task task = new Task(id, title, "");
        taskService.add(task);
    }
    
    public void delete(Integer id) {
        taskService.delete(id);
    }

    @Reference
    public void setTaskService(TaskResource taskService) {
        this.taskService = taskService;
    }
}
