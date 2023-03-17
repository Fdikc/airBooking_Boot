package edu.bzu.fdick.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;


@Component
public class MyHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {

         this.setFieldValByName("created", new Date(), metaObject);
         this.setFieldValByName("updated",new Date(),metaObject);
//         this.setFieldValByName("orderDate",new Date(),metaObject);


        Object password = getFieldValByName("password", metaObject);
        if (password == null) {
            setFieldValByName("password", "123", metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updated",new Date(),metaObject);
    }
}
