package com.example.HelloWorld.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.json.JSONObject;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Controller
@EnableAutoConfiguration
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TodoController {

    @RequestMapping("/hello")
    @ResponseBody
    public String sayHello(){
        return "Hello World Sushovan";
    }

    @RequestMapping("/detail")
    @ResponseBody
    public String sayDetails(){
        return "Sushovan works at Tavisca";
    }

    @RequestMapping("/student")
    @ResponseBody
    public ResponseEntity<?>getData(){
        String responseBody = "Suso";
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @RequestMapping("/age")
    @ResponseBody
    public ResponseEntity<String> getAge(){
        return new ResponseEntity<>("Your Age is 23", HttpStatus.OK );
    }

    @GetMapping(path = "/todos")
    public ResponseEntity<?> getTodos() {
        return sendResponse("All todo is retrieved");
    }

    public List<String> todoList = new ArrayList<String>();

    @GetMapping(path = "/todos/{id}")
    public ResponseEntity<?> getTodo(@PathVariable("id") int todoid){
        if(todoid > todoList.size())
            return new ResponseEntity<>("TODO id has exceeded", HttpStatus.BAD_REQUEST);

        JSONObject jsonObject = new JSONObject()
                .put("todoname", todoList.get(todoid))
                .put("status", "Todo " +(todoid + 1) + " is retrieved")
                .put("timestamp", Instant.now().toString());
        return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
    }

    @PostMapping("/todos")
    public ResponseEntity<?> addTodo(@RequestBody String json){
        JSONObject jsonObject = new JSONObject(json);
        String todoname = jsonObject.getString("todoname");
        todoList.add(todoname);
        return sendResponse("ToDo "+ todoname +" has been added");
    }

    @PutMapping(path = "/todos/{id}")
    public ResponseEntity<?> updateTodo(@PathVariable("id") int todoid, @RequestBody String json){
        if(todoid > todoList.size())
            return new ResponseEntity<>("TODO id has exceeded", HttpStatus.BAD_REQUEST);

        JSONObject jsonObject = new JSONObject(json);
        String todoname = jsonObject.getString("todoname");
        todoList.set(todoid, todoname);
        return sendResponse("ToDo "+ (todoid + 1) + " has been updated");
    }

    @DeleteMapping(path = "/todos/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable("id") int todoid){
        if(todoid > todoList.size())
            return new ResponseEntity<>("TODO id has exceeded", HttpStatus.BAD_REQUEST);

        todoList.remove(todoid);
        return sendResponse("ToDo "+ (todoid + 1) + " has been deleted");
    }

    private ResponseEntity<?> sendResponse(String status) {
        JSONObject jsonObject = new JSONObject()
                .put("todo", todoList)
                .put("status", status)
                .put("timestamp", Instant.now().toString());
        return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
    }
}
