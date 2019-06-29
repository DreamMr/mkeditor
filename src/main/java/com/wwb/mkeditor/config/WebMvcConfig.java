package com.wwb.mkeditor.config;

import com.wwb.mkeditor.Properties.imgUploadProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author wwb
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    imgUploadProperties imgUploadProperties;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(imgUploadProperties.getStaticAccessPath()).addResourceLocations("file:"+imgUploadProperties.getUploadFolder());
    }
}
