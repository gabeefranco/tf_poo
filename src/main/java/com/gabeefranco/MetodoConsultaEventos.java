package com.gabeefranco;

import java.util.List;

import com.gabeefranco.entidades.Evento;

public interface MetodoConsultaEventos {
  List<Evento> consulta(List<Evento> dados);
}
