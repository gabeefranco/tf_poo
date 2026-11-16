package com.gabeefranco;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class App {

    public static void main(String[] args) {
        Sistema.inicializarSistema();
        SpringApplication.run(App.class, args);
    }
}
