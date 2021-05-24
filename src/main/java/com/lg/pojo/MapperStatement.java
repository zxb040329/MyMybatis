package com.lg.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zxb
 * @date 2021-05-22 14:23
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MapperStatement {

    private String id;

    private String resultType;

    private String parameterType;

    private String sql;

}
