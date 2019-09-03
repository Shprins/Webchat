package com.socket.demo;

import com.socket.demo.Service.Convetutil;
import com.socket.demo.po.Constant;
import com.socket.demo.po.Dbconstant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
        Constant.setUrl("101.37.172.227:8005");
        Dbconstant.setDbrecord(false);
        Dbconstant.setUsername("root");
        Dbconstant.setPassword("1234567");
        Dbconstant.setDriverClassName("com.mysql.jdbc.Driver");
        Dbconstant.setUrl("jdbc:mysql://localhost:3306/practicee?characterEncoding=UTF8&useUnicode=true&useSSL=false");
        Dbconstant.setUrlBean("com.socket.demo.po.");
        Convetutil.connectdata();
    }

}
