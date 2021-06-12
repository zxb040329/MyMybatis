package com.lg.sqlSession.impl;

import com.lg.pojo.Configuration;
import com.lg.sqlSession.SqlSession;
import com.lg.sqlSession.SqlSessionFactory;
import com.lg.sqlSession.impl.DefaultSqlSession;

/**
 * @author zxb
 * @date 2021-05-22 15:18
 **/
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private Configuration configuration;
    public DefaultSqlSessionFactory(Configuration configuration){
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }


}
