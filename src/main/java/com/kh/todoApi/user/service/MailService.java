package com.kh.todoApi.user.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.kh.todoApi.user.model.vo.AuthData;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailService {
	// mail 관련 객체 추가(주입)
	private final JavaMailSender sender;
	public MailService(JavaMailSender sender) {
		this.sender = sender;
		
		// 스케쥴러 추가 => 1분마다 인증 정보를 정리
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		scheduler.scheduleAtFixedRate(()->clearData(), 0, 1, TimeUnit.MINUTES);
		// 실행할 작업, 초기 지연시간, 실행 주기, 시간 단위 => 1분마다 clearData 실행
	}
	
	// 이메일, 인증 정보(인증코드, 유효시간)를 관리하기 위한 Map 추가
	private Map<String, AuthData> authInfo = new HashMap<>();
	
	public void sendMail(String email) throws MessagingException {
		
		// 인증 정보 생성 --> AuthData
		AuthData authData = new AuthData();
		String code = authData.getCode();
		
		// 메일 내용 작성 --> 제목, 받는사람, 내용
		String subject = "[TODO MANAGER] 인증 코드";
		String[] to = { email };
		String content = "<p>아래의 인증코드를 입력해주세요.</p>"
					   + "<h3>" + code + "</h3>"
					   + "<p>인증코드는 3분간 유효합니다.</p>";
		
		// 메일 전송
		MimeMessage mm = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mm);
		
		helper.setSubject(subject);
		helper.setTo(to);
		helper.setText(content, true);
		
		sender.send(mm);
		
		// 이메일, 인증 정보를 Map에 추가
		authInfo.put(email, authData);
		
	}
	
	public boolean verifyCode(String email, String code) {
		// Map에서 email에 해당하는 인증 정보가 있는지 체크
		AuthData authData = authInfo.get(email);
		
		if (authData == null) {	// 이메일에 대한 인증 정보가 없을 경우 false 리턴
			return false;
		}
		
		// 인증 정보가 있다면 코드값과 전달된 코드를 비교하여 동일한 지 체크
		// 제한시간도 체크 --> 현재시간 >= 제한시간 :: 제한시간 초과 return false
//		long currTime = System.currentTimeMillis();
//		if (currTime > authData.getTime()) {
//			authInfo.remove(email);
//			return false;
//		}
		
		
//		if (code.equals(authData.getCode())) {
//			authInfo.remove(email);
//			return true;
//		} else {
//			return false;
//		}
		
		if (code.equals(authData.getCode())) {
			authInfo.remove(email);
			return true;
		} else {
			return false;
		}
		
		
	}
	
	private void clearData() {
		long currTime = System.currentTimeMillis();
		
		authInfo.entrySet().removeIf(entry-> currTime > entry.getValue().getTime());
	}

	
	
}
