package com.relationship.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

//    @Autowired
//    private JwtFilter jwtFilter;
//
//    @Bean
//    public FilterRegistrationBean jwtFilter() {
//        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//        registrationBean.setFilter(jwtFilter);        //添加需要拦截的url
//        List<String> urlPatterns = new ArrayList<String>();
//        urlPatterns.add("/**");
//        registrationBean.setUrlPatterns(urlPatterns);
//        return registrationBean;
//    }

//    @Bean
//    public FilterRegistrationBean corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.addAllowedOrigin("*");
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("OPTIONS");
//        config.addAllowedMethod("HEAD");
//        config.addAllowedMethod("GET");
//        config.addAllowedMethod("PUT");
//        config.addAllowedMethod("POST");
//        config.addAllowedMethod("DELETE");
//        config.addAllowedMethod("PATCH");
//        source.registerCorsConfiguration("/**", config);
//        final FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
//        bean.setOrder(0);
//        return bean;
//    }
//    @Bean
//    public WebMvcConfigurer mvcConfigurer() {
//        return new WebMvcConfigurerAdapter() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**").allowedMethods("GET", "PUT", "POST", "GET", "OPTIONS");
//            }
//        };
//    }
}
