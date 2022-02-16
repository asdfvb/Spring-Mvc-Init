package me.seyoung.demowebmvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.awt.*;
import java.io.Reader;
import java.io.Writer;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @sessionAttributes
 *
 * - model.addAttribute에 키값이 설정된 ({"event", "text"}) 키값과 일치하면 자동으로 값을 세션에 등록해준다.
 */

@SessionAttributes({"event", "test"})
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
    /*@GetMapping("/events/name/{name}")
    public Event getEventsError(@Validated(Events.ValidateLimit.class) @ModelAttribute Event event, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(c -> {
                System.out.println(c.toString());
            });
        }

        return event;
    }*/



    /*
    *
    * 화면 새로고침을 할경우 form action 요청이 중복으로 발생하는 것을 방지 하기 위한 기법 (Post / Redirect / Get 패턴)
    *
    * 1. Post요청을 보낸다.
    * 2. Return을 Redirect로 뷰를 호출한다.
    * 3. Redirect한 Url을 GetMapping한다.
    *
    * */
    @PostMapping("/eventsName")
    public String createEvent(@Validated @ModelAttribute Events event, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/events/form";
        }

        return "redirect:/events/list";
    }


    /*
     * HttpSession vs @SessionAttribute
     * HttpSession : 객체에서 데이터를 꺼낼때 리턴값이 오브젝트
     * @SessionAttribute : ()에 키를 정해서 원하는 데이터 형으로 바로 형변환 가능
     *
     * */
    @GetMapping("/events/list")
    public String getEvents(Model model, @SessionAttribute("visitTime") LocalDateTime visitTime){

        System.out.println(visitTime);

        //RdirectAttribute.addFlashAttribute로 담긴 Object 형식의 데이터를 model에서 받아서 사용할수 있다.
        Events newEvent = (Events) model.asMap().get("newEvent");

        List<Events> eventList = new ArrayList<>();
        Events events = new Events();
        events.setName("aaa");
        events.setLimit(10);
        eventList.add(events);
        eventList.add(newEvent);
        model.addAttribute("eventList" , eventList);
        return "/events/list";
    }



    //******************* 멀티폼 데이터 입력 받기 **********************//
    @GetMapping("/events/form/name")
    public String eventFormName(Model model) {
        model.addAttribute("event", new Events());

        return "events/form-name";
    }

    @GetMapping("/events/form/limit")
    public String eventFormLimit(@ModelAttribute Events events,  Model model) {
        model.addAttribute("event", events);

        return "events/form-limit";
    }

    @PostMapping("/events/form/name")
    public String eventsFormNameSubmit(@Validated @ModelAttribute Events event, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/events/form-name";
        }

        return "redirect:/events/form/limit";
    }



    /*
    * Redirect시에 model에 담긴 원시타입 데이터는 URL에 파라미터 값으로 같이 전송된다.
    * 단) 특정값만 URL에 파라미터 값으로 넘기고 싶을 경우 RedirectAttributes를 사용하면된다.
    *
    * */
    @PostMapping("/events/form/limit")
    public String eventsFormLimitSubmit(@Validated @ModelAttribute Events event, BindingResult bindingResult,
                                        SessionStatus sessionStatus, Model model,
                                        RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            return "/events/form-limit";
        }

        //작업이 완료된 경우 세션에서 모델 객체 제거하기.
        sessionStatus.setComplete();
        attributes.addAttribute("name", event.getName());
        attributes.addAttribute("limit", event.getLimit());
        //session객체에 일회성으로 담긴다.  단) 리다이렉트된 URL(/event/list)에서 사용이 완료되면 session객체에서 자동으로 사라진다.
        attributes.addFlashAttribute("newEvent", event);
        return "redirect:/events/list";
    }

}
