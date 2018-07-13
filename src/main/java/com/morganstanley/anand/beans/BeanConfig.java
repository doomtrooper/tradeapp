package com.morganstanley.anand.beans;

import com.morganstanley.anand.validation.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class BeanConfig {
    @Bean
    public ValidatorService validatorService(){
        return () -> Arrays.asList(new AlphaNumbericValidation(), new IntegerValidation(), new DateValidation(), new BuySellValidation());
    }
}
