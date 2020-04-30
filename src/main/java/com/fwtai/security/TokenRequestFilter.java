package com.fwtai.security;

import com.fwtai.config.ConfigFile;
import com.fwtai.tool.ToolToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * token拦截器(最先请求的拦截器)||更换token在这里实现
 */
@Component
public class TokenRequestFilter extends OncePerRequestFilter {

    @Resource
    private UserDetailsService userDetailsService;

    @Autowired
    private ToolToken toolToken;

    private String header = "Authorization";

    @Override
    protected void doFilterInternal(final HttpServletRequest request,final HttpServletResponse response,final FilterChain chain) throws ServletException, IOException {
        final String uri = request.getRequestURI();
        final String[] urls = ConfigFile.urls;
        for(int x = 0; x < urls.length; x++){
            final String url = urls[x];
            if(uri.equals(url)){
                chain.doFilter(request, response);
                return;
            }
        }
        final String token = request.getHeader(header);
        System.out.println("token = " + token);
        System.out.println("request getMethod = " + request.getMethod());
        if (!StringUtils.isEmpty(token)) {
            //判断令牌是否过期，默认是一周
            //比较好的解决方案是：
            //登录成功获得token后，将token存储到数据库（redis）
            //将数据库版本的token设置过期时间为15~30分钟
            //如果数据库中的token版本过期，重新刷新获取新的token
            //注意：刷新获得新token是在token过期时间内有效。
            //如果token本身的过期（1周），强制登录，生成新token。
            boolean check = false;
            try {
                check = toolToken.isTokenExpired(token);
            } catch (Exception e) {
                new Throwable("令牌已过期，请重新登录。"+e.getMessage());
            }
            if (!check){
                //通过令牌获取用户名称
                final String username = toolToken.extractUsername(token);
                System.out.println("username = " + username);
                //判断用户不为空，且SecurityContextHolder授权信息还是空的
                final SecurityContext context = SecurityContextHolder.getContext();
                if (username != null && context.getAuthentication() == null) {
                    //通过用户信息得到UserDetails
                    final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    //验证令牌有效性
                    boolean validata = false;
                    try {
                        validata = toolToken.validateToken(token,userDetails);
                    }catch (Exception e) {
                        new Throwable("验证token无效:"+e.getMessage());
                    }
                    if (validata) {
                        // 将用户信息存入 authentication，方便后续校验,这个方法是要保存角色权限信息的
                        final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        // 将 authentication 存入 ThreadLocal，方便后续获取用户信息
                        context.setAuthentication(authentication);
                    }
                }
            }
        }
        chain.doFilter(request, response);
    }
}