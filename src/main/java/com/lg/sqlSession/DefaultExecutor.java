package com.lg.sqlSession;

import com.lg.pojo.Configuration;
import com.lg.pojo.MapperStatement;

import java.util.List;

/**
 * @author zxb
 * @date 2021-05-22 15:20
 **/
public class DefaultExecutor implements Executor{

    public <E> List<E> query(Configuration configuration, MapperStatement mapperStatement, Object... params) {
        return null;
    }
}
