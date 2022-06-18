package com.example.demo.upload;

import java.nio.file.Path;
import java.nio.file.Paths;
 
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
 
@Configuration
public class MvcConfig implements WebMvcConfigurer {
 
 
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        exposeDirectory("chef-photos", registry);
        exposeDirectory("buffet-photos", registry);
        //qui ci aggiungo in coda le foto delle altre entità
    }
     
    private void exposeDirectory(String dirName, ResourceHandlerRegistry registry) {
        Path uploadDir = Paths.get(dirName);
        String uploadPath = uploadDir.toFile().getAbsolutePath();
         
        if (dirName.startsWith("../")) dirName = dirName.replace("../", "");
         
        registry.addResourceHandler("/" + dirName + "/**").addResourceLocations("file:/"+ uploadPath + "/");
    }
}
