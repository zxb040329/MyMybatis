package com.lg.config;

import com.lg.utils.ParameterMapping;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zxb
 * @date 2021-05-25 7:03
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoundSql {

    private String sqlText; //解析过后的sql

    private List<ParameterMapping> parameterMappingList;

}
