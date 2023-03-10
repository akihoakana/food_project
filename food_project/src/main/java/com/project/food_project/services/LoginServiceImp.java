package com.project.food_project.services;

import com.project.food_project.entity.RoleEntity;
import com.project.food_project.entity.TokenExpiredEntity;
import com.project.food_project.entity.UserEntity;
import com.project.food_project.entity.UserRoleEntity;
import com.project.food_project.payload.request.LogInRequest;
import com.project.food_project.repository.TokenRepository;
import com.project.food_project.repository.UserRepository;
import com.project.food_project.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service

public class LoginServiceImp implements LoginService {
    private final long expiredDate = 30 * 1000;
    private final long expiredDate1 = 2*60 * 1000;
    @Value("${spring.mail.username}")
    private String emailown;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    JavaMailSender mailSender;
    @Autowired
    private TokenRepository tokenRepository;
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserEntity checkLogin(String email) {
        List<UserEntity> users = userRepository.findByEmail(email);

        return users.size() > 0 ? users.get(0) : null;
    }

    @Override
    public List<TokenExpiredEntity> invalidToken(String token) {
        TokenExpiredEntity tokenExpiredEntity =new TokenExpiredEntity();
        tokenExpiredEntity.setName(token);
        tokenRepository.save(tokenExpiredEntity);
        return tokenRepository.findAll();
    }

    @Override
    public boolean checkToken(String token) {
        return tokenRepository.findByName(token)!=null ;
    }

    @Override
    public List<RoleEntity> getRoles(String email) {
        List<RoleEntity> list=new ArrayList<>();
        Set<UserRoleEntity> userRoleEntities=userRepository.findByEmail(email).get(0).getUserRoleEntity();
        for (UserRoleEntity userRoleEntity:userRoleEntities){
            RoleEntity roleEntity =userRoleEntity.getRoleEntity();
            list.add(roleEntity);
        }
        return list;
    }
    @Override
    public UserEntity registerNewUserAccount(LogInRequest logInRequest, String siteURL) throws UnsupportedEncodingException, MessagingException {
        if (emailExists(logInRequest.getEmail())) {
            System.out.println("There is an account with that email address: "
                    + logInRequest.getEmail());
            return null;
        }
        else {
            UserEntity userEntity = new UserEntity();
            userEntity.setEmail(logInRequest.getEmail());
            userEntity.setFullname(logInRequest.getFullName());
            userEntity.setPassword(passwordEncoder.encode(logInRequest.getPassword()));
            System.out.println("????ng k?? th??nh c??ng");
            sendVerificationEmail(logInRequest.getEmail(), siteURL);
            return userRepository.save(userEntity);
        }
    }

    @Override
    public List<UserRoleEntity> addRoleByIdUserAndRole(List<RoleEntity> roleEntities,int id) {
        List<UserRoleEntity> userRoleEntities=new ArrayList<>();
//        roleEntities.stream().map(roleEntity -> new UserRoleEntity(id,roleEntity.getId())).collect(Collectors.toList())
        for ()
        UserRoleEntity userRoleEntity=new UserRoleEntity();
        userRoleEntity.setIdRole();
        return userRoleEntities;
    }
    @Override
    public UserEntity confirmByEmail(String email) {
        List<UserEntity> usersEntities= userRepository.findByEmail(email);
        if (usersEntities.size()>0){
            usersEntities.get(0).setEmailVerify(true);
            userRepository.save(usersEntities.get(0));
            return userRepository.save(usersEntities.get(0));
        }
        else
            return null;
    }


    @Override
    public boolean emailExists(String email) {
        List<UserEntity> usersEntities= userRepository.findByEmail(email);
        return  (usersEntities.size()>0)?true:false;
    }

    @Override
    public void sendVerificationEmail(String email, String siteURL) throws UnsupportedEncodingException, MessagingException {
        String toAddress = email;
        String fromAddress = emailown;
        String senderName = "Booking";
        String subject = "Ch??? c???n nh???p chu???t ????? x??c nh???n";
        String content = "<h1>X??c minh ?????a ch??? email c???a b???n</h1>" +
                "B???n v???a t???o t??i kho???n v???i ?????a ch??? email: [[email]]<br>" +
                "Nh???n n??t \"X??c nh???n\" ????? ch???ng th???c ?????a ch??? email v?? m??? kh??a cho to??n b??? t??i kho???n.<br>" +
                "Ch??ng t??i c??ng s??? nh???p c??c ?????t ph??ng b???n ???? th???c hi???n v???i ?????a ch??? email n??y.<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">X??c nh???n</a></h3>";
        content = content.replace("[[email]]", email);
        System.out.println("verifyURL = " + siteURL);
        content = content.replace("[[URL]]", siteURL);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
    }
    @Override
    public List<UserEntity> newPassword(String email, String password, String passwordConfirm) {
        List<UserEntity> usersEntities= userRepository.findByEmail(email);
        if (usersEntities.size()>0 && StringUtils.hasText(passwordConfirm) && StringUtils.hasText(password)) {
            if (password.equals(passwordConfirm)) {
                usersEntities.get(0).setPassword(passwordEncoder.encode(password));
                userRepository.saveAll(usersEntities);
                System.out.println("newPassord th??nh c??ng");
                return usersEntities;
            }
            else{System.out.println("Hai password kh??ng gi???ng nhau");
                return null;
            }
        }
        else{
            System.out.println("newPassword th???t b???i");
            return null;
        }
    }
}
