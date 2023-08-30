package com.koopey.api.controller;

import com.koopey.api.ServerApplication;
import com.koopey.api.configuration.WebClientTestConfigurationTest;
import com.koopey.api.configuration.WebSecurityConfiguration;
import com.koopey.api.model.dto.AssetDto;
import com.koopey.api.model.entity.Asset;
import com.koopey.api.model.entity.User;
import com.koopey.api.service.AuthenticationService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

//@AutoConfigureMockMvc
//@WebAppConfiguration
@ContextConfiguration(classes = { WebSecurityConfiguration.class })
@SpringBootTest(classes = ServerApplication.class /* webEnvironment = WebEnvironment.RANDOM_PORT */)
public class AssetControllerTest {

   // @LocalServerPort
  //  int randomServerPort;
    List<User> buyers, sellers;
    AssetDto asset;

    // @MockBean
    // private AuthenticationService contractService;
    // @Mock
    // BCryptPasswordEncoder bcryptEncoder;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        User buyer = User.builder().id(UUID.fromString("00000000-0000-0000-0000-000000000001")).build();
        List<User> buyers = new ArrayList<>();
        buyers.add(buyer);

        User seller = User.builder().id(UUID.fromString("00000000-0000-0000-0000-000000000002")).build();
        List<User> sellers = new ArrayList<>();
        sellers.add(seller);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();

    }


    @Test
    @WithUserDetails(value = "spring")
    public void whenAdminAccessUserEndpoint_thenOk() throws Exception {
          asset = AssetDto.builder().description("description").name("name").buyers(buyers)
                .sellers(sellers).build();

        mockMvc.perform(post("/api/asset/create",asset))
          .andExpect(status().isOk());
    }


   // @Autowired
  //  private TestRestTemplate template;

   /*  @WithUserDetails()
    @Test
    public void testAddAsset() throws URISyntaxException {
        asset = Asset.builder().description("description").name("name").buyers(buyers)
                .sellers(sellers).build();

        // RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:" + randomServerPort + "/api/asset/create";
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-COM-PERSIST", "true");

        HttpEntity<Asset> request = new HttpEntity<>(asset, headers);

        ResponseEntity<String> result = template.withBasicAuth("spring", "secret")
                .postForEntity(uri, request, String.class);

        Assertions.assertEquals(201, result.getStatusCodeValue());
    }*/

}
