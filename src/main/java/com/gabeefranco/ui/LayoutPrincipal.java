package com.gabeefranco.ui;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;

public class LayoutPrincipal extends AppLayout {

    public LayoutPrincipal() {
        H1 titulo = new H1("Gestão de Eventos e Ingressos");
        titulo.getStyle().set("font-size", "var(--lumo-font-size-l)").set("margin", "0");

        SideNav menu = new SideNav();
        menu.addItem(new SideNavItem("Eventos", EventosView.class, VaadinIcon.CALENDAR.create()));
        menu.addItem(new SideNavItem("Novo evento", CadastroEventoView.class, VaadinIcon.PLUS.create()));
        menu.addItem(new SideNavItem("Nova venda", VendaView.class, VaadinIcon.TICKET.create()));
        menu.addItem(new SideNavItem("Vendas do evento", VendasEventoView.class, VaadinIcon.CART.create()));
        menu.addItem(new SideNavItem("Consultas gerenciais", ConsultasView.class, VaadinIcon.CHART.create()));

        addToNavbar(new DrawerToggle(), titulo);
        addToDrawer(menu);
    }
}
