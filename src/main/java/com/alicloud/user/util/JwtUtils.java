package com.alicloud.user.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import java.util.Date;

public class JwtUtils {
    private final static Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    final static String base64EncodedSecretKey = "base64EncodedSecretKey";//私钥
    //final static long TOKEN_EXP = 1000 * 60 * 60 *24;//过期时间, 1000 * 60测试使用60秒
    final static long TOKEN_EXP = 1000 * 60;//过期时间, 1000 * 60测试使用60秒

    public static String getToken(String userName) {
        return Jwts.builder()
                .setSubject(userName)
                .claim("username", userName)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXP)) /*过期时间*/
                .signWith(SignatureAlgorithm.HS256, base64EncodedSecretKey)
                .compact();
    }

    //解析token
    public static void checkToken(String token) throws ServletException {
        try {
            final Claims claims = Jwts.parser().setSigningKey(base64EncodedSecretKey).parseClaimsJws(token).getBody();
            logger.info("从token中解析到的username=="+claims);
            String username= (String) claims.get("username");
            logger.info("username=="+username);
        } catch (ExpiredJwtException e1) {
            throw new ServletException("token expired");
        } catch (Exception e) {
            throw new ServletException("other token exception");
        }
    }

}