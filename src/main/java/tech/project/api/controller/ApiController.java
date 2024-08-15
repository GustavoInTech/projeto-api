package tech.project.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.project.api.model.Task;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping
public class ApiController {
    private List<Task> tasks = new ArrayList<>();
    private ObjectMapper objectMapper;

    public ApiController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @GetMapping(path = "/tasks")
    public ResponseEntity<String> listTasks() throws JsonProcessingException {
        return ResponseEntity.ok(objectMapper.writeValueAsString(tasks));
    }

    @PostMapping(path = "/tasks")
    public ResponseEntity<Void> createTask(@RequestBody Task task){
        task.setId((long) (tasks.size() + 1)); // Simples auto-incremento de ID
        tasks.add(task);
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "/tasks/{id}")
    public ResponseEntity<Void> updateTask(@PathVariable Long id, @RequestBody Task updateTask) {
        for (Task task : tasks){
            if (task.getId().equals(id)){
                task.setTitle(updateTask.getTitle());
                task.setDescription(updateTask.getDescription());
                task.setPriority(updateTask.getPriority());
                task.setStatus(updateTask.getStatus());
                task.setDueDate(updateTask.getDueDate());
                return ResponseEntity.ok().build();
            }
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Void> deletetask(@PathVariable Long id) {
        tasks.removeIf(task -> task.getId().equals(id));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/tasks")
    public ResponseEntity<Void> clearTasks(){
        tasks = new ArrayList<>();
        return ResponseEntity.ok().build();
    }
}
