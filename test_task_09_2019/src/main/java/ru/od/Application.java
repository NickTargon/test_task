package ru.od;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.od.model.MainEntity;
import ru.od.model.SubEntity;
import ru.od.repository.MainEntityRepository;
import ru.od.repository.SubEntityRepository;

@EnableJpaRepositories
@SpringBootApplication
public class Application {
    @Bean
    public CommandLineRunner loadData(MainEntityRepository mainEntityRepository, SubEntityRepository subEntityRepository){
        return (args)->{
            for (int a = 0; a < 1000; a++) {
                MainEntity mainEntity = new MainEntity();
                mainEntity.setName(String.format("Entity name of %d", Math.round(Long.MAX_VALUE * Math.random())));
                for (int i = 0; i < 10; i++) {
                    SubEntity subEntity = new SubEntity();
                    subEntity.setName(" i " + i);
                    subEntityRepository.save(subEntity);
                    mainEntity.getSubEntities().add(subEntity);
                }
                mainEntityRepository.save(mainEntity);
            }

        };
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
