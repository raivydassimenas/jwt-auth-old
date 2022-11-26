package lt.vu.jwtauth.controller;

import lt.vu.jwtauth.domain.Role;
import lt.vu.jwtauth.domain.User;
import lt.vu.jwtauth.repo.UserRepo;
import lt.vu.jwtauth.service.UserService;
import lt.vu.jwtauth.service.UserServiceImplementation;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.ArrayList;

@Configuration
@ComponentScan("lt.vu.jwtauth.service")
@EnableJpaRepositories(basePackages = {"lt.vu.jwtauth.repo"})
public class UserServiceApplication {

    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {
            userService.saveRole(new Role(null, "ROLE_USER"));
            userService.saveRole(new Role(null, "ROLE_MANAGER"));
            userService.saveRole(new Role(null, "ROLE_ADMIN"));
            userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));
            userService.saveUser(new User(null, "John", "john", "1234", new ArrayList<>()));

            userService.addRoleToUser("john", "ROLE_USER");
        };
    }
}
