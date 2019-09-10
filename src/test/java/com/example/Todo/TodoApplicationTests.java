package com.example.Todo;

import java.time.Instant;
import java.util.List;
import java.util.ArrayList;

import com.example.Todo.controller.TodoController;
import com.example.Todo.services.TodoService;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = TodoController.class, secure = false)
public class TodoApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TodoService todoservice;

	List<String> todoList = new ArrayList<String>() {
		{
			add("Eat");
			add("Code");
			add("sleep");
		}
	};

	@Test
	public void getTodosTest() throws Exception {
		JSONObject jsonObject = new JSONObject()
			.put("todo", todoList)
			.put("status", "All todo is retrieved")
			.put("timestamp", Instant.now().toString());
		ResponseEntity responseEntity = new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);

		given(todoservice.getTodos()).willReturn(responseEntity);

		this.mockMvc.perform(get("/todos"))
			.andExpect(status().isOk())
			.andExpect(content().json(responseEntity.getBody().toString()));
	}

	@Test
	public void getTodoTest() throws Exception {

		//given
		int todoid = 1;
		JSONObject jsonObject = new JSONObject()
			.put("todoname", todoList.get(todoid))
			.put("status", "Todo " +(todoid + 1) + " is retrieved")
            .put("timestamp", Instant.now().toString());
		
		ResponseEntity responseEntity = new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
		
		given(todoservice.getTodo(1)).willReturn(responseEntity);

		this.mockMvc.perform(get("/todos/1"))
			.andExpect(status().isOk())
			.andExpect(content().json(responseEntity.getBody().toString()));
	}

	@Test
	public void addTodoTest() throws Exception {
		//given
		String json = "{'todoname' : 'bath'}" ;

		JSONObject jsonObject = new JSONObject()
			.put("todoname", json)
			.put("status", "ToDo bath has been added")
			.put("timestamp", Instant.now().toString());
		ResponseEntity responseEntity = new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);

		given(todoservice.addTodo(json)).willReturn(responseEntity);

		this.mockMvc.perform(post("/todos").content(json))
			.andExpect(status().isOk())
			.andExpect(content().json(responseEntity.getBody().toString()));
	}

	@Test
	public void updateTodoTest() throws Exception {
		//given
		int todoid = 1;
		String json = "{'todoname' : 'bath'}" ;

		JSONObject jsonObject = new JSONObject()
			.put("todoname", todoList.get(todoid))
			.put("status", "ToDo "+ (todoid + 1) + " has been updated")
			.put("timestamp", Instant.now().toString());
		ResponseEntity responseEntity = new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);

		given(todoservice.updateTodo(todoid, json)).willReturn(responseEntity);

		this.mockMvc.perform(put("/todos/1").content(json))
			.andExpect(status().isOk())
			.andExpect(content().json(responseEntity.getBody().toString()));
	}

	@Test
	public void deleteTodoTest() throws Exception {
		//given
		int todoid = 1;

		JSONObject jsonObject = new JSONObject()
			.put("todoname", todoList.get(todoid))
			.put("status", "ToDo "+ (todoid + 1) + " has been deleted")
			.put("timestamp", Instant.now().toString());
		ResponseEntity responseEntity = new ResponseEntity<>(jsonObject.toString(),HttpStatus.OK);

		given(todoservice.deleteTodo(todoid)).willReturn(responseEntity);

		this.mockMvc.perform(delete("/todos/1"))
			.andExpect(status().isOk())
			.andExpect(content().json(responseEntity.getBody().toString()));
	}
}
