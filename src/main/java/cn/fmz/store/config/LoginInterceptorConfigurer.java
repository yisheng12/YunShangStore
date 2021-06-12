package cn.fmz.store.config;

import java.util.ArrayList;
import java.util.List;

import cn.fmz.store.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LoginInterceptorConfigurer implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        HandlerInterceptor interceptor = new LoginInterceptor();

        List<String> excludeList = new ArrayList<>();
        excludeList.add("/users/reg");
        excludeList.add("/users/login");
        excludeList.add("/districts/**");
        excludeList.add("/products/**");

        excludeList.add("/web/register.html");
        excludeList.add("/web/login.html");
        excludeList.add("/web/index.html");
        excludeList.add("/web/product.html");

        excludeList.add("/bootstrap3/**");
        excludeList.add("/images/**");
        excludeList.add("/css/**");
        excludeList.add("/js/**");

        registry.addInterceptor(interceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(excludeList);
    }

}







