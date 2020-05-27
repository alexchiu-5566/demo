package com.alicloud.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.teasoft.honey.osql.serviceimpl.ObjSQLRichServiceImpl;
import org.teasoft.honey.osql.serviceimpl.ObjSQLServiceImpl;

@Configuration
public class BeeConfig {

    @Bean
    ObjSQLServiceImpl objSQLService(){
        return new ObjSQLServiceImpl();
    }

    @Bean
    ObjSQLRichServiceImpl objSQLRichService(){
        return new ObjSQLRichServiceImpl();
    }
}
