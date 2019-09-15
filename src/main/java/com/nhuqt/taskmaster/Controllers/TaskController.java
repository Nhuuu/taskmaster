package com.nhuqt.taskmaster.Controllers;


import com.nhuqt.taskmaster.Models.History;
import com.nhuqt.taskmaster.Models.Task;
import com.nhuqt.taskmaster.Repositories.S3Client;
import com.nhuqt.taskmaster.Repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/taskmaster")
public class TaskController {

  @Autowired
  TaskRepository taskRepository;

  @Autowired
  S3Client s3Client;

  @GetMapping("/tasks")
  public List<Task> getTasks(){return (List) taskRepository.findAll();}

  @GetMapping("/users/{name}/tasks")
  public List<Task> getUserTasks(@PathVariable String name){
    return (List) taskRepository.findAllByAssignee(name);
  }

  @GetMapping("/tasks/{id}")
  public Task getOneTask(@PathVariable String id){
    return taskRepository.findById(id).get();
  }

  @PostMapping("/tasks")
  public Task addNewTask(@RequestBody Task task){
    Task t = new Task(task.getAssignee(), task.getTitle(), task.getDescription());
    History h = new History("available");
    t.addHistory(h);
    taskRepository.save(t);
    return t;
  }

  @PostMapping("/tasks/{id}/images")
  public Task uploadFile(@RequestPart(value ="file") MultipartFile file, @PathVariable String id){
    String pic = this.s3Client.uploadFile(file);
    Task t = taskRepository.findById(id).get();
    t.setPic(pic);
    taskRepository.save(t);
    return t;
  }

  @PutMapping("/tasks/{id}/state")
  public Task updateState(@PathVariable String id){
    Task t = taskRepository.findById(id).get();
    if(t.getStatus().equals("available")){
      t.setStatus("assigned");
    } else if(t.getStatus().equals("assigned")){
      t.setStatus("accepted");
    } else if(t.getStatus().equals("accepted")){
      t.setStatus("finished");
    }
    t.addHistory(new History(t.getStatus()));
    taskRepository.save(t);
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
