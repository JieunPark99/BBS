package com.sample.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.common.common.CommandMap;
import com.sample.service.SampleService;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
public class SampleController {
	Logger log = Logger.getLogger(this.getClass());
	@Autowired
	@Qualifier("sampleService")
	
	//Resource = autowired(빈 가져옴) + qualifiler(빈 이름 매칭)
	//근데 리소스 어노테이션은 자바 8부터 안씀
	//그래서 오토랑 퀄리파이어랑 같이 써야 함. 
	
	//@Resource(name = "sampleService")
	
	private SampleService sampleService;

//	기존 페이징
//	@RequestMapping(value = "/sample/openBoardList.do")
//	public ModelAndView openBoardList(CommandMap commandMap) throws Exception {
//		ModelAndView mv = new ModelAndView("boardList");
//		Map<String, Object> resultMap = sampleService.selectBoardList(commandMap.getMap());
//		mv.addObject("paginationInfo", (PaginationInfo) resultMap.get("paginationInfo"));
//		mv.addObject("list", resultMap.get("result"));
//		return mv;
//	}
	
//	제이쿼리 ajax이용한 페이징
	@RequestMapping(value = "/sample/openBoardList.do")
	public ModelAndView openBoardList(CommandMap commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("boardList");
		return mv;
	}
	
	@RequestMapping(value = "/sample/selectBoardList.do")
	public ModelAndView selectBoardList(CommandMap commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("jsonView");
		List<Map<String, Object>> list = sampleService.selectBoardList(commandMap.getMap());
		mv.addObject("list", list);
		if (list.size() > 0) {
			mv.addObject("TOTAL", list.get(0).get("TOTAL_COUNT"));
		} else {
			mv.addObject("TOTAL", 0);
		}
		return mv;
	}
	
//	제이쿼리 ajax이용한 페이징 관련 추가 끝
	
	
	@RequestMapping(value = "/sample/testMapArgumentResolver.do")
	public ModelAndView testMapArgumentResolver(CommandMap commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("");
		if (commandMap.isEmpty() == false) {
			Iterator<Entry<String, Object>> iterator = commandMap.getMap().entrySet().iterator();
			Entry<String, Object> entry = null;
			while (iterator.hasNext()) {
				entry = iterator.next();
				log.debug("key : " + entry.getKey() + ", value : " + entry.getValue());
			}
		}
		return mv;
	}
	
	@RequestMapping(value = "/sample/openBoardWrite.do")
	public ModelAndView openBoardWrite(CommandMap commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("boardWrite");
		return mv;
	}
	
	@RequestMapping(value = "/sample/insertBoard.do")
	public ModelAndView insertBoard(
			CommandMap commandMap
			, HttpServletRequest request
	) throws Exception {
		ModelAndView mv = new ModelAndView("redirect:/sample/openBoardList.do");
		sampleService.insertBoard(commandMap.getMap(), request);
		return mv;
	}
	
	@RequestMapping(value = "/sample/openBoardDetail.do")
	public ModelAndView openBoardDetail(CommandMap commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("boardDetail");
		Map<String, Object> map = sampleService.selectBoardDetail(commandMap.getMap());
		mv.addObject("map", map.get("map"));
		mv.addObject("list",map.get("list"));
		return mv;
	}
	
	@RequestMapping(value="/sample/openBoardUpdate.do")
	public ModelAndView openBoardUpdate(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("boardUpdate");
		
		Map<String,Object> map = sampleService.selectBoardDetail(commandMap.getMap());
		mv.addObject("map", map.get("map"));
		mv.addObject("list", map.get("list"));
		return mv;
	}

	@RequestMapping(value = "/sample/updateBoard.do")
	public ModelAndView updateBoard(
			CommandMap commandMap
			, HttpServletRequest request
	) throws Exception {
		ModelAndView mv = new ModelAndView("redirect:/sample/openBoardDetail.do");
		sampleService.updateBoard(commandMap.getMap(), request);
		mv.addObject("IDX", commandMap.get("IDX"));
		return mv;
	}
	
	@RequestMapping(value = "/sample/deleteBoard.do")
	public ModelAndView deleteBoard(CommandMap commandMap) throws Exception {
		ModelAndView mv = new ModelAndView("redirect:/sample/openBoardList.do");
		sampleService.deleteBoard(commandMap.getMap());
		return mv;
	}
}
