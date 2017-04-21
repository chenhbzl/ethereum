package com.blackbrother;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

@EnableAutoConfiguration
@ImportResource(locations={"classpath:mybatis-config.xml"})
public class BIApplication extends SpringBootServletInitializer  {
	
	//implements EmbeddedServletContainerCustomizer
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BIApplication.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(BIApplication.class, args);
        
        Logger logger = LoggerFactory.getLogger(BIApplication.class);
        
        logger.info("============================服务器启动=====================================");
    }

//	@Override
//	public void customize(ConfigurableEmbeddedServletContainer container) {
//		container.setSessionTimeout(1, TimeUnit.MINUTES);
//		 Logger logger = LoggerFactory.getLogger(BIApplication.class);
//		logger.info("============================配置session=====================================");
//	}
	
//	@Bean
//	public EmbeddedServletContainerCustomizer containerCustomizer(){
//	       return new EmbeddedServletContainerCustomizer() {
//	           @Override
//	           public void customize(ConfigurableEmbeddedServletContainer container) {
//	                container.setSessionTimeout(60);//单位为S 
//	                Logger logger = LoggerFactory.getLogger(BIApplication.class);
//	                
//	                logger.info("============================配置session=====================================");
//	          }
//	    };
//	}
}
