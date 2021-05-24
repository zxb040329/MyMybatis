package com.lg.io;

import java.io.InputStream;

/**
 * @author zxb
 * @date 2021-05-22 14:16
 **/
public class Resource {

    public static InputStream resourceAsStream(String path){
        return Resource.class.getClassLoader().getResourceAsStream(path);
    }
}
