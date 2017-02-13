package com.dogFoot.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.dogFoot.dao.DogFootDao;
import com.dogFoot.vo.BoardVo;
import com.dogFoot.vo.DetailGoodsVo;
import com.dogFoot.vo.EventBoardVo;
import com.dogFoot.vo.MemberVo;
import com.dogFoot.vo.OrderDetailVo;
import com.dogFoot.vo.OrdersVo;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class KJSDogFootController {
	
	@Autowired
	private DogFootDao dFDao;
	public void setdFDao(DogFootDao dFDao) {
		this.dFDao = dFDao;
	}
		
	@RequestMapping("/login.do")
	public ModelAndView login(HttpServletRequest request){
		ModelAndView view = new ModelAndView();
		String id = (String)request.getParameter("id");
		String pwd = (String)request.getParameter("pwd");
		//System.out.println(id);
		//System.out.println(pwd);
		
		
		//  사용자가 아이디, 비밀번호를 제대로 입력했다면,
		if(id != null && !id.equals("") && pwd != null && !pwd.equals("") ){
			MemberVo mVo = dFDao.checkIdPwd(id, pwd);
			
			//  DB에 해당 아이디, 비번에 대한 정보가 없다면!
			if(mVo == null){
				System.out.println("KJSDogFootController-login-해당id,pwd에 따른 DB없음!");
			}
			//  DB에 해당 아이디, 비번에 대한 정보가 있다면!
			else if( mVo.getM_no() > -1){
				System.out.println("KJSDogFootController-login-mVo.getM_no()"+mVo.getM_no());
				
				//  올바른 사용자 정보 세션담기
				HttpSession session = request.getSession();
				session.setAttribute("mVo", mVo);
				session.setAttribute("m_no", mVo.getM_no());
				session.setAttribute("m_level", mVo.getM_level());
			}
			
		//  사용자가 아이디, 비밀번호를 제대로 입력하지 않았다면	
		}else{
			System.out.println("DogFootKJSDogFootControllerController-login-id,pwd를 제대로 입력하지 않음!");
			
		}		

		view.setViewName("redirect:front.do");  //  로그인 ok/실패여부 상관없이 반환페이지: front.do
		
		return view;
	}	
	
	@RequestMapping("/logout.do")
	public ModelAndView logout(HttpSession session){
		ModelAndView view = new ModelAndView();

		view.setViewName("redirect:front.do");
		session.invalidate();		
		
		return view;
	}
	
		
	@RequestMapping(value="/compareId.do", produces="text/plain;charset=utf-8")
	@ResponseBody
	public String compareId(String id){
		System.out.println("KJSDogFootController-compareId-id:"+id);
		String str = "";
		ObjectMapper mapper = new ObjectMapper();
		try {
			str = mapper.writeValueAsString(dFDao.compareId(id));
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		return str;
	}
	
	@RequestMapping("/join.do")
	public ModelAndView join(HttpServletRequest request, MemberVo mVo){
		System.out.println("KJSDogFootController-join-mVo.getId():"+mVo.getId());
		System.out.println("KJSDogFootController-join-mVo.getM_no():"+mVo.getM_no());
		System.out.println("KJSDogFootController-join-mVo.getAddr():"+mVo.getAddr());
		System.out.println("KJSDogFootController-join-mVo.getE_mail():"+mVo.getE_mail());
		System.out.println("KJSDogFootController-join-mVo.getM_level():"+mVo.getM_level());
		System.out.println("KJSDogFootController-join-mVo.getM_name():"+mVo.getM_name());
		System.out.println("KJSDogFootController-join-mVo.getPhone():"+mVo.getPhone());
		System.out.println("KJSDogFootController-join-mVo.getPwd():"+mVo.getPwd());
		System.out.println("KJSDogFootController-join-mVo.getSns_id():"+mVo.getSns_id());
		System.out.println("KJSDogFootController-join-mVo.getSns_type():"+mVo.getSns_type());
		ModelAndView view = new ModelAndView();

		int re = dFDao.join(mVo);
		
		String id = mVo.getId();
		String pwd = mVo.getPwd();
		
		MemberVo mVo1 = dFDao.checkIdPwd(id, pwd);
		
		HttpSession session = request.getSession();
		session.setAttribute("m_no", mVo1.getM_no());
		session.setAttribute("m_level", mVo1.getM_level());
		
		view.setViewName("redirect:front.do");
		System.out.println("KJSDogFootController-join-re:" + re);
		return view;
	}
	
	@RequestMapping("/listEventBoard.do")
	public ModelAndView listEventBoard(HttpServletRequest request, @RequestParam(value="eventPageNUM", defaultValue="1") int eventPageNUM, String eventSearchField, String eventKeyword, String eventOrderField, @RequestParam(value="index", defaultValue="-1") String index){
		
		HttpSession session = request.getSession();
		
		String before_eventSearchField = (String)session.getAttribute("eventSearchField");
		String before_eventKeyword = (String)session.getAttribute("eventKeyword");
		String before_eventOrderField = (String)session.getAttribute("eventOrderField");
		//System.out.println("DogFootController-selectReviewBoard-before_searchField:"+before_eventSearchField);
		//System.out.println("DogFootController-selectReviewBoard-before_keyword:"+before_eventKeyword);
		//System.out.println("DogFootController-selectReviewBoard-before_orderField:"+before_eventOrderField);
		
		
		//  index가 1이면 최초 목록이므로, null로 해줘야함!
		if(index.equals("1")){
			eventSearchField = null;
			eventKeyword = null;
			eventOrderField = null;
		}
		//  index가 1이 아니면, 이전에 검색되어진 키워드들로 상태유지가 되어야함!
		else{
			//  맨처음에는 검색키워드들이 없으므로, listEventBoard.do 서비스요청시 keyword들은 전부 null 이거나 ""임!
			//  이러한 상황에서 if로 값을 입혀줘야함!
			if( eventKeyword == null || eventKeyword.equals("")){
				if(before_eventSearchField != null && !before_eventSearchField.equals("")){
					eventSearchField = before_eventSearchField;
					eventKeyword = before_eventKeyword;
				}
			}
			
			//  정렬필드도 똑같이
			if( eventOrderField == null || eventOrderField.equals("")){
				if(before_eventOrderField != null && !before_eventOrderField.equals("")){
					eventOrderField = before_eventOrderField;
				}
			}
		}
		
		
		
		//  그담 세션에 담기
		session.setAttribute("eventSearchField", eventSearchField);
		session.setAttribute("eventKeyword", eventKeyword);
		session.setAttribute("eventOrderField", eventOrderField);

		//  dFDao 정적변수를 갱신시켜주기!
		dFDao.eventTotalRecord = dFDao.eventTotalRecord(eventSearchField, eventKeyword);
		//dFDao.eventTotalPage = (int)Math.ceil( dFDao.eventTotalRecord(eventSearchField, eventKeyword) / dFDao.eventPageSIZE); 
		
		ModelAndView view = new ModelAndView();
		view.addObject("eBList", dFDao.listEventBoard(eventPageNUM, eventSearchField, eventKeyword, eventOrderField) );
		view.addObject("eventPageStr", dFDao.eventPageStr() );
		view.addObject("page","event/listEventBoard.jsp");
		view.setViewName("template");
		
		//System.out.println("DogFootController-selectReviewBoard-bList.size():"+dFDao.selectEventBoard().size());
		return view;
	}
	
	
	@RequestMapping(value="/insertEventBoard.do",method=RequestMethod.GET)
	public ModelAndView insertEventBoardForm(){
		ModelAndView view = new ModelAndView();
		view.addObject("page","event/insertEventBoard.jsp");
		view.setViewName("template");
		return view;
	}
	
	@RequestMapping(value="/insertEventBoard.do",method=RequestMethod.POST)
	public ModelAndView insertEventBoardSubmit(HttpServletRequest request, EventBoardVo ebVo){
		ModelAndView view = new ModelAndView();
		
		int b_no = dFDao.nextBoardNo();
		
		ebVo.setB_no(b_no);
		
		//  파일 넣기위한 코딩(아래)
		String path = request.getRealPath("resources/event");
		System.out.println("KJSDogFootController-insertEventBoardSubmit-path:" + path);
		String fname = "";
		
		
		//  이미지 파일첨부를 안했을 수도 있으므로, 아래처럼 두기 
		MultipartFile uploadFile = null; 
		
		if(ebVo.getUploadFile() != null && !ebVo.getUploadFile().equals("")){
			uploadFile = ebVo.getUploadFile();
			fname = uploadFile.getOriginalFilename();  //  실제업로드된 파일이름
			System.out.println("KJSDogFootController-insertEventBoardSubmit-fname(저장할파일이름):"+fname);
		}else{
			ebVo.setB_img("");
		}
		
		try {
			//  실제업로드된 파일이 있다면
			if(fname != null && !fname.equals("")){
				byte [] data = uploadFile.getBytes();
				ebVo.setB_img(fname);
				FileOutputStream fos = new FileOutputStream(path+"/"+fname);  //  실제 파일 흐름만들기 + 실제파일저장할 경로까지 정해주기
				fos.write(data);  //  실제파일 저장하기
				fos.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("KJSDogFootController-insertEventBoardSubmit-파일저장실패:"+e);
		}
		int re = dFDao.insertEventBoardVo(ebVo);
		if(re==1){
			System.out.println("KJSDogFootController-insertEventBoardSubmit-게시판등록 성공");
		}else{
			System.out.println("KJSDogFootController-insertEventBoardSubmit-게시판등록 실패");
		}
		
		view.addObject("eventPageStr", dFDao.eventPageStr() );
		view.setViewName("redirect:listEventBoard.do");
		return view;
	}
	
	@RequestMapping(value="/detailEventBoard.do")
	public ModelAndView detailEventBoard(HttpServletRequest request, int b_no){
		ModelAndView view = new ModelAndView();
		dFDao.updateHitEventBoard(b_no);  //  조회수 올리기
		EventBoardVo ebVo = dFDao.detailEventBoard(b_no);
		
		String fname = ebVo.getB_img();
		/*if( !fname.equals("") && fname != null){
			String path = request.getRealPath("resources/event");
			System.out.println("DogFootController-detailEventBoard-path:" + path);
			ebVo.setB_img(path+"/"+fname);
		}*/
		view.addObject("ebVo",ebVo);
		view.addObject("page","event/detailEventBoard.jsp");
		view.setViewName("template");
		return view;
	}
	
	@RequestMapping(value="/down.do", produces="text/plain;charset=utf-8")
	public ModelAndView download(HttpServletRequest request, int b_no) throws Exception{

		request.setCharacterEncoding("UTF-8");
		BoardVo bVo = dFDao.selectBoardVoOne(b_no);
		String fname = bVo.getB_img();
		System.out.println("DogFootController-download-fname(파라미터로 넘어온 것):" + fname );
		String path = request.getRealPath("resources/event"); // 기존에 파일업로드시 자동저장시켰던 장소의 경로
		System.out.println("DogFootController-DogFootController-path:"+path);
		File file = new File(path+"/"+fname);
		ModelAndView view = new ModelAndView("download", "file", file);
		return view;
	}
	
	@RequestMapping(value="updateEventBoard.do", method=RequestMethod.GET)
	public ModelAndView updateEventBoardForm(int b_no){
		ModelAndView view = new ModelAndView();
	
		view.addObject("ebVo", dFDao.detailEventBoard(b_no) );
	    view.addObject("page","event/updateEventBoard.jsp");
		view.setViewName("template");
		return view;
	}
	
	@RequestMapping(value="updateEventBoard.do", method=RequestMethod.POST)
	public ModelAndView updateEventBoardSubmit(HttpServletRequest request, EventBoardVo ebVo){
		
		ModelAndView view = new ModelAndView();
		String oldFname = ebVo.getB_img();  //  update하기전(수정전 파일이름)
		MultipartFile uploadFile = ebVo.getUploadFile();
		String path = request.getRealPath("resources/event");
		String fname = uploadFile.getOriginalFilename();  //  update해야할 파일이름
		System.out.println("KJSDogFootController-updateEventBoardSubmit-uploadFile-fileName:"+uploadFile.getOriginalFilename());
		
		try {
			//  수정할 파일이름이 있다는 가정하에 파일 업로드
			if(fname !=null && !fname.equals("")){
			     System.out.println("KJSDogFootController-updateEventBoardSubmit-fname(존재하므로):"+fname);
			     ebVo.setB_img(fname);
			     byte [] data = uploadFile.getBytes();
		         FileOutputStream fos = new FileOutputStream(path+"/"+fname);
		         fos.write(data);
		         fos.close();
		     }else{
		    	 ebVo.setB_img("");
		     }
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		
		int re = dFDao.updateEventBoard(ebVo);
		//  파일수정이 성공했다는 전제하에 파일 삭제
		if( re==1 ){
		     if( !fname.equals(oldFname) && oldFname != null && !oldFname.equals("")){
		         File file = new File(path + "/" + oldFname);
		         file.delete();
		     }		
		}
		view.setViewName("redirect:/listEventBoard.do");
		
		return view;
	}
	
			
	@RequestMapping("/deleteEventBoard.do")
	public ModelAndView deleteEventBoard(HttpServletRequest request, int b_no){
		ModelAndView view = new ModelAndView();
		EventBoardVo ebVo = dFDao.detailEventBoard(b_no);  //  파일도 삭제하기 위해서 vo생성
		
		String oldFname = ebVo.getB_img();
		String path = request.getRealPath("resources/event");
		
		int re = dFDao.deleteEventBoard(b_no);
		
		//  db에 레코드 삭제가 성공했을때만 파일삭제 가능!
		if(re==1){
			//  기존의 파일이름이 있었다면 실제 파일도 삭제하기!
			if( oldFname != null && !oldFname.equals("")){
			     File file = new File(path + "/" + oldFname);
			     file.delete();
			}
		}
		
		view.setViewName("redirect:listEventBoard.do");
		return view;
	}
	
	
	//   MyBoard 시작	
	@RequestMapping("/listMyBoard.do")
	public ModelAndView listMyBoard(HttpServletRequest request, @RequestParam(value="myBoardPageNUM", defaultValue="1") int myBoardPageNUM, String myBoardSearchField, String myBoardKeyword, String myBoardOrderField, @RequestParam(value="index", defaultValue="-1") String index, @RequestParam(value="b_type", defaultValue="1") int b_type){
		
		HttpSession session = request.getSession();
		
		String before_myBoardSearchField = (String)session.getAttribute("myBoardSearchField");
		String before_myBoardKeyword = (String)session.getAttribute("myBoardKeyword");
		String before_myBoardOrderField = (String)session.getAttribute("myBoardOrderField");
		
		System.out.println("KJSDogFootController-listMyBoard-before-myBoardSearchField:"+before_myBoardSearchField);
		
		int m_no = (Integer)session.getAttribute("m_no");
		System.out.println("KJSDogFootController-listMyBoard-before-m_no:"+m_no);
		
		//  index가 1이면 최초 목록이므로, null로 해줘야함!
		if(index.equals("1")){
			myBoardSearchField = null;
			myBoardKeyword = null;
			myBoardOrderField = null;
		}
		
		//  index가 1이 아니면, 이전에 검색되어진 키워드들로 상태유지가 되어야함!
		else{
			//  맨처음에는 검색키워드들이 없으므로, listEventBoard.do 서비스요청시 keyword들은 전부 null 이거나 ""임!
			//  이러한 상황에서 if로 값을 입혀줘야함!
			if( myBoardKeyword == null || myBoardKeyword.equals("")){
				if(before_myBoardSearchField != null && !before_myBoardSearchField.equals("")){
					myBoardSearchField = before_myBoardSearchField;
					myBoardKeyword = before_myBoardKeyword;
				}
			}
			
			//  정렬필드도 똑같이
			if( myBoardOrderField == null || myBoardOrderField.equals("")){
				if(before_myBoardOrderField != null && !before_myBoardOrderField.equals("")){
					myBoardOrderField = before_myBoardOrderField;
				}
			}
		}
		
		//  그담 세션에 담기
		session.setAttribute("myBoardSearchField", myBoardSearchField);
		session.setAttribute("myBoardKeyword", myBoardKeyword);
		session.setAttribute("myBoardOrderField", myBoardOrderField);
		
		//  dFDao 정적변수를 갱신시켜주기!
		dFDao.myBoardTotalRecord = dFDao.myBoardTotalRecord(myBoardSearchField, myBoardKeyword, m_no, b_type);
			 
		
		ModelAndView view = new ModelAndView();
		view.addObject("mbList", dFDao.listMyBoard(myBoardPageNUM, myBoardSearchField, myBoardKeyword, myBoardOrderField, m_no, b_type) );
		view.addObject("myBoardPageStr", dFDao.myBoardPageStr() );
		view.addObject("page","afterLogin/default_Side_myBoard.jsp");
		view.setViewName("template");
		return view;
	}
	
	//  MyBoard 끝
	
	
	
	//  마이페이지(로그인 필터 끝난 이후에 아래와 같이 진행됨 시작
	@RequestMapping("/myInfoPage.do")
	public ModelAndView afterLoginFilter(){
		ModelAndView view = new ModelAndView();
		
		view.addObject("page","afterLogin/default_Side_myInfo.jsp");
		view.setViewName("template");
		return view;
	}
	//  마이페이지(로그인 필터 끝난 이후에 아래와 같이 진행됨 끝
	
	
	@RequestMapping("/myBasket.do")
	public ModelAndView myBasket(HttpSession session){
		ModelAndView view = new ModelAndView();

		//  아래 cart 넘어오는지 확인용 시작
			List<DetailGoodsVo> cartList = new ArrayList<DetailGoodsVo>();
			if(session.getAttribute("cartList") != null)
			{
				cartList = (List<DetailGoodsVo>)session.getAttribute("cartList");
			}
			System.out.println( "KJSDogFootController-myBasket.do-cartList.size()(필터로넘긴후, 필터에서 확인):"+cartList.size() );  //  장바구니가 제대로 넘어왔는지 확인
		//  아래 cart 넘어오는지 확인용 끝
			
		view.addObject("page","afterLogin/default_Side_myBasket.jsp");
		view.setViewName("template");
		return view;
	}
	
	//  마이페이지(결재내역페이지 )
	@RequestMapping("/myPayList.do")
	public ModelAndView myPayList(HttpServletRequest request, @RequestParam(value="myPayPageNUM", defaultValue="1") int myPayPageNUM, String myPaySearchField, String myPayKeyword, String myPayOrderField, @RequestParam(value="index", defaultValue="-1") String index){
		
		HttpSession session = request.getSession();
		
		String before_myPaySearchField = (String)session.getAttribute("myPaySearchField");
		String before_myPayKeyword = (String)session.getAttribute("myPayKeyword");
		String before_myPayOrderField = (String)session.getAttribute("myPayOrderField");
		
		System.out.println("DogFootController-myPayList-before-myPaySearchField:"+before_myPaySearchField);
		
		int m_no = (Integer)session.getAttribute("m_no");
		System.out.println("KJSDogFootController-myPayList-m_no:"+m_no);
		
		//  index가 1이면 최초 목록이므로, null로 해줘야함!
		if(index.equals("1")){
			myPaySearchField = null;
			myPayKeyword = null;
			myPayOrderField = null;
		}
		
		//  index가 1이 아니면, 이전에 검색되어진 키워드들로 상태유지가 되어야함!
		else{
			//  맨처음에는 검색키워드들이 없으므로, myPayList.do 서비스요청시 keyword들은 전부 null 이거나 ""임!
			//  이러한 상황에서 if로 값을 입혀줘야함!
			if( myPayKeyword == null || myPayKeyword.equals("")){
				if(before_myPaySearchField != null && !before_myPaySearchField.equals("")){
					myPaySearchField = before_myPaySearchField;
					myPayKeyword = before_myPayKeyword;
				}
			}
			
			//  정렬필드도 똑같이
			if( myPayOrderField == null || myPayOrderField.equals("")){
				if(before_myPayOrderField != null && !before_myPayOrderField.equals("")){
					myPayOrderField = before_myPayOrderField;
				}
			}
		}
		
		//  그담 세션에 담기
		session.setAttribute("myPaySearchField", myPaySearchField);
		session.setAttribute("myPayKeyword", myPayKeyword);
		session.setAttribute("myPayOrderField", myPayOrderField);
		
		//  dFDao 정적변수를 갱신시켜주기!
		dFDao.myPayTotalRecord = dFDao.myPayTotalRecord(myPaySearchField, myPayKeyword, m_no);

		ModelAndView view = new ModelAndView();
		view.addObject("oVoList", dFDao.listMyPay(myPayPageNUM, myPaySearchField, myPayKeyword, myPayOrderField, m_no) );
		view.addObject("myPayPageStr", dFDao.myPayPageStr() );
		view.addObject("page","afterLogin/default_Side_myPay.jsp");
		view.setViewName("template");
		return view;
	}
		
	//  개인정보 수정 update
	@RequestMapping(value="/updateMyInfo.do", method=RequestMethod.POST)
	public ModelAndView updateMyInfo(HttpSession session, MemberVo mVo){
		ModelAndView view = new ModelAndView();
		view.setViewName("redirect:myInfoPage.do");
		
		int re = dFDao.updateMyInfo(mVo);
		
		//  수정이후, 세션 회원정보 다시 갱신해주기
		MemberVo mVo1 = dFDao.checkIdPwd(mVo.getId(), mVo.getPwd());
		session.setAttribute("mVo", mVo1);
		session.setAttribute("m_no", mVo1.getM_no());
		session.setAttribute("m_level", mVo1.getM_level());
		
		System.out.println("KJSDogFootController-updateMyInfo-개인정보수정결과re:"+re);
		
		return view;
	}
	
	@RequestMapping(value="/checkPwd.do", produces="text/plain;charset=utf-8")
	@ResponseBody
	public String checkPwd(HttpSession session, String pwd){
		System.out.println("KJSDogFootController-checkPwd-pwd:"+pwd);
		int m_no = (Integer)session.getAttribute("m_no");
		System.out.println("KJSDogFootController-checkPwd-m_no:"+m_no);
		
		MemberVo mVo = dFDao.selectMemberVo(m_no);
		String str = "";
		if( !mVo.getPwd().equals(pwd) ){
			str = "error";
			System.out.println("비밀번호대조결과str:"+str);
		}else{
			str = "ok";
			System.out.println("비밀번호대조결과str:"+str);
		}
		return str;
	}
	
	//  결제주문장 insert
	@RequestMapping(value="/order.do", produces="text/plain;charset=utf-8", method=RequestMethod.POST)
	@ResponseBody
	public void insertOrder(HttpServletRequest request, OrdersVo oVo){
		//ModelAndView view = new ModelAndView();
		//view.setViewName("redirect:myPayList.do");
		System.out.println("KJSDogFootController-insertOrder-주문장들어옮!");
		System.out.println("KJSDogFootController-insertOrder-oVo.getReceiver_addr():"+oVo.getReceiver_addr());
		oVo.setO_no(dFDao.nextOrderNo());  //  주문장번호 다음번호 함수 써서 넣음!
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd, hh:mm:ss a"); 
		String delivery_no = "CJ"+ sdf.format(dt).toString();
		oVo.setDelivery_no(delivery_no);  //  운송장번호 현재시간데이터값 String으로 넣음!
		oVo.setO_code(2);  //  배송준비중으로 초기값 넣음!		
		
		int re = dFDao.insertOrder(oVo);
		
		System.out.println("KJSDogFootController-insertOrder-주문장등록결과re:"+re);

	}
	
	@RequestMapping(value="/orderDetail.do", produces="text/plain;charset=utf-8")
	@ResponseBody
	public void insertOrderDetail(OrderDetailVo oDVo){
		System.out.println("KJSDogFootController-insertOrderDetail-들어왔다!");
		System.out.println("KJSDogFootController-insertOrderDetail-oDVo.getSubtotal:"+oDVo.getSubtotal());
		
		oDVo.setOd_no(dFDao.nextOrderDetailNo());
		oDVo.setO_no(dFDao.MaxOrderNo());
		
		System.out.println("KJSDogFootController-insertOrderDetail-oDVo.getOd_no():"+oDVo.getOd_no());
		System.out.println("KJSDogFootController-insertOrderDetail-oDVo.getO_no():"+oDVo.getO_no());
		System.out.println("KJSDogFootController-insertOrderDetail-oDVo.getG_no():"+oDVo.getG_no());
		System.out.println("KJSDogFootController-insertOrderDetail-oDVo.getCount():"+oDVo.getCount());
		System.out.println("KJSDogFootController-insertOrderDetail-oDVo.getSubtotal():"+oDVo.getSubtotal());
		int re =dFDao.insertOrderDetail(oDVo);
		System.out.println("KJSDogFootController-insertOrderDetail-re:"+re);
		
	}
	
	
	@RequestMapping(value="/totalChat.do", produces="application/json; charset=UTF-8")
	@ResponseBody
	public void updateMyInfo(HttpSession session, String totalData){
		System.out.println("totalData:" + totalData);

		session.setAttribute("totalData", totalData);
	}
	

}
