package edu.bzu.fdick.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.bzu.fdick.domain.Flight;
import edu.bzu.fdick.service.FlightService;
import edu.bzu.fdick.mapper.FlightMapper;
import org.springframework.stereotype.Service;

/**
* @author FU
* @description 针对表【flight】的数据库操作Service实现
* @createDate 2023-03-09 15:21:24
*/
@Service
public class FlightServiceImpl extends ServiceImpl<FlightMapper, Flight>
    implements FlightService{

}




