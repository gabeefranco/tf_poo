package com.gabeefranco.entidades;

import java.time.LocalDateTime;

public record Venda(String id, String eventoId, String comprador,
                           LocalDateTime dataHora) {}

