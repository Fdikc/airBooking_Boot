package edu.bzu.fdick.service.impl;

import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wf.captcha.ArithmeticCaptcha;
import edu.bzu.fdick.domain.SysUser;
import edu.bzu.fdick.mapper.SysUserMapper;
import edu.bzu.fdick.result.Result;
import edu.bzu.fdick.service.LoginService;
import edu.bzu.fdick.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Service
public class LoginServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
    implements  LoginService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result login(String name, String password, String yzm, String key,HttpServletResponse response) {

//        System.out.println(name+" "+password+" "+yzm+" "+key);
        //从redis中取出验证码
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String code = ops.get("code:" + key);
        if (code == null) {
            return Result.error("验证码已过期");
        }
        //判断验证码是否正确
        if (code.equals(yzm)) {
            //验证码正确
            //判断用户名和密码是否正确
            SysUser sysUser = new SysUser();
            sysUser.setUsername(name);
            String digestHex = new MD5().digestHex(password);
            sysUser.setPassword(digestHex);
            SysUser one = this.getOne(new LambdaQueryWrapper<>(sysUser)
                    .eq(SysUser::getUsername, name)
                    .eq(SysUser::getPassword, digestHex));
            if (one != null) {
                //用户名和密码正确
                //生成token
                one.setPassword(null);
                String token = JWTUtil.getToken(one);
                //将token存入redis
                ops.set("token:"+one.getId(),token,30, TimeUnit.MINUTES);
                response.setHeader("authorization",token);
                return Result.success(one);
            } else {
                //用户名或密码错误
                return Result.error("用户名或密码错误");
            }
        } else {
            //验证码错误
            return Result.error("验证码错误");
        }

    }

    @Override
    public void getCode(HttpServletResponse response) throws IOException {
        ServletOutputStream outputStream = response.getOutputStream();
        //算术验证码 数字加减乘除. 建议2位运算就行:captcha.setLen(2);
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(120, 40);
        // 中文验证码
//        ChineseCaptcha captcha = new ChineseCaptcha(120, 40);
        // 英文与数字验证码
//        SpecCaptcha captcha = new SpecCaptcha(120, 40);
        //英文与数字动态验证码
//        GifCaptcha captcha = new GifCaptcha(120, 40);
        // 中文动态验证码
//        ChineseGifCaptcha captcha = new ChineseGifCaptcha(120, 40);
        // 几位数运算，默认是两位
        captcha.setLen(2);
        // 获取运算的结果
        String result = captcha.text();
        String key = UUID.randomUUID().toString();
        response.setHeader("key",key);
        // 禁止图像缓存
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

//        System.out.println(result);
        //redis 存key,result 两分钟过期
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        //存在code文件下
        ops.set("code:"+key, result, 2, TimeUnit.MINUTES);
//        System.out.println(ops.get("code:"+key));
        captcha.out(outputStream);
    }
}
