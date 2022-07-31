package com.common.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.StringUtils;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

public class AbstractDAO {
	protected Log log = LogFactory.getLog(AbstractDAO.class);
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;
	
	//Resource = autowired(빈 가져옴) + qualifiler(빈 이름 매칭)
	//근데 리소스 어노테이션은 자바 8부터 안씀
	//그래서 오토랑 퀄리파이어랑 같이 써야 함. 

	protected void printQueryId(String queryId) {
		if (log.isDebugEnabled()) {
			log.debug("\t QueryId \t: " + queryId);
		}
	}

	public Object insert(String queryId, Object params) {
		printQueryId(queryId);
		return sqlSession.insert(queryId, params);
	}

	public Object update(String queryId, Object params) {
		printQueryId(queryId);
		return sqlSession.update(queryId, params);
	}

	public Object delete(String queryId, Object params) {
		printQueryId(queryId);
		return sqlSession.delete(queryId, params);
	}

	public Object selectOne(String queryId) {
		printQueryId(queryId);
		return sqlSession.selectOne(queryId);
	}

	public Object selectOne(String queryId, Object params) {
		printQueryId(queryId);
		return sqlSession.selectOne(queryId, params);
	}

	@SuppressWarnings("rawtypes")
	public List selectList(String queryId) {
		printQueryId(queryId);
		return sqlSession.selectList(queryId);
	}

	@SuppressWarnings("rawtypes")
	public List selectList(String queryId, Object params) {
		printQueryId(queryId);
		return sqlSession.selectList(queryId, params);
	}
	
	/* 그냥? 페이징 */
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	public Map selectPagingList(String queryId, Object params) {
//		printQueryId(queryId);
//		Map<String, Object> map = (Map<String, Object>) params;
//		PaginationInfo paginationInfo = null;
//		if (map.containsKey("currentPageNo") == false || StringUtils.isEmpty(map.get("currentPageNo")) == true)
//			map.put("currentPageNo", "1");
//		paginationInfo = new PaginationInfo();
//		paginationInfo.setCurrentPageNo(Integer.parseInt(map.get("currentPageNo").toString()));
//		if (map.containsKey("PAGE_ROW") == false || StringUtils.isEmpty(map.get("PAGE_ROW")) == true) {
//			paginationInfo.setRecordCountPerPage(10);//한 페이지에 보여줄 게시글의 수
//		} else {
//			paginationInfo.setRecordCountPerPage(Integer.parseInt(map.get("PAGE_ROW").toString()));
//		}
//		paginationInfo.setPageSize(5); //페이지네이션 수
//		int start = paginationInfo.getFirstRecordIndex();
//		int end = start + paginationInfo.getRecordCountPerPage();
//		map.put("START", start + 1);
//		map.put("END", end);
//		params = map;
//		Map<String, Object> returnMap = new HashMap<String, Object>();
//		List<Map<String, Object>> list = sqlSession.selectList(queryId, params);
//		if (list.size() == 0) {
//			map = new HashMap<String, Object>();
//			map.put("TOTAL_COUNT", 0);
//			list.add(map);
//			if (paginationInfo != null) {
//				paginationInfo.setTotalRecordCount(0);
//				returnMap.put("paginationInfo", paginationInfo);
//			}
//		} else {
//			if (paginationInfo != null) {
//				paginationInfo.setTotalRecordCount(Integer.parseInt(list.get(0).get("TOTAL_COUNT").toString()));
//				returnMap.put("paginationInfo", paginationInfo);
//			}
//		}
//		returnMap.put("result", list);
//		return returnMap;
//	}
	
	/* jQuery 및 ajax를 이용한 페이징 */
	@SuppressWarnings("unchecked")
	public Object selectPagingList(String queryId, Object params) {
		printQueryId(queryId);
		Map<String, Object> map = (Map<String, Object>) params;
		String strPageIndex = (String) map.get("PAGE_INDEX");
		String strPageRow = (String) map.get("PAGE_ROW");
		int nPageIndex = 0;
		int nPageRow = 20;
		if (StringUtils.isEmpty(strPageIndex) == false) {
			nPageIndex = Integer.parseInt(strPageIndex) - 1;
		}
		if (StringUtils.isEmpty(strPageRow) == false) {
			nPageRow = Integer.parseInt(strPageRow);
		}
		map.put("START", (nPageIndex * nPageRow) + 1);
		map.put("END", (nPageIndex * nPageRow) + nPageRow);
		return sqlSession.selectList(queryId, map);
	}
}
