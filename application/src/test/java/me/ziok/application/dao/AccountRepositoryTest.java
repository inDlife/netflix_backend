package me.ziok.application.dao;

import me.ziok.application.model.Account;
import me.ziok.application.model.Post;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @Test
    public void crud() {
        Account account = new Account();

        account.setAccountId("kkkkk@naver.com");
        account.setPassword("passpass");
        account.setNickName("nick");

        accountRepository.save(account);

        List<Account> all = accountRepository.findAll();
        assertThat(all.size()).isEqualTo(1);
    }

}