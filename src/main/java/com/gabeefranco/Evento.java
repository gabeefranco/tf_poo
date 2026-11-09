package com.gabeefranco;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;


public record Evento(String id, String titulo, String descricao,
                            LocalDate data, String local, int capacidade,
                            BigDecimal precoBase, LocalTime horarioInicio) { }
