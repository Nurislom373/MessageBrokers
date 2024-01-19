package org.khasanof.springamqp.config;

import com.github.javafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Nurislom
 * @see org.khasanof.springamqp.config
 * @since 1/20/2024 12:36 AM
 */
@Configuration
public class FakerConfiguration {

    @Bean
    public Faker faker() {
        return new Faker();
    }

}
