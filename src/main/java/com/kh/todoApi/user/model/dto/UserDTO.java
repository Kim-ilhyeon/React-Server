package com.kh.todoApi.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {

	private String userId;
	private String userPwd;
	private String nickname;
	private String email;
	public UserDTO(String userId, String nickname, String email) {
		super();
		this.userId = userId;
		this.nickname = nickname;
		this.email = email;
	}
	
	

}
