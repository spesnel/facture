package fr.lpadmin.facture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import fr.lpadmin.facture.service.MainProcessService;

@SpringBootApplication
public class Main { 

    @Autowired
    private ApplicationContext context;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);

    }

    @Bean
    CommandLineRunner runTask() {
        return args -> {
            MainProcessService mainProcess = context.getBean(MainProcessService.class);
            mainProcess.processFactures();
            System.exit(0);
        };
    }
}
