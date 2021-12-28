package me.seyoung.demowebmvc;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SampleController {
    /*
    * URI로 요청 매핑하기
    * ? : 한글자는 어떤것이든 매핑 시켜준다. ex) hello? : hello1,hello2,hello3,helloe ...
    * * : 여러글자를 매핑 시켜준다. ex) hello* : hello123, helloHi...
    * ** : 이후로 나오는 패스가 몇개던지 모두 매핑 가능하다. ex)hello/** : /hello/a/b/c/d..
     */
    //정규식으로 url매핑이 가능하다.
    /*@RequestMapping("/{name : [a-z]+}}")
    public String hello(@PathVariable String name){ //@PathVariable이 정규식에 걸리는 값을 받아준다.
        return "hello " + name;

    }*/

    //*******************************************************

    //consumes : 설정된 Content-Type에 해당하는 요청만 받는다. ex) APPLICATION_JSON_UTF8_VALUE : Json 데이터만 받겠다.
    //produces : 응답을 설정된 Type으로만 Return 시키는 요청만 받는다. ex) TEXT_PLAIN_VALUE : text 타입의 응답을 요청한 것만 받는다.
    /*@RequestMapping(value = "/hello"
            , consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
            , produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String hello2(){ //@PathVariable이 정규식에 걸리는 값을 받아준다.
        return "hello seyoung";

    }*/


    //*******************************************************

    //headers : 설정된 해더 값을 가진 요청만 받는다. ex) headers = HttpHeaders.FROM
    //headers = "!" + Httpheaders.From  : header에 From 을 가지지 않은 요청만 받는다.
    //headers = HttpHeaders.FROM + "=" + "222" : header에 FROM값이 222인 요청만 받는다.

    //params : 설정된 해당 키값을 가진 파라미터가 있는 요청만 받는다.
    //params = "name=spring" : 설정된 name 파라미터의 값이 spring인 요청만 받는다.
    //@RequestMapping(value="/hello", headers = HttpHeaders.FROM, params = "name=seyoung")
    @ResponseBody
    public String hello(){return "hello";}
}
