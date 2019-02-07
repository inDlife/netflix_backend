package me.ziok.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Properties;

@Component
public class AppRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        String accessToken = "dddd";
//        Facebook facebook = new FacebookTemplate(accessToken);
//
//        User userProfile = facebook.userOperations().getUserProfile();
//        if (userProfile == null) {
//            System.out.println("null point exception!!!");
//        } else {
//            System.out.println("success!!");
//            System.out.println(userProfile.getName());
//        }



    }
}
