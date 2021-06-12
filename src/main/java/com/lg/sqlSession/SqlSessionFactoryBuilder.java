package com.lg.sqlSession;

import com.lg.config.XMLConfigBuilder;
import com.lg.pojo.Configuration;
import com.lg.sqlSession.impl.DefaultSqlSessionFactory;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;

/**
 * @author zxb
 * @date 2021-05-22 15:07
 **/
public class SqlSessionFactoryBuilder {

    public static SqlSessionFactory build(InputStream inputStream) throws PropertyVetoException, DocumentException {
        //1.使用dom4j解析配置文件，将解析出来的内容封装到容器对象中
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
        Configuration configuration = xmlConfigBuilder.parseConfig(inputStream);

        //2.创建SqlSessionFactory对象
        DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);
        return sqlSessionFactory;
    }
}
