package com.service.dida.Utils.Oauth.helper;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

@Configuration
public class converter implements Converter<String, SocialLoginType> {

    @Override
    public SocialLoginType convert(String source) {
        return SocialLoginType.valueOf(source.toUpperCase());
    }
}