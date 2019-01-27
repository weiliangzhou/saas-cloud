package com.wegoo.saasdao.config;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;

/**
 * ${DESCRIPTION}
 * author 二师兄超级帅
 * create 2018-06-27 10:44
 **/
@Configuration
@AutoConfigureAfter(DataSourceConfig.class)
@MapperScan("com.wegoo.saasdao.mapper")
public class MybatisMapperScannerConfig {
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("com.wegoo.saasdao.mapper");
        return mapperScannerConfigurer;
    }
}
