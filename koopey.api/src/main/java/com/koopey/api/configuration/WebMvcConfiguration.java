package com.koopey.api.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

  @Override
public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new LocaleChangeInterceptor());
}


  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
      registry.addResourceHandler("swagger-ui.html")
              .addResourceLocations("classpath:/META-INF/resources/");
  }
  
    @Override
    public void addCorsMappings(CorsRegistry registry) {
      //  registry.addMapping("/**").allowedOrigins("http://127.0.0.1");
                // .allowedOrigins("http://127.0.0.1") .allowCredentials(true)  .allowedHeaders("*").allowedMethods("HEAD", "DELETE", "GET", "OPTIONS", "POST", "PUT")
             //  .allowedOriginPatterns("http://127.0.0.1").allowedOriginPatterns("https://*.hoopey.com").allowCredentials(false)
    }
}