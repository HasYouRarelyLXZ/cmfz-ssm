package test;

import com.baizhi.entity.Section;
import com.baizhi.dao.AlbumDao;
import com.baizhi.dao.SectionDao;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.UUID;

public class MenuTest {
    @Test
    public void testAlbumDao() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        SectionDao sectionDao = (SectionDao) context.getBean("section");

        UUID uuid = UUID.randomUUID();
        String s = uuid.toString();
        String s1 = s.replaceAll("-", "");
        /*sectionDao.add(new Section(s1,"#","#","#",new Date(),"#","#","#"));*/

    }
}
