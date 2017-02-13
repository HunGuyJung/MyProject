package com.dogFoot.dogCodi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.ModelAndView;

import com.dogFoot.dao.DogFootDao;
import com.dogFoot.vo.DetailGoodsVo;
import com.dogFoot.vo.GoodsVo;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml",
		"file:src/main/webapp/WEB-INF/spring/root-context.xml"
})
public class ControllerTest {
	
	@Autowired DogFootDao dao;
	
	@Test
	public void detailGoods()
	{
		int b_no = 1;
		ModelAndView mv = new ModelAndView();
		//System.out.println("��ǰ�� �۹�ȣ : "+b_no);
		DetailGoodsVo dgv = dao.detailGoods(b_no);
		mv.addObject("dgv", dgv);
		//System.out.println("��ǰ�� ��ǰ�ڵ� : "+dgb.getG_code());
		mv.addObject("color", dao.detailGoods_color(dgv.getG_code()));
		//System.out.println("��ǰ�� ���� : "+dao.detailGoods_color(dgb.getG_code()));
		mv.addObject("page", "goods/detailGoodsBoard.jsp");
		mv.setViewName("template");
		System.out.println("�׽�Ʈ Ȯ��");
	}
}
