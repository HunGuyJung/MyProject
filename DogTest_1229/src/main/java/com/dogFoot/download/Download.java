package com.dogFoot.download;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

public class Download extends AbstractView{

	public Download(){
		setContentType("appplication/download;charset=UTF-8");
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		File file = (File)model.get("file");
		response.setContentType(getContentType());
		response.setContentLength((int)file.length());
		String fileName = java.net.URLEncoder.encode(file.getName(), "UTF-8");  // 저장될 화면상에서 한글이름으로도 저장가능하게 인코딩기능 추가!!
		
		// 파일이 저장될때 팝업창에 자동으로 등록되는 이름
		// 원래 fileName="fileName"; 으로 들어가야함! ex) 그래서 역슬러쉬\"를쓰면 "만 들어가게 됨!
		response.setHeader("Content-Disposition", "attachment;filename=\""+fileName+"\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		//  response.setHeader("Content-Type", contentTyp+"; charset=utf-8");
		// 위와같은 설정이 끝나면, 아래처럼 stream과 data를 이용해서 파일을 보내고 받아야함!
		OutputStream out = response.getOutputStream();  // 출력을 위한 스트림 선언
		FileInputStream fis = null;  // 메모리에 읽기위해서 스트림 선언
		try {
			fis = new FileInputStream(file);
			FileCopyUtils.copy(fis, out);  // 메모리에 읽은 것(fis)을 내보낸다!(out)
			fis.close();
			out.flush();					
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("download-파일다운실패:"+e);
		}
	}
	
}
