package edu.bzu.fdick.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.bzu.fdick.domain.User;
import edu.bzu.fdick.service.UserService;
import edu.bzu.fdick.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author FU
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2023-03-10 17:10:56
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




