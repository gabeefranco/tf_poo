package com.gabeefranco.ui;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/** Funcionalidade 8: ranking de eventos por quantidade de ingressos vendidos. */
@Route(value = "ranking", layout = LayoutPrincipal.class)
@PageTitle("Ranking de eventos")
public class RankingEventosView extends VerticalLayout {

    // TODO: trocar o tipo Object pelo tipo real devolvido pela consulta do domínio.
    private final Grid<Object> gridRanking = new Grid<>();

    public RankingEventosView() {
        configurarComponentes();
        montarTela();
    }

    private void configurarComponentes() {
        setSizeFull();

        gridRanking.addColumn(o -> "").setHeader("Posição");
        gridRanking.addColumn(o -> "").setHeader("Evento");
        gridRanking.addColumn(o -> "").setHeader("Ingressos vendidos");
        gridRanking.setSizeFull();
    }

    private void montarTela() {
        add(new H2("Ranking de eventos"), gridRanking);
        atualizarRanking();
    }

    private void atualizarRanking() {
        // TODO: consultar os eventos ordenados pela quantidade de ingressos vendidos (decrescente)
        //       e preencher gridRanking.setItems(...).
        gridRanking.setItems();
    }
}
