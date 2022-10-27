package com.webservice.webservice;

import com.webservice.webservice.mybatis.dbo.AccountDBO;
import org.apache.ibatis.type.MappedTypes;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MappedTypes(AccountDBO.class)
@MapperScan("com/webservice/webservice/mybatis/mapper")
@SpringBootApplication
public class WebserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebserviceApplication.class, args);
	}

}
