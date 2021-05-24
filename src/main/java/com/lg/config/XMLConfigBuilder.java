package com.lg.config;

import com.lg.io.Resource;
import com.lg.pojo.Configuration;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.xml.parsers.SAXParser;
import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * @author zxb
 * @date 2021-05-22 15:26
 **/
public class XMLConfigBuilder {

    private Configuration configuration;
    public XMLConfigBuilder(){
        this.configuration = new Configuration();
    }

    public  Configuration parseConfig(InputStream in) throws DocumentException, PropertyVetoException {
        final SAXReader saxReader = new SAXReader();
        final Document document = saxReader.read(in);
        final Element rootElement = document.getRootElement();

        final List<Element> list = rootElement.selectNodes("//property");
        final Properties properties = new Properties();
        for (Element element : list) {
            final String name = element.attributeValue("name");
            final String value = element.attributeValue("value");
            properties.put(name,value);
        }

        //1.给configuration的属性dataSource赋值
        final ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass(properties.getProperty("driver"));
        comboPooledDataSource.setJdbcUrl(properties.getProperty("url"));
        comboPooledDataSource.setUser(properties.getProperty("username"));
        comboPooledDataSource.setPassword(properties.getProperty("password"));
        configuration.setDataSource(comboPooledDataSource);

        //2.给configuration的属性mapperStatementMap赋值
        final List<Element> mapperList = rootElement.selectNodes("//mapper");
        for (Element element : mapperList) {
            final String resources = element.attributeValue("resources");
            final XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(configuration);
            final InputStream inputStream = Resource.resourceAsStream(resources);
            xmlMapperBuilder.parseMapper(inputStream);
        }

        return configuration;
    }
}
