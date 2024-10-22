package com.coderdream.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.coderdream.AppApplication;
import javax.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(classes = AppApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class YourControllerTest     {

    @Resource
    private MockMvc mockMvc;

    @Test
    public void testYourController() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/get_header")
            .header("token", "abc")
            .param("id", "123"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.jsonPath("accessToken").value("abc"))
            .andReturn();
    }
}
