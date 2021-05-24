package com.lg.sqlSession;

import com.lg.pojo.Configuration;
import com.lg.pojo.MapperStatement;

import java.util.List;

/**
 * @author zxb
 * @date 2021-05-22 15:16
 **/
public interface Executor{

    <E> List<E> query(Configuration configuration, MapperStatement mapperStatement,Object...params);

}
