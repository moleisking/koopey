package com.koopey.api.controller;

/*import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;*/
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
/*import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.koopey.api.controller.UserController;*/

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class UserControllerTests {

  /*  private MockMvc mockMvc;

    @BeforeAll
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new UserController()).build();
    }

    @Test
    public void testHomePage() throws Exception {
        this.mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(content().string("This is Home page"));
    }

    @Test
    public void testHelloPage() throws Exception {
        this.mockMvc.perform(get("/users/ping")).andExpect(status().isOk()).andExpect(content().string("Hello world!"));
    }*/

    /*@Rule
	public RestDocumentation restDocumentation = new RestDocumentation("target/generated-snippets");

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.standaloneSetup(new TransferObjectController())
				.apply(documentationConfiguration(this.restDocumentation)).build();
	}

	@Test
	public void testAddTransferObject() throws Exception {
		this.mockMvc.perform(post("/transferObjects/{name}", "hi")).andExpect(status().isOk())
				.andDo(document("transferObject", pathParameters(
						parameterWithName("name").description("The name of the new Transfer Object to be created."))));
	}*/
}