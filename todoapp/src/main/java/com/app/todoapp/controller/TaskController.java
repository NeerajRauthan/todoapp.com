package com.app.todoapp.controller;

import com.app.todoapp.models.Task;
import com.app.todoapp.services.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public String viewTasks(Model model) {
        model.addAttribute("tasks", taskService.getAllTasks());
        model.addAttribute("newTask", new Task());
        return "tasks";
    }

    @GetMapping("/completed")
    public String viewCompletedTasks(Model model) {
        model.addAttribute("tasks", taskService.getTasksByCompletionStatus(true));
        model.addAttribute("newTask", new Task());
        return "tasks";
    }

    @GetMapping("/pending")
    public String viewPendingTasks(Model model) {
        model.addAttribute("tasks", taskService.getTasksByCompletionStatus(false));
        model.addAttribute("newTask", new Task());
        return "tasks";
    }

    @GetMapping("/search")
    public String searchTasks(@RequestParam String title, Model model) {
        model.addAttribute("tasks", taskService.searchTasksByTitle(title));
        model.addAttribute("newTask", new Task());
        return "tasks";
    }

    @PostMapping
    public String createTask(@ModelAttribute("newTask") Task task) {
        taskService.createTask(task);
        return "redirect:/tasks";
    }

    @PostMapping("/{id}/toggle")
    public String toggleTaskCompletion(@PathVariable Long id) {
        taskService.toggleTaskCompletion(id);
        return "redirect:/tasks";
    }

    @PostMapping("/{id}/delete")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "redirect:/tasks";
    }
}