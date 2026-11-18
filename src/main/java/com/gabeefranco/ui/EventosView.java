package com.gabeefranco.ui;

import java.util.List;

import com.gabeefranco.consultas.MetodoConsultaEventos;
import com.gabeefranco.consultas.MetodoEventosFuturos;
import com.gabeefranco.consultas.MetodoEventosRealizados;
import com.gabeefranco.Sistema;
import com.gabeefranco.cadastros.CadastroEventos;
import com.gabeefranco.entidades.Evento;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = LayoutPrincipal.class)
@PageTitle("Eventos")
public class EventosView extends VerticalLayout {

    private final ComboBox<String> filtroSituacao = new ComboBox<>("Situação");
    private final TextField filtroLocal = new TextField("Local");
    private final DatePicker filtroInicio = new DatePicker("De");
    private final DatePicker filtroFim = new DatePicker("Até");

    private final Grid<Evento> grid = new Grid<>(Evento.class, false);
    private final Span totalEncontrado = new Span("0 evento(s)");

    public EventosView() {
        configurarComponentes();
        registrarOuvintes();
        montarTela();
    }

    private void configurarComponentes() {
        setSizeFull();

        filtroSituacao.setItems("Futuros", "Realizados");
        filtroSituacao.setValue("Futuros");
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
        grid.addItemClickListener(e -> UI.getCurrent().navigate(EventoView.class, e.getItem().id()));
    }

    private void montarTela() {
        HorizontalLayout filtros = new HorizontalLayout(filtroSituacao, filtroLocal, filtroInicio, filtroFim);
        filtros.setDefaultVerticalComponentAlignment(Alignment.BASELINE);

        add(new H2("Eventos"), filtros, totalEncontrado, grid);
        atualizarGrid();
    }

    private void atualizarGrid() {
        CadastroEventos cadastro = Sistema.getCadastroEventos();
        MetodoConsultaEventos metodo;
        List<Evento> encontrados;
        switch(filtroSituacao.getValue()) {
          case "Realizados":
            metodo = new MetodoEventosRealizados(filtroLocal.getValue(), filtroInicio.getValue(), filtroFim.getValue());
            encontrados = cadastro.consultaEventos(metodo);
            break;
          case "Futuros":
            metodo = new MetodoEventosFuturos(filtroLocal.getValue(), filtroInicio.getValue(), filtroFim.getValue());
            encontrados = cadastro.consultaEventos(metodo);
            break;
          default:
            encontrados = cadastro.getEventos();
        }
      
        grid.setItems(encontrados);
        totalEncontrado.setText(encontrados.size() + " evento(s)");
    }
}
