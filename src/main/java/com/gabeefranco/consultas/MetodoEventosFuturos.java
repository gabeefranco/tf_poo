package com.gabeefranco.consultas;

import java.time.LocalDate;
import java.util.List;

import com.gabeefranco.entidades.Evento;

public class MetodoEventosFuturos implements MetodoConsultaEventos {

	@Override
	public List<Evento> consulta(List<Evento> dados) {
    LocalDate agora = LocalDate.now();
    return dados.stream().filter(e -> e.data().isAfter(agora)).toList();
	}
  
  
}
