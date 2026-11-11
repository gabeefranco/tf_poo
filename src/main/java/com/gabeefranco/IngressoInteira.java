package com.gabeefranco;
import java.math.BigDecimal;

public class IngressoInteira extends Ingresso{

    public IngressoInteira(String id, String vendaId, String participanteNome, String participanteCpf){
        this.id = id;
        this.vendaId = vendaId;
        modalidade = "Inteira";
        this.participanteNome = participanteNome;
        this.participanteCpf = participanteCpf;
    }

    @Override 
    public BigDecimal calcularPreco(BigDecimal precoBase){ 
        return precoBase;
    }
    public boolean permiteEntradaAntecipada(){
        return false; 
    }

}