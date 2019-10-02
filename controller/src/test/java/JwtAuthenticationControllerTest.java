import com.fasterxml.jackson.databind.ObjectMapper;
import com.kc.jbt.controller.JwtAuthenticationController;
import com.kc.jbt.jwt.JwtRequest;
import com.kc.jbt.jwt.JwtTokenUtil;
import com.kc.jbt.jwt.JwtUserDetailsService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class JwtAuthenticationControllerTest {

    @InjectMocks
    private JwtAuthenticationController jwtAuthenticationController;

    @Mock
    private JwtUserDetailsService jwtUserDetailsService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    private ObjectMapper _mapper = new ObjectMapper();

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.jwtAuthenticationController).build();
    }

    @Test
    public void test_success_when_authenticate() throws Exception {
        given(authenticationManager.authenticate(anyObject())).willReturn(null);
        given(jwtUserDetailsService.loadUserByUsername("itti.test")).willReturn(mock(User.class));
        given(jwtTokenUtil.generateToken(anyObject())).willReturn("token123");

        String result = this.mockMvc.perform(post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(_mapper.writeValueAsString(new JwtRequest())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jwtToken", is("token123")))
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertNotNull(result);
        assertEquals("{\"jwtToken\":\"token123\"}", result);
    }

    @Test
    public void test_fail_when_authenticate_exception() throws Exception {
        given(authenticationManager.authenticate(anyObject())).willThrow(Exception.class);

        String result = this.mockMvc.perform(post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(_mapper.writeValueAsString(new JwtRequest())))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertNotNull(result);
        assertEquals("{\"jwtToken\":null}", result);
    }

    @Test
    public void test_fail_when_authenticate_userNotFound() throws Exception {
        given(authenticationManager.authenticate(anyObject())).willReturn(null);
        given(jwtUserDetailsService.loadUserByUsername("testUserNotFound")).willThrow(UsernameNotFoundException.class);

        JwtRequest request = new JwtRequest();
        request.setUsername("testUserNotFound");
        request.setPassword("testPassword");

        String result = this.mockMvc.perform(post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(_mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertNotNull(result);
        assertEquals("{\"jwtToken\":null}", result);
    }
}
