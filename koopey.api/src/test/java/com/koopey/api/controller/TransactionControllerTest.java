package com.koopey.api.controller;

import com.koopey.api.ServerApplication;
import com.koopey.api.configuration.WebSecurityConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ContextConfiguration(classes = { WebSecurityConfiguration.class })
@SpringBootTest(classes = ServerApplication.class)
public class TransactionControllerTest {

        private MockMvc mockMvc;

        @Autowired
        private WebApplicationContext webApplicationContext;

        @BeforeEach
        public void setup() {

                mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                                .apply(springSecurity())
                                .build();
        }

        @Test
        @WithUserDetails(value = "test")
        public void whenUserCreateTransaction_thenOk() throws Exception {

                mockMvc.perform(post("/transaction/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                                .content("""
                                        {
                                        "name":"test",
                                        "buyerId":"00000000-0000-0000-0000-000000000001",
                                        "sellerId":"00000000-0000-0000-0000-000000000002",
                                        "grade":5
                                        }
                                        """))
                                .andExpect(status().isCreated());
        }

        @Test
        @WithUserDetails(value = "test")
        public void whenUserReadTransaction_thenOk() throws Exception {

                mockMvc.perform(get("/transaction/read/00000000-0000-0000-0000-000000000001")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8"))
                                .andExpect(status().isOk());
        }
}
