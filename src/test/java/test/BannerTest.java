package test;

import com.baizhi.dao.BannerDao;
import com.baizhi.service.BannerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
public class BannerTest {
    @Autowired(required = true)
    private BannerDao bannerDao;

    @Test
    public void testBanner() {
        int i = bannerDao.queryBannerNum();
        System.out.println(i);
    }
}
