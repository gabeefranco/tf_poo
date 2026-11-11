package com.gabeefranco;

import java.util.ArrayList;
import java.util.List;


public class CadastroVendas {
  private List<Venda> vendas;
  private Long lastId = 0L;

  public CadastroVendas() {
    vendas = new ArrayList<>();
  }
  
  public void criarVenda(CriarVendaDTO dto) {
    String id = "V" + ++lastId;  
    Venda v = new Venda(id, dto.eventoId(), dto.comprador(), dto.dataHora());
    vendas.add(v);
  }

  public List<Venda> getVendas(String eventoId) {
    return vendas.stream().filter(v -> v.eventoId().equals(eventoId)).toList();
  }
}
