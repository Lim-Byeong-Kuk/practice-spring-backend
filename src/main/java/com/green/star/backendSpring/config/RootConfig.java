package com.green.star.backendSpring.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RootConfig {
    //자바 객체로 보면 된다. Configuration과 Bean 을 사용하면 객체를 만들어준다.
    @Bean
    public ModelMapper getMapper() {
        //model Mapper 란?
        //entity와 dto 를 자동으로 변환해 주는 tool
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper;
    }
}
