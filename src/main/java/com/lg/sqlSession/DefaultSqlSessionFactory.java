package com.lg.sqlSession;

import com.lg.pojo.Configuration;

/**
 * @author zxb
 * @date 2021-05-22 15:18
 **/
public class DefaultSqlSessionFactory implements SqlSessionFactory{

    private Configuration configuration;
    public DefaultSqlSessionFactory(Configuration configuration){
        this.configuration = configuration;
    }

    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }


}
