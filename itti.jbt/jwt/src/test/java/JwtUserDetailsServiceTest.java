import com.kc.jbt.jwt.JwtUserDetailsService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class JwtUserDetailsServiceTest {

    @InjectMocks
    private JwtUserDetailsService jwtUserDetailsService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_success_when_foundUsername() {
        UserDetails user = jwtUserDetailsService.loadUserByUsername("itti.test");

        assertNotNull(user);
        assertEquals("itti.test", user.getUsername());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void test_fail_when_userNotFound() {
        jwtUserDetailsService.loadUserByUsername("testUserNotFound");
    }
}
