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

        CssLayout innerLayout = new CssLayout();
        innerLayout.setWidth(256, Unit.PIXELS);
        innerLayout.setHeight(100, Unit.PERCENTAGE);
        Button testButton = new Button("Test button");
        innerLayout.addComponent(testButton);
        final NavDrawer component = new NavDrawer(innerLayout);

        Button button = new Button("Toggle");
        button.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                component.toggle();
            }
        });
        button.setPrimaryStyleName("margin-left: auto;\n" +
                "margin-right: auto;");
        final CssLayout layout = new CssLayout();
        layout.setSizeFull();
        layout.addComponent(component);
        layout.addComponent(button);
        setContent(layout);

    }

}
