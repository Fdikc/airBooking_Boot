package edu.bzu.fdick.interceptor;

import edu.bzu.fdick.utils.JWTUtil;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @Author FDC
 * @Date 2023/1/19 17:28
 * @Description 登录拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {
    /**
     * @Description 在请求处理之前进行调用（Controller方法调用之前）
     * @Param [request, response, handler]
     * @Return boolean
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!request.getMethod().equals("OPTIONS")) {
            String token = request.getHeader("Authorization");
            System.out.println("token:" + token);
            if (token == null || token.isEmpty()) {
                throw new RuntimeException("请登录");
            }

            if (!JWTUtil.verify(token)) {
                throw new IllegalArgumentException("token 验证失败，请重新登录！");
            }

            //共享token





//        if(!(handler instanceof HandlerMethod)){
//            return true;
//        }
//        HandlerMethod handlerMethod=(HandlerMethod) handler;
//        Method method=handlerMethod.getMethod();
            //检查是否有passToken注释，有则跳过验证
//        if(method.isAnnotationPresent(PassToken.class)) {
//            PassToken passToken = method.getAnnotation(PassToken.class);
//            if (passToken.required()) {
//                return true;
//            }
//
//        }
        }
        return true;
    }

    /**
     * @Description 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     * @Param [request, response, handler, modelAndView]
     * @Return void
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {


    }

    /**

     * @Description 在整个请求结束之后被调用，也就是在DispatcherServlet渲染了对应的视图之后执行（主要是用于进行资源清理工作）
     * @Param [request, response, handler, ex]
     * @Return void
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}

