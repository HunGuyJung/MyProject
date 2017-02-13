package com.dogFoot.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.dogFoot.dao.DogFootDao;
import com.dogFoot.vo.BoardLikeVo;
import com.dogFoot.vo.BoardVo;
import com.dogFoot.vo.CommentsVo;
import com.dogFoot.vo.DealVo;
import com.dogFoot.vo.DetailGoodsVo;
import com.dogFoot.vo.GoodsGroupVo;
import com.dogFoot.vo.GoodsVo;
import com.dogFoot.vo.MemberVo;
import com.dogFoot.vo.OrderDetailVo;
import com.dogFoot.vo.OrdersVo;
import com.dogFoot.vo.SearchVo;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class JHGDogFootController {
	
	@Autowired
	private DogFootDao dao;

	public void setDao(DogFootDao dao) {
		this.dao = dao;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	
	//상품상세 페이지 색상선택시 해당색상의 사이즈 콤보생성
	@RequestMapping(value="/size_combo.do", produces="text/plain;charset=utf-8")
	@ResponseBody
	public String size_combo(GoodsVo g)
	{
		//System.out.println("사이즈 콤보 ajax 통신!");
		String size = "";
		ObjectMapper mapper = new ObjectMapper();
		try{
			size = mapper.writeValueAsString(dao.size_combo(g));
		}catch (Exception e) {
			System.out.println(e);
		}
		return size;
	}
	
	// 상품상세 댓글달기
	@RequestMapping("/comment.json")
	@ResponseBody
	public String insertComment(CommentsVo c)
	{
		//System.out.println("넘어온 댓글 내용 : "+c.getC_content());
		String str="";
		int re = dao.insertComment(c);
		ObjectMapper mapper = new ObjectMapper();
		try{
			str = mapper.writeValueAsString(re+"");
		}catch (Exception e) {
			System.out.println(e);
		}
		return str;
	}
	
	//상품상세 댓글 리스트 불러오기
	@RequestMapping("/commentList.json")
	@ResponseBody
	public String listComment(int b_no){
		List<CommentsVo> list = dao.listComment(b_no);
		//System.out.println("가져온 글번호의 댓글 : "+list.get(0).getC_content());
		String str = "";
		ObjectMapper mapper = new ObjectMapper();
		JSONArray comment_list = new JSONArray();
		
		for( CommentsVo c : list)
		{
			JSONObject comment_info = new JSONObject();
			comment_info.put("id", c.getId());
			comment_info.put("b_no", c.getB_no());
			comment_info.put("c_content", c.getC_content());
			comment_info.put("c_ref", c.getC_ref());
			comment_info.put("c_level", c.getC_level());
			comment_info.put("c_step", c.getC_step());
			comment_info.put("create_time", c.getCreate_time());
			comment_list.add(comment_info);
		}
		
		try{
			str = mapper.writeValueAsString(comment_list.toString());
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		return str;
	}
	
	//상품상세 페이지 보여주기
	@RequestMapping("/detailGoodsBoard.do")
	public ModelAndView detailGoods(int b_no)
	{
		ModelAndView mv = new ModelAndView();
		//System.out.println("상품상세 글번호 : "+b_no);
		DetailGoodsVo dgv = dao.detailGoods(b_no);
		mv.addObject("dgv", dgv);
		//System.out.println("상품상세 상품코드 : "+dgb.getG_code());
		mv.addObject("color", dao.detailGoods_color(dgv.getG_code()));
		//System.out.println("상품상세 색상 : "+dao.detailGoods_color(dgb.getG_code()));
		mv.addObject("link_codi",dao.link_codi(dgv.getB_img()));
		mv.addObject("link_review",dao.link_review(dgv.getG_code()));	
		mv.addObject("page", "goods/detailGoodsBoard.jsp");
		mv.setViewName("template");
		
		return mv;
	}
	
	//상품글 목록 보여주기 - 검색 후
	@RequestMapping(value="/listGoodsBoard.do", method=RequestMethod.POST)
	public ModelAndView listGoodsBoard(HttpServletRequest request, SearchVo sv)
	{
		ModelAndView mv = new ModelAndView();
		String path = request.getRealPath("resources/upload");
		System.out.println("파일저장 실경로 : "+path);
		//System.out.println("검색에서 넘어온 값 : "+sv.getKeyword());
		//System.out.println("검색에서 넘어온 값(카테고리) : "+sv.getCategory());
		mv.addObject("list", dao.goodsBoardList_search(sv));
		mv.addObject("category", dao.categoryList());
		mv.addObject("brand", dao.brandList());
		mv.addObject("color", dao.colorList());
		mv.addObject("g_size", dao.g_sizeList());
		mv.addObject("page", "goods/listGoodsBoard.jsp");	
		mv.setViewName("template");
		
		return mv;
	}
	
	//상품글 목록 보여주기 - 검색 전or 검색 x
	@RequestMapping(value="/listGoodsBoard.do", method=RequestMethod.GET)
	public ModelAndView listGoodsBoard(HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView();
		String path = request.getRealPath("resources/upload");
		System.out.println("파일저장 실경로 : "+path);
		mv.addObject("list", dao.goodsBoardList());
		mv.addObject("category", dao.categoryList());
		mv.addObject("brand", dao.brandList());
		mv.addObject("color", dao.colorList());
		mv.addObject("g_size", dao.g_sizeList());
		mv.addObject("page", "goods/listGoodsBoard.jsp");	
		mv.setViewName("template");
		
		return mv;
	}
	
	//상품 구매 버튼 클릭시
	@RequestMapping("/insertCart.do")
	public ModelAndView insertCart(HttpSession session , DetailGoodsVo dgv)
	{
		ModelAndView mv = new ModelAndView();
		
		//System.out.println("구매수량 : "+dgv.getAmount());
		//System.out.println("글 번호 : "+dgv.getB_no());
		//System.out.println("상품코드 : "+dgv.getG_code());
		//System.out.println("상품가격 : "+dgv.getPrice());
		//System.out.println("상품 색상 : "+dgv.getColor());
		//System.out.println("상품 사이즈 : "+dgv.getG_size());
		//System.out.println("구매 합계 : "+dgv.getTotal());
		
		List<DetailGoodsVo> cartList = new ArrayList<DetailGoodsVo>();
		if(session.getAttribute("cartList") != null)
		{
			cartList = (List<DetailGoodsVo>)session.getAttribute("cartList");
		}
		cartList.add(dgv);
		session.setAttribute("cartList", cartList);
		List<DetailGoodsVo> cartList1 = (List<DetailGoodsVo>)session.getAttribute("cartList");
		System.out.println("JHGDogFootController-insertCart-cartList1.size()(필터보내기전 담기는지 확인):"+cartList1.size());
		mv.setViewName("redirect:/myBasket.do");
		
		return mv;
	}
	
	//좋아요 생성 
	@RequestMapping(value="/boardLike.do", produces="text/plain;charset=utf-8")
	@ResponseBody
	public String boardLike(BoardLikeVo blv)
	{
		//System.out.println("좋아요 넘어온 회원번호 : "+blv.getM_no());
		//System.out.println("좋아요 넘어온 글번호 : "+blv.getB_no());
		String str = "";
		String result = "";
		int n = dao.insertBoardLike(blv);
		if(n==1)
		{
			int i = dao.boardLikeCountUpdate(blv.getB_no());
			//System.out.println("좋아요 후 like_count 추가 : "+n);
			if(dao.getCodiBoard(blv.getB_no(), false) != null)
			{
				result = dao.getCodiBoard(blv.getB_no(), false).getLike_count()+"";
			}
		}
		ObjectMapper mapper = new ObjectMapper();
		try{
			str = mapper.writeValueAsString(result);
		}catch (Exception e) {
			System.out.println(e);
		}
		
		return str;
	}
	
	//================================= 매니저 페이지 Controller =================================
	
	// 발주취소시 삭제
	@RequestMapping("/manager_Order_orderDelete.do")
	public ModelAndView deleteDeal(DealVo d)
	{
		ModelAndView mv = new ModelAndView();
		
		int re = dao.deleteDeal(d);
		String str = "";
		if( re == 1 )
		{
			str = "발주 취소 성공!";
		}else{
			str = "발주 취소 실패 ㅠㅠ";
		}
		
		mv.setViewName("redirect://manager_Order.do?str="+str);
		return mv;
	}
	
	//발주 확인시 발주컨펌 & 상품 재고수량 증가
	@RequestMapping("/manager_Order_orderUpdate.do")
	public ModelAndView manager_Order_orderUpdate(DealVo d)
	{
		ModelAndView mv = new ModelAndView();
		
		int d_qty = d.getD_qty();
		int n = dao.confirmDeal(d);
		String str = "";
		if( n==1 )
		{
			int re = dao.updateGoodsAmount(d);
			if( re == 1 )
			{
				str = "발주 확인 성공!!";
			}
			else{
				str = "발주 확인 실패ㅜㅜ";
			}
		}
		
		mv.setViewName("redirect://manager_Order.do?str="+str);
		return mv;
	}
	
	// 새로운 발주 등록
	@RequestMapping("/insertDeal.do")
	public ModelAndView insertDeal(HttpSession session ,DealVo d)
	{
		ModelAndView mv = new ModelAndView();
		
		int qty = d.getD_qty();
		int price = d.getD_total();
		d.setD_total(qty*price);
		//System.out.println("발주수량 : "+d.getD_qty());
		//System.out.println("발주금액 : "+d.getD_total());
		int n = dao.insertDeal(d);
		String str = "";
		if(n==1)
		{
			str = "발주 성공!!";
		}else{
			str = "발주 실패ㅠㅠ";
		}
		
		mv.setViewName("redirect://manager_Order.do?str="+str);
		return mv;
	}
	
	//상품발주페이지 보여주기
	@RequestMapping("/manager_Order.do")
	public ModelAndView manager_Order(@RequestParam(value="str", defaultValue = "") String str)
	{
		ModelAndView mv = new ModelAndView();
		
		if(!str.equals(""))
		{
			mv.addObject("manager_Order_msg", str);
		}
		mv.addObject("nonOk_dealList", dao.nonOk_dealList());
		mv.addObject("lowStockGoodsList", dao.lowStockGoodsList());
		mv.addObject("Ok_dealList", dao.Ok_dealList());
		mv.addObject("page", "manager_Order.jsp");
		mv.setViewName("manager/manager_template");
		
		return mv;
	}
	
	//판매현황페이지 보여주기
	@RequestMapping("/manager_Sales.do")
	public ModelAndView manager_Sales()
	{
		ModelAndView mv = new ModelAndView();
		
		mv.addObject("sales_list_days", dao.chart2_data());
		mv.addObject("sales_list_month", dao.manager_Sales_chart1());
		mv.addObject("page", "manager_Sales.jsp");
		mv.setViewName("manager/manager_template");
		
		return mv;
	}
	
	//상품상세 글 쓰기
	@RequestMapping("/goodsBoard_insert.do")
	public ModelAndView GoodsBoard_insert(BoardVo b)
	{
		ModelAndView mv = new ModelAndView();
		
		//System.out.println("작성자 : "+b.getM_no());
		//System.out.println("제목 : "+b.getTitle());
		//System.out.println("상품코드 : "+b.getG_code());
		//System.out.println("내용 : "+b.getContent());
		//System.out.println("사진 : "+b.getB_img());
		int b_no = dao.getNextNo();
		b.setB_no(b_no);
		int re = 0;
		String goodsBoard_insert_msg = "";
		List<GoodsVo> list = dao.goodsGroup_insert_list(b.getG_code());
		if(dao.goodsBoard_insert(b) == 1)
		{
			
			for(GoodsVo g : list)
			{
				GoodsGroupVo ggv = new GoodsGroupVo(b_no,g.getG_no(),g.getG_img());
				re = re + dao.goodsGroup_insert(ggv);
			}
			//System.out.println("상품군 등록 해야 할 리스트의 크기 : "+list.size());
			//System.out.println("상품군 등록 성공 수 : "+re);
			if( re == list.size())
			{
				goodsBoard_insert_msg = "success!!";
				
			}
		}else{
			goodsBoard_insert_msg = "false_TT";
		}
		mv.setViewName("redirect://manager_Goods.do?goodsBoard_insert_msg="+goodsBoard_insert_msg);
		
		return mv;
	}
	
	@RequestMapping("/manager_Goods_delete.do")
	public ModelAndView manager_Goods_delete(GoodsVo g)
	{
		ModelAndView mv = new ModelAndView();
		
		int re = dao.manager_Goods_delete(g);
		String str = "";
		if(re==1)
		{
			int mggs = dao.manager_Goods_goodsgroup_select(g.getG_no());
			if(mggs>=1)
			{
				int mggd = dao.manager_Goods_goodsgroup_delete(g.getG_no());
				if(mggd != -1)
				{
					str = "상품삭제 성공!!";
				}
				
			}
		}else{
			str = "상품삭제 실패 ㅠㅠ";
		}
		mv.setViewName("redirect://manager_Goods.do?str="+str);
		
		return mv;
	}
	
	//상품정보 수정
	@RequestMapping("/manager_Goods_update.do")
	public ModelAndView manager_Goods_update(HttpServletRequest request, GoodsVo g)
	{
		//System.out.println("상품정보 수정 시작!!!");
		ModelAndView mv = new ModelAndView();
		//System.out.println("상품입력 정보 : "+g);
		MultipartFile uploadFile = g.getUploadFile();
		String path = request.getRealPath("resources/upload");
		String old_fname = "";
		if(g.getG_img() != null && !g.getG_img().equals("") && !g.getG_img().equals("null") )
		{
			old_fname = g.getG_img();
		}
		String fname = "";
		if(uploadFile.getSize() != 0)
		{
			fname = uploadFile.getOriginalFilename();
			try{
				byte [] data = uploadFile.getBytes();
				FileOutputStream fos = new FileOutputStream(path+"/"+fname);
				fos.write(data);
				fos.close();
			}catch (Exception e) {
				// TODO: handle exception
				System.out.println("상품정보 사진파일 생성 오류 : "+e);
			}
			g.setG_img(fname);
		}
		int re = dao.manager_Goods_update(g);
		String str = "";
		if( re==1)
		{
			str = "상품정보 수정 성공!";
			if( !fname.equals("") && !fname.equals(old_fname) )
			{
				File file = new File(path+"/"+old_fname);
				file.delete();
			}
		}else{
			str = "상품정보 수정 실패ㅜㅜ";
		}
		//System.out.println("상품정보 수정 : "+str);
		
		mv.setViewName("redirect://manager_Goods.do?str="+str);
		
		return mv;
	}
	
	// 상품정보 등록
	@RequestMapping("/manager_Goods_insert.do")
	public ModelAndView manager_Goods_insert(HttpServletRequest request, GoodsVo g)
	{
		ModelAndView mv = new ModelAndView();
		//System.out.println("상품입력 정보 : "+g);
		MultipartFile uploadFile = g.getUploadFile();
		String path = request.getRealPath("resources/upload");
		String fname = "";
		if(uploadFile.getSize() != 0)
		{
			fname = uploadFile.getOriginalFilename();
			g.setG_img(fname);
		}
		int re = dao.manager_Goods_insert(g);
		String str = "";
		if(re==1)
		{
			try{
				byte [] data = uploadFile.getBytes();
				FileOutputStream fos = new FileOutputStream(path+"/"+fname);
				fos.write(data);
				fos.close();
			}catch (Exception e) {
				// TODO: handle exception
				System.out.println("상품등록 사진파일 생성 오류 : "+e);
			}
			str = "상품정보 등록 성공!";
		}else{
			str = "상품정보 등록 실패ㅠㅠ";
		}
		System.out.println("상품등록 결과 : "+str);
		
		mv.setViewName("redirect://manager_Goods.do?str="+str);
		
		return mv;
	}
	
	//상품관리 페이지
	@RequestMapping("/manager_Goods.do")
	public ModelAndView manager_Goods(String goodsBoard_insert_msg,
									  @RequestParam(value="str", defaultValue = "") String str)
	{
		ModelAndView mv = new ModelAndView();
		System.out.println("상품관리 페이지로 넘어온 메세지 : "+str);
		if(!str.equals(""))
		{
			mv.addObject("manager_Goods_msg", str);
		}
		mv.addObject("g_code_comboList", dao.goodsBoard_insert_g_code_comboList());
		mv.addObject("manager_Goods_list", dao.manager_Goods_list());
		mv.addObject("page", "manager_Goods.jsp");
		mv.setViewName("manager/manager_template");
		return mv;
	}
	
	// 회원관리 페이지에서 회원이 쓴 글 목록 클릭시 해당 글 디테일로 이동
	@RequestMapping("/manager_Members_Boardlist_detail.do")
	public ModelAndView manager_Members_Boardlist_detail(BoardVo b)
	{
		ModelAndView mv = new ModelAndView();
		String page = "";
		int b_no = b.getB_no();
		int b_type = b.getB_type();
		//System.out.println("넘어온 글번호 : "+b.getB_no());
		//System.out.println("넘어온 글종류 : "+b.getB_type());
		if(b_type == 1){
			page = "redirect://detailCodi.do?b_no="+b_no;
		}
		else if(b_type == 2){
			page = "redirect://detailReview.do?b_no="+b_no;
		}
		//System.out.println("반환할 view page : "+page);
		mv.setViewName(page);
		return mv;
	}
	
	//회원 관리 페이지에서 회원 등급 조정
	@RequestMapping(value="/manager_Members.do", method=RequestMethod.POST) 
	public ModelAndView manager_Members(MemberVo m,HttpServletRequest request)
	{
		ModelAndView mv = new ModelAndView();
		String manager_Members_msg = "";
		String path = request.getRealPath("/resources/lib");
		

		File pastFile = new File(path+"/");
		
		File[] pastFileList = pastFile.listFiles();
		
		if(pastFileList.length>0){
			for(int i=0;i<pastFileList.length;i++){
				pastFileList[i].delete();
			}
		}
		if(m != null)
		{
			int n = dao.manager_Members_update(m);
			if(n == 1)
			{
				manager_Members_msg = "회원정보 수정에 성공 하였습니다.";
			}
			else
			{
				manager_Members_msg = "회원정보 수정에 실패 하였습니다.";
			}
		}
		
		mv.addObject("page", "manager_Members.jsp");
		mv.addObject("manager_Members_msg", manager_Members_msg);
		mv.addObject("manager_Members_list", dao.manager_Members_list());
		mv.addObject("manager_Members_Boardlist", dao.manager_Members_Boardlist());
		mv.setViewName("manager/manager_template");
		
		return mv;
	}
	
	//회원 관리 페이지 보여주기
	@RequestMapping(value="/manager_Members.do", method=RequestMethod.GET) 
	public ModelAndView manager_Members()
	{
		ModelAndView mv = new ModelAndView();
	
		mv.addObject("page", "manager_Members.jsp");
		mv.addObject("manager_Members_list", dao.manager_Members_list());
		mv.addObject("manager_Members_Boardlist", dao.manager_Members_Boardlist());
		mv.setViewName("manager/manager_template");
		//System.out.println("회원목록 : "+dao.manager_Members_list());
		//System.out.println("회원번호 : "+dao.manager_Members_list().get(0).getM_no());
		//System.out.println("회원아이디 : "+dao.manager_Members_list().get(0).getId());
		//System.out.println("회원등급 : "+dao.manager_Members_list().get(0).getM_level());
		
		return mv;
	}
	
	//관리자 메인 페이지 보여주기
	@RequestMapping("/manager_Main.do")
	public ModelAndView manager_Main()
	{
		ModelAndView mv = new ModelAndView();
	
		mv.addObject("lowStockGoodsList", dao.lowStockGoodsList());
		mv.addObject("page", "manager_Main.jsp");
		mv.setViewName("manager/manager_template");
		
		return mv;
	}
	
	//관리자 메인 chart1 data
	@RequestMapping(value="/manager_Main_chart1.json", produces="text/plain;charset=utf-8")
	@ResponseBody
	public String manager_Main_chart1()
	{
		String str = "";
		ObjectMapper mapper = new ObjectMapper();
		List<GoodsVo> list = dao.chart1_data();
		JSONObject data = new JSONObject();
		
		JSONObject col1 = new JSONObject();
		JSONObject col2 = new JSONObject();
		JSONArray title = new JSONArray();
		col1.put("label", "카테고리");
		col1.put("type", "string");
		col2.put("label", "수량");
		col2.put("type", "number");
		title.add(col1);
		title.add(col2);
		data.put("cols", title);
		
		JSONArray body = new JSONArray();
		for( GoodsVo g : list )
		{
			JSONObject category = new JSONObject();
			category.put("v", g.getCategory());
			JSONObject amount = new JSONObject();
			amount.put("v", g.getAmount());
			JSONArray rows = new JSONArray();
			rows.add(category);
			rows.add(amount);
			JSONObject cell = new JSONObject();
			cell.put("c", rows);
			body.add(cell);
		}
		data.put("rows", body);
		//System.out.println("제이슨 오브젝트 : "+data);
		try{
			str = mapper.writeValueAsString(data.toString());
		}catch (Exception e) {
			System.out.println(e);
		}
		//System.out.println("data.toString() 한 값 : "+str);
		return str;
	}
	
	//관리자 메인 chart2 data
	@RequestMapping(value="/manager_Main_chart2.json", produces="text/plain;charset=utf-8")
	@ResponseBody
	public String manager_Main_chart2()
	{
		String str = "";
		ObjectMapper mapper = new ObjectMapper();
		List<OrdersVo> list = dao.chart2_data();
		JSONObject data = new JSONObject();
		
		JSONObject col1 = new JSONObject();
		JSONObject col2 = new JSONObject();
		JSONArray title = new JSONArray();
		col1.put("label", "날짜");
		col1.put("type", "string");
		col2.put("label", "판매액");
		col2.put("type", "number");
		title.add(col1);
		title.add(col2);
		data.put("cols", title);
		
		JSONArray body = new JSONArray();
		for( OrdersVo o : list )
		{
			JSONObject create_time = new JSONObject();
			create_time.put("v", o.getCreate_time());
			JSONObject total = new JSONObject();
			total.put("v", o.getTotal());
			JSONArray row = new JSONArray();
			row.add(create_time);
			row.add(total);
			JSONObject cell = new JSONObject();
			cell.put("c", row);
			body.add(cell);
		}
		data.put("rows", body);
		try{
			str = mapper.writeValueAsString(data.toString());
		}catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("일자별 매출 : "+str);
		return str;
	}
	
	//manager_Sales chart1 data
	@RequestMapping(value="/manager_Sales_chart1.json", produces="text/plain;charset=utf-8")
	@ResponseBody
	public String manager_Sales_chart1()
	{
		String str = "";
		ObjectMapper mapper = new ObjectMapper();
		List<OrdersVo> list = dao.manager_Sales_chart1();
		JSONObject data = new JSONObject();
		
		JSONObject col1 = new JSONObject();
		JSONObject col2 = new JSONObject();
		JSONArray title = new JSONArray();
		col1.put("label", "날짜");
		col1.put("type", "string");
		col2.put("label", "판매액");
		col2.put("type", "number");
		title.add(col1);
		title.add(col2);
		data.put("cols", title);
		
		JSONArray body = new JSONArray();
		for( OrdersVo o : list )
		{
			JSONObject create_time = new JSONObject();
			create_time.put("v", o.getCreate_time());
			JSONObject total = new JSONObject();
			total.put("v", o.getTotal());
			JSONArray row = new JSONArray();
			row.add(create_time);
			row.add(total);
			JSONObject cell = new JSONObject();
			cell.put("c", row);
			body.add(cell);
		}
		data.put("rows", body);
		try{
			str = mapper.writeValueAsString(data.toString());
		}catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("월별 매출 : "+str);
		return str;
	}
	
	// 판매현황 차트2 데이터 
	@RequestMapping(value="/manager_Sales_chart2.json", produces="text/plain;charset=utf-8")
	@ResponseBody
	public String manager_Sales_chart2()
	{
		String str = "";
		ObjectMapper mapper = new ObjectMapper();
		List<OrderDetailVo> list = dao.manager_Sales_chart2();
		JSONObject data = new JSONObject();
		
		JSONObject col1 = new JSONObject();
		JSONObject col2 = new JSONObject();
		JSONArray title = new JSONArray();
		col1.put("label", "카테고리");
		col1.put("type", "string");
		col2.put("label", "판매액");
		col2.put("type", "number");
		title.add(col1);
		title.add(col2);
		data.put("cols", title);
		
		JSONArray body = new JSONArray();
		for( OrderDetailVo o : list )
		{
			JSONObject source_name = new JSONObject();
			source_name.put("v", o.getG_no());
			JSONObject total = new JSONObject();
			total.put("v", o.getSubtotal());
			JSONArray row = new JSONArray();
			row.add(source_name);
			row.add(total);
			JSONObject cell = new JSONObject();
			cell.put("c", row);
			body.add(cell);
		}
		data.put("rows", body);
		try{
			str = mapper.writeValueAsString(data.toString());
		}catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("카테고리별 매출 : "+str);
		
		return str;
		
	}
	
	// 판매현황 차트3 데이터 
	@RequestMapping(value="/manager_Sales_chart3.json", produces="text/plain;charset=utf-8")
	@ResponseBody
	public String manager_Sales_chart3()
	{
		String str = "";
		ObjectMapper mapper = new ObjectMapper();
		List<OrdersVo> list = dao.manager_Sales_chart3();
		JSONObject data = new JSONObject();
		
		JSONObject col1 = new JSONObject();
		JSONObject col2 = new JSONObject();
		JSONArray title = new JSONArray();
		col1.put("label", "회원ID");
		col1.put("type", "string");
		col2.put("label", "구매액");
		col2.put("type", "number");
		title.add(col1);
		title.add(col2);
		data.put("cols", title);
		
		JSONArray body = new JSONArray();
		for( OrdersVo o : list )
		{
			JSONObject m_id = new JSONObject();
			m_id.put("v", o.getDepositor());
			JSONObject total = new JSONObject();
			total.put("v", o.getTotal());
			JSONArray row = new JSONArray();
			row.add(m_id);
			row.add(total);
			JSONObject cell = new JSONObject();
			cell.put("c", row);
			body.add(cell);
		}
		data.put("rows", body);
		try{
			str = mapper.writeValueAsString(data.toString());
		}catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("회원별 구매 금액 : "+str);
		
		return str;
		
	}
	
}





























