package com.gabeefranco.consultas;

import java.time.LocalDate;
import java.util.List;

import com.gabeefranco.entidades.Evento;

public class MetodoEventosRealizados implements MetodoConsultaEventos {

  private String local;
  private LocalDate de, ate;

  public MetodoEventosRealizados(String local, LocalDate de, LocalDate ate) {
    this.local = local;
    this.de = de;
    this.ate = ate;
  }

	@Override
	public List<Evento> consulta(List<Evento> dados) {
    LocalDate agora = LocalDate.now();
    return dados.stream().filter(e -> {
      boolean filtro = e.data().isBefore(agora);
      if(local != null && !local.equals("")) {
        filtro = filtro && e.local().toLowerCase().contains(local.toLowerCase());
      }
      if(de != null) {
        filtro = filtro && e.data().isAfter(de);
      }
      if(ate != null) {
        filtro = filtro && e.data().isBefore(ate);
      }

      return filtro;
    }).sorted((e1, e2) -> e1.data().compareTo(e2.data())).toList();
	}
  
}
