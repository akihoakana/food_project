package com.project.food_project.jwt;

import com.google.gson.Gson;
import com.project.food_project.entity.RoleEntity;
import com.project.food_project.services.LoginService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    @Autowired
    private LoginService loginService;
    private Gson gson = new Gson();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromHeader(request);
        if(token != null){
            if(jwtTokenHelper.validaToken(token)){
                Claims json = jwtTokenHelper.decodeToken(token);
                Map<String, Object>  map = gson.fromJson(json.getSubject(), Map.class);
                if(StringUtils.hasText(map.get("type").toString())
                        && !map.get("type").toString().equals("refesh")){
                    Collection authorities =
                            Arrays.stream(json.get("list").toString().split(","))
                                    .map(SimpleGrantedAuthority::new)
                                    .collect(Collectors.toList());
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken
                            ("","",authorities);
                    SecurityContext securityContext = SecurityContextHolder.getContext();
                    securityContext.setAuthentication(authenticationToken);
                }
            }
        }

        filterChain.doFilter(request,response);
    }

    private String getTokenFromHeader(HttpServletRequest request){
        String strToken = request.getHeader("Authorization");
        if(StringUtils.hasText(strToken) && strToken.startsWith("Bearer ")){
            String finalToken = strToken.substring(7);
            return finalToken;
        }else{
            return null;
        }
    }
}
