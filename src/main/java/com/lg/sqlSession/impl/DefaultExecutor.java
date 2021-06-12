package com.lg.sqlSession.impl;

import com.lg.config.BoundSql;
import com.lg.pojo.Configuration;
import com.lg.pojo.MapperStatement;
import com.lg.sqlSession.Executor;
import com.lg.utils.GenericTokenParser;
import com.lg.utils.ParameterMapping;
import com.lg.utils.ParameterMappingTokenHandler;
import com.lg.utils.TokenHandler;
import lombok.SneakyThrows;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zxb
 * @date 2021-05-22 15:20
 **/
public class DefaultExecutor implements Executor {

    @SneakyThrows
    @Override
    public <E> List<E> query(Configuration configuration, MapperStatement mapperStatement, Object... params) throws SQLException, ClassNotFoundException {
        //1.获得连接
        final Connection connection = configuration.getDataSource().getConnection();

        //2.获取sql select * from t_user where id = #{id} and username = #{username}
        //转换sql语句：select * from t_user where id = ？ and username = ？  ，转换过程中，还需要对#{}里面的值进行解析存储
        String sql = mapperStatement.getSql();
        BoundSql boundSql = getBoundSql(sql);

        //3.执行sql
        final PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());

        //4.设置参数
        //获取到参数的全路径
        final String parameterType = mapperStatement.getParameterType();
        Class<?> parameterTypeClass = getClassType(parameterType);
        final List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
        for (int i = 0; i < parameterMappingList.size(); i++) {
            final ParameterMapping parameterMapping = parameterMappingList.get(i);
            final String content = parameterMapping.getContent();
            //使用反射
            final Field declaredField = parameterTypeClass.getDeclaredField(content);
            //设置下暴力访问
            declaredField.setAccessible(true);
            Object o = declaredField.get(params[0]);
            preparedStatement.setObject(i+1,o);

        }
        //5.执行sql
        final ResultSet resultSet = preparedStatement.executeQuery();

        final String resultType = mapperStatement.getResultType();
        final Class<?> resultTypeClass = getClassType(resultType);

        ArrayList<Object> objects = new ArrayList<>();

        //6.封装返回结果
        while (resultSet.next()){
            //元数据
            final ResultSetMetaData metaData = resultSet.getMetaData();
            final Object o = resultTypeClass.newInstance();
            //查询结果的总列数
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                //字段名
//                final String catalogName = metaData.getCatalogName(i);
                final String columnName = metaData.getColumnName(i);
                //字段值
                final Object value = resultSet.getObject(columnName);
                //使用反射或者内省，根据数据库表和实体的对应关系，完成封装
                final PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName,resultTypeClass);
                final Method writeMethod = propertyDescriptor.getWriteMethod();
                writeMethod.invoke(o,value);
            }
            objects.add(o);
        }

        return (List<E>) objects;
    }

    /**
     * 根据某个类的全路径来获取它的Class对象
     *
     * @param parameterType
     * @return
     */
    private Class<?> getClassType(String parameterType) throws ClassNotFoundException {
        if (parameterType != null) {
            final Class<?> aClass = Class.forName(parameterType);
            return aClass;
        }
        return null;
    }

    /**
     * 完成对#{}的解析工作：1.将#{}使用？进行代替，2.解析出#{}里面的值进行存储
     *
     * @param sql
     * @return
     */
    private BoundSql getBoundSql(String sql) {
        //标记处理类：配置标记解析器来完成对占位符的解析处理工作
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        final GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
        //解析出来的sql
        final String parse = genericTokenParser.parse(sql);
        //从#{}解析出来的参数名称
        final List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();
        final BoundSql boundSql = new BoundSql();
        boundSql.setSqlText(parse);
        boundSql.setParameterMappingList(parameterMappings);

        return boundSql;
    }
}
