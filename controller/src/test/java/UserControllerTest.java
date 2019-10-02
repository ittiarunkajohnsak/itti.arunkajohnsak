import com.fasterxml.jackson.databind.ObjectMapper;
import com.kc.jbt.controller.UserController;
import com.kc.jbt.service.entity.UserEntity;
import com.kc.jbt.service.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private ObjectMapper _mapper = new ObjectMapper();

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.userController).build();
    }

    @Test
    public void test_success_when_getUser() throws Exception {
        given(userService.findAll()).willReturn(new ArrayList<>());

        String result = this.mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertNotNull(result);
        assertEquals("[]", result);
        verify(userService, times(1)).findAll();
    }


    @Test
    public void test_fail_when_getUser_invalidUrl() throws Exception {
        this.mockMvc.perform(get("/user"))
                .andExpect(status().isMethodNotAllowed())
                .andReturn();
    }

    @Test
    public void test_success_when_createUser() throws Exception {
        UserEntity mockUserEntity = new UserEntity();
        mockUserEntity.setId(1L);
        mockUserEntity.setUsername("itti.test");
        mockUserEntity.setPassword("password");
        mockUserEntity.setFirstName("testFirstName");
        mockUserEntity.setPhone("0876752766");
        mockUserEntity.setSalary("40000");

        given(userService.create(any())).willReturn(mockUserEntity);

        String result = this.mockMvc.perform(post("/user")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(_mapper.writeValueAsString(mockUserEntity)))
                            .andExpect(status().isOk())
                            .andExpect(jsonPath("$.id", is(1)))
                            .andExpect(jsonPath("$.username", is("itti.test")))
                            .andReturn()
                            .getResponse()
                            .getContentAsString();

        assertNotNull(result);
    }
}
