package test;

import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
public class RedisTest {

    @Test
    public void testRedis() {
        Jedis jedis = new Jedis("192.168.218.129", 6379);
        String code = jedis.get("code");
        System.out.println(code);
        jedis.set("can","哈哈哈");
        jedis.expire("can",30);

        while(true){
            String can1 = jedis.get("can");
            if(can1!=null){
                Long can = jedis.ttl("can");
                System.out.println(can);
            }else {
                break;
            }
        }
    }

}
