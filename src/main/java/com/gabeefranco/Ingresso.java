package com.gabeefranco;

import java.math.BigDecimal;

public abstract class Ingresso {
  protected String id;
  protected String vendaId;
  protected String modalidade;
  protected String participanteNome;
  protected String participanteCpf;
  protected String justificativa;


  public abstract BigDecimal calcularPreco(BigDecimal precoBase);
  public abstract boolean permiteEntradaAntecipada();

  public String getId() {
    return id;
  }
  public String getVendaId() {
    return vendaId;
  }
  public String getModalidade() {
    return modalidade;
  }
  public String getParticipanteNome() {
    return participanteNome;
  }
  public String getParticipanteCpf() {
    return participanteCpf;
  }
  public String getJustificativa() {
    return justificativa;
  }
}
