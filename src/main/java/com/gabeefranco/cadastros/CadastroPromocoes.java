package com.gabeefranco.cadastros;
import java.util.ArrayList;
import java.util.List;

import com.gabeefranco.entidades.Promocao;

public class CadastroPromocoes {
    private List<Promocao> promocoes;
    public CadastroPromocoes(){
        promocoes= new ArrayList<>();
    }
    public void criarPromocao(Promocao promocao){
        promocoes.add(promocao);
    }
    public List<Promocao> getPromocoes(String eventoId){
        return promocoes.stream().filter(p -> p.eventoId().equals(eventoId)).toList();
    }
    public Promocao getPromocaoPorEvento(String eventoId){
      for(Promocao p : promocoes) {
        if(p.eventoId().equals(eventoId)) {
          return p;
        }
      }
      return null;
    }
}
