package com.dogFoot.mail;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Mail {

	private static String str="";
	private JavaMailSender mailSender;
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
			
	@RequestMapping("/mail2.do")
	public void mail1(HttpSession session, String mailText1, String mailText2, String mailText3, String mailText4){
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		System.out.println("Mail-mail2-mailText1:"+mailText1);
		mailMessage.setSubject("한빛김준성");
		mailMessage.setFrom("dogfootshop@naver.com");
		mailMessage.setText("아이디 또는 이름");
		mailMessage.setTo("dogfootshop@naver.com");
		try {
			mailSender.send(mailMessage);
			session.setAttribute("msg", "메일발송하였습니다.");
		} catch (Exception e) {
		// TODO: handle exception
			System.out.println(e);
		}
	}
	
	@RequestMapping("/mail1.do")
	public ModelAndView mail2(HttpSession session, String mailText1, String mailText2, String mailText3, String mailText4){
		ModelAndView view = new ModelAndView();
	
		System.out.println("Mail-mail1-mailText1:"+mailText1);
		str = "";
		str += "아이디 또는 이름: " + mailText1;
		str += "<br>연락처 또는 이메일: " + mailText2;
		str += "<br>제목: " + mailText3;
		str += "<br>메시지: " + mailText4;
		mailSender.send(new MimeMessagePreparator() {
	
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
			// TODO Auto-generated method stub
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				message.setFrom("dogfootshop@naver.com");
				message.setTo("dogfootshop@naver.com");
				message.setSubject("dogFootShop메일문의");
				message.setText(str, true);
				//message.addInline("myPic", new ClassPathResource("img/kimgowun.GIF"));
				//message.addAttachment("myDoc", new ClassPathResource("doc/testDoc.txt"));
			}
		});
		view.setViewName("redirect:/front.do");
		session.setAttribute("msg", "메일전송을 성공하였습니다.");  //  테스트위해서 session에 메일추가함
		return view;
	}
}
