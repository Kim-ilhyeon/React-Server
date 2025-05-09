package com.kh.todoApi.user.model.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.kh.todoApi.user.model.dto.UserDTO;

@Repository
public class UserMapper {
	// * class => 빈 등록 : @Repository, DB연동(작업): SqlSession
	
	// * interface => 빈등록 : @Mapper, DB연동(작업): 추상메소드 정의
	//			mapper.xml 파일 namespace에 해당 인터페이스 전체 경로 설정
	private final SqlSession sqlSession;
	public UserMapper(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	// 전달된 아이와 일치하는 개수 조회 (DML, SELECT, 단일 행)
	public int countBymemberId(String memberId) {
		int result = sqlSession.selectOne("userMapper.countBymemberId", memberId);
		
		return result;
	}

	// 전달된 회원 정보를 DB에 추가 (DML, INSERT)
	public int insertUser(UserDTO newUser) {
		int result = sqlSession.insert("userMapper.insertUser", newUser);
		
		// 트랜잭션 처리 --> auto commit 되어서 트랜잭션 처리X
//		if (result > 0) {
//			sqlSession.commit();
//		} else {
//			sqlSession.rollback();
//		}
		
		return result;
	}

	public UserDTO selectBymemberIdAndPwd(UserDTO userDTO) {
		System.out.println("userMapper 요청까지는 옴");
		System.out.println("userMapper 에서 전달받은 userDTO : " + userDTO);
		UserDTO loginUser = sqlSession.selectOne("userMapper.selectBymemberIdAndPwd", userDTO);
		System.out.println("userMapper에서 DB에 조회한 loginUser" + loginUser);
		return loginUser;
	}
	
}
