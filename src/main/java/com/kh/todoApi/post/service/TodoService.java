package com.kh.todoApi.post.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.todoApi.post.model.dao.TodoMapper;
import com.kh.todoApi.post.model.dto.TodoDTO;
import com.kh.todoApi.post.model.vo.Todo;

@Service
public class TodoService {

	private TodoMapper todoMapper;
	@Autowired
	public TodoService (TodoMapper todoMapper) {
		this.todoMapper = todoMapper;
	}
	
	// 할 일 목록 조회
	public ArrayList<Todo> selectTodoList(String memberId) {
		ArrayList<Todo> todoList = todoMapper.selectTodoList(memberId);
		return todoList;
	}

	// 할 일 추가 후 조회
	public Todo insertTodo(TodoDTO todoDto) {
		Todo newTodo = null;
		int result = todoMapper.insertTodo(todoDto);
		
		if (result > 0) {
			newTodo = todoMapper.selectByMaxNo(todoDto.getMemberId());
		}
		
		return newTodo;
	}
	
}
