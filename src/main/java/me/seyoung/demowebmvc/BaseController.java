package me.seyoung.demowebmvc;

import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Arrays;

//전역 컨트롤러에 적용되도록 해준다.
@ControllerAdvice(assignableTypes = {ArgumentController.class, EventApi.class})
public class BaseController {

    /**
     * 특정 예외가 발생한 요청을 처리하는 핸들러 정의
     *
     * */
    @ExceptionHandler({EventException.class, RuntimeException.class})
    public String eventErrorHandler(RuntimeException e, Model model){
        model.addAttribute("message", "event error");
        return "error";
    }


    //@ModelAttribute는 해당 클래스의 정의된 모든 핸들러의 model에 값을 적용한다.
    @ModelAttribute
    public void categories(Model model){
        model.addAttribute("categories", Arrays.asList("study", "seminar", "bod"));
    }

    //이름을 지정해주고 리턴 값을 주면 이름을 키로 model에 자동으로 등록된다.
    /*@ModelAttribute("categories")
    public List<String> categoriesReturnList(Model model){
        return Arrays.asList("study", "seminar", "bod");
    }*/

    //@InitBinder 필수 요소
    //return = void , 매개변수 = WebDataBinder (두 조건 필수)
    //@InitBinder 의미 : 데이터 바인딩에 대해 컨트롤 가능하다.
    @InitBinder("events") //이름이 지정된 객체에 데이터가 바인딩 될때만 실행됨.단)설정되지 않으면 모든 객체에 적용됨.
    public void initEventBinding(WebDataBinder webDataBinder) {
        //받고 싶지 않은 필드 값을 지정할수 있다.
        webDataBinder.setDisallowedFields("id");
        webDataBinder.addValidators(new EventValidator());
    }

}
