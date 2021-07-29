package com.mobiquity.domains.builders;

import com.mobiquity.domains.entity.User;

public class UserBuilder {

    private final String _name = "Eren Degerli";
    private String _username = "Eren";
    private String _email = "erencdegerli@gmail.com";
    private final String _phone = "(775)976-6794 x41206";
    private final String _website = "www.conrad.com";

    public UserBuilder withUsername(String username) {
        _username = username;
        return this;
    }

    public UserBuilder withEmail(String email) {
        _email = email;
        return this;
    }

    public User build() {
        return new User() {{
            setName(_name);
            setUsername(_username);
            setEmail(_email);
            setPhone(_phone);
            setWebsite(_website);
        }};
    }
}
