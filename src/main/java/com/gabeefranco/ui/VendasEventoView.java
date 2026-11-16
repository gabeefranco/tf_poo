package com.gabeefranco.ui;

import com.gabeefranco.entidades.Evento;
import com.gabeefranco.entidades.Ingresso;
import com.gabeefranco.entidades.Venda;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/** Funcionalidades 5 e 7: vendas, ingressos, disponibilidade, arrecadação e ocupação de um evento. */
@Route(value = "vendas", layout = LayoutPrincipal.class)
@PageTitle("Vendas do evento")
public class VendasEventoView extends VerticalLayout {

    private final ComboBox<Evento> evento = new ComboBox<>("Evento");

    // Indicadores do evento
    private final Span ingressosVendidos = new Span();
    private final Span disponibilidadeGeral = new Span();
    private final Span disponibilidadePromocional = new Span();
    private final Span ocupacao = new Span();
    private final Span arrecadacao = new Span();

    private final Grid<Venda> gridVendas = new Grid<>(Venda.class, false);
    private final Grid<Ingresso> gridIngressos = new Grid<>(Ingresso.class, false);

    public VendasEventoView() {
        configurarComponentes();
        registrarOuvintes();
        montarTela();
    }

    private void configurarComponentes() {
        evento.setItemLabelGenerator(Evento::titulo);
        evento.setWidth("350px");
        // TODO: carregar todos os eventos (inclusive passados) em evento.setItems(...)

        gridVendas.addColumn(Venda::id).setHeader("Venda").setAutoWidth(true);
        gridVendas.addColumn(Venda::comprador).setHeader("Comprador").setAutoWidth(true).setSortable(true);
        gridVendas.addColumn(Venda::dataHora).setHeader("Data/hora").setAutoWidth(true).setSortable(true);
        // TODO: coluna "Valor total" (soma dos preços dos ingressos da venda).

        gridIngressos.addColumn(Ingresso::getId).setHeader("Ingresso").setAutoWidth(true);
        gridIngressos.addColumn(Ingresso::getModalidade).setHeader("Modalidade").setAutoWidth(true);
        gridIngressos.addColumn(Ingresso::getParticipanteNome).setHeader("Participante").setAutoWidth(true);
        gridIngressos.addColumn(Ingresso::getParticipanteCpf).setHeader("CPF").setAutoWidth(true);
        gridIngressos.addColumn(Ingresso::getJustificativa).setHeader("Justificativa").setAutoWidth(true);
        // TODO: coluna "Preço" (calcularPreco com o preço-base do evento) e "Entrada antecipada"
        //       (permiteEntradaAntecipada).
    }

    private void registrarOuvintes() {
        evento.addValueChangeListener(e -> atualizarEvento(e.getValue()));
        gridVendas.asSingleSelect().addValueChangeListener(e -> atualizarIngressos(e.getValue()));
    }

    private void montarTela() {
        HorizontalLayout indicadores = new HorizontalLayout(ingressosVendidos, disponibilidadeGeral,
                disponibilidadePromocional, ocupacao, arrecadacao);
        indicadores.setWrap(true);

        add(new H2("Vendas do evento"), evento, indicadores,
                new H3("Vendas"), gridVendas,
                new H3("Ingressos da venda selecionada"), gridIngressos);
    }

    private void atualizarEvento(Evento selecionado) {
        // TODO: buscar as vendas do evento e preencher os indicadores (vendidos, disponibilidade
        //       geral e promocional, percentual de ocupação e arrecadação).
        gridVendas.setItems();
        gridIngressos.setItems();
    }

    private void atualizarIngressos(Venda venda) {
        // TODO: buscar os ingressos da venda selecionada (null = nenhuma linha selecionada).
        gridIngressos.setItems();
    }
}
