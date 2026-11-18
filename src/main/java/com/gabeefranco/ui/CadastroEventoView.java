package com.gabeefranco.ui;

import java.math.BigDecimal;

import com.gabeefranco.Sistema;
import com.gabeefranco.dto.CriarEventoDTO;
import com.gabeefranco.entidades.Evento;
import com.gabeefranco.entidades.Promocao;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "novo-evento", layout = LayoutPrincipal.class)
@PageTitle("Novo evento")
public class CadastroEventoView extends VerticalLayout {

    private final TextField titulo = new TextField("Título");
    private final TextArea descricao = new TextArea("Descrição");
    private final DatePicker data = new DatePicker("Data");
    private final TimePicker horarioInicio = new TimePicker("Horário de início");
    private final TextField local = new TextField("Local");
    private final IntegerField capacidade = new IntegerField("Capacidade máxima");
    private final BigDecimalField precoBase = new BigDecimalField("Preço-base (R$)");

    private final IntegerField descontoPromocional = new IntegerField("Desconto promocional (%)");
    private final IntegerField limitePromocional = new IntegerField("Limite de ingressos promocionais");

    private final Button salvar = new Button("Cadastrar evento");
    private final Button limpar = new Button("Limpar");

    public CadastroEventoView() {
        configurarComponentes();
        registrarOuvintes();
        montarTela();
    }

    private void configurarComponentes() {
        capacidade.setMin(1);
        capacidade.setStepButtonsVisible(true);
        descontoPromocional.setMin(0);
        descontoPromocional.setMax(100);
        descontoPromocional.setStepButtonsVisible(true);
        limitePromocional.setMin(0);
        limitePromocional.setStepButtonsVisible(true);
        salvar.addThemeVariants(ButtonVariant.PRIMARY);
    }

    private void registrarOuvintes() {
        salvar.addClickListener(e -> salvar());
        limpar.addClickListener(e -> limpar());
    }

    private void montarTela() {
        FormLayout evento = new FormLayout(titulo, local, data, horarioInicio, capacidade, precoBase);
        FormLayout promocao = new FormLayout(descontoPromocional, limitePromocional);
        descricao.setWidthFull();

        VerticalLayout formulario = new VerticalLayout(new H3("Dados do evento"), evento, descricao,
                new H3("Modalidade promocional"), promocao, new HorizontalLayout(limpar, salvar));
        formulario.setMaxWidth("800px");

        add(new H2("Novo evento"), formulario);
    }

    private void salvar() {
        if(titulo.getValue() == null || titulo.getValue().strip().equals("")) {
          Notification.show("Título não informado", 4000, Position.TOP_CENTER);
          return;
        }
        if(local.getValue() == null || local.getValue().strip().equals("")) {
          Notification.show("Local não informado", 4000, Position.TOP_CENTER);
          return;
        }
        if(data.getValue() == null) {
          Notification.show("Data não informada", 4000, Position.TOP_CENTER);
          return;
        }
        if(horarioInicio.getValue() == null) {
          Notification.show("Horario de inicio não informado", 4000, Position.TOP_CENTER);
          return;
        }
        if(capacidade.getValue() == null) {
          Notification.show("Capacidade máxima não informada", 4000, Position.TOP_CENTER);
          return;
        }
        if(precoBase.getValue() == null || precoBase.getValue().compareTo(BigDecimal.ZERO) <= 0) {
          Notification.show("Preço-base não informado", 4000, Position.TOP_CENTER);
          return;
        }

        CriarEventoDTO dto = new CriarEventoDTO(titulo.getValue(), descricao.getValue(), local.getValue(), data.getValue(), horarioInicio.getValue(), capacidade.getValue(), precoBase.getValue());
        Evento e = Sistema.getCadastroEventos().criarEvento(dto);
        
        if(descontoPromocional.getValue() != null && descontoPromocional.getValue() != 0
            && descontoPromocional.getValue() < 100 && limitePromocional.getValue() != null
            && limitePromocional.getValue() != 0) {
          Promocao p = new Promocao(e.id(), descontoPromocional.getValue(), limitePromocional.getValue());
          Sistema.getCadastroPromocoes().criarPromocao(p);   
           
        }

        // TODO: navegar para tela deste evento criado, quando EventoView estiver finalizada  
    }

    private void limpar() {
        titulo.clear();
        descricao.clear();
        data.clear();
        horarioInicio.clear();
        local.clear();
        capacidade.clear();
        precoBase.clear();
        descontoPromocional.clear();
        limitePromocional.clear();
        titulo.focus();
    }
}
