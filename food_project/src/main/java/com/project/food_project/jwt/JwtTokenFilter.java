package com.project.food_project.jwt;

import com.google.gson.Gson;
import com.project.food_project.services.LoginService;
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
                String json = jwtTokenHelper.decodeToken(token);
                System.out.println("json = " + json);
                Map<String, Object> map = gson.fromJson(json, Map.class);
//                System.out.println("map.get(\"roles\").toString() = " + map.get("roles").toString());
                if(StringUtils.hasText(map.get("type").toString())
                        && !map.get("type").toString().equals("refesh")){
//                        List<SimpleGrantedAuthority> list1 =
//                                Arrays.stream(map.get("roles").toString().split(",    "))
//                                        .map(SimpleGrantedAuthority::new).collect(Collectors.toList());
//                    System.out.println("list1 = " + list1);
                        List<GrantedAuthority> list =new ArrayList<>();
//                        for (SimpleGrantedAuthority simpleGrantedAuthority:list1)
//                        {
//                            list.add(new SimpleGrantedAuthority(simpleGrantedAuthority.getAuthority()));
//                            System.out.println("simpleGrantedAuthority.getAuthority() = " + simpleGrantedAuthority.getAuthority());
//                        }
                        list.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
                        list.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken
                            (map.get("username"),"",list);
                    System.out.println("authenticationToken.getAuthorities() = " + authenticationToken.getAuthorities());
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
