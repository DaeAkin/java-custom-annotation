# ‼️ 스프링 부트의 autoconfiguration



![](https://github.com/DaeAkin/DaeAkin.github.io/blob/master/img/blog/custom-annotation/javacompiler.png?raw=true)



J2SE 5.0 부터 어노테이션을 작성할 때 java.lang.annotation 패키지에 있는 4가지 어노테이션을 이용하여 작성합니다.

- @Documented : Java doc에 어노테이션을 추가할지 여부를 알려줍니다.

- @Retention : 어노테이션의 지속 시간을 정합니다.

  - RetentionPolicy.SOURCE : 컴파일 동안에는 동작되지 않습니다. 이 어노테이션은 컴파일이 완료된 후에는 의미가 없으므로, 바이트 코드에 기록되지 않습니다. 예시로는 @Override와 @SuppressWarnings 어노테이션이 있습니다.
  - RetentionPolicy.CLASS : 클래스 로드동안에는 작동하지 않습니다. 바이트코드 레벨에서 후 처리기를 동작이 필요 할 때 사용합니다. @Retention에 아무 속성을 주지 않으면, 기본 값으로 설정됩니다.
  - RetentionPlicy.RUNTIME : 이 어노테이션은 런타임시 반영됩니다. 커스텀 어노테이션을 만들 때 주로 사용합니다.

- @Target : 어노테이션을 작성할 곳 입니다. 작성하지 않으면, 어노테이션은 어디든지 존재하게 됩니다.Where annotation can be placed. If you don’t specify this, annotation can be placed anywhere. The following are valid values. One important point here is that it’s inclusive only, which means if you want annotation on 7 attributes and just want to exclude only one attribute, you need to include all 7 while defining the target.

  > *ElementType.TYPE (class, interface, enum)*
  >
  > *ElementType.FIELD (instance variable)*
  >
  > *ElementType.METHOD*
  >
  > *ElementType.PARAMETER*
  >
  > *ElementType.CONSTRUCTOR*
  >
  > *ElementType.LOCAL_VARIABLE*
  >
  > *ElementType.ANNOTATION_TYPE (on another annotation)*
  >
  > *ElementType.PACKAGE (remember package-info.java)*

- @Inherited : 자식클래스에 영향을 미칠지 결정합니다.





## 🎯 Custom 어노테이션 만들기

커스텀 어노테이션을 만들 때 몇가지 규칙이 있습니다.

1. 어노테이션 타입은 @interface로 정의해야합니다. 모든 어노테이션은 자동적으로 `java.lang.Annotation` 인터페이스를 상속받아야하며, 다른 클래스나 인터페이스를 상속 받으면 안됩니다.
2. 파라미터 멤버들의 접근자는 public이거나 default여야만 합니다.
3. 파라미터 멤머들은 byte,short,char,int,float,double,boolean,의 기본타입과 String, Enum, Class, 어노테이션만 사용할 수 있습니다.
4. 클래스 메소드와 필드에 관한 어노테이션 정보를 얻고 싶으면, 리플렉션만 이용해서 얻을 수 있습니다. 다른 방법으로는 어노테이션 객체를 얻을 수 없습니다.
5. 어노테이션은 멤버 변수가 없으면 , 해당 어노테이션은 사용되지 않습니다. ?







## 💡 Spring은 어노테이션을 어떻게 사용하고 있을까?

Spring에서 애플리케이션을 실행 시 @Service나, @Component가 붙은 클래스들을 스캔해서 IoC 컨테이너에 등록해주는 과정이 있습니다. 

##### @Service 어노테이션

```java
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Service {
    @AliasFor(
        annotation = Component.class
    )
    String value() default "";
}
```

Service 어노테이션은, Target이 TYPE으로 지정되어 있습니다. Class나 Interface를 타겟으로 삼는다는 의미 입니다.

또한 같은 패키지인`org.springframework.stereotype` 의 Component 어노테이션을 쓰고 있는걸 볼 수 있습니다.

##### @Component 어노테이션

```java
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface Component {
    String value() default "";
}
```

Spring에서는 @Component , @Service , @Controller 등 어노테이션이 사용된 클래스는 bean으로 등록하게 됩니다. 등록해주는 스프링의 코드는 다음과 같습니다.

##### ClassPathBeanDefinitionScanner.java

![](https://github.com/DaeAkin/java-custom-annotation/blob/master/docs/%EC%8A%A4%ED%8A%B8%EB%A0%88%EC%98%A4%ED%83%80%EC%9E%85%20%EC%96%B4%EB%85%B8%ED%85%8C%EC%9D%B4%EC%85%98%EC%9D%84%20%EB%93%B1%EB%A1%9D%ED%95%B4%EC%A3%BC%EB%8A%94%20%ED%81%B4%EB%9E%98%EC%8A%A4.png?raw=true)

이 메소드가 ClassPath에 있는 패키지의 모든 클래스를 읽어, @Component 어노테이션이 붙은 클래스를 IoC 컨테이너에 등록해주는 메소드 입니다.



## 참고자료

https://docs.spring.io/spring-boot/docs/2.1.1.RELEASE/reference/htmlsingle/#using-boot-auto-configuration

https://programmersought.com/article/6032481348/

https://docs.spring.io/spring/docs/4.0.x/spring-framework-reference/htmlsingle/#beans-factory-scopes-singleton

[다이어그램](https://app.diagrams.net/#G1IQGFbL7rTgsTyJL0irGu2-B3p-ENyhPm)

[스프링 싱글톤 구현법](https://stackoverflow.com/questions/2637864/singleton-design-pattern-vs-singleton-beans-in-spring-container)