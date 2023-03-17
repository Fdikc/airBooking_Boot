package edu.bzu.fdick.controller;


import edu.bzu.fdick.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class Test {


    @GetMapping("/getCity")
    public Result getCity(){
        Map<String, String> map = new HashMap<>();
        map.put("1", "北京");
        map.put("2", "上海");
        map.put("3", "广州");
        map.put("4", "深圳");
        map.put("5", "杭州");
        map.put("6", "南京");
        map.put("7", "成都");
        map.put("8", "武汉");
        map.put("9", "西安");
        map.put("10", "厦门");



//        { value: 'element', link: 'https://github.com/ElemeFE/element' },
//        { value: 'cooking', link: 'https://github.com/ElemeFE/cooking' },
//        { value: 'mint-ui', link: 'https://github.com/ElemeFE/mint-ui' },
//        { value: 'vuex', link: 'https://github.com/vuejs/vuex' },
//        { value: 'vue-router', link: 'https://github.com/vuejs/vue-router' },
//        { value: 'babel', link: 'https://github.com/babel/babel' },
        return Result.success(map);
    }
}
