package project.intern.demo.configuration;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import project.intern.demo.entity.User;
import project.intern.demo.repository.UserRepository;

@Configuration
public class InitConfig {

    @Bean
    ApplicationRunner applicationRunner (UserRepository userRepository)
    {
        return args -> {
            if(userRepository.findByUsername("admin").isEmpty())
            {
                User user = new User();
                user.setUsername("admin");
                user.setRole("ADMIN");
                PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
                user.setPassword(passwordEncoder.encode("admin"));
                userRepository.save(user);
            }

        };
    }

}
