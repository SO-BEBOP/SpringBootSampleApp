package com.example.springvirtualstore.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

	@Around("@annotation(org.springframework.web.bind.annotation.GetMapping)")
	public Object startLog(ProceedingJoinPoint jp) throws Throwable {
		System.out.println("メソッド開始：" + jp.getSignature());
		try {
			// メソッド実行
			Object result = jp.proceed();
			System.out.println("メソッド終了：" + jp.getSignature());
			return result;
		} catch (Exception e) {
			System.out.println("メソッド異常終了：" + jp.getSignature());
			e.printStackTrace();
			throw e;
		}
	}

	// UserDaoクラスのログ出力
	@Around("execution(* *..*.*UserDao*.*(..))")
	public Object daoLog(ProceedingJoinPoint jp) throws Throwable {
		System.out.println("メソッド開始：" + jp.getSignature());
		try {
			Object result = jp.proceed();
			System.out.println("メソッド終了：" + jp.getSignature());
			return result;
		} catch (Exception e) {
			System.out.println("メソッド異常終了：" + jp.getSignature());
			e.printStackTrace();
			throw e;
		}
	}

	// =======================================
	// AOP Sample
	// =======================================
	// @Before("execution(* *..*.*.*Controller.*(..))")
	// public void startLog(JoinPoint jp) {
	// System.out.println("メソッド開始：" + jp.getSignature());
	//
	// }
	//
	// @After("execution(*
	// com.example.springvirtualstore.controller.LoginController.getLogin(..))")
	// public void endLog(JoinPoint jp) {
	// System.out.println("メソッド終了：" + jp.getSignature());
	//
	// }
	// @Around("execution(* *..*.*.*Controller.*(..))")
	// public Object startLog(ProceedingJoinPoint jp) throws Throwable {
	// System.out.println("メソッド開始：" + jp.getSignature());
	// try {
	// //メソッド実行
	// Object result = jp.proceed();
	// System.out.println("メソッド終了：" + jp.getSignature());
	// return result;
	// } catch (Exception e) {
	// System.out.println("メソッド異常終了：" + jp.getSignature());
	// e.printStackTrace();
	// throw e;
	// }
	// }
	// //ポイント：Bean名で指定
	// @Around("bean(∗Controller)")
	// public Object startLog(ProceedingJoinPoint jp) throws Throwable {
	// System.out.println("メソッド開始：" + jp.getSignature());
	// try {
	// //メソッド実行
	// Object result = jp.proceed();
	// System.out.println("メソッド終了：" + jp.getSignature());
	// return result;
	// } catch (Exception e) {
	// System.out.println("メソッド異常終了：" + jp.getSignature());
	// e.printStackTrace();
	// throw e;
	// }
	// }
	// //UserDaoクラスのログ出力
	// @Around("execution(*com.example.springvirtualstore.controller.LoginController.getLogin(..))")
	// public Object endLog(ProceedingJoinPoint jp) throws Throwable {
	// System.out.println("メソッド開始：" + jp.getSignature());
	// try {
	// Object result = jp.proceed();
	// System.out.println("メソッド終了：" + jp.getSignature());
	// return result;
	// } catch (Exception e) {
	// System.out.println("メソッド異常終了：" + jp.getSignature());
	// e.printStackTrace();
	// throw e;
	// }
	// }
}
