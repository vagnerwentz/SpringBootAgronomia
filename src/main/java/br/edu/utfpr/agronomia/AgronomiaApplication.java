package br.edu.utfpr.agronomia;

import br.edu.utfpr.agronomia.Domain.Person;
import br.edu.utfpr.agronomia.Domain.Role;
import br.edu.utfpr.agronomia.Domain.RoleName;
import br.edu.utfpr.agronomia.Repository.RoleRepository;
import br.edu.utfpr.agronomia.Service.PersonService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Arrays;

@SpringBootApplication
public class AgronomiaApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgronomiaApplication.class, args);
    }

    @Bean
    CommandLineRunner run(PersonService personService, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            Role rUser = roleRepository.save(new Role(RoleName.USER));
            Role rAdmin = roleRepository.save(new Role(RoleName.ADMIN));

            personService.save(new Person("Administrator", "admin@admin.com", passwordEncoder.encode("1234"),
                    LocalDate.now(), Arrays.asList(rAdmin, rUser)));

        };
    }
}
