package indi.zhuyst.mysqlimage;

import indi.zhuyst.mysqlimage.dao.ImageDao;
import indi.zhuyst.mysqlimage.pojo.Image;
import indi.zhuyst.mysqlimage.service.ImageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MysqlImageApplicationTests {

	@Autowired
	private ImageDao imageDao;

	@Test
	public void contextLoads() {
	}
}
