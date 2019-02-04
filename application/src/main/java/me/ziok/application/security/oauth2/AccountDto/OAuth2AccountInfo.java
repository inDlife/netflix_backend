package me.ziok.application.security.oauth2.AccountDto;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@RequiredArgsConstructor
public abstract class OAuth2AccountInfo {

    @NonNull
    protected Map<String, Object> attributes;

    public abstract String getId();

    public abstract String getEmail();

    public abstract String getImageUrl();
}
