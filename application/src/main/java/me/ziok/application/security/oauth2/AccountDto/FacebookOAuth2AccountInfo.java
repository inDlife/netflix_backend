package me.ziok.application.security.oauth2.AccountDto;

import java.util.Map;

public class FacebookOAuth2AccountInfo extends OAuth2AccountInfo {
    public FacebookOAuth2AccountInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {

        return (String) attributes.get("id");
    }

    @Override
    public String getEmail() {
        return (String)attributes.get("email");
    }

    @Override
    public String getImageUrl() {

        if (!attributes.containsKey("picture")) {
            return null;
        }

        Map<String, Object> picture = (Map<String, Object>) attributes.get("picture");
        if (!picture.containsKey("data")) {
            return null;
        }

        Map<String, Object> data = (Map<String, Object>) picture.get("data");

        if (!data.containsKey("url")) {
            return null;
        }

        return (String)data.get("url");
    }
}
