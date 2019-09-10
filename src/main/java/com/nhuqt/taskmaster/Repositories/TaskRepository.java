package com.nhuqt.taskmaster.Repositories;

import com.nhuqt.taskmaster.Models.Task;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;


public interface TaskRepository extends CrudRepository<Task, String> {
  Optional<Task> findById(String id);
}
