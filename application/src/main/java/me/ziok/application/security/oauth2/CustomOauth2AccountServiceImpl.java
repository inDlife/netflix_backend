package me.ziok.application.security.oauth2;

import me.ziok.application.exceptions.OAuth2AuthenticationProcessingException;
import me.ziok.application.model.Account;
import me.ziok.application.model.AuthProviderType;
import me.ziok.application.security.UserPrincipal;
import me.ziok.application.security.oauth2.AccountDto.OAuth2AccountInfo;
import me.ziok.application.security.oauth2.AccountDto.OAuth2AccountInfoFactory;
import me.ziok.application.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.StringUtils;

import javax.naming.AuthenticationException;
import java.security.AuthProvider;

public class CustomOauth2AccountServiceImpl extends DefaultOAuth2UserService implements CustomOauth2AccountService {

    @Autowired
    AccountService accountService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
//        } catch (AuthenticationException ex) {
//            throw ex;
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());

        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {

        OAuth2AccountInfo oAuth2UserInfo = OAuth2AccountInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        //todo: 이메일이 없을 경우도 있다고 해서, 이 부분 수정 필요할 듯.
        if(StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }

        Account account = accountService.loadAccountByEmail(oAuth2UserInfo.getEmail());

        if (account != null) {
            if(!account.getProviderType().equals(AuthProviderType.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
                throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " + account.getProviderType() + " account. Please use your "+ account.getProviderType() + " account to log in");
            }

            account = updateExistingAccount(account, oAuth2UserInfo);
        } else {
            account = registerNewAccount(oAuth2UserRequest, oAuth2UserInfo);
        }

        return UserPrincipal.create(account, oAuth2User.getAttributes());
    }

    private Account updateExistingAccount(Account existingAccount, OAuth2AccountInfo oAuth2UserInfo) {
//        existingAccount.setName(oAuth2UserInfo.getName());
        existingAccount.setImageUrl(oAuth2UserInfo.getImageUrl());

        return accountService.saveAccount(existingAccount);
    }

    private Account registerNewAccount(OAuth2UserRequest oAuth2UserRequest, OAuth2AccountInfo oAuth2UserInfo) {
        Account account = new Account();
        //todo: build 패턴으로 바꾸기
        account.setProviderType(AuthProviderType.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
        account.setProviderId(oAuth2UserInfo.getId());
        //todo: set name 넣을지 고민
//        account.setName(oAuth2UserInfo.getName());
        account.setEmail(oAuth2UserInfo.getEmail());
        account.setImageUrl(oAuth2UserInfo.getImageUrl());

        return accountService.saveAccount(account);
    }

    
}
