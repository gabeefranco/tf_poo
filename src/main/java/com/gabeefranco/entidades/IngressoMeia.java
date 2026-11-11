package com.gabeefranco.entidades;
import java.math.BigDecimal;
public class IngressoMeia extends Ingresso {
    public IngressoMeia(String id, String vendaId, String participanteNome, String participanteCpf, String justificativa){
       this.id=id;
       this.vendaId=vendaId;
       modalidade="Meia";
       this.participanteNome=participanteNome;
       this.participanteCpf=participanteCpf;
       this.justificativa=justificativa;
    }
    @Override
    public BigDecimal calcularPreco(BigDecimal precoBase){
        BigDecimal div= new BigDecimal(2);
        return precoBase.divide(div);
    }
    public boolean permiteEntradaAntecipada(){
        return false;
    };
    
}
