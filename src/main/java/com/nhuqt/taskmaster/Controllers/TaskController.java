package com.nhuqt.taskmaster.Controllers;


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
    t.setTitle(task.getTitle());
    t.setDescription(task.getDescription());
    t.setStatus("available");
    taskRepository.save(t);
    return t;
  }

}
