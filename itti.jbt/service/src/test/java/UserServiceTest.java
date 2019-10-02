import com.kc.jbt.service.entity.MemberType;
import com.kc.jbt.service.entity.UserEntity;
import com.kc.jbt.service.repository.UserRepository;
import com.kc.jbt.service.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.security.core.parameters.P;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_success_when_createUser() {
        UserEntity entity = new UserEntity();
        entity.setPhone("0871234567");
        entity.setSalary("40000");

        given(userRepository.save(entity)).willReturn(entity);

        UserEntity result = userService.create(entity);

        assertNotNull(result);
        assertNotNull(result.getRefCode());
        assertEquals(MemberType.Gold, result.getMemberType());
    }

    @Test
    public void test_success_when_createUser_phoneIsEmpty(){
        UserEntity entity = new UserEntity();
        entity.setPhone(null);

        given(userRepository.save(entity)).willReturn(entity);

        UserEntity result = userService.create(entity);

        assertNotNull(result);
        assertNotNull(result.getRefCode());
        assertEquals(result.getRefCode(), "");
        assertEquals(MemberType.None, result.getMemberType());
    }

    @Test
    public void test_success_when_findAll() {
        userService.findAll();
        verify(userRepository, times(1)).findAll();
    }
}
