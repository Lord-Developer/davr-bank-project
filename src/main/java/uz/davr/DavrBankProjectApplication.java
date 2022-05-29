package uz.davr;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import uz.davr.model.User;
import uz.davr.service.UserService;

import java.util.ArrayList;

@SpringBootApplication
public class DavrBankProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(DavrBankProjectApplication.class, args);
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    CommandLineRunner runner(UserService userService) {
        return args -> {

            userService.saveUser(new User( "john mafia", "john", "1234", "ROLE_TEACHER"));
            userService.saveUser(new User( "will terry", "will", "1234", "ROLE_ADMIN"));
            userService.saveUser(new User( "jim john", "jim", "1234", "ROLE_USER"));


        };
    }
}
