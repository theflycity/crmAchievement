package com.example.crmachievement;

import com.example.crmachievement.domain.User;
import com.example.crmachievement.mapper.UserMapper;
import com.example.crmachievement.rest.AuthController;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class AchievementApplicationTests {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AuthController authController;
    @Test
    void testConnect() {
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);
    }

    @Test
    void testLogin() throws BadRequestException {
        /*需要在 UserLoginRequestDTO类上加 @AllArgsConstructor
        LoginRequest user = new LoginRequest("001", "123456");
        UserLoginResponseDTO responseEntity = authController.doLogin(user);
        System.out.println("responseEntity = " + responseEntity);*/
    }

}
