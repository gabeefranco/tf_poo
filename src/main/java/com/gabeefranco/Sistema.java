package com.gabeefranco;

import java.io.IOException;
import java.nio.file.Path;

import com.gabeefranco.cadastros.CadastroEventos;
import com.gabeefranco.cadastros.CadastroIngressos;
import com.gabeefranco.cadastros.CadastroPromocoes;
import com.gabeefranco.cadastros.CadastroVendas;

public class Sistema {
  private static CadastroVendas cadastroVendas;
  private static CadastroEventos cadastroEventos;
  private static CadastroIngressos cadastroIngressos;
  private static CadastroPromocoes cadastroPromocoes;

  private Sistema() {

  }

  public static void inicializarSistema() {
    cadastroVendas = new CadastroVendas();
    cadastroEventos = new CadastroEventos();
    cadastroPromocoes = new CadastroPromocoes();
    cadastroIngressos = new CadastroIngressos();
    try {
      LeitorDadosCsv
        .lerEventos(Path.of("eventos.csv"))
        .forEach(cadastroEventos::criarEvento);
      LeitorDadosCsv
        .lerVendas(Path.of("vendas.csv"))
        .forEach(cadastroVendas::criarVenda);
      LeitorDadosCsv
        .lerPromocoes(Path.of("promocoes.csv"))
        .forEach(cadastroPromocoes::criarPromocao);
      LeitorDadosCsv
        .lerIngressos(Path.of("ingressos.csv"))
        .forEach(cadastroIngressos::criarIngresso);

    } catch(IOException e) {
      System.out.println("Erro lendo csv: " + e.getMessage());
      System.exit(1);
    }
  }

  public static CadastroVendas getCadastroVendas() {
    return cadastroVendas;
  }

  public static CadastroEventos getCadastroEventos() {
    return cadastroEventos;
  }

  public static CadastroIngressos getCadastroIngressos() {
    return cadastroIngressos;
  }

  public static CadastroPromocoes getCadastroPromocoes() {
    return cadastroPromocoes;
  }

  
}
