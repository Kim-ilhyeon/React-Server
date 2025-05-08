package com.kh.todoApi.user.service;

import org.springframework.stereotype.Service;

import com.kh.todoApi.user.model.dao.UserMapper;
import com.kh.todoApi.user.model.dto.UserDTO;

@Service
public class UserService {
	private final UserMapper userMapper;
	public UserService(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	/**
	 *  아이디 중복체크
	 *  @param userId 사용자 아이디
	 *  @return true : 사용가능, false : 중복(사용 불가)
	 */
	public boolean checkId(String userId) {
		
		int count = userMapper.countByUserId(userId);
		
		
		return count == 0;
	}

	
	/* 회원 가입 */
	public int insertUser(UserDTO newUser) {
		
		int result = userMapper.insertUser(newUser);
		
		return result;
	}

	
	/** 로그인
	 * @param UserDTO 로그인 정보 (아이디, 비밀번호)
	 * @return UserDTO 회원 정보 (아이디, 닉네임, 이메일)
	 *  */
	public UserDTO selectUser(UserDTO userDTO) {
		
		UserDTO loginUser = userMapper.selectByUserIdAndPwd(userDTO);
		
		return loginUser;
	}
	
	/* 회원 정보 조회 */
	
	/* 회원 탈퇴 */
	
}
