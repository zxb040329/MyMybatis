package com.lg.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 存放sqlMapConfig.xml解析出来的内容的容器类
 * @author zxb
 * @date 2021-05-22 14:19
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Configuration {

    private DataSource dataSource;

    private Map<String,MapperStatement> mapperStatementMap = new HashMap();

}
