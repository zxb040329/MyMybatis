package com.lg.config;

import com.lg.pojo.Configuration;
import com.lg.pojo.MapperStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 * @author zxb
 * @date 2021-05-22 16:36
 **/
public class XMLMapperBuilder {

    private Configuration configuration;

    public XMLMapperBuilder(Configuration configuration){
        this.configuration = configuration;
    }

    public void parseMapper(InputStream in) throws DocumentException {
        final SAXReader saxReader = new SAXReader();
        final Document document = saxReader.read(in);
        final Element rootElement = document.getRootElement();
        final String namespace = rootElement.attributeValue("namespace");
        final List<Element> elements = rootElement.selectNodes("//select");
        for (Element element : elements) {
            final String id = element.attributeValue("id");
            final String resultType = element.attributeValue("resultType");
            final String parameterType = element.attributeValue("parameterType");
            final String sql = element.getTextTrim();
            final MapperStatement statement = new MapperStatement();
            statement.setId(id);
            statement.setResultType(resultType);
            statement.setParameterType(parameterType);
            statement.setSql(sql);

            configuration.getMapperStatementMap().put(namespace+"."+id,statement);
        }
    }
}
