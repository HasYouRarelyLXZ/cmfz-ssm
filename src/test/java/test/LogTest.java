package test;

import com.baizhi.dao.LogMapper;
import com.baizhi.entity.Log;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
public class LogTest {
    @Autowired
    private LogMapper logMapper;

    @Test
    public void testLog() {
        List<Log> logs = logMapper.selectAllList();
        for (Log log : logs) {
            System.out.println(log);
        }

    }

    @Test
    public void uuid() {
        for (int i = 0; i < 70; i++) {
            String s = UUID.randomUUID().toString().replaceAll("-", "");
            System.out.println(s);

        }
    }

}
