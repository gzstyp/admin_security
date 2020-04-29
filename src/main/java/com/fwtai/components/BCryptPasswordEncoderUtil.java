package com.fwtai.components;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordEncoderUtil extends BCryptPasswordEncoder {

    @Override
    public String encode(final CharSequence rawPassword) {
        return super.encode(rawPassword);
    }

    @Override
    public boolean matches(final CharSequence rawPassword,final String encodedPassword) {
        return super.matches(rawPassword,encodedPassword);
    }
}