package com.lg.sqlSession.impl;

import com.lg.pojo.Configuration;
import com.lg.sqlSession.SqlSession;
import com.lg.sqlSession.impl.DefaultExecutor;

import java.lang.reflect.*;
import java.sql.SQLException;
import java.util.List;

/**
 * @author zxb
 * @date 2021-05-22 15:19
 **/
public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> selectList(String statementId, Object... params) throws SQLException, ClassNotFoundException {

        final DefaultExecutor defaultExecutor = new DefaultExecutor();
        final List<Object> query = defaultExecutor.query(configuration, configuration.getMapperStatementMap().get(statementId), params);
        return (List<E>) query;
    }

    @Override
    public <T> T selectOne(String statementId, Object... params) throws SQLException, ClassNotFoundException {
        final List<Object> objects = selectList(statementId, params);
        if(objects.size() > 1){
            throw new RuntimeException("返回结果过多");
        }
        if(objects.size() == 1){
            return (T) objects.get(0);
        }
        return null;
    }



    @Override
    public <T> T getMapper(Class<?> mapperClass) {
        final Object o = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws SQLException, ClassNotFoundException {
                final String methodName = method.getName();
                final String className = method.getDeclaringClass().getName();
                final StringBuilder sb = new StringBuilder();
                sb.append(className).append(".").append(methodName);
                String statementId = sb.toString();

                //准备参数2：params：args
                //获取被调用方法的返回值类型
                final Type genericReturnType = method.getGenericReturnType();
                if(genericReturnType instanceof ParameterizedType){
                    return selectList(statementId,args);
                }

                return selectOne(statementId,args);
            }
        });


        return (T) o;
    }
}
