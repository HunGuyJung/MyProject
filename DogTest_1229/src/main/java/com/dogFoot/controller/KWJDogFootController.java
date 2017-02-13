package com.dogFoot.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.dogFoot.dao.DogFootDao;
import com.dogFoot.vo.BoardVo;
import com.dogFoot.vo.CodiVo;
import com.dogFoot.vo.GoodsGroupVo;
import com.dogFoot.vo.MemberVo;

@Controller
public class KWJDogFootController {
	
	@Autowired
	private DogFootDao dFDao;
	public void setdFDao(DogFootDao dFDao) {
		this.dFDao = dFDao;
	}
	
	
	
	//================================김진혁============================================

	@RequestMapping("/front.do")
	public ModelAndView changePage(@RequestParam(value="page",defaultValue="body.jsp") String page,
									HttpSession session){
		ModelAndView view = new ModelAndView();
		view.addObject("front",dFDao.frontList());
		view.addObject("page",page);
		view.setViewName("template");
		
		session.setAttribute("test", "안뇽");		
		return view;
	}
	@RequestMapping("/listCodi.do")
	public ModelAndView selectCodiBoard(){
		ModelAndView view = new ModelAndView();
		view.addObject("list",dFDao.selectCodiBoard());
		view.addObject("page","codi/listCodi.jsp");
		view.setViewName("template");
		return view;
	}
	@RequestMapping("/detailCodi.do")
	public ModelAndView detailCodiBoard(int b_no){
		ModelAndView view = new ModelAndView();
		view.addObject("vo",dFDao.getCodiBoard(b_no,true));
		view.addObject("id",dFDao.getCodiId(dFDao.getCodiBoard(b_no, false).getM_no()));
		GoodsGroupVo ggVo1 = dFDao.getCodiGoods(b_no, "1");
		GoodsGroupVo ggVo2 = dFDao.getCodiGoods(b_no, "2");
		GoodsGroupVo ggVo3 = dFDao.getCodiGoods(b_no, "3");
		GoodsGroupVo ggVo4 = dFDao.getCodiGoods(b_no, "4");
		GoodsGroupVo ggVo5 = dFDao.getCodiGoods(b_no, "5");
		
		if(ggVo1 != null){
			view.addObject("vo_cap",ggVo1);
			if(dFDao.detailGoodsSender(Integer.parseInt(ggVo1.getG_no().substring(0,4))) != null){
				view.addObject("ggno_1",dFDao.detailGoodsSender(Integer.parseInt(ggVo1.getG_no().substring(0,4))));
			}
			
			
		}
		if(ggVo2 != null){
			view.addObject("vo_outer",ggVo2);
			if(dFDao.detailGoodsSender(Integer.parseInt(ggVo2.getG_no().substring(0,4))) != null){
				view.addObject("ggno_2",dFDao.detailGoodsSender(Integer.parseInt(ggVo2.getG_no().substring(0,4))));
			}
			
		}
		if(ggVo3 != null){
			view.addObject("vo_top",ggVo3);
			if(dFDao.detailGoodsSender(Integer.parseInt(ggVo3.getG_no().substring(0,4)))+"" != null){
				view.addObject("ggno_3",dFDao.detailGoodsSender(Integer.parseInt(ggVo3.getG_no().substring(0,4))));
			}
			
		}
		if(ggVo4 != null){
			view.addObject("vo_bottom",ggVo4);
			if(dFDao.detailGoodsSender(Integer.parseInt(ggVo4.getG_no().substring(0,4)))+"" != null){
				view.addObject("ggno_4",dFDao.detailGoodsSender(Integer.parseInt(ggVo4.getG_no().substring(0,4))));
			}
			
			
		}
		if(ggVo5 != null){
			view.addObject("vo_shoes",ggVo5);
			if(dFDao.detailGoodsSender(Integer.parseInt(ggVo5.getG_no().substring(0,4)))+"" != null){
				view.addObject("ggno_5",dFDao.detailGoodsSender(Integer.parseInt(ggVo5.getG_no().substring(0,4))));
			}
			
		}
		
		view.addObject("page","codi/detailCodi.jsp");
		view.setViewName("template");
		return view;
	}
	
