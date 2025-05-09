package com.kh.todoApi.post.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kh.todoApi.post.model.dto.TodoDTO;
import com.kh.todoApi.post.model.vo.Todo;
import com.kh.todoApi.post.service.TodoService;
import com.kh.todoApi.user.model.dto.UserDTO;

import jakarta.servlet.http.HttpSession;

@RestController
public class TodoController {

	private TodoService todoService;
	@Autowired
	public TodoController (TodoService todoService) {
		this.todoService = todoService;
	}
	
	/**
	 * 할 일 목록 조회
	 * [GET] /todo
	 * @return List<Todo> 할일 목록 (전체)
	 */
	@GetMapping("/todo")
	public ArrayList<Todo> selectTodoList(HttpSession session) {
		UserDTO loginUser = (UserDTO)session.getAttribute("loginUser");
		System.out.println("TodoController에서의 loginUser : " + loginUser);
		ArrayList<Todo> todoList = todoService.selectTodoList(loginUser.getMemberId());
		
		System.out.println("TodoController에서의 조회한 todoList : " + todoList);
		
		return todoList;
	}
	
	/**
	 * 할 일 추가
	 * [POST] /todo
	 * @param { content: 할 일 내용 }
	 * @return Todo 추가된 할 일 정보
	 */
	@PostMapping("/todo")
	public ResponseEntity<Object> insertTodo(@RequestBody TodoDTO todoDto , HttpSession session) {
		UserDTO loginUser = (UserDTO)session.getAttribute("loginUser");
		
		todoDto.setMemberId(loginUser.getMemberId());
		
		Todo todo = todoService.insertTodo(todoDto);
		
		if (todo != null) {
			// 추가 성공 시 추가된 할일 정보(Todo)
			return ResponseEntity.status(HttpStatus.OK).body(todo);
		} else {
			// 추가 실패 시 메세지 (String)
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("할 일 추가에 실패했습니다.");
		}
	}
	
}
