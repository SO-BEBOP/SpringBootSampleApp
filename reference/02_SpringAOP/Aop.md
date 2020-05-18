# AOP

## AOPとは

    AOPはアスペクト志向プログラミング(Aspect Oriented Programming)の略で複数のクラスに点在する横断的な関心事(
	ログ、キャッシュ、共通エラー処理など)を中心に設計や実装を行うプログラミング手法です。
    @Aspectアノテーションを付与したクラスに共通処理を実装する。
	ジョインポイントと呼ばれる部分で共通処理を埋め込む箇所を指定し、
	該当部分(クラスやメソッドなど)が呼ばれたタイミングで共通処理を実行させるというものです。

---

| 用語           | 概要                                                                                                                        |
| :------------- | :-------------------------------------------------------------------------------------------------------------------------- |
| Aspect         | AOPの単位となる横断的な関心事を示すモジュールそのもの。<br>「ログの出力」や「例外ハンドリング」などの関心事がAspectになる。 |
| Join point     | 横断的な関心事を実行するポイントのこと。SpringのAOPではメソッドの実行時。                                                   |
| Advice         | Join Pointで実行されるコードのこと。                                                                                        |
| Pointcut       | 実行対象のJoin Pointを選択する表現のこと。<br>Spring AOPではBean定義ファイルやアノテーションを使って定義する。              |
| Weaving        | アプリケーションコードの適切なポイントにAspectを入れ込む処理のこと。<br>Spring AOPでは実行時に行う。                        |
| Target         | AOP処理によって処理フローが変更されたオブジェクトのこと。                                                                   |
| Before         | JoinPointの前に実行される。<br>例外のスローをのぞいて、Join Pointの処理フローを防ぐことはできない。                         |
| After          | JoinPointの後に実行される。<br>Join Pointの正常終了や例外のスローにかかわらず、常に実行される。                             |
| AfterReturning | JoinPointが正常に終了された場合に実行される。<br>例外がスローされた場合には、実行されない。                                 |
| AfterThrowing  | JoinPointで例外がスローされた場合に実行される。<br>正常終了した場合には、実行されない。                                     |
| Around         | JoinPointの前後で実行される。                                                                                               |

---

### ※注意

    @Aroundは汎用的だが、処理をインターセプトして想定外の挙動を生む場合がある。
   

## 実装パターン

### @Before @After

```java
@Aspect
@Component
public class LogAspect {

    @Before("execution(* *..*.*.*Controller.*(..))")
	public void startLog(JoinPoint jp) {
		System.out.println("メソッド開始：" + jp.getSignature());
	}
	@After("execution(*
	com.example.springvirtualstore.controller.LoginController.getLogin(..))")
	public void endLog(JoinPoint jp) {
	System.out.println("メソッド終了：" + jp.getSignature());
    }
}
```

### @Around

```java
    @Around("execution(* *..*.*.*Controller.*(..))")
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
```

### アノテーションを対象とする

    @annotation 
    指定したアノテーションのメソッド全てを対象とします。
    @withn 
    指定したアノテーションが付与されたクラスのメソッド全てを対象とします。

```java
	@Around("@annotation(org.springframework.web.bind.annotation.GetMapping)")
	public Object startLog(ProceedingJoinPoint jp) throws Throwable {
        // 省略
    }

    @Around("@withn(org.springframework.stereotype.Controller)")
	public Object startLog(ProceedingJoinPoint jp) throws Throwable {
        // 省略
    }
```

### Beanを対象とする

```java
	@Around("bean(∗Controller)")
	public Object startLog(ProceedingJoinPoint jp) throws Throwable {
		// 省略
	}
```
