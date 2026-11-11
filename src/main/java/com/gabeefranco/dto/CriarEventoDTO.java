package com.gabeefranco.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record CriarEventoDTO(String titulo, String descricao, String local, LocalDate data, LocalTime horarioInicio,
		int capacidadeMaxima, BigDecimal precoBase) {}

