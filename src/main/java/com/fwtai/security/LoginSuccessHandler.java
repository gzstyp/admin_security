package com.fwtai.security;

import com.baomidou.mybatisplus.extension.api.R;
import com.fwtai.bean.SysFrontendMenuTable;
import com.fwtai.components.TokenCache;
import com.fwtai.service.SysFrontendMenuTableService;
import com.fwtai.tool.ToolToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录成功操作
 */
@Component
public class LoginSuccessHandler extends JSONAuthentication implements AuthenticationSuccessHandler{

    @Autowired
    private SysFrontendMenuTableService service;

    @Autowired
    private ToolToken toolToken;

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request,final HttpServletResponse response,final Authentication authentication) throws IOException, ServletException{
        //取得账号信息
        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //
        System.out.println("userDetails = " + userDetails);
        //取token
        //好的解决方案，登录成功后token存储到数据库中,只要token还在过期内，不需要每次重新生成
        //先去缓存中找
        String token = TokenCache.getTokenFromCache(userDetails.getUsername());
        if(token == null){
            System.out.println("初次登录，token还没有，生成新token。。。。。。");
            //如果token为空，则去创建一个新的token
            token = toolToken.generateToken(userDetails);
            //把新的token存储到缓存中
            TokenCache.setToken(userDetails.getUsername(),token);
        }
        //加载前端菜单
        final List<SysFrontendMenuTable> menus = service.getMenusByUserName(userDetails.getUsername());
        //
        final Map<String,Object> map = new HashMap<>(3);
        map.put("username",userDetails.getUsername());
        map.put("auth",userDetails.getAuthorities());
        map.put("menus",menus);
        map.put("token",token);
        //装入token
        final R<Map<String,Object>> data = R.ok(map);
        //输出
        this.WriteJSON(request,response,data);
    }
}