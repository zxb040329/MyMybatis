package com.lg.sqlSession;

import java.util.List;

/**
 * @author zxb
 * @date 2021-05-22 15:09
 **/
public interface SqlSession {
    /**
     * 查询所有
     * @param statementId
     * @param params
     * @param <E>
     * @return
     */
    <E> List<E> selectList(String statementId,Object...params);

    /**
     * 查询单个
     * @param statementId
     * @param params
     * @param <T>
     * @return
     */
    <T> T selectOne(String statementId,Object...params);

    /**
     * 为dao接口生成代理实现类
     * @param mapperClass
     * @param <T>
     * @return
     */
    <T> T getMapper(Class<?> mapperClass);



}
