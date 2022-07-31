package com.common.logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class LoggerAspect {
	//Log4j의 Log객체를 log라는 이름으로 생성한다.
	protected Log log = LogFactory.getLog(LoggerAspect.class);
	static String name = "";
	static String type = "";

	//아래의 표현식에 해당하는 클래스의 메서드가 실행되면 무조건 around를 먼저 실행함.
	@Around("execution(* com..controller.*Controller.*(..)) or execution(* com..service.*Impl.*(..)) or execution(* com..dao.*DAO.*(..))")
	public Object logPrint(ProceedingJoinPoint joinPoint) throws Throwable {
		type = joinPoint.getSignature().getDeclaringTypeName();
		if (type.indexOf("Controller") > -1) {
			name = "Controller \t: ";
		} else if (type.indexOf("Service") > -1) {
			name = "ServiceImpl \t: ";
		} else if (type.indexOf("DAO") > -1) {
			name = "DAO \t\t: ";
		}
		log.debug(name + type + "." + joinPoint.getSignature().getName() + "()");
		return joinPoint.proceed();
	}
}
