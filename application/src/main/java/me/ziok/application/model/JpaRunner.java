package me.ziok.application.model;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Component
@Transactional
public class JpaRunner implements ApplicationRunner {

    @PersistenceContext
    EntityManager entityManager;
    @Override
    public void run(ApplicationArguments args) throws Exception {

        Post post = new Post();
        post.setPostName("post");
        post.setContent("content");

        entityManager.persist(post);

        Account account = new Account();

        account.setAccountId("kkkkk@naver.com");
        account.setPassword("passpass");
        account.setNickName("nick");

        entityManager.persist(account);


    }
}
