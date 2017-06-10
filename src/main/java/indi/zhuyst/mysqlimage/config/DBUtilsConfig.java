package indi.zhuyst.mysqlimage.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zhuyst on 2017/6/8.
 * DBUtils的Bean配置
 */
@Configuration
public class DBUtilsConfig {
    @Autowired
    private BasicDataSource dataSource;

    //将QueryRunner注入Bean
    @Bean
    public QueryRunner myQueryRunner(){
        return new QueryRunner(dataSource);
    }
}
