package com.kh.todoApi.post.model.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.todoApi.post.model.dto.TodoDTO;
import com.kh.todoApi.post.model.vo.Todo;

@Repository
public class TodoMapper {

	private SqlSession sqlSession;
	@Autowired
	public TodoMapper(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	
	public ArrayList<Todo> selectTodoList(String memberId) {
		ArrayList<Todo> todoList = (ArrayList)sqlSession.selectList("todoMapper.selectTodoList", memberId);
		System.out.println("TodoMapper에서의 DB에서 조회한 todoList : " + todoList);
		return todoList;
	}

	public int insertTodo(TodoDTO todoDto) {
		int result = sqlSession.insert("todoMapper.inserTodo", todoDto);
		return result;
	}
	public Todo selectByMaxNo(String memberId) {
		Todo Todo = sqlSession.selectOne("todoMapper.selectByMaxNo", memberId);
		return Todo;
	}
	
}
