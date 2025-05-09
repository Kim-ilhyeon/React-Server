package com.kh.todoApi.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserDTO {

	private String memberId;
	private String password;
	private String nickname;
	private String email;
	public UserDTO(String memberId, String nickname, String email) {
		super();
		this.memberId = memberId;
		this.nickname = nickname;
		this.email = email;
	}
	
	

}
