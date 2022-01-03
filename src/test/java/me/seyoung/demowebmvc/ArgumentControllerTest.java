package me.seyoung.demowebmvc;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
class ArgumentControllerTest {
    @Autowired
    MockMvc mockMvc;


    @Test
    void eventsId() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/events/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1));
    }

    @Test
    void eventsRequestParam() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/events/1")
                        .param("name", "seyoung"))
                .andDo(print())
                .andExpect(jsonPath("name").value("seyoung"));
    }

    @Test
    void getEventsError() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/events/name/seyoung")
                        .param("limit", "-10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("seyoung"));
    }
}