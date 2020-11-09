package com.example.fileSharing;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static com.example.fileSharing.helpers.ConstantClass.AVATAR_FOLDER;

@SpringBootApplication
public class FileSharingApplication {
	public static void main(String[] args) {
		SpringApplication.run(FileSharingApplication.class, args);
	}

	@Value("${avatar.path}")
	public String avatarPath;

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
					.allowedOrigins("http://localhost:3000")
					.allowedMethods("*");
			}

			@Override
			public void addResourceHandlers(ResourceHandlerRegistry registry) {
				System.out.println("file://" + avatarPath);
				registry
					.addResourceHandler("/avatars/**")
					.addResourceLocations("file://" + avatarPath);
			}
		};
	}
}
