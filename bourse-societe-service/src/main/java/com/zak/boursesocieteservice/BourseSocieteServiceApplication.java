package com.zak.boursesocieteservice;

import com.zak.boursesocieteservice.dao.SocieteRepository;
import com.zak.boursesocieteservice.entities.Societe;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

import java.util.stream.Stream;

@EnableEurekaClient
@SpringBootApplication
public class BourseSocieteServiceApplication {

    public static void main(String[] args) {


        ApplicationContext appContext = SpringApplication.run(BourseSocieteServiceApplication.class, args);
        SocieteRepository societeRepository = appContext.getBean(SocieteRepository.class);
        Stream.of("A","B","C","D").forEach(s ->{
            societeRepository.save(new Societe(s));
        });

        societeRepository.findAll().forEach(s-> {
            System.out.println(s.getNom());
        });
    }

}

