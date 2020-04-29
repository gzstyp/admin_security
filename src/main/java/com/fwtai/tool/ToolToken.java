package com.fwtai.tool;

import com.fwtai.config.ConfigFile;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * json web token 工具类
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2020-04-26 0:37
 * @QQ号码 444141300
 * @Email service@dwlai.com
 * @官网 http://www.fwtai.com
*/
@Component
public class ToolToken{

    public static final String roles = "roles";

    private final static long expiry = 1000 * 60 * 60;//1个小时

    private final static String issuer = "贵州富翁泰科技有限责任公司";

    private final static String secret = "V1JGR0dEZDJRZzAyYUhCVkhjVjZ1Umg5bHZvOG05VlVYd0FMUXlydEZOQUxhcitLWjM5ZitjNjR0WlgwSFBOQg==";

    public String extractUsername(final String token){
        return extractClaim(token,Claims::getSubject);
    }

    public Date extractExpiration(final String token){
        return extractClaim(token,Claims::getExpiration);
    }

    public <T> T extractClaim(final String token,final Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(final String token){
        final Key key = Keys.hmacShaKeyFor(secret.getBytes());
        final JwtParserBuilder builder = Jwts.parserBuilder();
        return builder.requireIssuer(issuer).setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    //是否有效
    public Boolean isTokenExpired(final String token){
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(final String userName,final List<String> roles){
        final Map<String,Object> claims = new HashMap<>(1);
        claims.put(ConfigFile.roles,roles);
        return createToken(userName,claims);
    }

    // setSubject 不能和s etClaims() 同时使用,如果用不到 userId() 的话可以把setId的值设为 userName !!!
    private static String createToken(final String userName,final Map<String,Object> claims){
        final long date = System.currentTimeMillis();
        final Key key = Keys.hmacShaKeyFor(secret.getBytes());
        final JwtBuilder builder = Jwts.builder().signWith(key,SignatureAlgorithm.HS384);
        if(claims != null && claims.size() > 0){
            for(final String k : claims.keySet()){
                builder.claim(k,claims.get(k));
            }
        }
        return builder.setSubject(userName).setIssuer(issuer).setIssuedAt(new Date(date)).setExpiration(new Date(date + expiry)).compact();
    }

    public String generateToken(final UserDetails userDetails){
        final long date = System.currentTimeMillis();
        final Key key = Keys.hmacShaKeyFor(secret.getBytes());
        final JwtBuilder builder = Jwts.builder().signWith(key,SignatureAlgorithm.HS384);
        builder.claim(this.roles,userDetails.getAuthorities()).setIssuer(issuer).setIssuedAt(new Date(date)).setExpiration(new Date(date + expiry)).setSubject(userDetails.getUsername());
        return builder.compact();
    }

    private String createToken(final String id,final String subject,final Map<String,Object> claims){
        final long date = System.currentTimeMillis();
        final Key key = Keys.hmacShaKeyFor(secret.getBytes());
        final JwtBuilder builder = Jwts.builder().signWith(key,SignatureAlgorithm.HS384);
        if(claims != null && claims.size() > 0){
            for(final String k : claims.keySet()){
                builder.claim(k,claims.get(k));
            }
        }
        builder.setId(id).setIssuer(issuer).setIssuedAt(new Date(date)).setExpiration(new Date(date + expiry)).setSubject(subject);
        return builder.compact();
    }

    public Boolean validateToken(final String token,final UserDetails userDetails){
        final String userName = extractUsername(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}