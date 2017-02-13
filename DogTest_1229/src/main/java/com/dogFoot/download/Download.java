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
		String fileName = java.net.URLEncoder.encode(file.getName(), "UTF-8");  // ����� ȭ��󿡼� �ѱ��̸����ε� ���尡���ϰ� ���ڵ���� �߰�!!
		
		// ������ ����ɶ� �˾�â�� �ڵ����� ��ϵǴ� �̸�
		// ���� fileName="fileName"; ���� ������! ex) �׷��� ��������\"������ "�� ���� ��!
		response.setHeader("Content-Disposition", "attachment;filename=\""+fileName+"\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		//  response.setHeader("Content-Type", contentTyp+"; charset=utf-8");
		// ���Ͱ��� ������ ������, �Ʒ�ó�� stream�� data�� �̿��ؼ� ������ ������ �޾ƾ���!
		OutputStream out = response.getOutputStream();  // ����� ���� ��Ʈ�� ����
		FileInputStream fis = null;  // �޸𸮿� �б����ؼ� ��Ʈ�� ����
		try {
			fis = new FileInputStream(file);
			FileCopyUtils.copy(fis, out);  // �޸𸮿� ���� ��(fis)�� ��������!(out)
			fis.close();
			out.flush();					
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("download-���ϴٿ����:"+e);
		}
	}
	
}
