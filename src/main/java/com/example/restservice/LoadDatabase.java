package com.example.restservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new Employee("Bilbo Baggins1", "burglar", 1)));
            log.info("Preloading " + repository.save(new Employee("Frodo Baggins2", "thief", 2)));
            log.info("Preloading " + repository.save(new Employee("Bilbo Baggins3", "burglar", 3)));
            log.info("Preloading " + repository.save(new Employee("Frodo Baggins4", "thief", 4)));
            log.info("Preloading " + repository.save(new Employee("Bilbo Baggins5", "burglar", 5)));
            log.info("Preloading " + repository.save(new Employee("Frodo Baggins6", "thief", 6)));
            log.info("Preloading " + repository.save(new Employee("Bilbo Baggins7", "burglar", 7)));
            log.info("Preloading " + repository.save(new Employee("Frodo Baggins8", "thief", 8)));
            log.info("Preloading " + repository.save(new Employee("Bilbo Baggins9", "burglar", 9)));
            log.info("Preloading " + repository.save(new Employee("Frodo Baggins10", "thief", 10)));
            log.info("Preloading " + repository.save(new Employee("Bilbo Baggins11", "burglar", 11)));
            log.info("Preloading " + repository.save(new Employee("Frodo Baggins12", "thief", 200)));
        };
    }
}