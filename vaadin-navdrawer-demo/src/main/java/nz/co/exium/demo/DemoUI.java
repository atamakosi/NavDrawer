package nz.co.exium.demo;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import nz.co.exium.NavDrawer;

import javax.servlet.annotation.WebServlet;

@Theme("demo")
@Title("NavDrawer Add-on Demo")
@SuppressWarnings("serial")
public class DemoUI extends UI
{

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = DemoUI.class, widgetset = "nz.co.exium.demo.DemoWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {

        final NavDrawer component = new NavDrawer();
        component.setWidth(256, Unit.PIXELS);
        component.setHeight("100%");
        Button button = new Button("Toggle");
        button.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                component.toggle();
            }
        });

        final CssLayout layout = new CssLayout();
        layout.setSizeFull();
        layout.addComponent(component);
        layout.addComponent(button);
        setContent(layout);

    }

}
