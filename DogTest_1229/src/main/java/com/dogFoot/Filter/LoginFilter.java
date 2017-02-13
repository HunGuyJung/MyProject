package com.dogFoot.Filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dogFoot.vo.DetailGoodsVo;

/**
 * Servlet Filter implementation class LoginFilter
 */
public class LoginFilter implements Filter {

    /**
     * Default constructor. 
     */
    public LoginFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		
		HttpSession session = ((HttpServletRequest)request).getSession();
		/*List<DetailGoodsVo> cartList = (List<DetailGoodsVo>) session.getAttribute("cartList");
		System.out.println("������ ���:"+cartList.size());*/
		System.out.println("LoginFilter-doFilter-session:"+session);
		
		//  �Ʒ� cart �Ѿ������ Ȯ�ο� ����
		List<DetailGoodsVo> cartList = new ArrayList<DetailGoodsVo>();
		if(session.getAttribute("cartList") != null)
		{
			cartList = (List<DetailGoodsVo>)session.getAttribute("cartList");
		}
		System.out.println( "LoginFilter-doFilter-cartList.size()(���ͷγѱ���, ���Ϳ��� Ȯ��):"+cartList.size() );  //  ��ٱ��ϰ� ����� �Ѿ�Դ��� Ȯ��
		//  �Ʒ� cart �Ѿ������ Ȯ�ο� ��
		
		
		int m_no = -1;
		if(session.getAttribute("m_no") == null ){
			m_no = -1;
		}else{
			m_no = (Integer) session.getAttribute("m_no");
		}
		
		System.out.println("LoginFilter-doFilter-m_no(String):"+m_no);
		//  ȸ����ȣ�� ����ִٸ�!(�α����� �Ǿ��ִٸ� -> �������� ��û�� ���񽺸� ����(doFilter)
		if(m_no != -1){
			System.out.println("LoginFilter-doFilter-m_no(-1�ƴ�):"+m_no);
			chain.doFilter(request, response);
			
		}
		
		else{  //  �α����� ����� �ȵǾ��ִٸ�! -> ����Ʈ�������� �̵�(�α��� ���� �ʾұ⶧���� �ش� ���񽺸� ���� ����ɼ� ����!
			((HttpServletResponse)response).sendRedirect("front.do");
		}	             
		
		// pass the request along the filter chain
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
