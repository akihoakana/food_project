package com.project.food_project.controller;

import com.project.food_project.entity.RoleEntity;
import com.project.food_project.jwt.JwtTokenHelper;
import com.project.food_project.payload.response.DataResponse;
import com.project.food_project.payload.response.DataTokenResponse;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/refresh-token")
public class RefreshTokenController {
    //Phải upload được file
    //Phải lấy ra được file đó và trả đường dẫn file cho người dùng

    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    private Gson gson = new Gson();
    private long expiredDate = 8 * 60 * 60 * 1000;
    private long refreshExpiredDate = 80 * 60 * 60 * 1000;

    @PostMapping("")
    public ResponseEntity<?> index(@RequestParam("token") String token){
        DataResponse dataResponse = new DataResponse();
        if(jwtTokenHelper.validaToken(token)){
            Claims json = jwtTokenHelper.decodeToken(token);
            Map<String, Object> map = gson.fromJson(json.getSubject(), Map.class);
            if(StringUtils.hasText(map.get("type").toString())
                    && map.get("type").toString().equals("refesh")){
                List<GrantedAuthority> list= (List<GrantedAuthority>) map.get("list");

                String tokenAuthen = jwtTokenHelper.generateToken(map.get("username").toString(),"authen",list,expiredDate);
                String refeshToken = jwtTokenHelper.generateToken(map.get("username").toString(),"refesh",list,refreshExpiredDate);

                DataTokenResponse dataTokenResponse = new DataTokenResponse();
                dataTokenResponse.setToken(tokenAuthen);
                dataTokenResponse.setRefreshToken(refeshToken);

                dataResponse.setStatus(HttpStatus.OK.value());
                dataResponse.setSuccess(true);
                dataResponse.setDesc("");
                dataResponse.setData(dataTokenResponse);
            }
        }else{
            dataResponse.setStatus(HttpStatus.OK.value());
            dataResponse.setSuccess(true);
            dataResponse.setDesc("");
            dataResponse.setData("");
        }


        return new ResponseEntity<>(dataResponse, HttpStatus.OK);
    }

}
