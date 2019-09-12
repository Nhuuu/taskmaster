package com.nhuqt.taskmaster.Controllers;


import com.nhuqt.taskmaster.Models.History;
import com.nhuqt.taskmaster.Models.Task;
import com.nhuqt.taskmaster.Repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/taskmaster")
public class TaskController {

  @Autowired
  TaskRepository taskRepository;

  @GetMapping("/tasks")
  public List<Task> getTasks(){return (List) taskRepository.findAll();}

  @PostMapping("/tasks")
  public Task addNewTask(@RequestBody Task task){
    Task t = new Task();
    t.setAssignee(task.getAssignee());
    t.setTitle(task.getTitle());
    t.setDescription(task.getDescription());
    t.setStatus("available");
    History h = new History("available");
    t.addHistory(h);
    taskRepository.save(t);
    return t;
  }

  @GetMapping("/users/{name}/tasks")
  public List<Task> getUserTasks(@PathVariable String name){
    return (List) taskRepository.findAllByAssignee(name);
  }

  @PutMapping("/tasks/{id}/state")
  public Task updateState(@PathVariable String id){
    Task t = taskRepository.findById(id).get();
    if(t.getStatus().equals("available")){
      t.setStatus("assigned");
      t.addHistory(new History("assigned"));
      taskRepository.save(t);
    }
    if(t.getStatus().equals("assigned")){
      t.setStatus("accepted");
      t.addHistory(new History("accepted"));
      taskRepository.save(t);
    }
    if(t.getStatus().equals("accepted")){
      t.setStatus("finished");
      t.addHistory(new History("finished"));
      taskRepository.save(t);
    }
    return t;
  }

  @PutMapping("/tasks/{id}/assign/{assignee}")
  public Task assignTask(@PathVariable String id, @PathVariable String assignee){
    Task t = taskRepository.findById(id).get();
    t.setAssignee(assignee);
    t.setStatus("assigned");
    t.addHistory(new History("assigned"));
    taskRepository.save(t);
    return t;
  }

  @DeleteMapping("/tasks/{id}/delete")
  public Task deleteTask(@PathVariable String id){
    Task t = taskRepository.findById(id).get();
    taskRepository.delete(t);
    return t;
  }

}
