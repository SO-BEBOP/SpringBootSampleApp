# Lombok

## Lombokとは

    読み方はロンボック、またはロンボク。
    Lombokとは、Java特有の冗長なコードを簡潔にしてくれるオープンソースのライブラリです。
    ライセンスはMITライセンスなので自由に利用することが出来ます。
    LombokはEclipseの自動生成機能の代わりにアノテーションを使うことで冗長なコードを簡潔にします。

---

### **@Getter、@Setter**

    コンパイル時にgetter、setterを自動生成します。

【使用例】

```java
@Getter
@Setter
public class Sample {
  private String id;
  private String name;
  private Integer age:
}

// 個別で設定することもできる。
public class Member {
    @Getter
    private int id;
    @Setter
    private String name;
}
```

---

### **@NoArgsConstructor**

    デフォルトコンストラクタを自動生成します。

---

### **@AllArgsConstructor**

    全フィールドに対する初期化値を引数にとるコンストラクタを自動生成します。
    
    @NoArgsConstructor と @AllArgsConstructor をクラスに付与することで、デフォルトコンストラクタと全メンバへ値をセットするための引数付きコンストラクタの両方を、自動生成することができます。

---

### **@RequiredArgsConstructor**

    finalフィールドに対し、初期化値を引数にとるコンストラクタを生成します。

【使用例】

実装したSample.javaが以下だとすると、

```java
@RequiredArgsConstructor
public class Sample {
  private final String id;
  private final String name;
  private Integer age;
}
```

以下のようなSample.classが作られます。

```java
public class Sample {
  private final String id;
  private final String name;
  private Integer age;

  public Sample(String id, String name) {
    this.id = id;
    this.name = name;
  }
}
```

---

### **@ToString**

    @ToStringは、toStringを実装(オーバーライド)してくれます。
    またexcludeを使用すると対象のフィールドを除外することが可能です。

【使用例】
```java
@ToString(exclude = "age")
public class Sample {
  private String id;
  private String name;
  private Integer age;
}
```

---

### **@EqualsAndHashCode**

    equalsとhashCodeが実装(オーバーライド)されます。

【使用例】

```java
@EqualsAndHashCode
public class Sample {
  private String name;
  private int age;
}
```

---

### **@Value**

    @Valueを付与すると以下のアノテーションが付与され、イミュータブルなオブジェクトとなります。

    ・@Getter
    ・@ToString
    ・@EqualsAndHashCode
    ・@AllArgsConstructor
    ・クラス及び各フィールドにfinalが付与される。
    ・各フィールドの可視性がprivateになる。

    生成時にコンストラクタによって値を設定し、以降は値の変更が不可能になります。
    staticConstructorオプションを設定すると、staticなファクトリメソッドを生成できます。
    その場合のコンストラクタはprivateに変更されますので、ファクトリメソッドを通さないとインスタンスが生成できなくなります。

---

### **@Data**

    @Data付与すると以下のアノテーションが付与された状態と同じになります。

    ・@Getter
    ・@Setter
    ・@ToString
    ・@EqualsAndHashCode
    ・@RequiredArgsConstructor

---
