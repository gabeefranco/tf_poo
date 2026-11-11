package com.gabeefranco;
import java.math.BigDecimal;

public class IngressoVip extends Ingresso{

    public IngressoVip(String id, String vendaId, String participanteNome, String participanteCpf){
      
        this.id = id;
        this.vendaId = vendaId;
        modalidade = "VIP";
        this.participanteNome = participanteNome;
        this.participanteCpf = participanteCpf;
    }

    @Override 
    public BigDecimal calcularPreco(BigDecimal precoBase){ 
        BigDecimal precoVip = precoBase.multiply(new BigDecimal("1.5"));
        return precoVip;
    }
    public boolean permiteEntradaAntecipada(){
        return true; 
    }

}