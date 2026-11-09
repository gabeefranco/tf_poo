package com.gabeefranco;

import java.util.ArrayList;
import java.util.List;

public class CadastroEventos {
  private List<Evento> eventos;
  private Long lastId = 0L;

  public CadastroEventos() {
    eventos = new ArrayList<>();
  }

  public void criarEvento(CriarEventoDTO dto) {
    String id = "E" + ++lastId;
    Evento e = new Evento(id, dto.titulo(), dto.descricao(), dto.data(), dto.local(), dto.capacidadeMaxima(), dto.precoBase(), dto.horarioInicio());
    eventos.add(e);
  }

  public List<Evento> getEventos() {
    return eventos;
  }

  public List<Evento> consultaEventos(MetodoConsultaEventos metodo) {
    return null;
  }
  
}
