package com.codeit.aop.aspect;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect        // AOP aspect
@Component
public class BasicAdvice {

	// Spring AOP는 패키지 + 클래스의 메소드 단위로 컨트롤이 가능하다.

    /*

		AOP @Pointcut 내부 문법 정리
			 execution(public Integer com.edu.aop..(*))
		com.edu.aop 패키지에 속해있고, 파라미터가 1개인 모든 메서드, 리턴값은 Integer

				execution(* com.edu...get(..))

		com.edu 패키지 및 하위 패키지에 속해있고, 이름이 get으로 시작하는 파라미터가 0개 이상인 모든 메서드

				execution(* com.edu.aop..Service.(..))

		com.edu.aop 패키지 및 하위 패키지에 속해있고, 이름이 Service로 끝나는 인터페이스의 파라미터가 0개 이상인 모든 메서드

				execution(* com.edu.aop.BoardService.(..))

		com.edu.aop.BoardService class에 속한 파마리터가 0개 이상인 모든 메서드

				execution( some(, *))

		메소드 이름이 some으로 시작하고 파라미터가 2개인 모든 메서드

		* : 와일드 카드
	*/

	@Pointcut("execution(* com.codeit.aop.service.UserService.*(..))")
	public void servicePointcut() {
		// 동작하지 않는 부분으로 빈칸으로 남길 것!
	}

	// @Before : 대상 메서드가 호출되기 이전의 실행되는 어드바이스
	@Before("servicePointcut()")
	public void printBeforeLog(JoinPoint jp) {
		System.out.println("@Before : " + jp.getSignature().getName());    // 대상 메서드 이름 호출
		System.out.println("@Before args : " + Arrays.toString(jp.getArgs()));
	}

	// @After : 대상 메서드가 호출 된 후 이후의 실행되는 어드바이스
	// -> 대상 메서드의 인자나 리턴값을 가져올수 없음
	@After("servicePointcut()")
	public void printAfterLog(JoinPoint jp) {
		System.out.println("@After : " + jp.getSignature().getName());

	}

	// @AfterReturning : 메서드가 호출 된 이후에 리턴 될때 호출되는 advice, 대상 메서드가 종료되기 전에 호출됨.
	// @After보다 더 빨리 호출이 된다, 리턴값을 확인할 수 있다. // 예외가 발생한 경우 호출 X

	@AfterReturning(pointcut = "servicePointcut()", returning = "result")
	public void printAfterReturning(JoinPoint jp, Object result) {
		System.out.println("@AfterReturning : " + jp.getSignature().getName());
		System.out.println("@AfterReturning result : " + result);
	}

	// @AfterThrowing : 메서드에서 예외가 발생하여 에러가 던져졌을 때 호출되는 메서드
	@AfterThrowing(pointcut = "servicePointcut()", throwing = "ex")
	public void printErrorLog(JoinPoint jp, Exception ex){
		System.out.println("@AfterThrowing : " + jp.getSignature().getName());
		ex.printStackTrace();
	}

	// @Around : 대상 메서드가 호출되기 전과 후에 처리하는 advice
	@Around("servicePointcut()")
	public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("@Around 실행 전 : " + pjp.getSignature().getName() + "() 호출 됨");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		// 대상 메서드 실행
		Object result = pjp.proceed();

		stopWatch.stop();
		System.out.println("@Around 실행 후 : " + pjp.getSignature().getName() + "() 호출됨");
		System.out.println("@Around 메소드 실행 시간 : " + stopWatch.getTotalTimeMillis() + "ms");
		System.out.println("@Around 메소드 실행 시간 : " + stopWatch.getTotalTimeNanos() + "ns");
		return result;
	}
}

