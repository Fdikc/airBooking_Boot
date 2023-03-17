package edu.bzu.fdick.domain;


import edu.bzu.fdick.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserTest {


    @Autowired
    private UserService userService;

    @Test
    public void test(){
        User user = new User();
        user.setUsername("fdick");
//        user.setPhone(123456789);


        boolean save = userService.save(user);


        System.out.println("save = " + save);

    }

}
