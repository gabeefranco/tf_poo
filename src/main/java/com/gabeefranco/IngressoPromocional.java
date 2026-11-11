package com.gabeefranco;
import java.math.BigDecimal;
public class IngressoPromocional extends Ingresso {
    private int descontopercentual;
    public IngressoPromocional(String id, String vendaId, String participanteNome, String participanteCpf, int descontopercentual){
        this.id = id;
        this.vendaId = vendaId;
        modalidade = "Promocional";
        this.participanteNome = participanteNome;
        this.participanteCpf = participanteCpf;
        this.descontopercentual= descontopercentual;
    }
    @Override 
    public BigDecimal calcularPreco(BigDecimal precoBase){
        BigDecimal precopromocional = precoBase.multiply(new BigDecimal((1-(descontopercentual/100))));
        return precopromocional;
    }
    public boolean permiteEntradaAntecipada(){
        return false; 
    }
}