	@RequestMapping(value="/insertCodi.do",method=RequestMethod.GET)
	public ModelAndView insertForm(){
		ModelAndView view = new ModelAndView();
		view.addObject("cap",dFDao.listCodiGoods("모자"));
		view.addObject("outer",dFDao.listCodiGoods("아우터"));
		view.addObject("top",dFDao.listCodiGoods("상의"));
		view.addObject("bottom",dFDao.listCodiGoods("하의"));
		view.addObject("shoes",dFDao.listCodiGoods("신발"));
		
		view.addObject("page","codi/insertCodi.jsp");
		view.setViewName("template");
		
		return view;
	}
	@RequestMapping(value="/insertCodi.do",method=RequestMethod.POST)
	public ModelAndView insertSubmit(CodiVo c,HttpServletRequest request,HttpSession session){
		ModelAndView view = new ModelAndView();
		
		int b_no = dFDao.getNextNo();
		
		c.setB_no(b_no);
		
		GoodsGroupVo gg_cap = new GoodsGroupVo();
		GoodsGroupVo gg_outer = new GoodsGroupVo();
		GoodsGroupVo gg_top = new GoodsGroupVo();
		GoodsGroupVo gg_bottom = new GoodsGroupVo();
		GoodsGroupVo gg_shoes = new GoodsGroupVo();
		
		MultipartFile uploadFile = c.getUploadFile();
		
		String path = request.getRealPath("resources/upload");
		
		String fname = uploadFile.getOriginalFilename();
		
		try{
			c.setB_img(fname);
			byte[] fileData = uploadFile.getBytes();
			FileOutputStream fos = new FileOutputStream(path+"/"+fname);
			fos.write(fileData);
			fos.close();
		}catch(Exception e){
			System.out.println("파일업로드"+e);
		}
		
		int re = dFDao.insertCodiBoard(c);
		if(c.getCap() != null && c.getCap() != ""){
			gg_cap.setB_no(b_no);
			gg_cap.setG_no(c.getCap());
			gg_cap.setG_img(dFDao.getG_imgName(c.getCap()));
			dFDao.insertCodiGoods(gg_cap);
		}
		if(c.getOuter() != null && c.getOuter() != ""){
			gg_outer.setB_no(b_no);
			gg_outer.setG_no(c.getOuter());
			gg_outer.setG_img(dFDao.getG_imgName(c.getOuter()));
			dFDao.insertCodiGoods(gg_outer);
		}
		if(c.getTop() != null && c.getTop() != ""){
			gg_top.setB_no(b_no);
			gg_top.setG_no(c.getTop());
			gg_top.setG_img(dFDao.getG_imgName(c.getTop()));
			dFDao.insertCodiGoods(gg_top);
		}
		if(c.getBottom() != null && c.getBottom() != ""){
			gg_bottom.setB_no(b_no);
			gg_bottom.setG_no(c.getBottom());
			gg_bottom.setG_img(dFDao.getG_imgName(c.getBottom()));
			dFDao.insertCodiGoods(gg_bottom);
		}
		if(c.getShoes() != null && c.getShoes() != ""){
			gg_shoes.setB_no(b_no);
			gg_shoes.setG_no(c.getShoes());
			gg_shoes.setG_img(dFDao.getG_imgName(c.getShoes()));
			dFDao.insertCodiGoods(gg_shoes);
		}
		
		if(re == 1){
			session.setAttribute("codi_msg", "코디가 등록되었습니다!");
		}else{
			session.setAttribute("codi_msg", "글 등록에 실패하였습니다.");
		}
		
		view = selectCodiBoard();
		
		return view;
	}
	@RequestMapping(value="/updateCodi.do",method=RequestMethod.GET)
	public ModelAndView updateForm(int b_no){
		ModelAndView view = new ModelAndView();
		view.addObject("b_no", b_no);
		view.addObject("vo",dFDao.getCodiBoard(b_no,false));
		view.addObject("cap_init",dFDao.getCodiGoods(b_no,"1"));
		view.addObject("outer_init",dFDao.getCodiGoods(b_no,"2"));
		view.addObject("top_init",dFDao.getCodiGoods(b_no,"3"));
		view.addObject("bottom_init",dFDao.getCodiGoods(b_no,"4"));
		view.addObject("shoes_init",dFDao.getCodiGoods(b_no,"5"));
		view.addObject("cap",dFDao.listCodiGoods("모자"));
		view.addObject("outer",dFDao.listCodiGoods("아우터"));
		view.addObject("top",dFDao.listCodiGoods("상의"));
		view.addObject("bottom",dFDao.listCodiGoods("하의"));
		view.addObject("shoes",dFDao.listCodiGoods("신발"));
		
		view.addObject("page","codi/updateCodi.jsp");
		view.setViewName("template");
		
		return view;
	}
	@RequestMapping(value="/updateCodi.do",method=RequestMethod.POST)
	public ModelAndView updateSubmit(CodiVo c,HttpSession session,HttpServletRequest request){
		ModelAndView view = new ModelAndView();
		String path = request.getRealPath("resources/upload");
		MultipartFile uploadFile = c.getUploadFile();
		
		HashMap<String,Object> cap_map = new HashMap<String, Object>();
		HashMap<String,Object> outer_map = new HashMap<String, Object>();
		HashMap<String,Object> top_map = new HashMap<String, Object>();
		HashMap<String,Object> bottom_map = new HashMap<String, Object>();
		HashMap<String,Object> shoes_map = new HashMap<String, Object>();
		
		
		int b_no = c.getB_no();
		
		String oldb_img = dFDao.getCodiBoard(b_no,false).getB_img();
		c.setB_img(oldb_img);
		
		String b_img = uploadFile.getOriginalFilename();
		
		if(b_img != null && !b_img.equals("") && !b_img.equals(null)){
			try{
				c.setB_img(b_img);
				byte[] fileData = uploadFile.getBytes();
				FileOutputStream fos = new FileOutputStream(path+"/"+b_img);
				fos.write(fileData);
				fos.close();
				
				File file = new File(path+"/"+oldb_img);
				file.delete();
			}catch(Exception e){
				System.out.println(e);
			}
		}
		
		int re = dFDao.updateCodiBoard(c);
		
		if(c.getCap() != null && !c.getCap().equals("")){
			if(c.getCap_init() != null && !c.getCap_init().equals("")){
				cap_map.put("b_no", b_no);
				cap_map.put("g_no", c.getCap());
				cap_map.put("g_img", dFDao.getG_imgName(c.getCap()));
				cap_map.put("g_init", c.getCap_init());
				
				int re1 = dFDao.updateGoodsGroup(cap_map);
			}else{
				GoodsGroupVo gg_cap = new GoodsGroupVo();
				gg_cap.setB_no(b_no);
				gg_cap.setG_no(c.getCap());
				gg_cap.setG_img(dFDao.getG_imgName(c.getCap()));
				dFDao.insertCodiGoods(gg_cap);
			}
			
		}else{
			if(c.getCap_init() != null && !c.getCap_init().equals("")){
				dFDao.deleteGoodsGroup(b_no, c.getCap_init());
			}
		}
		
		if(c.getOuter() != null && !c.getOuter().equals("")){
			if(c.getOuter_init() != null && !c.getOuter_init().equals("")){
				outer_map.put("b_no", b_no);
				outer_map.put("g_no", c.getOuter());
				outer_map.put("g_img", dFDao.getG_imgName(c.getOuter()));
				outer_map.put("g_init", c.getOuter_init());
				
				int re2 = dFDao.updateGoodsGroup(outer_map);
			}else{
				GoodsGroupVo gg_outer = new GoodsGroupVo();
				gg_outer.setB_no(b_no);
				gg_outer.setG_no(c.getOuter());
				gg_outer.setG_img(dFDao.getG_imgName(c.getOuter()));
				dFDao.insertCodiGoods(gg_outer);
			}
		}else{
			if(c.getOuter_init() != null && !c.getOuter_init().equals("")){
				dFDao.deleteGoodsGroup(b_no, c.getOuter_init());
			}
		}
		if(c.getTop() != null && !c.getTop().equals("")){
			if(c.getTop_init() != null && !c.getTop_init().equals("")){
				top_map.put("b_no", b_no);
				top_map.put("g_no", c.getTop());
				top_map.put("g_img", dFDao.getG_imgName(c.getTop()));
				top_map.put("g_init", c.getTop_init());
				
				int re3 = dFDao.updateGoodsGroup(top_map);
			}else{
				GoodsGroupVo gg_top = new GoodsGroupVo();
				gg_top.setB_no(b_no);
				gg_top.setG_no(c.getTop());
				gg_top.setG_img(dFDao.getG_imgName(c.getTop()));
				dFDao.insertCodiGoods(gg_top);
			}
		}else{
			if(c.getTop_init() != null && !c.getTop_init().equals("")){
				dFDao.deleteGoodsGroup(b_no, c.getTop_init());
			}
		}
		if(c.getBottom() != null && !c.getBottom().equals("")){
			if(c.getBottom_init() != null && !c.getBottom_init().equals("")){
				bottom_map.put("b_no", b_no);
				bottom_map.put("g_no", c.getBottom());
				bottom_map.put("g_img", dFDao.getG_imgName(c.getBottom()));
				bottom_map.put("g_init", c.getBottom_init());
				
				int re4 = dFDao.updateGoodsGroup(bottom_map);
			}else{
				GoodsGroupVo gg_bottom = new GoodsGroupVo();
				gg_bottom.setB_no(b_no);
				gg_bottom.setG_no(c.getBottom());
				gg_bottom.setG_img(dFDao.getG_imgName(c.getBottom()));
				dFDao.insertCodiGoods(gg_bottom);
			}
		}else{
			if(c.getBottom_init() != null && !c.getBottom_init().equals("")){
				dFDao.deleteGoodsGroup(b_no, c.getBottom_init());
			}
		}
		if(c.getShoes() != null && !c.getShoes().equals("")){
			if(c.getShoes_init() != null && !c.getShoes_init().equals("")){
				shoes_map.put("b_no", b_no);
				shoes_map.put("g_no", c.getShoes());
				shoes_map.put("g_img", dFDao.getG_imgName(c.getShoes()));
				shoes_map.put("g_init", c.getShoes_init());
				
				int re5 = dFDao.updateGoodsGroup(shoes_map);
			}else{
				GoodsGroupVo gg_shoes = new GoodsGroupVo();
				gg_shoes.setB_no(b_no);
				gg_shoes.setG_no(c.getShoes());
				gg_shoes.setG_img(dFDao.getG_imgName(c.getShoes()));
				dFDao.insertCodiGoods(gg_shoes);
			}
		}else{
			if(c.getShoes_init() != null && !c.getShoes_init().equals("")){
				dFDao.deleteGoodsGroup(b_no, c.getShoes_init());
			}
		}
		
		view = selectCodiBoard();
		
		return view;
	}
	@RequestMapping(value="/deleteCodi.do")
	public ModelAndView deleteCodi(int b_no,HttpServletRequest request){
		ModelAndView view = new ModelAndView();
		String path = request.getRealPath("resources/upload");
		
		String fname = dFDao.getCodiBoard(b_no,false).getB_img();
		
		
		int re2 = dFDao.deleteCodiGoods(b_no);
		
			int re = dFDao.deleteCodiBoard(b_no);
			File file = new File(path+"/"+fname);
			file.delete();
	
		
		
		view = selectCodiBoard();
		
		return view;
	}
	///////////////////////////리뷰/////////////////////////////////////
	@RequestMapping(value="/listReview.do")
	public ModelAndView listReviewBoard(){
		ModelAndView view = new ModelAndView();
		view.addObject("list",dFDao.listReviewBoard());
		view.addObject("page","review/listReview.jsp");
		view.setViewName("template");
		return view;
	}
	@RequestMapping(value="/insertReview.do",method=RequestMethod.GET)
	public ModelAndView insertReviewForm(HttpSession session){
		ModelAndView view = new ModelAndView();
		int m_no = (Integer)session.getAttribute("m_no");
		view.addObject("list",dFDao.buyList(m_no));
		view.addObject("page","review/insertReview.jsp");
		view.setViewName("template");
		return view;
	}
	@RequestMapping(value="/insertReview.do",method=RequestMethod.POST)
	public ModelAndView insertReviewSubmit(BoardVo b){
		ModelAndView view = new ModelAndView();
		int b_no = dFDao.getNextNo();
		b.setB_no(b_no);
		b.setLike_count(5);
		int re = dFDao.insertReviewBoard(b);
		view.setViewName("redirect:/listReview.do");
		return view;
	}
	@RequestMapping(value="/detailReview.do")
	public ModelAndView detailReview(int b_no){
		ModelAndView view = new ModelAndView();
		view.addObject("vo",dFDao.detailReviewBoard(b_no));
		if(dFDao.detailGoodsSender(dFDao.detailReviewBoard(b_no).getG_code()) != null){
			view.addObject("g_sender",dFDao.detailGoodsSender(dFDao.detailReviewBoard(b_no).getG_code()));
		}
		
		view.addObject("page","review/detailReview.jsp");
		view.setViewName("template");
		return view;
	}
	@RequestMapping(value="/deleteReview.do")
	public ModelAndView deleteReview(int b_no){
		ModelAndView view = new ModelAndView();
		int re = dFDao.deleteReviewBoard(b_no);
		view.setViewName("redirect:/listReview.do");
		return view;
	}
	//==================================================================================
}
