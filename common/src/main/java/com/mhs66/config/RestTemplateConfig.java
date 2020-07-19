package com.mhs66.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author lijun
 * @date 2020-07-19 8:45
 * @description
 * @error
 * @since version-1.0
 */
@Configuration
public class RestTemplateConfig {
    @Bean
    RestTemplate intoRestTemplate() {
        return new RestTemplate();
    }
}
