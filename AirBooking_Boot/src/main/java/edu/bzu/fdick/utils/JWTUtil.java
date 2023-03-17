package edu.bzu.fdick.utils;


import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;

public class JWTUtil {

    private static final String KEY = "fdick_airbooking";

    /*
     * @Author FDC
     * @Date 2023/1/7 19:05
     * @Description 生成token
     * @Param [user]
     * @Return token
//     */
    public static String getToken(Object user) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        String token = JWT.create()
                .withClaim("userInfo", JSON.toJSONString(user))
                .withExpiresAt(calendar.getTime())
                .sign(Algorithm.HMAC256(KEY));
        return token;
    }

    /*
     * @Author FDC
     * @Date 2023/1/7 19:05
     * @Description 验证token
     * @Param [token]
     * @Return boolean
     */
    public static boolean verify(String token) {
        try {
            DecodedJWT verify = JWT.require(Algorithm.HMAC256(KEY)).build().verify(token);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /*
     * @Author FDC
     * @Date 2023/1/7 19:05
     * @Description 获取token中的信息
     * @Param [token]
     * @Return java.lang.String
     */
    public static String getUserInfo(String token) {
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(KEY)).build().verify(token);
        return verify.getClaim("userInfo").asString();

        //
//        JSON.parseObject(verify.getClaim("userInfo").asString(), User.class);
    }
    public static <T> T getUserInfo(String token,Class<T> clazz) {
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(KEY)).build().verify(token);
        return JSON.parseObject(verify.getClaim("userInfo").asString(), clazz);
    }
}