package test;

import com.baizhi.dao.LogMapper;
import com.baizhi.entity.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
public class TestLog {
    @Autowired
    private LogMapper logMapper;

    @Test
    public void test1() {
        logMapper.insertSelective(new Log(UUID.randomUUID().toString().replaceAll("-", ""), "zhangsan", new Date(), "执行了插入操作", "成功"));
    }
}
