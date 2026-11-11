package com.gabeefranco.cadastros;

import java.util.ArrayList;
import java.util.List;

import com.gabeefranco.dto.CriarIngressoDTO;
import com.gabeefranco.entidades.Ingresso;
import com.gabeefranco.entidades.IngressoInteira;
import com.gabeefranco.entidades.IngressoMeia;
import com.gabeefranco.entidades.IngressoPromocional;
import com.gabeefranco.entidades.IngressoVip;

public class CadastroIngressos {
  private List<Ingresso> ingressos;
  private Long lastId = 0L;

  public CadastroIngressos() {
    ingressos = new ArrayList<>();
  }

  public void criarIngresso(CriarIngressoDTO dto) {
    String id = "I" + ++lastId;
    Ingresso i;

    switch(dto.modalidade()) {
      case "VIP":
        i = new IngressoVip(id, dto.vendaId(), dto.nomeParticipante(), dto.cpfParticipante());
        break;
      case "MEIA_ENTRADA":
        i = new IngressoMeia(id, dto.vendaId(), dto.nomeParticipante(), dto.cpfParticipante(), dto.justificativa());
        break;
      case "PROMOCIONAL":
        i = new IngressoPromocional(id, dto.vendaId(), dto.nomeParticipante(), dto.cpfParticipante(), dto.descontoPercentual());
        break;
      case "INTEIRA":
        i = new IngressoInteira(id, dto.vendaId(), dto.nomeParticipante(), dto.cpfParticipante());
        break;
      default:
        throw new IllegalArgumentException("Modalidade inválida: " + dto.modalidade());
    } 

    ingressos.add(i);
  }

  public List<Ingresso> getIngressos(String vendaId) {
    return ingressos
      .stream()
      .filter(i -> i.getVendaId().equals(vendaId))
      .toList();
  }
  
}
