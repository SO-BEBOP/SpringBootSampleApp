# SpringTest

    SpringBootのプロジェクトをした際、デフォルトでライブラリに入っているSpringを起動した後に、JUnitなどのテストフレームワークを動かすことが出来るライブラリ。Springでテストする場合、SpringBootを起動してからでないと、コントローラやサービスクラスが動きません。そのため、Springを起動した後に、JUnitなどを動かす必要がある。SpringTestがそれを実現します。

---

## 主なテスト用アノテーション

### @SpringBootTest

    DIコンテナにBeanとして登録するコンポーネントをテストしたり、mock化する場合に宣言する。これをつけないとテストしたいクラスを@Autowiredできない。
    webEnvironment属性を設定するとテスト実行時にサーバーを立ち上げる。

### @WebMvcTest

    @Controllerや@RestControllerのテストをする場合に宣言する。
    @Controllerコンポーネントのみを対象にDIコンテナ（ApplicationContext）を生成する。MockMvcを@Autowiredできるようにしてくれる。

### @Test

    このメソッドが、テストメソッドであることを示します。
    JUnit4の@Test アノテーションとは異なり、このアノテーションはどんな属性も宣言しません。JUnitJupiter のテスト拡張は、それ専用のアノテーションベースで行われるからですこのメソッドは、オーバーライドされない限り、継承されます。
    ※ JUnit4と5を併用する場合、import文が異なるので注意。

### @ParameterizedTest

    このメソッドが、パラメーター化テストであることを示します。
    このメソッドは、オーバーライドされない限り、継承されます。

### @RepeatedTest

    このメソッドが、繰り返しテストのためのテストテンプレートであることを示します。
    このメソッドは、オーバーライドされない限り、継承されます。

### @TestFactory

    このメソッドが、動的テストのためのテストファクトリであることを示します。
    このメソッドは、オーバーライドされない限り、継承されます。

### @TestInstance

    アノテーションを付与したテストクラスに対して、
    テストインスタンスのライフサイクルを設定するために使われます。
    このアノテーションは、継承されます。

### @TestTemplate

    このメソッドが、テストケースのテンプレートであることを示します。
    このメソッドは、登録したプロバイダが返す実行コンテキストの数に応じて、
    複数回呼び出されます。このメソッドは、オーバーライドされない限り、継承されます。

### @DisplayName

    テストクラスやテストメソッドにカスタムの表示名を指定します。
    このアノテーションは、継承されません。

### @BeforeEach

    このメソッドが現在のクラスの
    @Test,@RepeatedTest,@ParameterizedTest,@TestFactory
    メソッドの前に毎回実行されるよう指定します。JUnit4の@Beforeと同じです。
    このメソッドは、オーバーライドされない限り、継承されます。

### @AfterEach

    このメソッドが現在のクラスの
    @Test,@RepeatedTest,@ParameterizedTest,@TestFactoryメソッドの後に、
    毎回実行されるよう指定します。JUnit4の@Afterと同じです。
    このメソッドは、オーバーライドされない限り、継承されます。

### @BeforeAll

    このメソッドが、現在のクラスのすべての
    @Test,@RepeatedTest,@ParameterizedTest,@TestFactoryメソッドの前に、
    一度だけ実行されるよう指定します。JUnit4の@BeforeClassと同じです。
    このメソッドは、（隠すかオーバーライドするかしない限り）継承されますが、
    （テストインスタンスのライフサイクルに"per-class"が使われない限り）staticでなければいけません。

### @AfterAll

    このメソッドが現在のクラスのすべての
    @Test,@RepeatedTest,@ParameterizedTest,@TestFactoryメソッドの後に、
    一度だけ実行されるよう指定します。JUnit4の@AfterClassと同じです。
    このメソッドは、（隠すかオーバーライドするかしない限り）継承されますが、
    （テストインスタンスのライフサイクルに"per-class"が使われない限り）staticでなければいけません。

### @Nested

    このクラスが、ネストした非staticのテストクラスであることを示します。
    @BeforeAllおよび@AfterAllメソッドは、テストインスタンスのライフサイクルに"per-class"が使われない限り、
    @Nestedテストクラスの中で直接使うことはできません。このアノテーションは、継承されません。

### @Tag

    クラスまたはメソッドレベルでテストをフィルタリングするためのタグを宣言するのに使われます。
    このアノテーションは、クラスレベルでは継承されますが、メソッドレベルでは継承されません。

### @Disabled

    テストクラスやテストメソッドを無効化するのに使われます。
    このアノテーションは、継承されません。

### @ExtendWith

    カスタムの拡張機能を登録するために使われます。
    このアノテーションは、継承されます。

### @SpringJUnitConfig

    JUnit5上でSpringTestContextFrameworkを利用することを示すための合成アノテーション。
    (@ExtendWith(SpringExtension.class)+@ContextConfiguration)

### @SpringJUnitWebConfig

    JUnit5上でWEB環境向けのSpringTestContextFrameworkを利用することを示すための合成アノテーション。
    (@ExtendWith(SpringExtension.class) + @ContextConfiguration + @WebAppConfiguration)

### @EnabledIf

    指定した条件を充す（SpELで指定したExpressionの結果がtrueになる）
    場合にテストを実行することを示すアノテーション。

### @DisabledIf

    指定した条件を充す（SpELで指定したExpressionの結果がtrueになる）
    場合にテストをスキップすることを示すアノテーション。

### ※備考

以下のアノテーションの付与されたメソッドは、戻り値を返してはいけません。<br>
@Test,@TestTemplate,@RepeatedTest,@BeforeAll,@AfterAll,@BeforeEach,@AfterEach

---
