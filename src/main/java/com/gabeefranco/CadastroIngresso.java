package com.gabeefranco;

import java.util.List;

public class CadastroIngresso {
  private List<Ingresso> ingressos;
  private Long lastId = 0L;

  public CadastroIngresso() {
    ingressos = new ArrayList<>();
  }

  public void criarIngresso(CriarIngressoDTO dto) {
    // TODO: if para mapear modadlidade para diferentes subclasses de Ingresso;
  }
  
}
