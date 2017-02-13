package com.dogFoot.dao;

import java.io.Reader;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Repository;

import com.dogFoot.vo.BoardLikeVo;
import com.dogFoot.vo.BoardVo;
import com.dogFoot.vo.CodiVo;
import com.dogFoot.vo.CommentsVo;
import com.dogFoot.vo.DealVo;
import com.dogFoot.vo.DetailGoodsVo;
import com.dogFoot.vo.EventBoardVo;
import com.dogFoot.vo.GoodsGroupVo;
import com.dogFoot.vo.GoodsVo;
import com.dogFoot.vo.MemberVo;
import com.dogFoot.vo.MyPayVo;
import com.dogFoot.vo.OrderDetailVo;
import com.dogFoot.vo.OrdersVo;
import com.dogFoot.vo.ReviewVo;
import com.dogFoot.vo.SearchVo;

@Repository
public class DogFootDao {
	
	private static SqlSessionFactory factory;
	static{
		try {
			Reader reader = Resources.getResourceAsReader("com/dogFoot/data/sqlMapConfig.xml");
			factory = new SqlSessionFactoryBuilder().build(reader);
			reader.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
	}
	
	
	//===================================김준성 시작========================================	
	
	public MemberVo checkIdPwd(String id, String pwd){
		MemberVo mVo = null;
		SqlSession session = factory.openSession();
		HashMap<String, String> map = new HashMap<String, String>();
		System.out.println("DogFootDao-checkIdPwd-id:"+id);
		System.out.println("DogFootDao-checkIdPwd-pwd:"+pwd);
		map.put("id", id);
		map.put("pwd", pwd);
		mVo = session.selectOne("KJS.checkIdPwd", map);
		//  System.out.println("DogFootDao-checkIdPwd-mVo.getId():"+mVo.getId());
		session.close();
		return mVo;
	}	
	
	public MemberVo compareId(String id){
		MemberVo mVo = null;
		SqlSession session = factory.openSession();
		mVo = session.selectOne("KJS.compareId", id);
		session.close();
		return mVo;
	}
	
	//  회원번호중에 가장 큰번호의 다음번호 반환
	public int nextNo(){
		int nextNo = -1;
		SqlSession session = factory.openSession();
		nextNo = session.selectOne("KJS.nextNo");
		session.close();
		return nextNo;
	}
	
	public int join(MemberVo mVo){
		System.out.println("DogFootDao-join-mVo.getId():"+mVo.getId());
		SqlSession session = factory.openSession(true);
		int nextNo = nextNo();
		System.out.println("DogFootDao-join-nextNo:"+nextNo);
		int re = -1;
		mVo.setM_no(nextNo);  //  가장큰 뒤의 번호를 붙임
		System.out.println("DogFootDao-join-mVo.getSns_id():"+mVo.getSns_id());
		
		/*if( request.getParameter("sns_type") != null ){
			int sns_type = Integer.parseInt(request.getParameter("sns_type"));
			m.setSns_type(sns_type);
			String sns_id = request.getParameter("sns_id");
			m.setSns_id(sns_id);
		};*/
		
		if(mVo.getSns_id() == null ){
			mVo.setSns_id("null");
			mVo.setSns_type(-1);
		}
		re = session.insert("KJS.join", mVo);
		session.close();
		System.out.println("DogFootDao-join-re:"+re);
		return re; 
	}
	
	/////////////////////////////////이벤트 페이지 처리 로직 시작 /////////////////////
	
	//현재 페이지번호를 저장할 변수
	public static int eventPageNUM = 1;
	
	//한화면에 보여줄 페이지의 수 ex)pageGroup 수가 5이면 1 2 3 4 5 [다음] 이렇게 표시되게 하기위한 지표
	public static int eventPageGroup = 3;
	
	//한페이지에 보여줄 게시물의 수
	public static int eventPageSIZE = 5;
	
	//전체레코드 수를 저장할 변수
	public static int eventTotalRecord;
	
	//전체페이지 수를 저장할 변수
	public static int eventTotalPage;
	
	public int eventTotalRecord(String eventSearchField, String eventKeyword){
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("eventSearchField", eventSearchField);
		map.put("eventKeyword", eventKeyword);
		
		int re = 0;
		SqlSession session = factory.openSession();
		re = session.selectOne("KJS.eventTotalRecord", map);
		System.out.println("DogFootDao--eventTotalRecord-eventTotalRecord:"+re);
		session.close();
		return re;
	}
	
	public String eventPageStr(){
		String str = "";
		
		int eventStartPage = (eventPageNUM-1)/eventPageGroup * eventPageGroup + 1;
		int eventEndPage = eventStartPage + eventPageGroup -1;
		
		System.out.println("DogFootDao-getPageStr-eventStartPage:" +eventStartPage);
		System.out.println("DogFootDao-getPageStr-eventEndPage:" +eventEndPage);
		
		if(eventEndPage > eventTotalPage){
			eventEndPage = eventTotalPage;
		}
		
		//아직도 보여줘야할 totalPage가 [이전]있다는 의미 => 현재화면에 보이는 startPage가 1보다 큰경우
		if(eventStartPage > 1){
			str += "<a class='btn btn-default' href='listEventBoard.do?eventPageNUM="+(eventStartPage-1)+"'>" +"[이전]" + "</a>&nbsp;&nbsp;";
		}
		
		for(int i = eventStartPage; i <= eventEndPage; i ++){
			str += "<a class='btn btn-default' href='listEventBoard.do?eventPageNUM="+i+"'>" + i + "</a>&nbsp;&nbsp;";
		}
		
		//아직도 보여줘야할 totalPage가 [다음]있다는 의미 => 현재화면에 보이는 endPage < totalPage 일 경우
		if(eventEndPage < eventTotalPage){
			str += "<a class='btn btn-default' href='listEventBoard.do?eventPageNUM="+(eventEndPage+1)+"'>" +"[다음]" + "</a>&nbsp;&nbsp;";
		}
		
		return str;
	}
	
	
		
	public List<BoardVo> listEventBoard(int eventPageNUM, String eventSearchField, String eventKeyword, String eventOrderField){
		
		List<BoardVo> eBList = null;
		
		/////////////////////////////////이벤트 페이지 한화면에 보이는 처리 로직 시작 /////////////////////
		
		DogFootDao.eventPageNUM = eventPageNUM; // 현재페이지 전역변수를 매개변수로 들어온것에 입히는 것
		
		//현재 페이지 번호에 따른 시작레코드
		int eventStart = (eventPageNUM-1) * eventPageSIZE + 1;
		
		//현재 페이지 번호에 따른 마지막레코드
		int eventEnd = eventStart + eventPageSIZE -1;
		
		System.out.println("DogFootDao-listEventBoard-eventPageNUM:" +eventPageNUM);
		System.out.println("DogFootDao-listEventBoard-eventStart:" +eventStart);
		System.out.println("DogFootDao-listEventBoard-eventEnd:" +eventEnd);
		System.out.println("DogFootDao-listEventBoard-eventTotalRecord(Controller에서 이미 갱신함!):" +eventTotalRecord);
		System.out.println("DogFootDao-listEventBoard-eventTotalPage(계산전): "+eventTotalPage);
		
		//  하면서 수정한것!! 정말 중요!! (아래의 totalRecord와 totalPage가 있어야 제대로 출력이됨!)
		//  eventTotalRecord = eventTotalRecord(eventSearchField, eventKeyword);
		if(eventTotalRecord%eventPageSIZE !=0){
			eventTotalPage = (eventTotalRecord/eventPageSIZE)+1;
		}else{
			eventTotalPage = eventTotalRecord/eventPageSIZE;
		}		
		//  전역변수이기때문에 여기서 eventTotalPage를 정의해주면 자동으로 eventPageStr()안에서도 계산의 기준이됨!
		
		if(eventEnd > eventTotalRecord){
			eventEnd = eventTotalRecord;
		}
		System.out.println("DogFootDao-listEventBoard-eventEnd(조건eventEnd > eventTotalRecord일경우):" +eventEnd);
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("eventStart", eventStart);
		map.put("eventEnd", eventEnd);
		map.put("eventSearchField", eventSearchField);
		map.put("eventKeyword", eventKeyword);
		map.put("eventOrderField", eventOrderField);
		
		SqlSession session = factory.openSession();
		eBList = session.selectList("KJS.selectEventBoard", map);
		System.out.println("DogFootDao-listEventBoard-eBList.size():" +eBList.size());
		session.close();
		/////////////////////////////////이벤트 페이지 한화면에 보이는 처리 로직 끝 /////////////////////
		
		return eBList;
	}
	/////////////////////////////////이벤트 페이지 처리 로직 끝 /////////////////////
	
	
	public int insertEventBoardVo(EventBoardVo ebVo){
		
		int re = -1;
		SqlSession session = factory.openSession(true);
		re = session.insert("KJS.insertEventBoard", ebVo);
		session.close();
		return re;
	}
	
	public int nextBoardNo(){
		int nextBoardNo = -1;
		SqlSession session = factory.openSession();
		nextBoardNo = session.selectOne("KJS.nextBoardNo");
		session.close();
		return nextBoardNo;
	}
	
	public EventBoardVo detailEventBoard(int b_no){
		SqlSession session = factory.openSession();
		EventBoardVo ebVo = session.selectOne("KJS.detailEventBoard", b_no);
		
		
		session.close();
		return ebVo;
	}
	
	//  조회수 올리기
	public int updateHitEventBoard(int b_no){
		int re = -1;
		SqlSession session = factory.openSession(true);
		session.update("KJS.updateHitEventBoard", b_no);  
		session.close();
		return re;
	}
	
	public int updateEventBoard(EventBoardVo ebVo){
		int re = -1;
		SqlSession session = factory.openSession(true);
		session.update("KJS.updateEventBoard",ebVo);
		session.close();
		return re;
	}
	
	public int deleteEventBoard(int b_no){
		SqlSession session = factory.openSession(true);
		int re = session.update("KJS.deleteEventBoard", b_no);
		session.close();
		return re;
	}
	
	public MemberVo selectMemberVo(int m_no){
		SqlSession session = factory.openSession();
		MemberVo mVo = session.selectOne("KJS.selectMemberVo", m_no);
		session.close();
		return mVo;
	}
	
	
	/////////////////////////////////마이게시판 페이지 처리 로직 시작 /////////////////////
	
	//현재 페이지번호를 저장할 변수
	public static int myBoardPageNUM = 1;
	
	//한화면에 보여줄 페이지의 수 ex)pageGroup 수가 5이면 1 2 3 4 5 [다음] 이렇게 표시되게 하기위한 지표
	public static int myBoardPageGroup = 3;
	
	//한페이지에 보여줄 게시물의 수
	public static int myBoardPageSIZE = 5;
	
	//전체레코드 수를 저장할 변수
	public static int myBoardTotalRecord;
	
	//전체페이지 수를 저장할 변수
	public static int myBoardTotalPage;
	
	public int myBoardTotalRecord(String myBoardSearchField, String myBoardKeyword, int m_no, int b_type){
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("myBoardSearchField", myBoardSearchField);
		map.put("myBoardKeyword", myBoardKeyword);
		map.put("m_no", m_no+"");
		map.put("b_type", b_type+"");
		
		int re = 0;
		SqlSession session = factory.openSession();
		re = session.selectOne("KJS.myBoardTotalRecord", map);
		System.out.println("DogFootDao-myBoardTotalRecord-myBoardTotalRecord:"+re);
		session.close();
		return re;
	}
	
	public String myBoardPageStr(){
		String str = "";
		
		int myBoardStartPage = (myBoardPageNUM-1)/myBoardPageGroup * myBoardPageGroup + 1;
		int myBoardEndPage = myBoardStartPage + myBoardPageGroup -1;
		
		System.out.println("DogFootDao-myBoardPageStr-myBoardStartPage:" +myBoardStartPage);
		System.out.println("DogFootDao-myBoardPageStr-myBoardEndPage:" +myBoardEndPage);
		
		if(myBoardEndPage > myBoardTotalPage){
			myBoardEndPage = myBoardTotalPage;
		}
		
		//아직도 보여줘야할 totalPage가 [이전]있다는 의미 => 현재화면에 보이는 startPage가 1보다 큰경우
		if(myBoardStartPage > 1){
			str += "<a class='btn btn-default' href='listMyBoard.do?myBoardPageNUM="+(myBoardStartPage-1)+"'>" +"[이전]" + "</a>&nbsp;&nbsp;";
		}
		
		for(int i = myBoardStartPage; i <= myBoardEndPage; i ++){
			str += "<a class='btn btn-default' href='listMyBoard.do?myBoardPageNUM="+i+"'>" + i + "</a>&nbsp;&nbsp;";
		}
		
		//아직도 보여줘야할 totalPage가 [다음]있다는 의미 => 현재화면에 보이는 endPage < totalPage 일 경우
		if(myBoardEndPage < myBoardTotalPage){
			str += "<a class='btn btn-default' href='listMyBoard.do?myBoardPageNUM="+(myBoardEndPage+1)+"'>" +"[다음]" + "</a>&nbsp;&nbsp;";
		}
		
		return str;
	}
	
	public List<BoardVo> listMyBoard(int myBoardPageNUM, String myBoardSearchField, String myBoardKeyword, String myBoardOrderField, int m_no, int b_type){
		
		List<BoardVo> mbList = null;
		
		/////////////////////////////////마이게시판 페이지 한화면에 보이는 처리 로직 시작 /////////////////////
		
		DogFootDao.myBoardPageNUM = myBoardPageNUM; // 현재페이지 전역변수를 매개변수로 들어온것에 입히는 것
		
		//현재 페이지 번호에 따른 시작레코드
		int myBoardStart = (myBoardPageNUM-1) * myBoardPageSIZE + 1;
		
		//현재 페이지 번호에 따른 마지막레코드
		int myBoardEnd = myBoardStart + myBoardPageSIZE -1;
		
		System.out.println("DogFootDao-listMyBoard-myBoardPageNUM:" +myBoardPageNUM);
		System.out.println("DogFootDao-listMyBoard-myBoardStart:" +myBoardStart);
		System.out.println("DogFootDao-listMyBoard-myBoardEnd:" +myBoardEnd);
		System.out.println("DogFootDao-listMyBoard-myBoardTotalRecord(Controller에서 이미 갱신함!):" +myBoardTotalRecord);
		System.out.println("DogFootDao-listMyBoard-myBoardTotalPage(계산전): "+myBoardTotalPage);
		
		//  하면서 수정한것!! 정말 중요!! (아래의 totalRecord와 totalPage가 있어야 제대로 출력이됨!)
		//  myBoardTotalRecord = myBoardTotalRecord(myBoardSearchField, myBoardKeyword);
		if(myBoardTotalRecord%myBoardPageSIZE !=0){
			myBoardTotalPage = (myBoardTotalRecord/myBoardPageSIZE)+1;
		}else{
			myBoardTotalPage = myBoardTotalRecord/myBoardPageSIZE;
		}		
		//  전역변수이기때문에 여기서 myBoardTotalPage를 정의해주면 자동으로 myBoardPageStr()안에서도 계산의 기준이됨!
		
		if(myBoardEnd > myBoardTotalRecord){
			myBoardEnd = myBoardTotalRecord;
		}
		System.out.println("DogFootDao-listMyBoard-myBoardEnd(조건myBoardEnd > myBoardTotalRecord일경우):" +myBoardEnd);
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("myBoardStart", myBoardStart);
		map.put("myBoardEnd", myBoardEnd);
		map.put("myBoardSearchField", myBoardSearchField);
		map.put("myBoardKeyword", myBoardKeyword);
		map.put("myBoardOrderField", myBoardOrderField);
		map.put("m_no", m_no);  //  회원번호 있어야함!
		map.put("b_type", b_type);
		
		SqlSession session = factory.openSession();
		mbList = session.selectList("KJS.selectMyBoard", map);
		System.out.println("DogFootDao-listMyBoard-mbList.size():" +mbList.size());
		session.close();
		/////////////////////////////////마이게시판 페이지 한화면에 보이는 처리 로직 끝 /////////////////////
		
		return mbList;
	}
	/////////////////////////////////마이게시판 페이지 처리 로직 끝 /////////////////////
	
	
	
	/////////////////////////////////주문내역 페이지 처리 로직 시작 /////////////////////
				
	//현재 페이지번호를 저장할 변수
	public static int myPayPageNUM = 1;
	
	//한화면에 보여줄 페이지의 수 ex)pageGroup 수가 5이면 1 2 3 4 5 [다음] 이렇게 표시되게 하기위한 지표
	public static int myPayPageGroup = 3;
	
	//한페이지에 보여줄 게시물의 수
	public static int myPayPageSIZE = 5;
	
	//전체레코드 수를 저장할 변수
	public static int myPayTotalRecord;
	
	//전체페이지 수를 저장할 변수
	public static int myPayTotalPage;
	
	public int myPayTotalRecord(String myPaySearchField, String myPayKeyword, int m_no){
	
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("myPaySearchField", myPaySearchField);
		map.put("myPayKeyword", myPayKeyword);
		map.put("m_no", m_no+"");
		
		int re = 0;
		SqlSession session = factory.openSession();
		re = session.selectOne("KJS.myPayTotalRecord", map);
		System.out.println("DogFootDao-myPayTotalRecord-myPayTotalRecord:"+re);
		session.close();
		return re;
	}
		
	public String myPayPageStr(){
		String str = "";
		
		int myPayStartPage = (myPayPageNUM-1)/myPayPageGroup * myPayPageGroup + 1;
		int myPayEndPage = myPayStartPage + myPayPageGroup -1;
		
		System.out.println("DogFootDao-myPayPageStr-myPayStartPage:" +myPayStartPage);
		System.out.println("DogFootDao-myPayPageStr-myPayEndPage:" +myPayEndPage);
		System.out.println("DogFootDao-myPayPageStr-myPayTotalPage:" +myPayTotalPage);
		
		if(myPayEndPage > myPayTotalPage){
			myPayEndPage = myPayTotalPage;
		}
		
		//아직도 보여줘야할 totalPage가 [이전]있다는 의미 => 현재화면에 보이는 startPage가 1보다 큰경우
		if(myPayStartPage > 1){
			str += "<a class='btn btn-default' href='myPayList.do?myPayPageNUM="+(myPayStartPage-1)+"'>" +"[이전]" + "</a>&nbsp;&nbsp;";
		}
		
		for(int i = myPayStartPage; i <= myPayEndPage; i ++){
			str += "<a class='btn btn-default' href='myPayList.do?myPayPageNUM="+i+"'>" + i + "</a>&nbsp;&nbsp;";
		}
		
		//아직도 보여줘야할 totalPage가 [다음]있다는 의미 => 현재화면에 보이는 endPage < totalPage 일 경우
		if(myPayEndPage < myPayTotalPage){
			str += "<a class='btn btn-default' href='myPayList.do?myPayPageNUM="+(myPayEndPage+1)+"'>" +"[다음]" + "</a>&nbsp;&nbsp;";
		}
		
		return str;
	}
	
	
	public List<MyPayVo> listMyPay(int myPayPageNUM, String myPaySearchField, String myPayKeyword, String myPayOrderField, int m_no){
	
	List<MyPayVo> oVoList = null;
	
	/////////////////////////////////결재내역 페이지 한화면에 보이는 처리 로직 시작 /////////////////////
	
	DogFootDao.myPayPageNUM = myPayPageNUM; // 현재페이지 전역변수를 매개변수로 들어온것에 입히는 것
	
	//현재 페이지 번호에 따른 시작레코드
	int myPayStart = (myPayPageNUM-1) * myPayPageSIZE + 1;
	
	//현재 페이지 번호에 따른 마지막레코드
	int myPayEnd = myPayStart + myPayPageSIZE -1;
	
	System.out.println("DogFootDao-listMyPay-myPayPageNUM:" +myPayPageNUM);
	System.out.println("DogFootDao-listMyPay-myPayStart:" +myPayStart);
	System.out.println("DogFootDao-listMyPay-myPayEnd:" +myPayEnd);
	System.out.println("DogFootDao-listMyPay-myPayTotalRecord(Controller에서 이미 갱신함!):" +myPayTotalRecord);
	System.out.println("DogFootDao-listMyPay-myPayTotalPage(계산전): "+myPayTotalPage);
	
	//  하면서 수정한것!! 정말 중요!! (아래의 totalRecord와 totalPage가 있어야 제대로 출력이됨!)
	//  myPayTotalRecord = myPayTotalRecord(myPaySearchField, myPayKeyword);
	if(myPayTotalRecord%myPayPageSIZE !=0){
		myPayTotalPage = (myPayTotalRecord/myPayPageSIZE)+1;
	}else{
		myPayTotalPage = myPayTotalRecord/myPayPageSIZE;
	}		
	//  전역변수이기때문에 여기서 myPayTotalPage를 정의해주면 자동으로 myPayPageStr()안에서도 계산의 기준이됨!
	
	if(myPayEnd > myPayTotalRecord){
		myPayEnd = myPayTotalRecord;
	}
	System.out.println("DogFootDao-listMyPay-myPayTotalPage(계산후): "+myPayTotalPage);
	System.out.println("DogFootDao-listMyPay-myPayEnd(조건myPayEnd > myPayTotalRecord일경우):" +myPayEnd);
	
	HashMap<String, Object> map = new HashMap<String, Object>();
	map.put("myPayStart", myPayStart);
	map.put("myPayEnd", myPayEnd);
	map.put("myPaySearchField", myPaySearchField);
	map.put("myPayKeyword", myPayKeyword);
	map.put("myPayOrderField", myPayOrderField);
	map.put("m_no", m_no+"");  //  회원번호 있어야함!
	
	SqlSession session = factory.openSession();
	oVoList = session.selectList("KJS.selectMyPay", map);
	System.out.println("DogFootDao-listMyPay-oVoList.size():" +oVoList.size());
	session.close();
	/////////////////////////////////결재내역 페이지 한화면에 보이는 처리 로직 끝 /////////////////////
	
	return oVoList;
	}
	/////////////////////////////////결재내역 페이지 처리 로직 끝 /////////////////////	
	
	public int nextOrderNo(){
		SqlSession session = factory.openSession();
		int nextOrderNo = session.selectOne("KJS.nextOrderNo");
		session.close();
		return nextOrderNo;
	}
	
	
	public int insertOrder(OrdersVo oVo){
		SqlSession session = factory.openSession(true);
		int re = session.insert("KJS.insertOrder", oVo);
		System.out.println("DogFootDao-insertOrder-주문장등록결과re:" +re);
		session.close();
		return re;
	}
	
	public int updateMyInfo(MemberVo mVo){
		SqlSession session = factory.openSession(true);
		int re = session.update("KJS.updateMyInfo", mVo);
		System.out.println("DogFootDao-updateMyInfo-개인정보등록결과re:" +re);
		session.close();
		return re;
	}
	
	public BoardVo selectBoardVoOne(int b_no){
		SqlSession session = factory.openSession();
		BoardVo bVo = session.selectOne("KJS.selectBoardVoOne",b_no);
		session.close();
		return bVo;
				
	}
	
	public int insertOrderDetail(OrderDetailVo oDVo){
		SqlSession session = factory.openSession(true);
		int re = -1;
		re = session.insert("KJS.insertOrderDetail", oDVo);
		session.close();
		return re;
	}
	
	public int nextOrderDetailNo(){
		SqlSession session = factory.openSession();
		int nextOrderDetailNo = session.selectOne("KJS.nextOrderDetailNo");
		session.close();
		return nextOrderDetailNo;
	}
	
	public int MaxOrderNo(){
		SqlSession session = factory.openSession();
		int MaxOrderNo = session.selectOne("KJS.MaxOrderNo");
		session.close();
		return MaxOrderNo;
	}

	
	//===================================김준성끝========================================
	
	//===================================정현규=========================================
			
			// 댓글목록
			public List<CommentsVo> listComment(int b_no)
			{
				SqlSession session = factory.openSession();
				List<CommentsVo> list = session.selectList("JHG.listComment", b_no);
				session.close();
				return list;
			}
	
			//댓글 달기
			public int insertComment(CommentsVo c)
			{
				int re = -1;
				SqlSession session = factory.openSession(true);
				re = session.insert("JHG.insertComment", c);
				session.close();
				return re;
			}
	
			// 상품상세에서 후기글 링크
			public List<DetailGoodsVo> link_review(int g_code )
			{
				SqlSession session = factory.openSession();
				List<DetailGoodsVo> list = session.selectList("JHG.link_review_goods",g_code);
				session.close();
				return list;
			}
			
			// 상품상세에서 코디글 링크
			public List<DetailGoodsVo> link_codi(String b_img )
			{
				SqlSession session = factory.openSession();
				List<DetailGoodsVo> list = session.selectList("JHG.link_codi_goods",b_img);
				session.close();
				return list;
			}
	
			// 판매현황 차트3 data
			public List<OrdersVo> manager_Sales_chart3()
			{
				SqlSession session = factory.openSession();
				List<OrdersVo> list = session.selectList("JHG.manager_Sales_chart3");
				session.close();
				return list;
			}
			
			// 판매현황 차트2 data
			public List<OrderDetailVo> manager_Sales_chart2()
			{
				SqlSession session = factory.openSession();
				List<OrderDetailVo> list = session.selectList("JHG.manager_Sales_chart2");
				session.close();
				return list;
			}
			
			// 판매현황 차트1 data
			public List<OrdersVo> manager_Sales_chart1()
			{
				SqlSession session = factory.openSession();
				List<OrdersVo> list = session.selectList("JHG.manager_Sales_chart1");
				session.close();
				return list;
			}
			
			//발주 확인 목록
			public List<DealVo> Ok_dealList()
			{
				SqlSession session = factory.openSession();
				List<DealVo> list = session.selectList("JHG.Ok_dealList");
				session.close();
				return list;
			}
			
			// 발주 삭제
			public int deleteDeal(DealVo d)
			{
				SqlSession session = factory.openSession(true);
				int re = session.delete("JHG.deleteDeal", d);
				session.close();
				return re;
			}
			
			// 발주확인시 해당상품 재고수량 증가
			public int updateGoodsAmount(DealVo d)
			{
				SqlSession session = factory.openSession(true);
				int re = session.update("JHG.updateGoodsAmount", d);
				session.close();
				return re;
			}
			
			// 발주 확인 
			public int confirmDeal(DealVo d)
			{
				SqlSession session = factory.openSession(true);
				int re = session.update("JHG.confirmDeal", d);
				session.close();
				return re;
			}
			
			//상품정보 삭제시 해당상품 상품그룹에서 삭제
			public int manager_Goods_goodsgroup_delete(String g_no)
			{
				int re = -1;
				SqlSession session = factory.openSession(true);
				re = session.delete("JHG.manager_Goods_goodsgroup_delete", g_no);
				session.close();
				return re;
			}
			
			// 상품정보 삭제시 상품그룹 확인 
			public int manager_Goods_goodsgroup_select(String g_no)
			{
				SqlSession session = factory.openSession();
				int re = session.selectOne("JHG.manager_Goods_goodsgroup_select", g_no);
				session.close();
				return re;
			}
			
			// 상품정보 삭제
			public int manager_Goods_delete(GoodsVo g)
			{
				SqlSession session = factory.openSession(true);
				int re = session.update("JHG.manager_Goods_delete", g);
				session.close();
				return re;
			}
			
			//상품정보 수정
			public int manager_Goods_update(GoodsVo g)
			{
				SqlSession session = factory.openSession(true);
				int re = session.update("JHG.manager_Goods_update", g);
				session.close();
				return re;
			}
			
			//발주 생성
			public int insertDeal(DealVo d)
			{
				SqlSession session = factory.openSession(true);
				int re = session.insert("JHG.insertDeal", d);
				session.close();
				return re;
			}
			
			//상품발주목록_확인번튼 누르기전 발주들만
			public List<DealVo> nonOk_dealList()
			{
				SqlSession session = factory.openSession();
				List<DealVo> list = session.selectList("JHG.nonOk_dealList");
				session.close();
				return list;
			}
			
			//상품군에 상품들 등록
			public int goodsGroup_insert(GoodsGroupVo ggv)
			{
				SqlSession session = factory.openSession(true);
				int re = session.insert("JHG.goodsGroup_insert", ggv);
				session.close();
				return re;
			}
			
			//상품상세 글 등록시 상품군 등록할 목록 뽑기
			public List<GoodsVo> goodsGroup_insert_list(int g_code)
			{
				SqlSession session = factory.openSession();
				List<GoodsVo> list = session.selectList("JHG.goodsGroup_insert_list", g_code);
				session.close();
				return list;
			}
			
			//상품 상세 글 등록
			public int goodsBoard_insert(BoardVo b)
			{
				SqlSession session = factory.openSession(true);
				int re = session.insert("JHG.goodsBoard_insert", b);
				session.close();
				return re;
			}
			
			//상품 상세 글 등록 form 의 상품상세 글 안쓰여진 g_code 목록 뽑기
			public List<GoodsVo> goodsBoard_insert_g_code_comboList()
			{
				SqlSession session = factory.openSession();
				List<GoodsVo> list = session.selectList("JHG.goodsBoard_insert_g_code_comboList");
				session.close();
				return list;
			}
			
			public int manager_Goods_insert(GoodsVo g)
			{
				SqlSession session = factory.openSession(true);
				int n = session.insert("JHG.manager_Goods_insert", g);
				session.close();
				return n;
			}
			
			public List<GoodsVo> manager_Goods_list()
			{
				SqlSession session = factory.openSession();
				List<GoodsVo> list = session.selectList("JHG.manager_Goods_list");
				session.close();
				return list;
			}
			
			public int manager_Members_update(MemberVo m)
			{
				SqlSession session = factory.openSession(true);
				int re = session.update("JHG.manager_Members_update", m);
				session.close();
				return re;
			}
			
			public List<BoardVo> manager_Members_Boardlist()
			{
				SqlSession session = factory.openSession();
				List<BoardVo> list = session.selectList("JHG.manager_Members_Boardlist");
				session.close();
				return list;
			}
			
			public List<MemberVo> manager_Members_list()
			{
				SqlSession session = factory.openSession();
				List<MemberVo> list = session.selectList("JHG.manager_Members_list");
				session.close();
				return list;
			}
			
			public List<OrdersVo> chart2_data()
			{
				SqlSession session = factory.openSession();
				List<OrdersVo> list = session.selectList("JHG.chart2");
				session.close();
				return list;
			}
			
			public List<GoodsVo> chart1_data()
			{
				SqlSession session = factory.openSession();
				List<GoodsVo> list = session.selectList("JHG.chart1");
				session.close();
				return list;
			}
			
			public List<GoodsVo> lowStockGoodsList()
			{
				SqlSession session = factory.openSession();
				List<GoodsVo> list = session.selectList("JHG.lowStockGoodsList");
				session.close();
				return list;
			}
			
			public int boardLikeCountUpdate(int b_no)
			{
				SqlSession session = factory.openSession(true);
				int n = session.update("JHG.boardLikeCountUpdate", b_no);
				return n;
			}
			
			public int insertBoardLike(BoardLikeVo blv)
			{
				SqlSession session = factory.openSession(true);
				int re = session.insert("JHG.insertBoardLike", blv);
				session.close();
				return re;
			}
			
			public List<GoodsVo> size_combo(GoodsVo g)
			{
				SqlSession session = factory.openSession();
				List<GoodsVo> size = session.selectList("JHG.goodsDetail_sizeCombo", g);
				session.close();
				return size;
			}
			
			public List<String> detailGoods_color(int g_code)
			{
				SqlSession session = factory.openSession();
				List<String> color = session.selectList("JHG.goodsDetail_color", g_code);
				session.close();
				return color;
			}
			
			public DetailGoodsVo detailGoods(int b_no)
			{
				SqlSession session = factory.openSession();
				DetailGoodsVo dgv = session.selectOne("JHG.goodsDetail", b_no);
				//System.out.println(dgv.getAmount());
				//System.out.println(dgv.getB_img());
				//System.out.println(dgv.getB_no());
				//System.out.println(dgv.getContent());
				//System.out.println(dgv.getG_code());
				//System.out.println(dgv.getG_name());
				//System.out.println(dgv.getPrice());
				//System.out.println(dgv.getTitle());
				session.close();
				return dgv;
			}
			
			public List<BoardVo> goodsBoardList_search(SearchVo sv)
			{
				SqlSession session = factory.openSession();
				List<BoardVo> list = session.selectList("JHG.goodsBoardList_search",sv);
				session.close();
				return list;
			}
			
			public List<BoardVo> goodsBoardList()
			{
				SqlSession session = factory.openSession();
				List<BoardVo> list = session.selectList("JHG.goodsBoardList");
				session.close();
				return list;
			}
			
			public List<String> categoryList()
			{
				SqlSession session = factory.openSession();
				List<String> list = session.selectList("JHG.categoryList");
				session.close();
				return list;
			}
			
			public List<String> brandList()
			{
				SqlSession session = factory.openSession();
				List<String> list = session.selectList("JHG.brandList");
				session.close();
				return list;
			}
			
			public List<String> colorList()
			{
				SqlSession session = factory.openSession();
				List<String> list = session.selectList("JHG.colorList");
				session.close();
				return list;
			}
			
			public List<String> g_sizeList()
			{
				SqlSession session = factory.openSession();
				List<String> list = session.selectList("JHG.g_sizeList");
				session.close();
				return list;
			}

			//===================================정현규 끝========================================
	
///////////////////////////////// 김진혁 ///////////////////////////////////////////////////
		public List<CodiVo> selectCodiBoard(){
			List<CodiVo> list = null;
			SqlSession session = factory.openSession();
			list = session.selectList("KWJ.selectCodiBoard");
			session.close();
			return list;
		}
		public BoardVo getCodiBoard(int b_no,boolean hitAdd){
			BoardVo b = null;
			SqlSession session = factory.openSession(true);
			if(hitAdd){
			session.update("KWJ.updateHit",b_no);
			}
			b = session.selectOne("KWJ.getCodiBoard",b_no);
			session.close();
			return b;
		}
		public String getCodiId(int m_no){
			String id="";
			SqlSession session = factory.openSession();
			id = session.selectOne("KWJ.getCodiId",m_no);
			session.close();
			return id;
		}
		public GoodsGroupVo getCodiGoods(int b_no,String g_no){
			GoodsGroupVo g = null;
			SqlSession session = factory.openSession();
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("b_no", b_no);
			map.put("g_no", g_no);
			g = session.selectOne("KWJ.getCodiGoods",map);
			session.close();
			return g;
		}
		public List<GoodsVo> listCodiGoods(String category){
			List<GoodsVo> list = null;
			SqlSession session = factory.openSession();
			list = session.selectList("KWJ.listCodiGoods",category);
			session.close();
			return list;
		}
		public int insertCodiBoard(CodiVo c){
			int re = -1;
			SqlSession session = factory.openSession();
			re = session.insert("KWJ.insertCodiBoard",c);
			session.commit();
			session.close();
			return re;
		}
		public int insertCodiGoods(GoodsGroupVo gg){
			int re = -1;
			SqlSession session = factory.openSession();
			re = session.insert("KWJ.insertCodiGoods",gg);
			session.commit();
			session.close();
			return re;
		}
		public int getNextNo(){
			SqlSession session = factory.openSession();
			int no = session.selectOne("KWJ.getNextNo");
			session.close();
			return no;
		}
		public String getG_imgName(String g_no){
			String g_img="";
			SqlSession session = factory.openSession();
			g_img = session.selectOne("KWJ.getG_imgName",g_no);
			return g_img;
		}
		public int updateCodiBoard(CodiVo c){
			int re = -1;
			SqlSession session = factory.openSession();
			re = session.delete("KWJ.updateCodiBoard",c);
			session.commit();
			session.close();
			return re;
		}
		public int updateGoodsGroup(HashMap<String, Object> map){
			int re = -1;
			SqlSession session = factory.openSession();
			re = session.delete("KWJ.updateCodiGoods",map);
			session.commit();
			session.close();
			return re;
		}
		//코디 수정할때 이전 상품 삭제
		public int deleteGoodsGroup(int b_no,String g_no){
			int re = -1;
			SqlSession session = factory.openSession();
			HashMap<String,Object> map = new HashMap<String, Object>();
			map.put("b_no", b_no);
			map.put("g_no", g_no);
			re = session.delete("KWJ.deleteGoodsGroup",map);
			session.commit();
			session.close();
			return re;
		}
		public int deleteCodiBoard(int b_no){
			int re = -1;
			SqlSession session = factory.openSession();
			re = session.delete("KWJ.deleteCodiBoard",b_no);
			session.commit();
			session.close();
			return re;
		}
		//코디 삭제 할때 사용
		public int deleteCodiGoods(int b_no){
			int re = -1;
			SqlSession session = factory.openSession();
			re = session.delete("KWJ.deleteCodiGoods",b_no);
			session.commit();
			session.close();
			return re;
		}
		public String detailGoodsSender(int g_code){
			String b_no = null;
			SqlSession session = factory.openSession();
			b_no = session.selectOne("KWJ.detailGoodsSender",g_code);
			session.close();
			return b_no;
		}
	///////////////////////////////////////여기부터 리뷰/////////////////////
	
		public List<ReviewVo> listReviewBoard(){
			List<ReviewVo> list = null;
			SqlSession session = factory.openSession();
			list = session.selectList("KWJ.listReviewBoard");
			session.close();
			return list;
		}
		public List<GoodsVo> buyList(int m_no){
			List<GoodsVo> list = null;
			SqlSession session = factory.openSession();
			list = session.selectList("KWJ.buyList",m_no);
			session.close();
			return list;
		}
		public int insertReviewBoard(BoardVo b){
			int re = -1;
			SqlSession session = factory.openSession();
			re = session.insert("KWJ.insertReviewBoard",b);
			session.commit();
			session.close();
			return re;
		}
		public ReviewVo detailReviewBoard(int b_no){
			ReviewVo r = new ReviewVo();
			SqlSession session = factory.openSession();
			r = session.selectOne("KWJ.detailReviewBoard",b_no);
			session.close();
			return r;
		}
		public int deleteReviewBoard(int b_no){
			int r = -1;
			SqlSession session = factory.openSession();
			r = session.delete("KWJ.deleteReviewBoard",b_no);
			session.commit();
			session.close();
			return r;
		}
		////////////////////////////////////////////////////////////
		public List<BoardVo> frontList(){
			List<BoardVo> list = null;
			SqlSession session = factory.openSession();
			list = session.selectList("KWJ.frontList");
			session.close();
			return list;
		}
}
