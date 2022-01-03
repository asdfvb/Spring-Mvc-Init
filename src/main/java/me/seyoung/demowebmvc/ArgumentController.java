package me.seyoung.demowebmvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.awt.*;
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


    /*
    * - 생략 가능하나 헷갈림 방지를 위해 써주는게 좋을것 같다.
    * @RequestParam : get방식의 url이나 post 본문 body에 들어오는 요청 매개변수를 매핑하영 valuer값을 가져올수 있다.
    * - required : false - 필수x true - 필수 값  (default : true)
    * - defaultvalue : 값이 안들어 왔을때 기본값을 설정하여 사용 가능하다.
    *
    * */
    @PostMapping("/events")
    @ResponseBody
    public Events eventsRequestParam(@RequestParam(required = false, defaultValue = "seyoung") String name,
                                     @RequestParam(defaultValue = "1") Integer limit) {
        Events event = new Events();
        event.setLimit(limit);
        event.setName(name);
        return event;
    }

    @GetMapping("/events/form")
    public String eventForm(Model model) {
        Events events = new Events();
        events.setLimit(50);

        model.addAttribute("event", events);

        return "events/form";
    }

    /*
    * @ModelAttribute
    * - 여러 곳에(URI패스, 요청 매개변수, 세션 등등) 있는 단순 타입 데이터를 복합 타입 객체로 받아온다.
    * - 생략 가능
    *
    * 값을 바인딩 할 수 없어서 에러가 발생한 경우, BindingResult 타입을 이용하여 에러를 직접 다룰수 있다.
    * (에러 내용이 BindingResult에 들어감)
    *
    * @Validated
    *  - validation Group이라는 힌트를 사용하여 그룹별로 체크할수 있다. (Events.class 안에 어노테이션 참고)
    *
    * @Valid는 그룹을 지정할 방법이 없다.
    * */
    @GetMapping("/events/name/{name}")
    public Event getEventsError(@Validated(Events.ValidateLimit.class) @ModelAttribute Event event, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(c -> {
                System.out.println(c.toString());
            });
        }

        return event;
    }
}
