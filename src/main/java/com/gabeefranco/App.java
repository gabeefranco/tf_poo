package com.gabeefranco;

import java.io.IOException;
import java.nio.file.Path;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.gabeefranco.cadastros.CadastroEventos;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        // SpringApplication.run(App.class, args);
        CadastroEventos c = new CadastroEventos();
        try {
          LeitorDadosCsv
              .lerEventos(Path.of("eventos.csv"))
              .forEach(c::criarEvento);
        } catch (IOException e) {
          System.out.println(e.getMessage()); 
          System.exit(1);
        }
        
        c.getEventos().forEach(System.out::println);
    }
}
