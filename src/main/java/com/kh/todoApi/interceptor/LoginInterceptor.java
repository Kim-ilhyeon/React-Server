package com.kh.todoApi.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.kh.todoApi.user.model.dto.UserDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/*
 * 세션에 로그인 정보가 있을 경우, 컨트롤러를 동작.
 * 				  없을 경우, 응답헤더에 401 코드를 담아서 컨트롤러를 동작시키지 않음.
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

	/**
	 * Controller 요청 전 처리 : preHandle
	 * Controller 응답 전 처리 : postHandle
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// OPTIONS 요청에 대하여 인증체크 없이 허용. CORS preflight 요청.
		if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
//			response.setStatus(200);	// 아래줄과 같은 내용임
			response.setStatus(HttpServletResponse.SC_OK);
			return true;
		}
		
		/*
		 * true 리턴 : 정상적으로 Controller 동작.
		 * false 리턴 : Controller 동작하지 않음!!
		 */
		
		// 세션에 로그인 정보가 있는지 확인
		HttpSession session = request.getSession();
		UserDTO loginUser = (UserDTO)session.getAttribute("loginUser");
		System.out.println("LoginInterceptor에서 loginUser : " + loginUser);
		
		if (loginUser != null) {
			return true;
		} else {
//			response.setStatus(401); // 밑에 코드와 동일함
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().print("로그인 후 이용 가능합니다.");
			return false;
		}
		
	}
	// WebConfig 파일에서 addInterceptors 오버라이딩까지 해야지 적용됨

	
	
	
}
