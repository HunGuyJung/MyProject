package com.dogFoot.dogCodi;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dogFoot.dao.DogFootDao;
import com.dogFoot.vo.OrdersVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml",
		"file:src/main/webapp/WEB-INF/spring/root-context.xml"
})
public class DaoTest {
	
	
	
	@Test
	public void daoTest() {
		DogFootDao dao = new DogFootDao();
		List<OrdersVo> list = dao.manager_Sales_chart1();
		System.out.println(list);
		System.out.println("테스트 시작");
	}
	
}
