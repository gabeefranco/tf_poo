package com.gabeefranco.ui;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import com.gabeefranco.entidades.Evento;

/** Funcionalidade 8: consultas gerenciais obrigatórias (uma aba para cada). */
@Route(value = "consultas", layout = LayoutPrincipal.class)
@PageTitle("Consultas gerenciais")
public class ConsultasView extends VerticalLayout {

    private final TabSheet abas = new TabSheet();

    // TODO: trocar os tipos Object/String abaixo pelos tipos reais devolvidos pelas consultas do domínio.

    // Quantidade de ingressos vendidos por evento
    private final Grid<Object> gridVendidosPorEvento = new Grid<>();
    // Arrecadação total por evento
    private final Grid<Object> gridArrecadacaoPorEvento = new Grid<>();
    // Arrecadação agrupada por modalidade
    private final Grid<Object> gridArrecadacaoPorModalidade = new Grid<>();
    // Ranking de eventos por quantidade de ingressos vendidos
    private final Grid<Object> gridRanking = new Grid<>();
    // Percentual de ocupação de cada evento
    private final Grid<Object> gridOcupacao = new Grid<>();
    // Quantidade de participantes com direito à entrada antecipada, por evento
    private final Grid<Object> gridEntradaAntecipada = new Grid<>();

    // Modalidade mais vendida em um evento (depende do evento escolhido)
    private final ComboBox<Evento> eventoModalidade = new ComboBox<>("Evento");
    private final Span modalidadeMaisVendida = new Span("Selecione um evento");

    public ConsultasView() {
        configurarComponentes();
        registrarOuvintes();
        montarTela();
    }

    private void configurarComponentes() {
        setSizeFull();
        abas.setSizeFull();

        gridVendidosPorEvento.addColumn(o -> "").setHeader("Evento");
        gridVendidosPorEvento.addColumn(o -> "").setHeader("Ingressos vendidos");

        gridArrecadacaoPorEvento.addColumn(o -> "").setHeader("Evento");
        gridArrecadacaoPorEvento.addColumn(o -> "").setHeader("Arrecadação (R$)");

        gridArrecadacaoPorModalidade.addColumn(o -> "").setHeader("Modalidade");
        gridArrecadacaoPorModalidade.addColumn(o -> "").setHeader("Arrecadação (R$)");

        gridRanking.addColumn(o -> "").setHeader("Posição");
        gridRanking.addColumn(o -> "").setHeader("Evento");
        gridRanking.addColumn(o -> "").setHeader("Ingressos vendidos");

        gridOcupacao.addColumn(o -> "").setHeader("Evento");
        gridOcupacao.addColumn(o -> "").setHeader("Ocupação (%)");

        gridEntradaAntecipada.addColumn(o -> "").setHeader("Evento");
        gridEntradaAntecipada.addColumn(o -> "").setHeader("Participantes com entrada antecipada");

        eventoModalidade.setItemLabelGenerator(Evento::titulo);
        eventoModalidade.setWidth("350px");
        // TODO: carregar os eventos em eventoModalidade.setItems(...)
    }

    private void registrarOuvintes() {
        // TODO: atualizar o conteúdo ao trocar de aba (abas.addSelectedChangeListener) para refletir
        //       vendas recém registradas.
        eventoModalidade.addValueChangeListener(e -> atualizarModalidadeMaisVendida());
    }

    private void montarTela() {
        abas.add(new Tab("Vendidos por evento"), gridVendidosPorEvento);
        abas.add(new Tab("Arrecadação por evento"), gridArrecadacaoPorEvento);
        abas.add(new Tab("Arrecadação por modalidade"), gridArrecadacaoPorModalidade);
        abas.add(new Tab("Ranking de eventos"), gridRanking);
        abas.add(new Tab("Ocupação"), gridOcupacao);
        abas.add(new Tab("Modalidade mais vendida"),
                new VerticalLayout(new HorizontalLayout(eventoModalidade), modalidadeMaisVendida));
        abas.add(new Tab("Entrada antecipada"), gridEntradaAntecipada);

        add(new H2("Consultas gerenciais"), abas);
    }

    private void atualizarModalidadeMaisVendida() {
        // TODO: consultar a modalidade mais vendida do evento selecionado.
        modalidadeMaisVendida.setText("");
    }
}
