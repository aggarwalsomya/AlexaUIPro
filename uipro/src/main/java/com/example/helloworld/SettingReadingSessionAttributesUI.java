package com.example.helloworld;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * This UI is the application entry point. A UI may either represent a browser
 * window (or tab) or some part of a html page where a Vaadin application is
 * embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is
 * intended to be overridden to add component to the user interface and
 * initialize non-component functionality.
 */
@Theme("mytheme")
@PreserveOnRefresh
public class SettingReadingSessionAttributesUI extends UI {
	private static final long serialVersionUID = 1L;

	private VerticalLayout statusHolder = new VerticalLayout();
	private TextField textField = new TextField();

	@Override
	protected void init(VaadinRequest request) {
		System.out.println(request.getMethod());
		System.out.println(request.getParameter("componentName"));
		System.out.println(request.getParameter("componentValue"));
		
		statusHolder.addComponent(textField);
		statusHolder.addComponent(new Button("Reload page", new Button.ClickListener() {

			private static final long serialVersionUID = 1L;
			
			@Override
			public void buttonClick(ClickEvent event) {
				getCurrent().getSession().setAttribute("componentName", "button");
				getCurrent().getSession().setAttribute("componentValue", "Test");
				getPage().setLocation(getPage().getLocation());
			}
		}));
		setContent(statusHolder);
	}

	@Override
	protected void refresh(VaadinRequest request) {
		String compName = (String) getCurrent().getSession().getAttribute("componentName");
		String compVal = (String) getCurrent().getSession().getAttribute("componentValue");
		//String compVal = "test";
		if (compName.equalsIgnoreCase("button")) {
			appendNewComponentToUI(SettingReadingSessionAttributesUI.this, new Button(compVal));
		}
	}

	private void addComponent(Component comp) {
		statusHolder.addComponent(comp);
	}

	private static void appendNewComponentToUI(SettingReadingSessionAttributesUI ui, Component comp) {
		ui.addComponent(comp);
	}

	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = SettingReadingSessionAttributesUI.class, productionMode = false, widgetset = "com.example.uipro.UiproWidgetset")
	public static class MyUIServlet extends VaadinServlet {
		private static final long serialVersionUID = 1L;
	}
}