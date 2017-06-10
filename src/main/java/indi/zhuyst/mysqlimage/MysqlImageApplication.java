package indi.zhuyst.mysqlimage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class MysqlImageApplication {

	public static void main(String[] args) {
		SpringApplication.run(MysqlImageApplication.class, args);
	}
}
