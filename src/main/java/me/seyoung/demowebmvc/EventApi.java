package me.seyoung.demowebmvc;

import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
public class EventApi {

    /*
    * RequestBody vs HttpEntity 차이점
    *
    * RequestBody : body에만 접근 할수 있다.
    *
    * HttpEntity : body + header까지 접근 가능하다.
    *
    * */

    @PostMapping
    @ResponseBody //해당 핸들러에서 리턴하는 값을 http 응답 본문에 담아준다.(httpMessageConvter를 사용)
    public Events apiEvent(HttpEntity<Events> request){

        MediaType contentType
                = request.getHeaders().getContentType();

        System.out.println(contentType);

        //HttpEntity에 지정된 GeneralType(Events)의 본문을 리턴한다.
        return request.getBody();
    }

    @PostMapping
    //ResponseEntity : 응답 헤더 코드 본문을 직접 다루고 싶은 경우 사용한다.
    public ResponseEntity<Events> apiEvent2(HttpEntity<Events> request){

        MediaType contentType
                = request.getHeaders().getContentType();

        System.out.println(contentType);

        return ResponseEntity.ok(request.getBody());
    }
}
