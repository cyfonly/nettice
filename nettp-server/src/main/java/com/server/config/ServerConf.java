package com.server.config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;

/**
 * 系统配置文件接口类，使用 OWNER api
 * @author yunfeng.cheng
 * @create 2016-07-24
 */
@Sources("classpath:ServerConf.properties")
public interface ServerConf extends Config{
	
	@DefaultValue("dev")
	String env();
	
	@Key("server.${env}.port")
	@DefaultValue("8080")
	Integer port();
	
	@Key("server.${env}.filedir")
	@DefaultValue("D:\\")
	String filedir();
	
	@Key("server.${env}.router.config.path")
	@DefaultValue("router.xml")
	String routerConfigPath();
	
}
