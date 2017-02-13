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
		System.out.println("사이즈 출력:"+cartList.size());*/
		System.out.println("LoginFilter-doFilter-session:"+session);
		
		//  아래 cart 넘어오는지 확인용 시작
		List<DetailGoodsVo> cartList = new ArrayList<DetailGoodsVo>();
		if(session.getAttribute("cartList") != null)
		{
			cartList = (List<DetailGoodsVo>)session.getAttribute("cartList");
		}
		System.out.println( "LoginFilter-doFilter-cartList.size()(필터로넘긴후, 필터에서 확인):"+cartList.size() );  //  장바구니가 제대로 넘어왔는지 확인
		//  아래 cart 넘어오는지 확인용 끝
		
		
		int m_no = -1;
		if(session.getAttribute("m_no") == null ){
			m_no = -1;
		}else{
			m_no = (Integer) session.getAttribute("m_no");
		}
		
		System.out.println("LoginFilter-doFilter-m_no(String):"+m_no);
		//  회원번호가 담겨있다면!(로그인이 되어있다면 -> 정상으로 요청된 서비스명 가기(doFilter)
		if(m_no != -1){
			System.out.println("LoginFilter-doFilter-m_no(-1아님):"+m_no);
			chain.doFilter(request, response);
			
		}
		
		else{  //  로그인이 제대로 안되어있다면! -> 디폴트페이지로 이동(로그인 되지 않았기때문에 해당 서비스명 으로 진행될수 없음!
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
