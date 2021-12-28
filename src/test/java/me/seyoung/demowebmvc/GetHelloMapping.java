package me.seyoung.demowebmvc;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


//@Target(ElementType.METHOD)
//Retention : 해당 Annotation이 언제까지 유지 될지 설정. 설정이 되 있지 않은 Annotation은 Runtime시에 사라짐.
/*
 * Runtime : 런타임시에도 살아 있음.
 * */
@Retention(RetentionPolicy.RUNTIME)
@RequestMapping(method = RequestMethod.GET, value="/hello")
public @interface GetHelloMapping {

}



