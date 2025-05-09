package com.kh.todoApi.post.model.vo;

import java.sql.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Todo {

	private int todoNo;
	private String todoContent;
	private int todoStatus;
	private Date createDate;
	private String memberId;
}
