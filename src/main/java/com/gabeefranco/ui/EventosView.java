package com.gabeefranco.ui;

import java.util.List;

import com.gabeefranco.entidades.Evento;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/** Funcionalidades 1, 6 e 8 (eventos futuros/passados, filtro por período ou local). */
@Route(value = "", layout = LayoutPrincipal.class)
@PageTitle("Eventos")
public class EventosView extends VerticalLayout {

    // Filtros
    private final ComboBox<String> filtroSituacao = new ComboBox<>("Situação");
    private final TextField filtroLocal = new TextField("Local");
    private final DatePicker filtroInicio = new DatePicker("De");
    private final DatePicker filtroFim = new DatePicker("Até");

    // Lista
    private final Grid<Evento> grid = new Grid<>(Evento.class, false);
    private final Span totalEncontrado = new Span("0 evento(s)");

    // Detalhes do evento selecionado
    private final Span detalheTitulo = new Span("Selecione um evento");
    private final Span detalheDescricao = new Span();
    private final Span detalheDataLocal = new Span();
    private final Span detalheCapacidade = new Span();
    private final Span detalhePreco = new Span();
    private final Span detalhePromocao = new Span();
    private final Span detalheDisponibilidade = new Span();

    public EventosView() {
        configurarComponentes();
        registrarOuvintes();
        montarTela();
    }

    private void configurarComponentes() {
        setSizeFull();

        filtroSituacao.setItems("Todos", "Futuros", "Realizados");
        filtroSituacao.setValue("Todos");
        filtroLocal.setValueChangeMode(ValueChangeMode.LAZY);
        filtroLocal.setClearButtonVisible(true);
        filtroInicio.setClearButtonVisible(true);
        filtroFim.setClearButtonVisible(true);

        grid.addColumn(Evento::id).setHeader("Id").setAutoWidth(true);
        grid.addColumn(Evento::titulo).setHeader("Título").setAutoWidth(true).setSortable(true);
        grid.addColumn(Evento::data).setHeader("Data").setAutoWidth(true).setSortable(true);
        grid.addColumn(Evento::horarioInicio).setHeader("Início").setAutoWidth(true);
        grid.addColumn(Evento::local).setHeader("Local").setAutoWidth(true).setSortable(true);
        grid.addColumn(Evento::capacidade).setHeader("Capacidade").setAutoWidth(true);
        grid.addColumn(Evento::precoBase).setHeader("Preço-base").setAutoWidth(true);
        grid.setSizeFull();
    }

    private void registrarOuvintes() {
        filtroSituacao.addValueChangeListener(e -> atualizarGrid());
        filtroLocal.addValueChangeListener(e -> atualizarGrid());
        filtroInicio.addValueChangeListener(e -> {
            filtroFim.setMin(e.getValue());
            atualizarGrid();
        });
        filtroFim.addValueChangeListener(e -> atualizarGrid());
        grid.asSingleSelect().addValueChangeListener(e -> mostrarDetalhes(e.getValue()));
    }

    private void montarTela() {
        HorizontalLayout filtros = new HorizontalLayout(filtroSituacao, filtroLocal, filtroInicio, filtroFim);
        filtros.setDefaultVerticalComponentAlignment(Alignment.BASELINE);

        VerticalLayout lista = new VerticalLayout(filtros, totalEncontrado, grid);
        lista.setPadding(false);
        lista.setSizeFull();

        VerticalLayout detalhes = new VerticalLayout(new H3("Detalhes"), detalheTitulo, detalheDescricao,
                detalheDataLocal, detalheCapacidade, detalhePreco, detalhePromocao, detalheDisponibilidade);
        detalhes.setWidth("350px");

        HorizontalLayout conteudo = new HorizontalLayout(lista, detalhes);
        conteudo.setSizeFull();

        add(new H2("Eventos"), conteudo);
        atualizarGrid();
    }

    private void atualizarGrid() {
        // TODO: chamar a consulta do domínio com situação (todos/futuros/realizados),
        //       local e período (filtroInicio/filtroFim); eventos futuros ordenados por data.
        List<Evento> encontrados = List.of();
        grid.setItems(encontrados);
        totalEncontrado.setText(encontrados.size() + " evento(s)");
    }

    private void mostrarDetalhes(Evento evento) {
        if (evento == null) {
            detalheTitulo.setText("Selecione um evento");
            detalheDescricao.setText("");
            detalheDataLocal.setText("");
            detalheCapacidade.setText("");
            detalhePreco.setText("");
            detalhePromocao.setText("");
            detalheDisponibilidade.setText("");
            return;
        }
        // TODO: preencher com os dados do evento, os parâmetros da promoção
        //       (desconto e cota) e a disponibilidade geral/promocional.
        detalheTitulo.setText(evento.titulo());
    }
}
