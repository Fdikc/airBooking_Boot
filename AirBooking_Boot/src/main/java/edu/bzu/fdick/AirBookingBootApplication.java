package edu.bzu.fdick;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("edu.bzu.fdick.mapper")
public class AirBookingBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(AirBookingBootApplication.class, args);
    }

}
