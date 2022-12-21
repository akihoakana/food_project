package com.cybersoft.food_project.services;

import com.cybersoft.food_project.entity.UserEntity;
import com.cybersoft.food_project.payload.request.SignUpRequest;
import com.cybersoft.food_project.repository.UserRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class LoginServiceImp implements LoginService {
    private final long expiredDate = 30 * 1000;
    private final long expiredDate1 = 2*60 * 1000;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    JavaMailSender mailSender;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Override
    public boolean checkLogin(String email, String password) {
        List<UserEntity> users = userRepository.findByEmailAndPassword(email,password);

        return users.size() > 0;
    }

    @Override
    public UserEntity checkLogin(String email) {
        List<UserEntity> users = userRepository.findByEmail(email);

        return users.size() > 0 ? users.get(0) : null;
    }

    @Override
    public UserEntity registerNewUserAccount(SignUpRequest signUpRequest, String siteURL) throws UnsupportedEncodingException, MessagingException {
        if (emailExists(signUpRequest.getEmail())) {
            System.out.println("There is an account with that email address: "
                    + signUpRequest.getEmail());
            return null;
        }
        else {
            UserEntity userEntity =new UserEntity();
            userEntity.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
            userEntity.setFullname(signUpRequest.getFullName());
            userEntity.setEmail(signUpRequest.getEmail());
            String randomCode = RandomString.make(12);
            userEntity.setVerifyCode(randomCode);
            userEntity.setVerifyCodeExpired(new Timestamp(System.currentTimeMillis()+expiredDate));
            System.out.println("new Timestamp(System.currentTimeMillis()+expiredDate) = "
                    + new Timestamp(System.currentTimeMillis()+expiredDate));
            System.out.println("Đăng kí thành công");
            sendVerificationEmail(userEntity, siteURL);
            return userRepository.save(userEntity);
        }
    }

    @Override
    public String confirmByEmail(String verifyCode) {
        UserEntity userEntity= userRepository.findByVerifyCode(verifyCode);
        if (userEntity.getVerifyCode()!=null && !userEntity.getVerifyCode().equals("")){
            if (userEntity.getVerifyCodeExpired().after(new Timestamp(System.currentTimeMillis()))){
                userEntity.setActive(true);
                userRepository.save(userEntity);
                return "Active thành công";
            }
            return "Active thất bại";
        }
        else
            return "Active thất bại";

    }

    @Override
    public boolean newPassord(String verifyCode, String password) {
        UserEntity userEntity= userRepository.findByVerifyCode(verifyCode);
        if (userEntity.getVerifyCode()!=null && !userEntity.getVerifyCode().equals("")){
            if (userEntity.getVerifyCodeExpired().after(new Timestamp(System.currentTimeMillis()))){
                userEntity.setPassword(passwordEncoder.encode(password));
                userRepository.save(userEntity);
                System.out.println("newPassord thành công");
                return true;
            }
            System.out.println("newPassord thất bại");
            return false;
        }
        else
            System.out.println("newPassord thất bại");
        return false;
    }

    @Override
    public String forgetPassord(String mail, String siteURL) throws UnsupportedEncodingException, MessagingException {
        List<UserEntity> userEntities=  userRepository.findByEmail(mail);
        System.out.println("userEntities.size() = " + userEntities.size());
        if (userEntities.size()>0){
            String randomCode = RandomString.make(12);
            userEntities.get(0).setVerifyCode(randomCode);
            userEntities.get(0).setVerifyCodeExpired(new Timestamp(System.currentTimeMillis()+expiredDate1));
            System.out.println("password = "
                    + new Timestamp(System.currentTimeMillis()+expiredDate));
            userRepository.saveAll(userEntities);
            sendVerificationEmail(userEntities.get(0), siteURL);
            return "forgetPassord thành công " +randomCode;
        }
        else
            return "forgetPassord thất bại";
    }

    @Override
    public void resendconfirmByEmail(String verifyCode, String siteURL) throws UnsupportedEncodingException, MessagingException {
        UserEntity userEntity= userRepository.findByVerifyCode(verifyCode);
        String randomCode = RandomString.make(12);
        userEntity.setVerifyCode(randomCode);
        userEntity.setVerifyCodeExpired(new Timestamp(System.currentTimeMillis()+expiredDate));
        userRepository.save(userEntity);
        sendVerificationEmail(userEntity, siteURL);
        System.out.println("Gửi lại mail thành công");
    }

    @Override
    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).size()>0;
    }

    @Override
    public void sendVerificationEmail(UserEntity userEntity, String siteURL) throws UnsupportedEncodingException, MessagingException {
        String toAddress = userEntity.getEmail();
        String fromAddress = "akihoakana@gmail.com";
        String senderName = "Your company name1111";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Your company name.";
        content = content.replace("[[name]]", userEntity.getFullname());
        String verifyURL = siteURL + "/signup/verify/" + userEntity.getVerifyCode();
        content = content.replace("[[URL]]", verifyURL);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
    }

}
