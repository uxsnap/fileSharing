package com.example.fileSharing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.nio.file.Paths;

import static com.example.fileSharing.helpers.ConstantClass.AVATAR_FOLDER;

@SpringBootApplication
public class FileSharingApplication {
	public static void main(String[] args) {
		SpringApplication.run(FileSharingApplication.class, args);
	}

	@Autowired
	ResourceLoader resourceLoader;

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
					.allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
			}

			@Override
			public void addResourceHandlers(ResourceHandlerRegistry registry) {
				/* Kinda stupid solution for resource handling. Deeper research required */
				String avatarPath = String.format("%s/%s", System.getProperty("user.dir"), AVATAR_FOLDER);
				System.out.println(avatarPath);
				registry
					.addResourceHandler("/avatars/**")
					.addResourceLocations("file:///" + avatarPath);
			}
		};
	}
}
