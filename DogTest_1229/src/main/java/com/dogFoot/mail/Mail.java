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
		mailMessage.setSubject("�Ѻ����ؼ�");
		mailMessage.setFrom("dogfootshop@naver.com");
		mailMessage.setText("���̵� �Ǵ� �̸�");
		mailMessage.setTo("dogfootshop@naver.com");
		try {
			mailSender.send(mailMessage);
			session.setAttribute("msg", "���Ϲ߼��Ͽ����ϴ�.");
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
		str += "���̵� �Ǵ� �̸�: " + mailText1;
		str += "<br>����ó �Ǵ� �̸���: " + mailText2;
		str += "<br>����: " + mailText3;
		str += "<br>�޽���: " + mailText4;
		mailSender.send(new MimeMessagePreparator() {
	
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
			// TODO Auto-generated method stub
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				message.setFrom("dogfootshop@naver.com");
				message.setTo("dogfootshop@naver.com");
				message.setSubject("dogFootShop���Ϲ���");
				message.setText(str, true);
				//message.addInline("myPic", new ClassPathResource("img/kimgowun.GIF"));
				//message.addAttachment("myDoc", new ClassPathResource("doc/testDoc.txt"));
			}
		});
		view.setViewName("redirect:/front.do");
		session.setAttribute("msg", "���������� �����Ͽ����ϴ�.");  //  �׽�Ʈ���ؼ� session�� �����߰���
		return view;
	}
}
