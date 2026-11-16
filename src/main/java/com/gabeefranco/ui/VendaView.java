package com.gabeefranco.ui;

import java.util.ArrayList;
import java.util.List;

import com.gabeefranco.dto.CriarIngressoDTO;
import com.gabeefranco.entidades.Evento;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/** Funcionalidades 3 e 4: venda com vários ingressos (cesta), validada como um todo. */
@Route(value = "nova-venda", layout = LayoutPrincipal.class)
@PageTitle("Nova venda")
public class VendaView extends VerticalLayout {

    // Estado da tela: ingressos escolhidos, ainda não registrados
    private final List<CriarIngressoDTO> cesta = new ArrayList<>();

    // Cabeçalho da venda
    private final ComboBox<Evento> evento = new ComboBox<>("Evento");
    private final TextField comprador = new TextField("Nome do comprador");
    private final Span infoEvento = new Span();

    // Formulário de um ingresso
    private final ComboBox<String> modalidade = new ComboBox<>("Modalidade");
    private final TextField participanteNome = new TextField("Nome do participante");
    private final TextField participanteCpf = new TextField("CPF (11 dígitos)");
    private final TextField justificativa = new TextField("Justificativa (meia-entrada)");
    private final Button incluir = new Button("Incluir ingresso", VaadinIcon.PLUS.create());

    // Cesta e resumo
    private final Grid<CriarIngressoDTO> gridCesta = new Grid<>(CriarIngressoDTO.class, false);
    private final Span resumo = new Span("Nenhum ingresso na venda");
    private final Button confirmar = new Button("Confirmar venda");
    private final Button descartar = new Button("Descartar tudo");

    public VendaView() {
        configurarComponentes();
        registrarOuvintes();
        montarTela();
    }

    private void configurarComponentes() {
        evento.setItemLabelGenerator(Evento::titulo);
        evento.setWidth("350px");
        // TODO: carregar os eventos disponíveis para venda (futuros) em evento.setItems(...)

        modalidade.setItems("INTEIRA", "MEIA_ENTRADA", "VIP", "PROMOCIONAL");
        participanteCpf.setMaxLength(11);
        participanteCpf.setAllowedCharPattern("[0-9]");
        justificativa.setVisible(false);

        gridCesta.addColumn(CriarIngressoDTO::modalidade).setHeader("Modalidade").setAutoWidth(true);
        gridCesta.addColumn(CriarIngressoDTO::nomeParticipante).setHeader("Participante").setAutoWidth(true);
        gridCesta.addColumn(CriarIngressoDTO::cpfParticipante).setHeader("CPF").setAutoWidth(true);
        gridCesta.addColumn(CriarIngressoDTO::justificativa).setHeader("Justificativa").setAutoWidth(true);
        // TODO: coluna "Preço" com o preço calculado polimorficamente (Ingresso.calcularPreco).
        gridCesta.addComponentColumn(item -> {
            Button remover = new Button(VaadinIcon.TRASH.create(), e -> remover(item));
            remover.addThemeVariants(ButtonVariant.SMALL, ButtonVariant.TERTIARY, ButtonVariant.ERROR);
            return remover;
        }).setHeader("Remover");

        incluir.setEnabled(false);
        confirmar.addThemeVariants(ButtonVariant.PRIMARY);
        confirmar.setEnabled(false);
        descartar.setEnabled(false);
    }

    private void registrarOuvintes() {
        evento.addValueChangeListener(e -> mostrarInfoEvento(e.getValue()));
        modalidade.addValueChangeListener(e -> justificativa.setVisible("MEIA_ENTRADA".equals(e.getValue())));
        incluir.addClickListener(e -> incluir());
        confirmar.addClickListener(e -> pedirConfirmacao());
        descartar.addClickListener(e -> descartar());
        // TODO: habilitar/desabilitar "incluir", "confirmar" e "descartar" conforme os campos e a cesta.
    }

    private void montarTela() {
        HorizontalLayout cabecalho = new HorizontalLayout(evento, comprador);
        cabecalho.setDefaultVerticalComponentAlignment(Alignment.BASELINE);

        HorizontalLayout formIngresso = new HorizontalLayout(modalidade, participanteNome, participanteCpf,
                justificativa, incluir);
        formIngresso.setDefaultVerticalComponentAlignment(Alignment.BASELINE);

        add(new H2("Nova venda"), cabecalho, infoEvento,
                new H3("Ingressos"), formIngresso,
                gridCesta, resumo, new HorizontalLayout(descartar, confirmar));
    }

    private void mostrarInfoEvento(Evento selecionado) {
        // TODO: mostrar disponibilidade geral e promocional do evento (e avisar se for evento passado).
        infoEvento.setText("");
    }

    private void incluir() {
        // TODO: ler os campos, montar o CriarIngressoDTO, adicionar à cesta (sem chamar o domínio)
        //       e validar CPF repetido na cesta; depois atualizarTela().
        atualizarTela();
    }

    private void remover(CriarIngressoDTO item) {
        cesta.remove(item);
        atualizarTela();
    }

    private void descartar() {
        cesta.clear();
        atualizarTela();
    }

    private void pedirConfirmacao() {
        Dialog dialogo = new Dialog();
        dialogo.setHeaderTitle("Confirmar venda");
        // TODO: mostrar comprador, quantidade de ingressos e valor total.
        dialogo.add(new Paragraph("Resumo da venda..."));
        Button sim = new Button("Confirmar", e -> {
            dialogo.close();
            registrar();
        });
        sim.addThemeVariants(ButtonVariant.PRIMARY);
        Button nao = new Button("Voltar", e -> dialogo.close());
        dialogo.getFooter().add(nao, sim);
        dialogo.open();
    }

    private void registrar() {
        // TODO: chamar o domínio (valida tudo ou nada); se der certo, mostrar Notification de sucesso
        //       e limpar a cesta; se der erro (capacidade, cota promocional, evento passado...),
        //       mostrar Notification de erro e manter a cesta.
        atualizarTela();
    }

    private void atualizarTela() {
        gridCesta.setItems(cesta);
        // TODO: recalcular o resumo (quantidade e valor total) e o estado dos botões.
        resumo.setText(cesta.size() + " ingresso(s) na venda");
    }
}
