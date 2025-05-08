package com.kh.todoApi.user.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kh.todoApi.user.model.dto.UserDTO;
import com.kh.todoApi.user.service.MailService;
import com.kh.todoApi.user.service.UserService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;

@RestController // => @Controller + @ResponseBody
//@RequiredArgsConstructor	// 생성자 대신 사용하는 어노테이션
public class UserController {

	private final MailService mailService;
	private final UserService userService;

	@Autowired
	public UserController(MailService mailService, UserService userService) {
		this.mailService = mailService;
		this.userService = userService;
	}
	

	/**
	 * 이메일을 전달받아 인증코드를 메일로 전송
	 * 
	 * @param email
	 * @return "ok" : 인증코드 방송, "nok" : 인증코드 발송 실패
	 */
	@PostMapping("/email/send")
	public String sendEmailCode(@RequestBody Map<String, Object> requestBody) {
		/*
		 * 폼 요청 시 전송 형식과 axios(fetch) 요청 시 전송 형식이 달라 요청 본문에서 전달 데이터를 추출해야 함!!
		 * 
		 * @RequestBody : 요청 본문. 전송 형식이 application/json
		 * 
		 * @RequestParam : 쿼리 파라미터(get방식) 또는 폼 데이터. 단일 데이터 처리
		 * 
		 * @ModelAttribute: 쿼리 파라미터 또는 폼 데이터. 객체 바인딩 처리
		 */
		String email = (String) requestBody.get("email");
		System.out.println("email :: " + email);

		try {
			mailService.sendMail(email);
		} catch (MessagingException e) {
			return "nok";
		}

		return "ok"; // TODO

	}

	/**
	 * 이메일에 대한 인증코드 검증
	 * 
	 * @param requestBody {email: "이메일정보", code: "인증 코드"}
	 * @return "success": 검증 성공, "failed": 검증 실패
	 */
	@PostMapping("/email/verify")
	public String verifyEmailCode(@RequestBody Map<String, Object> requestBody) {
		String email = (String) requestBody.get("email");
		String code = (String) requestBody.get("code");

		System.out.println("email :: " + email);
		System.out.println("code :: " + code);

		boolean result = mailService.verifyCode(email, code);

		return result ? "success" : "failed"; // TODO
	}
	
	/**
	 * 아이디 중복 체크
	 * [POST] /checkId
	 * @param id 아이디
	 * @return "NNNNN" : 중복된 아이디, "NNNNY" : 사용 가능한 아이디
	 */
	@PostMapping("/checkId")
	public String checkId(@RequestBody Map<String, Object> requestBody) {
		
		String id = (String)requestBody.get("id");
		
		
		// 서비스로부터 중복체크 --> 사용자 테이블에서 id에 해당하는 개수를 조회할 것임
		boolean result = userService.checkId(id);
		
		if (result == false) {
			return "NNNNN";
		} else {
			return "NNNNY";
		}
		
	}
	
	/**
	 * 회원가입 (회원 정보 등록)
	 * [POST] /user/regist
	 * @param UserDTO 회원 정보 {userId: 아이디, userPwd: 비밀번호, nickname: 닉네임, email: 이메일}
	 * @return "success" : 가입성공, "failed" : 가입 실패
	 */
	@PostMapping("/user/regist")
//	public String insertUser(@RequestBody Map<String, Object> requestBody) {
	public String insertUser(@RequestBody UserDTO userDTO) {
//		String userId = (String)requestBody.get("id");
//		String userPwd = (String)requestBody.get("pwd");
//		String nickname = (String)requestBody.get("nickname");
//		String email = (String)requestBody.get("email");
		
//		UserDTO newUser = new UserDTO(userId, userPwd, nickname, email);

		UserDTO newUser = userDTO;
		
		int result = userService.insertUser(newUser);
				
		if (result > 0) {
			return "success";
		} else {
			return "failed";
		}
	}
	

	/**
	 * 로그인 (회원 정보 조회)
	 * [POST] /login
	 * @param UserDTO {userId: 아이디, userPwd: 비밀번호}
	 * @return "success" : 로그인 성공, "failed" : 로그인 실패
	 * 			로그인 성공 시, 세션에 사용자 정보를 저장
	 */
	@PostMapping("/login")
	public String selectUser(@RequestBody UserDTO userDTO, HttpSession session) {

		UserDTO loginUser = userService.selectUser(userDTO);
		
		if (loginUser != null) {
			System.out.println("로그인 성공");
			session.setAttribute("loginUser", loginUser);
			// 세션(session) => 서버에서만 관리! 클라이언트는 접근할 수 없음!!
			return "success";
		} else {
			System.out.println("로그인 실패");
			return "failed";
		}
		
	}
	
}
