package com.gabeefranco.ui;

import java.util.List;

import com.gabeefranco.Sistema;
import com.gabeefranco.cadastros.CadastroVendas;
import com.gabeefranco.entidades.Evento;
import com.gabeefranco.entidades.Ingresso;
import com.gabeefranco.entidades.Promocao;
import com.gabeefranco.entidades.Venda;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * Tela de um evento, aberta ao clicar em um evento na lista (rota evento/{id}).
 * Funcionalidades 1 (detalhes), 5 e 7 (vendas, ingressos, disponibilidade, arrecadação e ocupação)
 * e 8 (consultas gerenciais referentes a um evento).
 */
@Route(value = "evento", layout = LayoutPrincipal.class)
@PageTitle("Evento")
public class EventoView extends VerticalLayout implements HasUrlParameter<String> {

    private final Button voltar = new Button("Eventos", VaadinIcon.ARROW_LEFT.create());
    private final H2 titulo = new H2("Evento");

    // Detalhes do evento
    private final Span detalheDescricao = new Span();
    private final Span detalheDataLocal = new Span();
    private final Span detalheCapacidade = new Span();
    private final Span detalhePreco = new Span();
    private final Span detalhePromocao = new Span();

    private final TabSheet abas = new TabSheet();

    // Aba "Vendas": disponibilidade, vendas e ingressos
    private final Span disponibilidadeGeral = new Span();
    private final Span disponibilidadePromocional = new Span();
    private final Grid<Venda> gridVendas = new Grid<>(Venda.class, false);
    private final Grid<Ingresso> gridIngressos = new Grid<>(Ingresso.class, false);

    // Aba "Consultas gerenciais"
    // Quantidade de ingressos vendidos no evento
    private final Span ingressosVendidos = new Span();
    // Arrecadação total do evento
    private final Span arrecadacao = new Span();
    // Percentual de ocupação do evento
    private final Span ocupacao = new Span();
    // Modalidade mais vendida no evento
    private final Span modalidadeMaisVendida = new Span();
    // Quantidade de participantes com direito à entrada antecipada no evento
    private final Span entradaAntecipada = new Span();
    // Arrecadação agrupada por modalidade
    // TODO: trocar o tipo Object pelo tipo real devolvido pela consulta do domínio.
    private final Grid<Object> gridArrecadacaoPorModalidade = new Grid<>();

    public EventoView() {
        configurarComponentes();
        registrarOuvintes();
        montarTela();
    }

    private void configurarComponentes() {
        setSizeFull();
        abas.setWidthFull();
        voltar.addThemeVariants(ButtonVariant.TERTIARY);

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

        gridArrecadacaoPorModalidade.addColumn(o -> "").setHeader("Modalidade");
        gridArrecadacaoPorModalidade.addColumn(o -> "").setHeader("Arrecadação (R$)");
    }

    private void registrarOuvintes() {
        voltar.addClickListener(e -> UI.getCurrent().navigate(EventosView.class));
        gridVendas.asSingleSelect().addValueChangeListener(e -> atualizarIngressos(e.getValue()));
    }

    private void montarTela() {
        VerticalLayout detalhes = new VerticalLayout(detalheDescricao, detalheDataLocal, detalheCapacidade,
                detalhePreco, detalhePromocao);
        detalhes.setPadding(false);
        detalhes.setSpacing(false);

        HorizontalLayout disponibilidade = new HorizontalLayout(disponibilidadeGeral, disponibilidadePromocional);
        disponibilidade.setWrap(true);
        VerticalLayout vendas = new VerticalLayout(disponibilidade,
                new H3("Vendas"), gridVendas,
                new H3("Ingressos da venda selecionada"), gridIngressos);
        vendas.setPadding(false);

        HorizontalLayout indicadores = new HorizontalLayout(ingressosVendidos, arrecadacao, ocupacao,
                modalidadeMaisVendida, entradaAntecipada);
        indicadores.setWrap(true);
        VerticalLayout consultas = new VerticalLayout(indicadores,
                new H3("Arrecadação por modalidade"), gridArrecadacaoPorModalidade);
        consultas.setPadding(false);

        abas.add(new Tab("Vendas"), vendas);
        abas.add(new Tab("Consultas gerenciais"), consultas);

        add(voltar, titulo, detalhes, abas);
    }

    @Override
    public void setParameter(BeforeEvent event, String eventoId) {
        Evento evento = Sistema.getCadastroEventos().getEventoPorId(eventoId);
        if (evento == null) {
            Notification.show("Evento não encontrado", 4000, Position.TOP_CENTER);
            titulo.setText("Evento não encontrado: " + eventoId);
            return;
    }
        mostrarEvento(evento);
}

    private void mostrarEvento(Evento evento) {
        titulo.setText(evento.titulo());
        // TODO: preencher os detalhes com os dados do evento e os parâmetros da promoção
        //       (desconto e cota).
        atualizarVendas(evento);
        atualizarConsultas(evento);
    }

    private void atualizarVendas(Evento evento) {
        List<Venda> vendas = Sistema.getCadastroVendas().getVendas(evento.id());
        gridVendas.setItems(vendas);
        int vendidos = 0;
        int promocionaisVendidos = 0;
        for(Venda v: vendas){
            for(Ingresso i: Sistema.getCadastroIngressos().getIngressos(v.id())){
                vendidos ++;
                if(i.getModalidade().equalsIgnoreCase("Promocional")){
                    promocionaisVendidos++;
                }
            }
        }
        int vagas= evento.capacidade()-vendidos;
        disponibilidadeGeral.setText("Vagas disponíveis: " + vagas);
        Promocao promocao= Sistema.getCadastroPromocoes().getPromocaoPorEvento(evento.id());
        if(promocao==null){
            disponibilidadePromocional.setText("Não a disponibilidade de ingresso promocional");
        }
        else{
            int restantes = promocao.limitePromocional()- promocionaisVendidos;
            disponibilidadePromocional.setText("Ingressos promocionais disponíveis: " + restantes);
        }
    }

    private void atualizarIngressos(Venda venda) {
        // TODO: buscar os ingressos da venda selecionada (null = nenhuma linha selecionada).
        gridIngressos.setItems();
    }

    private void atualizarConsultas(Evento evento) {
        // TODO: preencher as consultas gerenciais do evento: ingressos vendidos, arrecadação total,
        //       percentual de ocupação, modalidade mais vendida, participantes com entrada antecipada
        //       e arrecadação agrupada por modalidade (gridArrecadacaoPorModalidade.setItems(...)).
        modalidadeMaisVendida.setText("");
    }
}
