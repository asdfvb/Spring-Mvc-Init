package me.seyoung.demowebmvc;


import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Events {

    interface ValidateLimit {}
    interface ValidateName {}

    private Integer id;
    //@Validated 어노테이션에 그룹을 ValidateName.class로 지정시 체크함.
//    @NotBlank(groups = ValidateName.class)
    @NotBlank
    private String name;

    //데이터 형식을 지정해서 받을수 있다.
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    //@Validated 어노테이션에 그룹을 ValidateLimit.class로 지정시 체크함.
    //최소값을 0으로 지정하여 요청에 의해 바인딩되는 값이 0 이상인지 체크함.
//    @Min(value = 0, groups = ValidateLimit.class)
    @Min(0)
    private Integer limit;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
}
