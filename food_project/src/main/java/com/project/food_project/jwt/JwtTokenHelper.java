package com.project.food_project.jwt;

import com.google.gson.Gson;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;

@Component
public class JwtTokenHelper {
    private final String strKey = "xJHDonkgbMOgIGNodeG7l2kgYuG6o28gbeG6rXQgxJHhuqd5IMSR4bunIDI1NiBiaXQ="; //Chuỗi base 64
    private Gson gson = new Gson();
    public String generateToken(String data, String type, Collection<? extends GrantedAuthority> list, long expiredDate){
        Date now = new Date();
        Date dateExpired = new Date(now.getTime() + expiredDate);
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(strKey));

        Map<String,Object> subJectData = new HashMap<>();
        subJectData.put("username",data);
        subJectData.put("type",type);

        String json = gson.toJson(subJectData);

        return Jwts.builder()
                .setSubject(json)
                .claim("list",list)
                .setIssuedAt(now)
                .setExpiration(dateExpired)
                .signWith(secretKey,SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims decodeToken(String token){
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(strKey));
        System.out.println("Jwts.parserBuilder().setSigningKey(secretKey).build()\n                .parseClaimsJws(token).getBody() = " + Jwts.parserBuilder().setSigningKey(secretKey).build()
                .parseClaimsJws(token).getBody());
        return Jwts.parserBuilder().setSigningKey(secretKey).build()
                .parseClaimsJws(token).getBody();
    }

    public boolean validaToken(String token){
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(strKey));
        boolean isSuccess = false;
        try{
            Jwts.parserBuilder().setSigningKey(secretKey).build()
                    .parseClaimsJws(token);
            isSuccess = true;
        }catch (MalformedJwtException ex) {
            System.out.println("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            System.out.println("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            System.out.println("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            System.out.println("JWT claims string is empty.");
        }
        return isSuccess;

    }

}
