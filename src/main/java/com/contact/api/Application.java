package com.contact.api;

import java.util.Properties;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@SpringBootApplication
@EntityScan(basePackageClasses = {
		Application.class,
		Jsr310JpaConverters.class
})
public class Application {

	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}
	
	@Bean
    public JavaMailSender getMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
         
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
	 	// mailSender.setUsername("your username");
		// mailSernder.setPassword("pass");_
        
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.starttls.enable", "true");
        javaMailProperties.put("mail.smtp.auth", "true");
        javaMailProperties.put("mail.transport.protocol", "smtp");
        javaMailProperties.put("mail.debug", "true");//Prints out everything on screen
        
        mailSender.setJavaMailProperties(javaMailProperties);
        return mailSender;
    }

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
