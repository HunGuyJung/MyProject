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
		
		
		//  ����ڰ� ���̵�, ��й�ȣ�� ����� �Է��ߴٸ�,
		if(id != null && !id.equals("") && pwd != null && !pwd.equals("") ){
			MemberVo mVo = dFDao.checkIdPwd(id, pwd);
			
			//  DB�� �ش� ���̵�, ����� ���� ������ ���ٸ�!
			if(mVo == null){
				System.out.println("KJSDogFootController-login-�ش�id,pwd�� ���� DB����!");
			}
			//  DB�� �ش� ���̵�, ����� ���� ������ �ִٸ�!
			else if( mVo.getM_no() > -1){
				System.out.println("KJSDogFootController-login-mVo.getM_no()"+mVo.getM_no());
				
				//  �ùٸ� ����� ���� ���Ǵ��
				HttpSession session = request.getSession();
				session.setAttribute("mVo", mVo);
				session.setAttribute("m_no", mVo.getM_no());
				session.setAttribute("m_level", mVo.getM_level());
			}
			
		//  ����ڰ� ���̵�, ��й�ȣ�� ����� �Է����� �ʾҴٸ�	
		}else{
			System.out.println("DogFootKJSDogFootControllerController-login-id,pwd�� ����� �Է����� ����!");
			
		}		

		view.setViewName("redirect:front.do");  //  �α��� ok/���п��� ������� ��ȯ������: front.do
		
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
		
		
		//  index�� 1�̸� ���� ����̹Ƿ�, null�� �������!
		if(index.equals("1")){
			eventSearchField = null;
			eventKeyword = null;
			eventOrderField = null;
		}
		//  index�� 1�� �ƴϸ�, ������ �˻��Ǿ��� Ű������ ���������� �Ǿ����!
		else{
			//  ��ó������ �˻�Ű������� �����Ƿ�, listEventBoard.do ���񽺿�û�� keyword���� ���� null �̰ų� ""��!
			//  �̷��� ��Ȳ���� if�� ���� ���������!
			if( eventKeyword == null || eventKeyword.equals("")){
				if(before_eventSearchField != null && !before_eventSearchField.equals("")){
					eventSearchField = before_eventSearchField;
					eventKeyword = before_eventKeyword;
				}
			}
			
			//  �����ʵ嵵 �Ȱ���
			if( eventOrderField == null || eventOrderField.equals("")){
				if(before_eventOrderField != null && !before_eventOrderField.equals("")){
					eventOrderField = before_eventOrderField;
				}
			}
		}
		
		
		
		//  �״� ���ǿ� ���
		session.setAttribute("eventSearchField", eventSearchField);
		session.setAttribute("eventKeyword", eventKeyword);
		session.setAttribute("eventOrderField", eventOrderField);

		//  dFDao ���������� ���Ž����ֱ�!
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
		
		//  ���� �ֱ����� �ڵ�(�Ʒ�)
		String path = request.getRealPath("resources/event");
		System.out.println("KJSDogFootController-insertEventBoardSubmit-path:" + path);
		String fname = "";
		
		
		//  �̹��� ����÷�θ� ������ ���� �����Ƿ�, �Ʒ�ó�� �α� 
		MultipartFile uploadFile = null; 
		
		if(ebVo.getUploadFile() != null && !ebVo.getUploadFile().equals("")){
			uploadFile = ebVo.getUploadFile();
			fname = uploadFile.getOriginalFilename();  //  �������ε�� �����̸�
			System.out.println("KJSDogFootController-insertEventBoardSubmit-fname(�����������̸�):"+fname);
		}else{
			ebVo.setB_img("");
		}
		
		try {
			//  �������ε�� ������ �ִٸ�
			if(fname != null && !fname.equals("")){
				byte [] data = uploadFile.getBytes();
				ebVo.setB_img(fname);
				FileOutputStream fos = new FileOutputStream(path+"/"+fname);  //  ���� ���� �帧����� + �������������� ��α��� �����ֱ�
				fos.write(data);  //  �������� �����ϱ�
				fos.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("KJSDogFootController-insertEventBoardSubmit-�����������:"+e);
		}
		int re = dFDao.insertEventBoardVo(ebVo);
		if(re==1){
			System.out.println("KJSDogFootController-insertEventBoardSubmit-�Խ��ǵ�� ����");
		}else{
			System.out.println("KJSDogFootController-insertEventBoardSubmit-�Խ��ǵ�� ����");
		}
		
		view.addObject("eventPageStr", dFDao.eventPageStr() );
		view.setViewName("redirect:listEventBoard.do");
		return view;
	}
	
	@RequestMapping(value="/detailEventBoard.do")
	public ModelAndView detailEventBoard(HttpServletRequest request, int b_no){
		ModelAndView view = new ModelAndView();
		dFDao.updateHitEventBoard(b_no);  //  ��ȸ�� �ø���
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
		System.out.println("DogFootController-download-fname(�Ķ���ͷ� �Ѿ�� ��):" + fname );
		String path = request.getRealPath("resources/event"); // ������ ���Ͼ��ε�� �ڵ�������״� ����� ���
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
		String oldFname = ebVo.getB_img();  //  update�ϱ���(������ �����̸�)
		MultipartFile uploadFile = ebVo.getUploadFile();
		String path = request.getRealPath("resources/event");
		String fname = uploadFile.getOriginalFilename();  //  update�ؾ��� �����̸�
		System.out.println("KJSDogFootController-updateEventBoardSubmit-uploadFile-fileName:"+uploadFile.getOriginalFilename());
		
		try {
			//  ������ �����̸��� �ִٴ� �����Ͽ� ���� ���ε�
			if(fname !=null && !fname.equals("")){
			     System.out.println("KJSDogFootController-updateEventBoardSubmit-fname(�����ϹǷ�):"+fname);
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
		//  ���ϼ����� �����ߴٴ� �����Ͽ� ���� ����
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
		EventBoardVo ebVo = dFDao.detailEventBoard(b_no);  //  ���ϵ� �����ϱ� ���ؼ� vo����
		
		String oldFname = ebVo.getB_img();
		String path = request.getRealPath("resources/event");
		
		int re = dFDao.deleteEventBoard(b_no);
		
		//  db�� ���ڵ� ������ ������������ ���ϻ��� ����!
		if(re==1){
			//  ������ �����̸��� �־��ٸ� ���� ���ϵ� �����ϱ�!
			if( oldFname != null && !oldFname.equals("")){
			     File file = new File(path + "/" + oldFname);
			     file.delete();
			}
		}
		
		view.setViewName("redirect:listEventBoard.do");
		return view;
	}
	
	
	//   MyBoard ����	
	@RequestMapping("/listMyBoard.do")
	public ModelAndView listMyBoard(HttpServletRequest request, @RequestParam(value="myBoardPageNUM", defaultValue="1") int myBoardPageNUM, String myBoardSearchField, String myBoardKeyword, String myBoardOrderField, @RequestParam(value="index", defaultValue="-1") String index, @RequestParam(value="b_type", defaultValue="1") int b_type){
		
		HttpSession session = request.getSession();
		
		String before_myBoardSearchField = (String)session.getAttribute("myBoardSearchField");
		String before_myBoardKeyword = (String)session.getAttribute("myBoardKeyword");
		String before_myBoardOrderField = (String)session.getAttribute("myBoardOrderField");
		
		System.out.println("KJSDogFootController-listMyBoard-before-myBoardSearchField:"+before_myBoardSearchField);
		
		int m_no = (Integer)session.getAttribute("m_no");
		System.out.println("KJSDogFootController-listMyBoard-before-m_no:"+m_no);
		
		//  index�� 1�̸� ���� ����̹Ƿ�, null�� �������!
		if(index.equals("1")){
			myBoardSearchField = null;
			myBoardKeyword = null;
			myBoardOrderField = null;
		}
		
		//  index�� 1�� �ƴϸ�, ������ �˻��Ǿ��� Ű������ ���������� �Ǿ����!
		else{
			//  ��ó������ �˻�Ű������� �����Ƿ�, listEventBoard.do ���񽺿�û�� keyword���� ���� null �̰ų� ""��!
			//  �̷��� ��Ȳ���� if�� ���� ���������!
			if( myBoardKeyword == null || myBoardKeyword.equals("")){
				if(before_myBoardSearchField != null && !before_myBoardSearchField.equals("")){
					myBoardSearchField = before_myBoardSearchField;
					myBoardKeyword = before_myBoardKeyword;
				}
			}
			
			//  �����ʵ嵵 �Ȱ���
			if( myBoardOrderField == null || myBoardOrderField.equals("")){
				if(before_myBoardOrderField != null && !before_myBoardOrderField.equals("")){
					myBoardOrderField = before_myBoardOrderField;
				}
			}
		}
		
		//  �״� ���ǿ� ���
		session.setAttribute("myBoardSearchField", myBoardSearchField);
		session.setAttribute("myBoardKeyword", myBoardKeyword);
		session.setAttribute("myBoardOrderField", myBoardOrderField);
		
		//  dFDao ���������� ���Ž����ֱ�!
		dFDao.myBoardTotalRecord = dFDao.myBoardTotalRecord(myBoardSearchField, myBoardKeyword, m_no, b_type);
			 
		
		ModelAndView view = new ModelAndView();
		view.addObject("mbList", dFDao.listMyBoard(myBoardPageNUM, myBoardSearchField, myBoardKeyword, myBoardOrderField, m_no, b_type) );
		view.addObject("myBoardPageStr", dFDao.myBoardPageStr() );
		view.addObject("page","afterLogin/default_Side_myBoard.jsp");
		view.setViewName("template");
		return view;
	}
	
	//  MyBoard ��
	
	
	
	//  ����������(�α��� ���� ���� ���Ŀ� �Ʒ��� ���� ����� ����
	@RequestMapping("/myInfoPage.do")
	public ModelAndView afterLoginFilter(){
		ModelAndView view = new ModelAndView();
		
		view.addObject("page","afterLogin/default_Side_myInfo.jsp");
		view.setViewName("template");
		return view;
	}
	//  ����������(�α��� ���� ���� ���Ŀ� �Ʒ��� ���� ����� ��
	
	
	@RequestMapping("/myBasket.do")
	public ModelAndView myBasket(HttpSession session){
		ModelAndView view = new ModelAndView();

		//  �Ʒ� cart �Ѿ������ Ȯ�ο� ����
			List<DetailGoodsVo> cartList = new ArrayList<DetailGoodsVo>();
			if(session.getAttribute("cartList") != null)
			{
				cartList = (List<DetailGoodsVo>)session.getAttribute("cartList");
			}
			System.out.println( "KJSDogFootController-myBasket.do-cartList.size()(���ͷγѱ���, ���Ϳ��� Ȯ��):"+cartList.size() );  //  ��ٱ��ϰ� ����� �Ѿ�Դ��� Ȯ��
		//  �Ʒ� cart �Ѿ������ Ȯ�ο� ��
			
		view.addObject("page","afterLogin/default_Side_myBasket.jsp");
		view.setViewName("template");
		return view;
	}
	
	//  ����������(���系�������� )
	@RequestMapping("/myPayList.do")
	public ModelAndView myPayList(HttpServletRequest request, @RequestParam(value="myPayPageNUM", defaultValue="1") int myPayPageNUM, String myPaySearchField, String myPayKeyword, String myPayOrderField, @RequestParam(value="index", defaultValue="-1") String index){
		
		HttpSession session = request.getSession();
		
		String before_myPaySearchField = (String)session.getAttribute("myPaySearchField");
		String before_myPayKeyword = (String)session.getAttribute("myPayKeyword");
		String before_myPayOrderField = (String)session.getAttribute("myPayOrderField");
		
		System.out.println("DogFootController-myPayList-before-myPaySearchField:"+before_myPaySearchField);
		
		int m_no = (Integer)session.getAttribute("m_no");
		System.out.println("KJSDogFootController-myPayList-m_no:"+m_no);
		
		//  index�� 1�̸� ���� ����̹Ƿ�, null�� �������!
		if(index.equals("1")){
			myPaySearchField = null;
			myPayKeyword = null;
			myPayOrderField = null;
		}
		
		//  index�� 1�� �ƴϸ�, ������ �˻��Ǿ��� Ű������ ���������� �Ǿ����!
		else{
			//  ��ó������ �˻�Ű������� �����Ƿ�, myPayList.do ���񽺿�û�� keyword���� ���� null �̰ų� ""��!
			//  �̷��� ��Ȳ���� if�� ���� ���������!
			if( myPayKeyword == null || myPayKeyword.equals("")){
				if(before_myPaySearchField != null && !before_myPaySearchField.equals("")){
					myPaySearchField = before_myPaySearchField;
					myPayKeyword = before_myPayKeyword;
				}
			}
			
			//  �����ʵ嵵 �Ȱ���
			if( myPayOrderField == null || myPayOrderField.equals("")){
				if(before_myPayOrderField != null && !before_myPayOrderField.equals("")){
					myPayOrderField = before_myPayOrderField;
				}
			}
		}
		
		//  �״� ���ǿ� ���
		session.setAttribute("myPaySearchField", myPaySearchField);
		session.setAttribute("myPayKeyword", myPayKeyword);
		session.setAttribute("myPayOrderField", myPayOrderField);
		
		//  dFDao ���������� ���Ž����ֱ�!
		dFDao.myPayTotalRecord = dFDao.myPayTotalRecord(myPaySearchField, myPayKeyword, m_no);

		ModelAndView view = new ModelAndView();
		view.addObject("oVoList", dFDao.listMyPay(myPayPageNUM, myPaySearchField, myPayKeyword, myPayOrderField, m_no) );
		view.addObject("myPayPageStr", dFDao.myPayPageStr() );
		view.addObject("page","afterLogin/default_Side_myPay.jsp");
		view.setViewName("template");
		return view;
	}
		
	//  �������� ���� update
	@RequestMapping(value="/updateMyInfo.do", method=RequestMethod.POST)
	public ModelAndView updateMyInfo(HttpSession session, MemberVo mVo){
		ModelAndView view = new ModelAndView();
		view.setViewName("redirect:myInfoPage.do");
		
		int re = dFDao.updateMyInfo(mVo);
		
		//  ��������, ���� ȸ������ �ٽ� �������ֱ�
		MemberVo mVo1 = dFDao.checkIdPwd(mVo.getId(), mVo.getPwd());
		session.setAttribute("mVo", mVo1);
		session.setAttribute("m_no", mVo1.getM_no());
		session.setAttribute("m_level", mVo1.getM_level());
		
		System.out.println("KJSDogFootController-updateMyInfo-���������������re:"+re);
		
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
			System.out.println("��й�ȣ�������str:"+str);
		}else{
			str = "ok";
			System.out.println("��й�ȣ�������str:"+str);
		}
		return str;
	}
	
	//  �����ֹ��� insert
	@RequestMapping(value="/order.do", produces="text/plain;charset=utf-8", method=RequestMethod.POST)
	@ResponseBody
	public void insertOrder(HttpServletRequest request, OrdersVo oVo){
		//ModelAndView view = new ModelAndView();
		//view.setViewName("redirect:myPayList.do");
		System.out.println("KJSDogFootController-insertOrder-�ֹ������!");
		System.out.println("KJSDogFootController-insertOrder-oVo.getReceiver_addr():"+oVo.getReceiver_addr());
		oVo.setO_no(dFDao.nextOrderNo());  //  �ֹ����ȣ ������ȣ �Լ� �Ἥ ����!
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd, hh:mm:ss a"); 
		String delivery_no = "CJ"+ sdf.format(dt).toString();
		oVo.setDelivery_no(delivery_no);  //  ������ȣ ����ð������Ͱ� String���� ����!
		oVo.setO_code(2);  //  ����غ������� �ʱⰪ ����!		
		
		int re = dFDao.insertOrder(oVo);
		
		System.out.println("KJSDogFootController-insertOrder-�ֹ����ϰ��re:"+re);

	}
	
	@RequestMapping(value="/orderDetail.do", produces="text/plain;charset=utf-8")
	@ResponseBody
	public void insertOrderDetail(OrderDetailVo oDVo){
		System.out.println("KJSDogFootController-insertOrderDetail-���Դ�!");
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
