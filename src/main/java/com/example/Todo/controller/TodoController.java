package com.example.Todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.Todo.services.TodoService;

@Controller
@EnableAutoConfiguration
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TodoController {

    @Autowired
    TodoService todoService;

    @GetMapping(path = "/todos")
    public ResponseEntity<?> getTodos() {
        return todoService.getTodos();
    }

    @GetMapping(path = "/todos/{id}")
    public ResponseEntity<?> getTodo(@PathVariable("id") int todoid){
        return todoService.getTodo(todoid);
    }

    @PostMapping("/todos")
    public ResponseEntity<?> addTodo(@RequestBody String json){
        return todoService.addTodo(json);
    }

    @PutMapping(path = "/todos/{id}")
    public ResponseEntity<?> updateTodo(@PathVariable("id") int todoid, @RequestBody String json){
        return todoService.updateTodo(todoid, json);
    }

    @DeleteMapping(path = "/todos/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable("id") int todoid){
        return todoService.deleteTodo(todoid);
    }
}
