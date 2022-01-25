package me.seyoung.demowebmvc;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class EventValidator implements Validator {
    @Override
    //어떠한 도메인 클래스에 대한 벨리데이션을 지원하는지 판단하는 메소드
    public boolean supports(Class<?> clazz) {
        return Events.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        //벨리데이션을 진행할 객체가 target에 들어옴
        Events event = (Events)target;

        if (event.getName().equalsIgnoreCase("aaa")) {
            errors.rejectValue("name","wrongValue", "the value is not allowed");
        }
    }
}
