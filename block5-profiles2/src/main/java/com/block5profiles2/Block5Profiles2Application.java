package com.block5profiles2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class Block5Profiles2Application implements CommandLineRunner {

    private final AppConfig appConfig;

    public static void main(String[] args) {
        SpringApplication.run(Block5Profiles2Application.class, args);
    }

    @Override
    public void run(String... args)
    {
        log.info("My app name: " +  appConfig.getBd());
    }
}

