package com.fwtai.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fwtai.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * 登录过滤器
 */
public class LoginAuthFilter extends UsernamePasswordAuthenticationFilter{

    @Autowired
    private SysUserService userService;

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request,final HttpServletResponse response) throws AuthenticationException{
        if(request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE) || request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)){
            final ObjectMapper mapper = new ObjectMapper();
            //取authenticationBean
            Map<String,String> authenticationBean = null;
            //用try with resource，方便自动释放资源
            try(InputStream is = request.getInputStream()){
                authenticationBean = mapper.readValue(is,Map.class);
            }catch(IOException e){
                //将异常放到自定义的异常类中
                throw new AuthExceptionHandler(e.getMessage());
            }
            try{
                if(!authenticationBean.isEmpty()){
                    //获得账号、密码
                    final String username = authenticationBean.get(SPRING_SECURITY_FORM_USERNAME_KEY);
                    final String password = authenticationBean.get(SPRING_SECURITY_FORM_PASSWORD_KEY);
                    //可以验证账号、密码
                    //检测账号、密码是否存在
                    if(userService.checkLogin(username,password)){
                        //将账号、密码装入UsernamePasswordAuthenticationToken中,即这个方法是没有角色或权限,只是单纯的保存用户名和密码
                        final UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username,password);// 这个方法是没有角色或权限
                        setDetails(request,authRequest);
                        return this.getAuthenticationManager().authenticate(authRequest);
                    }
                }
            }catch(Exception e){
                throw new AuthExceptionHandler(e.getMessage());
            }
            return null;
        }else{
            return this.attemptAuthentication(request,response);
        }
    }
}