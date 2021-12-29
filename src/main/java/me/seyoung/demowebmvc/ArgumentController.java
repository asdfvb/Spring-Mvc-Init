package me.seyoung.demowebmvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import java.io.Reader;
import java.io.Writer;

@Controller
public class ArgumentController {

    @GetMapping("/events")
    @ResponseBody
    public String events(
            /*
             *
             * 핸드러 메소드에 아규먼트
             *
             * WebRequest, NativeWebRequest ServletRequest(Response), HttpServletRequest - 요청 또는 응답 자체에 접근 가능
             *
             * InputStream, Reader, outputStream, Writer - 요청 본문을 읽어오거나, 응답 본문을 쓸 때 사용 가능
             *
             * pushBuilder - 스프링5, HTTP/2 리소스 푸쉬에 사용
             *             - 서버 요청후 viewResolver가 화면 호출하고 나서 화면이 로드 요청을 보내기전에 리소스를 내보내준다.
             *
             * HttpMethod  - GET, POST ...등에 대한 정보를 가질 수 있다.
             *
             * Locale, TimeZone, ZoneID - LocaleReolver가 분석한 요청의 Locale 정보
             *
             * */
    ) {
        return "events";
    }

    @GetMapping("/events/{id}")
    @ResponseBody
    //required = false : 해당 설정이 있으면 Integer id에 값이 할당되지 않아도 된다. (default : true)
    //name = "id" : url에서 넘어오는 변수 명과 name 값이 같지 않을떄 지정하여 맞춰줄수 있다. 단) 같으면 해당 설정은 안해줘도 매핑된다.
    public Events eventsId(@PathVariable(required = false, name = "id") Integer varId) {
        Events event = new Events();
        event.setId(varId);


        return event;
    }

}
