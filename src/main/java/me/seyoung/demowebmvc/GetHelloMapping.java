package me.seyoung.demowebmvc;

import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.*;

//Annotation이 사용된 Class파일에 JavaDoc에 @AliasFor를 사용하여 함께 명시되도록 한다.
@Documented

//어디에 사용할건지 작성 ex) ElementType.METHOD : 메서드에 해당 Annotation을 사용 하겠다
//{}를 이용하여 여러 타켓을 설정 가능함.
@Target({ElementType.METHOD})

/*
* Retention : 설정된 정책까지 해당 Annotation이 유지된다.  단) 설정하지 않으면 Runtime시 사라져서 Annotation이 달려있어도 실행 안됨.
* */
@Retention(RetentionPolicy.RUNTIME)

//Meta Annotation : Annotation Class안에서 위에 선언되는 Annotation ex) @Retention, @RequestMapping .....
//Composed Annotation : 다른 Annotation을 조합하여 만든 Annotation
@RequestMapping(method= RequestMethod.GET, value="/hello")
public @interface GetHelloMapping {
}
