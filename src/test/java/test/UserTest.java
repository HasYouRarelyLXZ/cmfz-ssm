package test;

import com.baizhi.dao.SectionDao;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
public class UserTest {
    @Autowired
    private UserService userService;

    @Test
    public void testUserDao() {
        int userNum = userService.getUserNum();
        List<User> users = userService.showUserAll(1, userNum);
        for (User user : users) {
            System.out.println(user);
        }

    }

    @Test
    public void uuid() {
        for (int i = 0; i < 70; i++) {
            String s = UUID.randomUUID().toString().replaceAll("-", "");
            System.out.println(s);

        }
    }

    @Test
    public void phone() {
        //手动生成一个短信验证码
        Random random = new Random();
        String codeValue = "";
        for (int i = 0; i < 6; i++) {
            int a = random.nextInt(10);  //随即生成0-9的数
            codeValue += String.valueOf(a);
        }
        System.out.println(codeValue);
    }

}
