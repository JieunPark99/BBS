package com.common.logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoggerInterceptor extends HandlerInterceptorAdapter {//HandlerInterceptorAdapter를 상속받아야 한다.
	//Log4j의 Log객체를 log라는 이름으로 생성한다.
	protected Log log = LogFactory.getLog(LoggerInterceptor.class);

//	Interceptor는 Controller가 요청되기 전에 수행된다. 
//	따라서 Interceptor와 DispatcherServlet이 같은 위치에 등록되어 있어야 정상적으로 수행이 된다. 
//	(같은 파일 경로에 있어야 함)
	
	//전처리기
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("================== START ===================");
			log.debug(" Request URI \t: " + request.getRequestURI());
		}
		return super.preHandle(request, response, handler);
	}

	//후처리기
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("=================== END =====================\n");
		}
	}
}