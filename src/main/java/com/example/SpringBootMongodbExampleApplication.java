package com.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.example.document.Users;
import com.example.repository.UserRepository;

@SpringBootApplication
@EnableMongoRepositories(basePackages ="com" )
public class SpringBootMongodbExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootMongodbExampleApplication.class, args);
	}


    /**
     * 项目启动后 会保存3条记录 用来测试.
     *
     * @param userRepository
     * @return
     */
    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository) {
        return strings -> {
            userRepository.save(new Users(1, "Peter", "Development", 3000L));
            userRepository.save(new Users(2, "Sam", "Operations", 2000L));
            userRepository.save(new Users(3, "Sam1", "Operations1", 2000L));
        };
    }


}
