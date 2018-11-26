package com.nndims.disaster.product.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
@Configuration
@ConfigurationProperties
@PropertySource(value= {"classpath:config.properties"},ignoreResourceNotFound=true,encoding="UTF-8",name="config.properties")
//当文件不存在时候不会报错
public class Config {
    private String excelPath;
    private String dataType;

    public String getExcelPath() {
        return excelPath;
    }

    public void setExcelPath(String excelPath) {
        this.excelPath = excelPath;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
}
