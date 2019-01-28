package me.ziok.application.service;

import me.ziok.application.model.Account;

public interface AccountService {
    Account loadAccountByAccountId(String accountId);
    Account saveAccount(String accountId, String password);
    void deleteAccount(String accountId);
    Account updateAccount(Account account);
    boolean isAbleToRegister(String accountId, String nickName);

}