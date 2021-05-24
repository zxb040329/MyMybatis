package com.lg.sqlSession;

import com.lg.pojo.Configuration;

import java.util.List;

/**
 * @author zxb
 * @date 2021-05-22 15:19
 **/
public class DefaultSqlSession implements SqlSession{

    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    public <E> List<E> selectList(String statementId, Object... params) {
        return null;
    }

    public <T> T selectOne(String statementId, Object... params) {

        return null;
    }

    public <T> T getMapper(Class<?> mapperClass) {
        return null;
    }
}
